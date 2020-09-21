package wengxiaoyang.personalfinanceassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import database.ManageDbSchema.ManageTable;
import database.ManageCursorWrapper;
import database.ManageBaseHelper;

public class ManageLab {
    private static ManageLab sManageLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ManageLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ManageBaseHelper(mContext).getWritableDatabase();
    }

    // 添加记录
    public void addManage(Manage m) {
        ContentValues values = getContentValues(m);
        mDatabase.insert(ManageTable.NAME, null, values);
    }

    // 删除记录
    public void removeManage(Manage c) {
        String uuidString = c.getId().toString();
        mDatabase.delete(ManageTable.NAME, ManageTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    // 得到照片
    public File getPhotoFile(Manage manage) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, manage.getPhotoFilename());
    }

    // 更新记录
    public void updateManage(Manage manage) {
        String uuidString = manage.getId().toString();
        ContentValues values = getContentValues(manage);
        mDatabase.update(ManageTable.NAME, values, ManageTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    // 查找记录
    private ManageCursorWrapper queryManages(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ManageTable.NAME,
                null,// null selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ManageCursorWrapper(cursor);
    }

    // 得到Manages集合
    public List<Manage> getManages() {
        List<Manage> manages = new ArrayList<>();

        ManageCursorWrapper cursor = queryManages(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                manages.add(cursor.getManage());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return manages;
    }

    // 根据ID获取Manage
    public Manage getManage(UUID id) {

        ManageCursorWrapper cursor = queryManages(
                ManageTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getManage();
        } finally {
            cursor.close();
        }
    }

    // 获得数据库数据
    private static ContentValues getContentValues(Manage manage) {
        ContentValues values = new ContentValues();
        values.put(ManageTable.Cols.UUID, manage.getId().toString());
        values.put(ManageTable.Cols.TITLE, manage.getTitle());
        values.put(ManageTable.Cols.MONEY, manage.getMoney());
        values.put(ManageTable.Cols.DATE, manage.getDate().getTime());
        values.put(ManageTable.Cols.PAYMETHOD, manage.getPayMethod());
        values.put(ManageTable.Cols.REMARK, manage.getRemark());

        return values;
    }

    public static ManageLab get(Context context) {
        if (sManageLab == null) {
            sManageLab = new ManageLab(context);
        }
        return sManageLab;
    }

}
