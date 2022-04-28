package com.upedge.pms.modules.alibaba.entity.translate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoxing on 2019/11/27.
 */
public class Data {

    List<Translation> translations=new ArrayList();

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
