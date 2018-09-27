package com.workshopproject.db_models;

/**
 * Created by HP on 25-09-2018.
 */
import java.io.Serializable;

public class Workshop implements Serializable {

    public static final String TAG = "Workshop";
    private static final long serialVersionUID = -7406082437623008161L;

    private int mId;
    private String mName;
    private String mIntro;
    private String mTimings;
    private String mFee;

    public Workshop() {}


    public Workshop(String name, String intro, String timing, String fee) {
        this.mName = name;
        this.mIntro = intro;
        this.mTimings = timing;
        this.mFee = fee;
    }


    public long getId() {
        return mId;
    }
    public void setId(int mId) {
        this.mId = mId;
    }
    public String getName() {
        return mName;
    }
    public void setName(String mFirstName) {
        this.mName = mFirstName;
    }

    public String getmIntro() {
        return mIntro;
    }
    public void setmIntro(String mIntro) {
        this.mIntro = mIntro;
    }
    public String getmTimings() {
        return mTimings;
    }
    public void setmTimings(String mTimings) {
        this.mTimings = mTimings;
    }
    public String getmFee() {
        return mFee;
    }
    public void setmFee(String mFee) {
        this.mFee = mFee;
    }
}
