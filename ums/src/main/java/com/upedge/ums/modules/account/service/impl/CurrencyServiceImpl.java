package com.upedge.ums.modules.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.ums.modules.account.dao.CurrencyDao;
import com.upedge.ums.modules.account.entity.Currency;
import com.upedge.ums.modules.account.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    CurrencyDao currencyDao;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public BigDecimal selectCnyRateByCode(String code) {

        Currency currency = currencyDao.selectByCurrencyCode(code);

        BigDecimal rate = currencyDao.selectCnyRateByCode(code);
        if (null == rate) {
            currency = getCurrency(code);
            if (null != currency) {
                currencyDao.insert(currency);
                rate = currency.getCnyRate();
            } else {
                return null;
            }
        }

        Field[] fields = currency.getClass().getDeclaredFields();
        for (Field f: fields) {
            f.setAccessible(true);
            try {
                redisTemplate.opsForHash().put(RedisKey.HASH_CURRENCY_RATE +code, f.getName(), f.get(currency));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                redisTemplate.opsForHash().put(RedisKey.HASH_CURRENCY_RATE+code, f.getName(), null);
            }
        }
        return rate;
    }

    @Override
    public Currency refreshCurrencyRate(String code) {

        Currency currency = getCurrency(code);
        if (null == currency) {
            return null;
        }

        BigDecimal rate = currencyDao.selectCnyRateByCode(code);

        Date date = new Date();

        if (rate != null) {
            if (rate.compareTo(currency.getCnyRate()) != 0) {
//                storeSettingMapper.refreshStoreCurrencyRate(code,currency.getCurrencyRate());
                currencyDao.updateByCurrencyCodeSelective(currency);
            }
        } else {
            currency.setCreateTime(date);
            currencyDao.insert(currency);
        }
        redisTemplate.opsForHash().put("currency:rate", code, currency.getCnyRate());
        return currency;
    }

    @Override
    public BigDecimal refreshUsdRate(String code) {

        Currency currency = currencyDao.selectByCurrencyCode(code);
        if (null == currency) {
            return null;
        }

        BigDecimal rate = getCurrencyUsdRate(code);

        currency = new Currency();
        currency.setUsdRate(rate);
        currency.setCurrencyCode(code);
        currencyDao.updateByCurrencyCodeSelective(currency);

        return rate;
    }

    synchronized Currency getCurrency(String code) {
//        if(count == 0){
//            start = System.currentTimeMillis();
//        }
//        count ++;
        try {
            URL u = new URL("http://api.k780.com?app=finance.rate_cnyquot&curno=" + code + "&appkey=51635&sign=867e432c81666422ca41b2805cd9df0f&format=json&bankno=BOC");
            InputStream in = u.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                byte buf[] = new byte[1024];
                int read = 0;
                while ((read = in.read(buf)) > 0) {
                    out.write(buf, 0, read);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
            byte b[] = out.toByteArray();

            String result = new String(b, "utf-8");

            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.getInteger("success") == 0) {
                return null;
            }
            Currency currency = jsonObject
                    .getJSONObject("result")
                    .getJSONObject(code)
                    .getJSONObject("BOC")
                    .toJavaObject(Currency.class);
            currency.setCnyRate(currency.getSeBuy().divide(new BigDecimal(100), 2));
            currency.setApiUpdateTime(currency.getUpddate());
            currency.setCurrencyCode(code);
            currency.setAppUpdateTime(new Date());
            return currency;
        } catch (Exception e) {
            return null;
        }

    }

    synchronized BigDecimal getCurrencyUsdRate(String code) {
//        if(count == 0){
//            start = System.currentTimeMillis();
//        }
//        count ++;
        try {
            URL u = new URL("http://api.k780.com/?app=finance.rate&scur=" + code + "&tcur=USD&appkey=51635&sign=867e432c81666422ca41b2805cd9df0f&format=json");
            InputStream in = u.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                byte buf[] = new byte[1024];
                int read = 0;
                while ((read = in.read(buf)) > 0) {
                    out.write(buf, 0, read);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
            byte b[] = out.toByteArray();

            String result = new String(b, "utf-8");

            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.getInteger("success") == 0) {
                return null;
            }
            BigDecimal rate = jsonObject.getJSONObject("result").getBigDecimal("rate");
            return rate;
        } catch (Exception e) {
            return null;
        }

    }
}
