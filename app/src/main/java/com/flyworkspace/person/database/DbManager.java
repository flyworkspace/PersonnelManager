package com.flyworkspace.person.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.flyworkspace.person.dao.DaoMaster;
import com.flyworkspace.person.dao.DaoSession;
import com.flyworkspace.person.dao.PersonInfoDao;

/**
 * Created by jinpengfei on 15-10-13.
 */
public class DbManager {
    private static PersonInfoDao personInfoDao;

    public static PersonInfoDao getPersonInfoDao(Context context) {
        if (personInfoDao == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "pm-db", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            personInfoDao = daoSession.getPersonInfoDao();
        }
        return personInfoDao;
    }
}
