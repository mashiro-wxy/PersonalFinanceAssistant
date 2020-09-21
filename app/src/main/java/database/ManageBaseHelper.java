package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.ManageDbSchema.ManageTable;
import database.ManageDbSchema.ManageTable2;

public class ManageBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "manageBase.db";

    public ManageBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // manage表
        db.execSQL("create table " + ManageTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ManageTable.Cols.UUID + ", " +
                ManageTable.Cols.TITLE + ", " +
                ManageTable.Cols.MONEY + ", " +
                ManageTable.Cols.DATE + ", " +
                ManageTable.Cols.PAYMETHOD  + ", " +
                ManageTable.Cols.REMARK + ")"
        );

        // 账号密码表
        db.execSQL("create table " + ManageTable2.NAME2 + "(" +
                "_id integer primary key autoincrement, " +
                ManageTable2.Cols2.UUID + ", " +
                ManageTable2.Cols2.ACCOUNT + ", " +
                ManageTable2.Cols2.PASSWORD + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
