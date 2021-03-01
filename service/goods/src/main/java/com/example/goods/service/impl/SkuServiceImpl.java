package com.example.goods.service.impl;

import com.example.exception.CRUDException;
import com.example.goods.mapper.*;
import com.example.goods.service.SkuService;
import com.example.goodsApi.domain.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.util.*;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:41
 * @since 1.0.0
 */
@Service
@Transactional(rollbackFor = CRUDException.class)
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpecMapper specMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @param spec "t1:e、e、e,t2:e、e、e"
     * @param firstSkuList 初始skuList
     * @return java.util.List<com.example.goodsApi.domain.Sku> 经过 规格筛选 后的skuList
     * @description TODO 将初步经过 name、status 筛选的 firstSkuList 再次用规格过滤
     * 实现思路:
     * 1. 将spec解析为 title:["title":"param","title":"param"] 的格式
     * 2. 每个商品的参数, 都遍历map, 检测是否拥有 list 中的一个规格
     * 3. 如果拥有, 计数
     * 如果 拥有的规格的数量 等于 条件规格的数量, 保持不变。
     * 如果 不等于, 删除, 下标 -1
     * @author gong_da_kai
     * @date 2021/2/17 21:18
     * @since 1.0.0
     */
    private List<Sku> filterSpec(List<Sku> firstSkuList, String spec) {
        // 如果没有规格, 直接返回初始skuList
        if (spec == null || "".equals(spec)) return firstSkuList;

        // 解析spec 为 LinkedMultiValueMap
        String[] specList = spec.split(",");
        Map<String, List<String>> map = new LinkedMultiValueMap<>();
        for (String s : specList) {
            String[] part = s.split(":");
            String title = part[0];
            String[] param = part[1].split("、");

            List<String> list = new ArrayList<>(param.length);
            for (int i = 0; i < param.length; i++) {
                list.add("\"" + title + "\":\"" + param[i] + "\"");
            }
            map.put(title, list);
        }

        // 遍历skuList
        int specNum = specList.length;
        for (int i = 0; i < firstSkuList.size(); i++) {
            Sku sku = firstSkuList.get(i);
            String skuSpec = sku.getSpec();
            int hasCount = 0;

            Set<String> keys = map.keySet();
            for (String key : keys) {
                List<String> list = map.get(key);

                for (String param : list) {
                    if (skuSpec.contains(param)) {
                        hasCount++;
                        break;
                    }
                }
            }
            // 去除不符合条件的sku
            if (hasCount != specNum) {
                firstSkuList.remove(i);
                // 后边的元素会前移一位
                i--;
            }
        }

        //System.err.println("数量-------" + firstSkuList.size());
        return firstSkuList;
    }

    /**
     * 生成查询到的 skuList 对应的 导航栏选项, 利用 HashSet 无序不可重复 的特点, 去重
     * 分类/规格
     * 1.有多种二级分类时:
     * (1) 查询每种二级分类对应的 skuList中含有的 三级分类列表, 以Map(String, List(Category))形式存储
     * K -> category2.name V -> List(Category3)
     * 2.只有一种二级分类时:
     * (1) 有多种三级分类时
     * 以Map(String, List(Category))形式存储
     * K -> category2.name V -> List(Category3)
     * (2) 只有一种三级分类时
     * 根据 templateId 查询规格(spec)
     * <p>
     * 品牌
     * 根据所有 spu.brandId 查询所有品牌
     *
     * @param pageNum
     * @param pageSize
     * @param skuList
     * @return 结果集
     */
    private Map<String, Object> getSearchOption(List<Sku> skuList, Integer pageNum, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();

        if (skuList.size() > 0) {
            // 根据去重后的spuIds查询出对应的Spu
            Set<Long> spuIds = new HashSet<>();
            for (Sku sku : skuList) {
                spuIds.add(sku.getSpuId());

                // 将id转换为String, 防止在前端出现数据不精准问题
                sku.setStringId(String.valueOf(sku.getId()));
            }

            List<Spu> spuList = spuMapper.selectByIds(spuIds);

            // 取出并去重Spu的 brandId 和 Category2Id
            Set<Integer> brandIds = new HashSet<>();
            Set<Integer> category2Ids = new HashSet<>();
            for (Spu spu : spuList) {
                brandIds.add(spu.getBrandId());
                category2Ids.add(spu.getCategory2Id());
            }

            // 根据brandIds取出所有相关品牌
            if (brandIds.size() > 1) {
                List<Brand> brandList = brandMapper.selectByIds(brandIds);
                result.put("brandList", brandList);
            }

            if (category2Ids.size() > 1) {

                // 二级分类数量大于1时, 找出所有对应的三级分类的spu, 二级分类id为K, 三级分类ids为V, 存入map中
                Map<Integer, Set<Integer>> categoryIdMap = new HashMap<>();
                for (Integer category2Id : category2Ids) {

                    Set<Integer> category3Ids = new HashSet<>();
                    for (Spu spu : spuList) {
                        if (spu.getCategory2Id().equals(category2Id)) {

                            category3Ids.add(spu.getCategory3Id());

                        }
                    }

                    categoryIdMap.put(category2Id, category3Ids);

                }

                Map<String, List<Category>> categoryMap = convertCategoryIdMap(categoryIdMap);
                result.put("categoryMap", categoryMap);

            } else if (category2Ids.size() == 1) {

                // 二级分类数量等于1时, 找出所有对应的三级分类, 检查三级分类的数量, 同一个二级分类, category2Id只有一个
                Set<Integer> category3Ids = new HashSet<>();
                for (Spu spu : spuList) {
                    category3Ids.add(spu.getCategory3Id());
                }

                if (category3Ids.size() > 1) {
                    // 三级分类数量大于1时, 二级分类id为K, 三级分类ids为V, 存入map中
                    Map<Integer, Set<Integer>> categoryIdMap = new HashMap<>();
                    for (Integer category2Id : category2Ids) {
                        categoryIdMap.put(category2Id, category3Ids);
                    }

                    Map<String, List<Category>> categoryMap = convertCategoryIdMap(categoryIdMap);
                    result.put("categoryMap", categoryMap);

                } else if (category3Ids.size() == 1) {

                    // 三级分类数量等于1时, 根据templateId查询规格, 同一个三级分类, templateId只有一个
                    Integer templateId = spuList.get(0).getTemplateId();
                    List<Spec> specList = specMapper.selectByTemplateId(templateId);
                    result.put("specList", specList);

                }

            }

        }

        Integer startIndex = pageNum * pageSize - pageSize;
        Integer endIndex = startIndex + pageSize;

        //防止sku数量少于endIndex时, 出现下标越界异常
        if (endIndex > skuList.size()) endIndex = skuList.size();

        List<Sku> limitSkuList = skuList.subList(startIndex, endIndex);
        PageInfo<Sku> pageInfo = new PageInfo<>(limitSkuList);
        // 手动设置pageInfo数据, 没有使用startPage, 数据不正确
        pageInfo.setTotal(skuList.size());
        pageInfo.setPageNum(pageNum);
        pageInfo.setSize(pageSize);
        pageInfo.setStartRow(startIndex);
        pageInfo.setEndRow(endIndex);

        System.err.println("页码--" + pageNum + "  条数--" + pageSize);
        System.err.println("起始位置--" + startIndex + "  结束位置--" + endIndex);
        System.err.println("查询出的商品总数量---" + skuList.size());
        result.put("pageInfo", pageInfo);

        return result;
    }

    /**
     * 将categoryIdMap 转换成 categoryMap
     *
     * @param categoryIdMap K category2Id, V List(category3Id)
     * @return Map<String, List < Category>>, K -> 二级分类.name, V -> List(Category)
     */
    private Map<String, List<Category>> convertCategoryIdMap(Map<Integer, Set<Integer>> categoryIdMap) {

        Map<String, List<Category>> categoryMap = new HashMap<>();
        Set<Integer> keys = categoryIdMap.keySet();
        for (Integer key : keys) {
            Category category2 = categoryMapper.selectByPrimaryKey(key);

            Set<Integer> category3Ids = categoryIdMap.get(key);

            List<Category> category3List = categoryMapper.selectByIds(category3Ids);

            categoryMap.put(category2.getName(), category3List);
        }

        return categoryMap;
    }
    @Override
    public Map<String, Object> search(Map<String, Object> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");


        String name = (String) params.get("name");
        if (name != null && !"".equals(name)) {
            String[] nameArray = name.split(",");
            params.put("name", nameArray);
        }

        List<Sku> firstSkuList = skuMapper.search(params);

        List<Sku> skuList = filterSpec(firstSkuList, (String)params.get("spec"));

        return getSearchOption(skuList, pageNum, pageSize);
    }




    @Override
    public Sku selectById(Long skuId) {
        return skuMapper.selectByPrimaryKey(skuId);
    }

    @Override
    public void sale(Long skuId, Integer num) throws CRUDException {
        if (skuMapper.sale(skuId, num) != 1) throw new CRUDException("商品库存不足");
    }


}
