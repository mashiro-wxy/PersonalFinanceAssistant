package wengxiaoyang.personalfinanceassistant;

import java.util.Date;
import java.util.UUID;

public class Manage {

    private UUID mId;
    private String mTitle;
    private String mMoney;
    private Date mDate;
    private int mPayMethod;
    private String mRemark;

    public Manage() {
        this(UUID.randomUUID());
    }

    public Manage(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMoney() {
        return mMoney;
    }

    public void setMoney(String money) {
        mMoney = money;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getPayMethod() {
        return mPayMethod;
    }

    public void setPayMethod(int payMethod) {
        mPayMethod = payMethod;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }


    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

}
