package wengxiaoyang.personalfinanceassistant;


import java.util.UUID;

public class Login {
    private UUID mId;
    private String mAccount;
    private String mPassword;

    public Login() {
        this(UUID.randomUUID());
    }

    public Login(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

}
