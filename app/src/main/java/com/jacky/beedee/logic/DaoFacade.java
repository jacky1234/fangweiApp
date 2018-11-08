package com.jacky.beedee.logic;

import com.jacky.beedee.logic.entity.module.MySelf;
import com.jacky.beedee.support.dao.DbManager;
import com.jacky.beedee.support.dao.generate.DaoSession;
import com.jacky.beedee.support.dao.generate.PreferenceDao;
import com.jacky.beedee.support.dao.module.Preference;
import com.jacky.beedee.support.util.JsonUtil;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 2018/10/31.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class DaoFacade {
    private static final long MYSELF_INFO = 1;
    private DaoSession daoSession;

    private DaoFacade() {
        daoSession = DbManager.get().getDaoSession();
    }

    public static DaoFacade get() {
        return DaoFacade.InstanceHolder.instance;
    }

    public void saveMyselfInfo(@Nullable MySelf info) {
        if (info != null) {
            daoSession.getPreferenceDao().insertOrReplace(new Preference(MYSELF_INFO, JsonUtil.toJSON(info)));
        } else {
            daoSession.getPreferenceDao().deleteByKey(MYSELF_INFO);
        }
    }

    @Nullable
    public MySelf getMySelfInfo() {
        List<Preference> list = daoSession.getPreferenceDao().queryBuilder()
                .where(PreferenceDao.Properties.Index.eq(MYSELF_INFO)).list();
        if (list.isEmpty()) {
            return null;
        }

        return JsonUtil.fromJSON(MySelf.class, list.get(0).getValue());
    }

    private static final class InstanceHolder {
        private static DaoFacade instance = new DaoFacade();
    }

}
