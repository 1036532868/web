package com.example.goods.service.impl;

import com.example.goods.mapper.*;
import com.example.goods.service.SkuService;
import com.example.goodsApi.domain.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:41
 * @since 1.0.0
 */
@Service
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

    @Override
    public Map<String, Object> searchByName(Map<String, Object> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");


        List<Sku> firstSkuList = skuMapper.searchByName(params);

        List<Sku> skuList = filterSkuList(params, firstSkuList);

        return getSearchOption(pageNum, pageSize, skuList);
    }

    /**
    * @description TODO 将初步经过 name、status 筛选的 firstSkuList 再次用规格过滤
    * @param params
    * @param firstSkuList 初始skuList
    * @return java.util.List<com.example.goodsApi.domain.Sku> 经过 规格筛选 后的skuList
    * @author gong_da_kai
    * @date 2021/2/17 21:18
    * @since 1.0.0
    */
    private List<Sku> filterSkuList(Map<String, Object> params, List<Sku> firstSkuList) {
        String spec = (String) params.get("spec");

        List<Sku> skuList = new ArrayList<>();
        if (spec != null && spec != ""){

            String[] specList = spec.split(",");

            for (Sku sku : firstSkuList) {
                String skuSpec = sku.getSpec();

                for (String specItem : specList){
                    if (skuSpec.contains(specItem)){
                        skuList.add(sku);
                    }
                }
            }

        }
        return skuList;
    }


    @Override
    public Map<String, Object> searchByCategoryId(Map<String, Object> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");

        List<Sku> skuList = skuMapper.searchByCategoryId(params);

        return getSearchOption(pageNum, pageSize, skuList);

    }

    /**
     * 生成查询到的 skuList 对应的 导航栏选项, 利用 HashSet 无序不可重复 的特点, 去重
     * 分类/规格
     *      1.有多种二级分类时:
     *          (1) 查询每种二级分类对应的 skuList中含有的 三级分类列表, 以Map(String, List(Category))形式存储
     *              K -> category2.name V -> List(Category3)
     *      2.只有一种二级分类时:
     *          (1) 有多种三级分类时
     *              以Map(String, List(Category))形式存储
     *              K -> category2.name V -> List(Category3)
     *          (2) 只有一种三级分类时
     *              根据 templateId 查询规格(spec)
     *
     * 品牌
     *      根据所有 spu.brandId 查询所有品牌
     *
     * @param pageNum
     * @param pageSize
     * @param skuList
     * @return 结果集
     */
    private Map<String, Object> getSearchOption(Integer pageNum, Integer pageSize, List<Sku> skuList) {
        Map<String, Object> result = new HashMap<>();

        if (skuList.size() > 0) {
            //根据去重后的skuIds查询出对应的Spu
            Set<Long> spuIds = new HashSet<>();
            for (Sku sku : skuList) {
                spuIds.add(sku.getSpuId());
            }

            List<Spu> spuList = spuMapper.selectByIds(spuIds);

            //取出并去重Spu的 brandId 和 Category2Id
            Set<Integer> brandIds = new HashSet<>();
            Set<Integer> category2Ids = new HashSet<>();
            for (Spu spu : spuList) {
                brandIds.add(spu.getBrandId());
                category2Ids.add(spu.getCategory2Id());
            }

            //根据brandIds取出所有相关品牌
            List<Brand> brandList = brandMapper.selectByIds(brandIds);
            result.put("brandList", brandList);

            if (category2Ids.size() > 1){

                //二级分类数量大于1时, 找出所有对应的三级分类的spu, 二级分类id为K, 三级分类ids为V, 存入map中
                Map<Integer, Set<Integer>> categoryIdMap = new HashMap<>();
                for (Integer category2Id : category2Ids) {

                    Set<Integer> category3Ids = new HashSet<>();
                    for (Spu spu : spuList) {
                        if (spu.getCategory2Id().equals(category2Id)){

                            category3Ids.add(spu.getCategory3Id());

                        }
                    }

                    categoryIdMap.put(category2Id, category3Ids);

                }

                Map<String, List<Category>> categoryMap = convertCategoryIdMap(categoryIdMap);
                result.put("categoryMap", categoryMap);

            } else if (category2Ids.size() == 1){

                //二级分类数量等于1时, 找出所有对应的三级分类, 检查三级分类的数量, 同一个二级分类, category2Id只有一个
                Set<Integer> category3Ids = new HashSet<>();
                for (Spu spu : spuList) {
                    category3Ids.add(spu.getCategory3Id());
                }

                if (category3Ids.size() > 1){
                    //三级分类数量大于1时, 二级分类id为K, 三级分类ids为V, 存入map中
                    Map<Integer, Set<Integer>> categoryIdMap = new HashMap<>();
                    for (Integer category2Id : category2Ids) {
                        categoryIdMap.put(category2Id, category3Ids);
                    }

                    Map<String, List<Category>> categoryMap = convertCategoryIdMap(categoryIdMap);
                    result.put("categoryMap", categoryMap);

                } else if (category3Ids.size() == 1){

                    //三级分类数量等于1时, 根据templateId查询规格, 同一个三级分类, templateId只有一个
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
        result.put("pageInfo", pageInfo);

        return result;
    }

    /**
     * 将categoryIdMap 转换成 categoryMap
     * @param categoryIdMap K category2Id, V List(category3Id)
     * @return Map<String, List<Category>>, K -> 二级分类.name, V -> List(Category)
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

}
