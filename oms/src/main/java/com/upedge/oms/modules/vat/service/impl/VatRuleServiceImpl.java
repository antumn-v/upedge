package com.upedge.oms.modules.vat.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.oms.constant.VatRuleType;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.vat.dao.VatRuleDao;
import com.upedge.oms.modules.vat.dao.VatRuleItemDao;
import com.upedge.oms.modules.vat.dao.VatRuleLogDao;
import com.upedge.oms.modules.vat.entity.VatRule;
import com.upedge.oms.modules.vat.entity.VatRuleItem;
import com.upedge.oms.modules.vat.entity.VatRuleLog;
import com.upedge.oms.modules.vat.request.VatRuleAddRequest;
import com.upedge.oms.modules.vat.request.VatRuleListRequest;
import com.upedge.oms.modules.vat.request.VatRuleUpdateRequest;
import com.upedge.oms.modules.vat.response.VatRuleAddResponse;
import com.upedge.oms.modules.vat.response.VatRuleListResponse;
import com.upedge.oms.modules.vat.response.VatRuleUpdateResponse;
import com.upedge.oms.modules.vat.service.VatRuleService;
import com.upedge.oms.modules.vat.vo.VatRuleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class VatRuleServiceImpl implements VatRuleService {

    @Autowired
    private VatRuleDao vatRuleDao;
    @Autowired
    private VatRuleItemDao vatRuleItemDao;
    @Autowired
    private VatRuleLogDao vatRuleLogDao;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        VatRule record = new VatRule();
        record.setId(id);
        return vatRuleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VatRule record) {
        return vatRuleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VatRule record) {
        return vatRuleDao.insert(record);
    }

    @Override
    public List<VatRule> selectAllAreaVatRule() {
        return vatRuleDao.selectAllAreaVatRule();
    }

    @Override
    public BigDecimal getOrderVatAmount(BigDecimal productAmount, BigDecimal shipPrice, Long areaId,Long customerId) {
        CustomerIossVo customerIossVo = (CustomerIossVo) redisTemplate.opsForValue().get(RedisKey.STRING_CUSTOMER_IOSS + customerId);
        if (null != customerIossVo
        && StringUtils.isNotBlank(customerIossVo.getIossNum())){
            return BigDecimal.ZERO;
        }

        String key = RedisKey.STRING_AREA_VAT_RULE + areaId;
        VatRule rule = (VatRule) redisTemplate.opsForValue().get(key);

        if(null == rule){
            rule = vatRuleDao.selectVatRuleByAreaId(areaId);
            if(null == rule) {
                return BigDecimal.ZERO;
            }else {
                redisTemplate.opsForValue().set(key,rule);
            }
        }

        BigDecimal vatAmount = BigDecimal.ZERO;
        switch (rule.getMethodType()){
            case VatRuleType
                    .ORDER_AMOUNT:
                vatAmount = (productAmount.add(shipPrice)).multiply(rule.getRatio());
            break;
            case VatRuleType.PRODUCT_AMOUNT:
                vatAmount = productAmount.multiply(rule.getRatio());
                break;
            default:
                vatAmount = BigDecimal.ZERO;
                break;
        }
        if(vatAmount.compareTo(rule.getMinAmount()) == -1){
            vatAmount = rule.getMinAmount();
        }else if(vatAmount.compareTo(rule.getMaxAmount()) == 1){
            vatAmount = rule.getMaxAmount();
        }

        return vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     */
    public VatRule selectByPrimaryKey(Long id){
        return vatRuleDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VatRule record) {
        return vatRuleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VatRule record) {
        return vatRuleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VatRule> select(Page<VatRule> record){
        record.initFromNum();
        return vatRuleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VatRule> record){
        return vatRuleDao.count(record);
    }

    /**
     * VAT规则列表
     * @param request
     * @return
     */
    @Override
    public VatRuleListResponse adminList(VatRuleListRequest request) {
        List<VatRule> results = this.select(request);
        Long total = this.count(request);
        List<VatRuleVo> vatRuleVoList=new ArrayList<>();
        results.forEach(vatRule -> {
            VatRuleVo vatRuleVo=new VatRuleVo();
            BigDecimal ratio=vatRule.getRatio().multiply(new BigDecimal(100));
            vatRule.setRatio(ratio);
            BeanUtils.copyProperties(vatRule,vatRuleVo);
            //地区数
            int areaNum=vatRuleItemDao.countAreaNumByRuleId(vatRule.getId());
            vatRuleVo.setAreaNum(areaNum);
            vatRuleVoList.add(vatRuleVo);
        });
        request.setTotal(total);
        return new VatRuleListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,vatRuleVoList,request);
    }

    @Override
    public VatRuleAddResponse addVatRule(VatRuleAddRequest request, Session session) {
        if(request.getRatio()==null
                ||request.getRatio().compareTo(BigDecimal.ZERO)<=0
                ||request.getRatio().compareTo(new BigDecimal(100))>0){
            return new VatRuleAddResponse(ResultCode.FAIL_CODE, "VAT比例异常！");
        }
        if(request.getMaxAmount()==null||
                request.getMaxAmount().compareTo(BigDecimal.ZERO)<=0){
            return  new VatRuleAddResponse(ResultCode.FAIL_CODE, "价格异常！");
        }
        if(request.getMinAmount()==null||
                request.getMinAmount().compareTo(BigDecimal.ZERO)<=0){
            return  new VatRuleAddResponse(ResultCode.FAIL_CODE, "价格异常！");
        }
        if(request.getMethodType()==null){
            return  new VatRuleAddResponse(ResultCode.FAIL_CODE, "申报方式异常！");
        }
        //VAT申报比例
        BigDecimal ratio=request.getRatio().multiply(new BigDecimal(0.01));
        request.setRatio(ratio);
        VatRule entity=request.toVatRule(String.valueOf(session.getId()));
        entity.setId(IdGenerate.nextId());
        vatRuleDao.insert(entity);
        return new VatRuleAddResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,entity,request);
    }

    public void recordLog(VatRule vatRule, String userCode){
        List<VatRuleItem> itemList=vatRuleItemDao.listVatRuleItemByRuleId(vatRule.getId());
        if(itemList!=null&&itemList.size()>0) {
            List<VatRuleLog> vatRuleLogList=new ArrayList<>();
            for (VatRuleItem vatRuleItem : itemList) {
                VatRuleLog vatRuleLog = new VatRuleLog();
                vatRuleLog.setRuleId(vatRule.getId());
                vatRuleLog.setAreaId(vatRuleItem.getAreaId());
                vatRuleLog.setAreaName(vatRuleItem.getAreaName());
                vatRuleLog.setMinAmount(vatRule.getMinAmount());
                vatRuleLog.setMaxAmount(vatRule.getMaxAmount());
                vatRuleLog.setMethodType(vatRule.getMethodType());
                vatRuleLog.setRatio(vatRule.getRatio());
                vatRuleLog.setCurrency(vatRule.getCurrency());
                vatRuleLog.setAdminUser(userCode);
                vatRuleLog.setCreateTime(new Date());
                vatRuleLogList.add(vatRuleLog);
            }
            vatRuleLogDao.insertByBatch(vatRuleLogList);
        }
    }

    @Transactional
    @Override
    public VatRuleUpdateResponse adminUpdate(Long id, VatRuleUpdateRequest request, Session session) {
        if(request.getRatio()==null
                ||request.getRatio().compareTo(BigDecimal.ZERO)<=0
                ||request.getRatio().compareTo(new BigDecimal(100))>0){
            return new VatRuleUpdateResponse(ResultCode.FAIL_CODE, "VAT比例异常！");
        }
        if(request.getMaxAmount()==null||
                request.getMaxAmount().compareTo(BigDecimal.ZERO)<=0){
            return  new VatRuleUpdateResponse(ResultCode.FAIL_CODE, "价格异常！");
        }
        if(request.getMinAmount()==null||
                request.getMinAmount().compareTo(BigDecimal.ZERO)<=0){
            return  new VatRuleUpdateResponse(ResultCode.FAIL_CODE, "价格异常！");
        }
        if(request.getMethodType()==null){
            return  new VatRuleUpdateResponse(ResultCode.FAIL_CODE, "申报方式异常！");
        }
        //VAT申报比例
        BigDecimal ratio=request.getRatio().multiply(new BigDecimal(0.01));
        request.setRatio(ratio);
        VatRule entity=request.toVatRule(id, String.valueOf(session.getId()));
        this.updateByPrimaryKeySelective(entity);
        this.recordLog(entity, String.valueOf(session.getId()));

        List<VatRuleItem> vatRuleItems = vatRuleItemDao.selectByRuleId(id);
        for (VatRuleItem vatRuleItem : vatRuleItems) {
            String key = RedisKey.STRING_AREA_VAT_RULE + vatRuleItem.getAreaId();
            redisTemplate.opsForValue().set(key,entity);
            orderService.initOrderVatAmountByAreaId(vatRuleItem.getAreaId());
        }

        return new VatRuleUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }
}