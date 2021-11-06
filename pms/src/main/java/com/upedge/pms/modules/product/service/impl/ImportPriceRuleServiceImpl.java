package com.upedge.pms.modules.product.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.dao.ImportPriceCentsDao;
import com.upedge.pms.modules.product.dao.ImportPriceRuleDao;
import com.upedge.pms.modules.product.entity.ImportPriceCents;
import com.upedge.pms.modules.product.entity.ImportPriceRule;
import com.upedge.pms.modules.product.request.ImportPriceRuleListRequest;
import com.upedge.pms.modules.product.response.ImportPriceRuleListResponse;
import com.upedge.pms.modules.product.response.ImportPriceRuleUpdateResponse;
import com.upedge.pms.modules.product.service.ImportPriceRuleService;
import com.upedge.pms.modules.product.vo.PriceRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ImportPriceRuleServiceImpl implements ImportPriceRuleService {

    @Autowired
    private ImportPriceRuleDao importPriceRuleDao;

    @Autowired
    ImportPriceCentsDao importPriceCentsDao;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ImportPriceRule record = new ImportPriceRule();
        record.setId(id);
        return importPriceRuleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ImportPriceRule record) {
        return importPriceRuleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ImportPriceRule record) {
        return importPriceRuleDao.insert(record);
    }

    @Override
    public ImportPriceRuleListResponse selectPriceRule() {

        Session session = UserUtil.getSession(redisTemplate);
        ImportPriceRuleListRequest request = new ImportPriceRuleListRequest();
        ImportPriceRule priceRule = new ImportPriceRule();
        priceRule.setCustomerId(session.getCustomerId());
        request.setT(priceRule);

        List<ImportPriceRule> priceRules = importPriceRuleDao.select(request);

        ImportPriceCents priceCents = importPriceCentsDao.selectByCustomerId(session.getCustomerId());

        PriceRuleVo vo = new PriceRuleVo();
        vo.setCents(priceCents);
        vo.setRules(priceRules);

        return new ImportPriceRuleListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,vo);
    }

    @Transactional
    @Override
    public ImportPriceRuleUpdateResponse updatePriceRule(JSONObject jsonObject) {

        Session session = UserUtil.getSession(redisTemplate);

        //删除之前的数据
        importPriceRuleDao.deleteByCustomerId(session.getCustomerId());
        importPriceCentsDao.deleteByCustomerId(session.getCustomerId());

        Date date = new Date();

        Boolean ck1 = (Boolean) jsonObject.get("ck1");
        Boolean ck2 = (Boolean) jsonObject.get("ck2");
        Boolean ck3 = (Boolean) jsonObject.get("ck3");
        Boolean ck4 = (Boolean) jsonObject.get("ck4");
        Integer ck1Price = objectToInteger(jsonObject.get("ck1Price"));
        Integer ck2Price = objectToInteger(jsonObject.get("ck2Price"));
        JSONArray jsonArray =  jsonObject.getJSONArray("rs");
        List<ImportPriceRule> importPriceRuleList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray res = jsonArray.getJSONArray(i);
            ImportPriceRule importPriceRule = new ImportPriceRule();
            if (res.size() > 4) {
                importPriceRule.setRangeStart(new BigDecimal(res.get(0).toString()));
                importPriceRule.setRangeEnd(new BigDecimal(res.get(1).toString()));
                importPriceRule.setMarkupVal(objectToBigDecimal(res.get(2)));
                importPriceRule.setMarkupType(objectToInteger(res.get(3)));
                importPriceRule.setCompareMarkupVal(objectToBigDecimal(res.get(4)));
                importPriceRule.setCompareMarkupType(objectToInteger(res.get(5)));
            } else {
                importPriceRule.setMarkupVal(objectToBigDecimal(res.get(0)));
                importPriceRule.setMarkupType(objectToInteger(res.get(1)));
                importPriceRule.setCompareMarkupVal(objectToBigDecimal(res.get(2)));
                importPriceRule.setCompareMarkupType(objectToInteger(res.get(3)));
            }
            importPriceRule.setCustomerId(session.getCustomerId());
            importPriceRule.setUserId(session.getId());
            importPriceRule.setCreateTime(date);
            importPriceRule.setUpdateTime(date);
            importPriceRuleList.add(importPriceRule);
        }
        Integer priceRulesOn = ck4 ? 1 : 0;
        Integer comparePriceOn = ck3 ? 1 : 0;

        ImportPriceCents priceCents = new ImportPriceCents();
        priceCents.setCustomerId(session.getCustomerId());
        priceCents.setUserId(session.getId());
        priceCents.setComparePriceOn(comparePriceOn);
        priceCents.setPriceRulesOn(priceRulesOn);
        priceCents.setComparePriceCents(ck1Price);
        priceCents.setPriceCents(ck2Price);
        importPriceCentsDao.insert(priceCents);

        if (ck4) {
            //加入新的价格range规则
            importPriceRuleDao.insertByBatch(importPriceRuleList);
        }
        return new ImportPriceRuleUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);

    }

    /**
     * 根据价格规则配置设置新的价格
     */
    @Override
    public BigDecimal resetPrice(Long customerId, BigDecimal price) {

        ImportPriceRule priceRule = importPriceRuleDao.selectRestPriceRule(customerId, price);
        //用户设置了开启价格区间配置
        if (null != priceRule) {
            if (
                    priceRule.getMarkupType() != null && priceRule.getMarkupVal() != null) {
                if (priceRule.getMarkupType() == 1) {
                    price = price.add(priceRule.getMarkupVal());
                }
                if (priceRule.getMarkupType() == 2) {
                    price = price.multiply(priceRule.getMarkupVal());
                }
            }
        }else {
            price = price.multiply(new BigDecimal("2"));
        }

        ImportPriceCents cents = importPriceCentsDao.selectByCustomerId(customerId);

        if(null != cents){
            //用户开启 Assign cents
            if(cents.getPriceRulesOn() == 1){
                price=price.setScale(0, BigDecimal.ROUND_DOWN ).add(new BigDecimal(cents.getPriceCents()*0.01));
            }
        }

        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal resetComparePrice(Long customerId, BigDecimal price) {

        ImportPriceRule priceRule = importPriceRuleDao.selectRestPriceRule(customerId, price);
        //用户设置了开启价格区间配置
        if (null != priceRule) {
            if (priceRule.getCompareMarkupType() != null && priceRule.getCompareMarkupVal() != null) {
                if (priceRule.getCompareMarkupType() == 1) {
                    price = price.add(priceRule.getCompareMarkupVal());
                }
                if (priceRule.getCompareMarkupType() == 2) {
                    price = price.multiply(priceRule.getCompareMarkupVal());
                }
            }
        }else {
            price = price.multiply(new BigDecimal("2"));
        }

        ImportPriceCents cents = importPriceCentsDao.selectByCustomerId(customerId);
        if(null != cents) {
            //用户开启 Assign cents
            if (cents.getComparePriceOn() == 1) {
                price = price.setScale(0, BigDecimal.ROUND_DOWN).add(new BigDecimal(cents.getComparePriceCents() * 0.01));
            }
        }
        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     *
     */
    public ImportPriceRule selectByPrimaryKey(Long id) {
        ImportPriceRule record = new ImportPriceRule();
        record.setId(id);
        return importPriceRuleDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(ImportPriceRule record) {
        return importPriceRuleDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(ImportPriceRule record) {
        return importPriceRuleDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<ImportPriceRule> select(Page<ImportPriceRule> record) {
        record.initFromNum();
        return importPriceRuleDao.select(record);
    }

    /**
     *
     */
    public long count(Page<ImportPriceRule> record) {
        return importPriceRuleDao.count(record);
    }


    public static Integer objectToInteger(Object obj) {
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static BigDecimal objectToBigDecimal(Object obj) {
        try {
            return new BigDecimal(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }
}