package com.jacky.beedee.support.dao;

import com.jacky.beedee.support.Starter;
import com.jacky.beedee.support.dao.generate.DaoMaster;
import com.jacky.beedee.support.dao.generate.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * 2018/10/31.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 * 只提供数据
 */
public class DbManager {
    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private DbManager() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(Starter.getContext(), "beedee.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DbManager get() {
        return InstanceHolder.instance;
    }


    private static final class InstanceHolder {
        private static DbManager instance = new DbManager();
    }
}
