package com.example.goods.service.impl;

import com.example.exception.CRUDException;
import com.example.goods.mapper.BrandMapper;
import com.example.goods.mapper.CategoryMapper;
import com.example.goods.mapper.SkuMapper;
import com.example.goods.mapper.SpuMapper;
import com.example.goods.service.SpuService;
import com.example.goodsApi.domain.*;
import com.example.util.IdWorker;
import com.example.util.UUIDUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/4 21:02
 * @since 1.0.0
 */
@Service
@Transactional(rollbackFor = CRUDException.class)
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void add(Goods goods) throws CRUDException {

        Spu spu = goods.getSpu();
        Long spuId = idWorker.nextId();

        spu.setId(spuId);
        String sn = spu.getSn();
        if (sn == null || "".equals(sn)){
            spu.setSn(UUIDUtil.getUUID());
        }
        if (spuMapper.insertSelective(spu) != 1) throw new CRUDException("添加商品失败，请重试");


        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        if (category == null) throw new CRUDException("添加商品失败，原因：类型不存在");

        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        if (brand == null) throw new CRUDException("添加商品失败，原因：品牌不存在");


        List<Sku> skuList = goods.getSkuList();
        Date date = new Date();
        for (Sku sku : skuList) {
            Long skuId = idWorker.nextId();

            sku.setId(skuId);
            sku.setSpuId(spuId);
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setCategoryId(spu.getCategory3Id());
            sku.setCategoryName(category.getName());
            sku.setBrandName(brand.getName());

            String spec = sku.getSpec();
            String fullName = spu.getName() + " ";
            if (spec != null && !"".equals(spec)) {

                try {

                    Map<String, String> map = objectMapper.readValue(spec, Map.class);
                    Set<String> keys = map.keySet();
                    for (String key : keys) {
                        fullName += key + map.get(key);
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new CRUDException("商品的规格json参数转换异常");
                }
            }
            sku.setName(fullName);

            if (skuMapper.insertSelective(sku) != 1) throw new CRUDException("商品信息添加失败");
                //throw new CRUDException(flag ? "商品信息添加失败":"商品信息修改失败");
        }

    }

    @Override
    public PageInfo<Map<String, Object>> pageList(Map param) {
        //System.err.println(param.toString());

        Integer pageNum = (int)param.get("pageNum");
        Integer pageSize = (int)param.get("pageSize");

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> l = spuMapper.selectSelective(param);

        for (Map<String, Object> spu : l) {
            Long id = (Long)spu.get("id");
            spu.put("id", id.toString());
        }

        return new PageInfo<>(l);
    }

    @Override
    public void audit(Long[] id, String status) throws CRUDException {
        Spu spu = new Spu();
        for (Long i : id) {
            spu.setId(i);
            spu.setStatus(status);
            if(spuMapper.updateByPrimaryKeySelective(spu) != 1){
                throw new CRUDException("审核状态更改失败，请重试");
            }
        }


    }

}
