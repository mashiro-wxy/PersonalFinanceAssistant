package database;

public class ManageDbSchema {
    // Manage表
    public static final class ManageTable {
        public static final String NAME = "manages";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String MONEY = "money";
            public static final String DATE = "date";
            public static final String PAYMETHOD = "paymethod";
            public static final String REMARK = "remark";
        }
    }

    // 账号密码表
    public static final class ManageTable2 {
        public static final String NAME2 = "login";
        public static final class Cols2 {
            public static final String UUID = "uuid";
            public static final String ACCOUNT = "account";
            public static final String PASSWORD = "password";
        }
    }

}
