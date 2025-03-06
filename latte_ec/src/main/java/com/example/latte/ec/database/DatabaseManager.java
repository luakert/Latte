package com.example.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

public class DatabaseManager {
    private DaoSession mDaoSession = null;
    private UserProfileDao mDao = null;

    private DatabaseManager() {

    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    public static DatabaseManager getInstance() {
        return Holder.DATABASE_MANAGER;
    }

    private final static class Holder {
        private final static DatabaseManager DATABASE_MANAGER = new DatabaseManager();
    }

    private void initDao(Context context) {
        ReleaseOpenHelper releaseOpenHelper = new ReleaseOpenHelper(context, "fast_ec.db");
        Database writableDb = releaseOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(writableDb).newSession();
        mDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao() {
        return mDao;
    }
}
