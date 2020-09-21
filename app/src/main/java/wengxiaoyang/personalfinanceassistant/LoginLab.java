package wengxiaoyang.personalfinanceassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.ManageDbSchema.ManageTable2;
import database.ManageCursorWrapper;
import database.ManageBaseHelper;


public class LoginLab {
    private static LoginLab sLoginLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    // 构造方法
    private LoginLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ManageBaseHelper(mContext).getWritableDatabase();
    }

    // 添加账号密码
    public void addLogin(Login l) {
        ContentValues values = getContentValues(l);
        mDatabase.insert(ManageTable2.NAME2, null, values);
    }

    // 在数据库中找到Login表
    private ManageCursorWrapper queryLogin(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ManageTable2.NAME2,
                null,// null selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ManageCursorWrapper(cursor);
    }

    /*public List<Login> getLogins() {
        List<Login> login = new ArrayList<>();

        ManageCursorWrapper cursor = queryLogin(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                login.add(cursor.getLogin());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return login;
    }*/

    // 根据账号密码查找
    public Login getAccountAndPassword(String account, String password) {

        ManageCursorWrapper cursor = queryLogin(
                ManageTable2.Cols2.ACCOUNT + " = ? and " + ManageTable2.Cols2.PASSWORD+ " = ?",
                new String[] { account, password }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getLogin();
        } finally {
            cursor.close();
        }
    }

    // 获得Login数据
    private static ContentValues getContentValues(Login login) {
        ContentValues values = new ContentValues();
        values.put(ManageTable2.Cols2.UUID, login.getId().toString());
        values.put(ManageTable2.Cols2.ACCOUNT, login.getAccount());
        values.put(ManageTable2.Cols2.PASSWORD, login.getPassword());

        return values;
    }

    // 根据上下文获取LoginLab
    public static LoginLab get(Context context) {
        if (sLoginLab == null) {
            sLoginLab = new LoginLab(context);
        }
        return sLoginLab;
    }

}

