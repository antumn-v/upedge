package com.upedge.pms.modules.quote.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.quote.dao.QuoteApplyItemDao;
import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import com.upedge.pms.modules.quote.service.QuoteApplyItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class QuoteApplyItemServiceImpl implements QuoteApplyItemService {

    @Autowired
    private QuoteApplyItemDao quoteApplyItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        QuoteApplyItem record = new QuoteApplyItem();
        record.setId(id);
        return quoteApplyItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(QuoteApplyItem record) {
        return quoteApplyItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(QuoteApplyItem record) {
        return quoteApplyItemDao.insert(record);
    }

    @Override
    public List<Long> selectAllQuotingStoreVariantIds() {
        return quoteApplyItemDao.selectAllQuotingStoreVariantIds();
    }

    @Override
    public QuoteApplyItem selectByStoreVariantId(Long storeVariantId) {
        if (null != storeVariantId){
            return quoteApplyItemDao.selectByStoreVariantId(storeVariantId);
        }
        return null;
    }

    /**
     *
     */
    public QuoteApplyItem selectByPrimaryKey(Long id){
        QuoteApplyItem record = new QuoteApplyItem();
        record.setId(id);
        return quoteApplyItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(QuoteApplyItem record) {
        return quoteApplyItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(QuoteApplyItem record) {
        return quoteApplyItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<QuoteApplyItem> select(Page<QuoteApplyItem> record){
        record.initFromNum();
        return quoteApplyItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<QuoteApplyItem> record){
        return quoteApplyItemDao.count(record);
    }

}