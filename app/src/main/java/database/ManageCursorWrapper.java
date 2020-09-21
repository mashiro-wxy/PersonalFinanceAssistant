package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import database.ManageDbSchema.ManageTable;
import database.ManageDbSchema.ManageTable2;
import wengxiaoyang.personalfinanceassistant.Login;
import wengxiaoyang.personalfinanceassistant.Manage;

public class ManageCursorWrapper extends CursorWrapper {
    public ManageCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Manage getManage() {
        // 获得manage信息
        String uuidString = getString(getColumnIndex(ManageTable.Cols.UUID));
        String title = getString(getColumnIndex(ManageTable.Cols.TITLE));
        String money = getString(getColumnIndex(ManageTable.Cols.MONEY));
        Long date = getLong(getColumnIndex(ManageTable.Cols.DATE));
        int payMethod = getInt(getColumnIndex(ManageTable.Cols.PAYMETHOD));
        String remark = getString(getColumnIndex(ManageTable.Cols.REMARK));

        // 将数据写入数据库
        Manage manage = new Manage(UUID.fromString(uuidString));
        manage.setTitle(title);
        manage.setMoney(money);
        manage.setDate(new Date(date));
        manage.setPayMethod(payMethod);
        manage.setRemark(remark);

        return manage;
    }


    public Login getLogin() {
        // 获得登录信息
        String uuidString = getString(getColumnIndex(ManageTable2.Cols2.UUID));
        String account = getString(getColumnIndex(ManageTable2.Cols2.ACCOUNT));
        String password = getString(getColumnIndex(ManageTable2.Cols2.PASSWORD));

        // 将登录信息存入数据库
        Login login = new Login(UUID.fromString(uuidString));
        login.setAccount(account);
        login.setPassword(password);

        return login;
    }
}
