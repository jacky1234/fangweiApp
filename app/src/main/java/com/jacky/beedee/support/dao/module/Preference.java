package com.jacky.beedee.support.dao.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 2018/10/31.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
@Entity
public class Preference {
    @Id
    @NotNull
    private long index;
    private String value;

    @Generated(hash = 30618483)
    public Preference(long index, String value) {
        this.index = index;
        this.value = value;
    }

    @Generated(hash = 655693095)
    public Preference() {
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
