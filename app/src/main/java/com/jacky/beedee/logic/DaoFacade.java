package com.jacky.beedee.logic;

import com.jacky.beedee.logic.entity.module.MySelf;
import com.jacky.beedee.support.dao.DbManager;
import com.jacky.beedee.support.dao.generate.DaoSession;
import com.jacky.beedee.support.dao.generate.PreferenceDao;
import com.jacky.beedee.support.dao.module.Preference;
import com.jacky.beedee.support.util.JsonUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 2018/10/31.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class DaoFacade {
    private static final long MYSELF_INFO = 1;
    private static final long SEARCH_KEY = 2;
    private static final long SETTING_PUSH = 3;         //是否打开推送
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

    @NotNull
    public List<String> getSearchHistoryKeys() {
        List<String> keys = new ArrayList<>();

        List<Preference> list = daoSession.getPreferenceDao().queryBuilder()
                .where(PreferenceDao.Properties.Index.eq(SEARCH_KEY)).list();
        if (!list.isEmpty()) {
            keys.addAll(JsonUtil.getListFromJSON(String.class, list.get(0).getValue()));
        }

        return keys;
    }

    public Set<String> addSearchKey(@NotNull String key) {
        Set<String> keys = new TreeSet<>();

        List<Preference> list = daoSession.getPreferenceDao().queryBuilder()
                .where(PreferenceDao.Properties.Index.eq(SEARCH_KEY)).list();
        keys.add(key);
        if (!list.isEmpty()) {
            keys.addAll(JsonUtil.getListFromJSON(String.class, list.get(0).getValue()));
        }
        daoSession.getPreferenceDao().insertOrReplace(new Preference(SEARCH_KEY, JsonUtil.toJSON(keys)));

        return keys;
    }

    public boolean isPushOpened() {
        List<Preference> list = daoSession.getPreferenceDao().queryBuilder()
                .where(PreferenceDao.Properties.Index.eq(SETTING_PUSH)).list();
        if (!list.isEmpty()) {
            return Integer.parseInt(list.get(0).getValue()) == 1;
        }

        return true;
    }

    public void togglePushSetting() {
        String value = isPushOpened() ? String.valueOf(0) : String.valueOf(1);
        daoSession.getPreferenceDao().insertOrReplace(new Preference(SETTING_PUSH, value));
    }

    private static final class InstanceHolder {
        private static DaoFacade instance = new DaoFacade();
    }

}
