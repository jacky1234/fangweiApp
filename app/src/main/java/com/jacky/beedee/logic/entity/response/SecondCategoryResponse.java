package com.jacky.beedee.logic.entity.response;

import com.jacky.beedee.logic.entity.module.Category;
import com.jacky.beedee.logic.entity.module.GoodItem;

import java.util.List;

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class SecondCategoryResponse {
    private Category category;
    private List<GoodItem> list;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<GoodItem> getList() {
        return list;
    }

    public void setList(List<GoodItem> list) {
        this.list = list;
    }
}
