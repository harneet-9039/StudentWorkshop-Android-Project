package com.workshopproject.getter_setters;

/**
 * Created by HP on 24-09-2018.
 */

public class Workshops {
    private String timing=null;
    private String workshopName=null;
    private String fee=null;
    private String Intro=null;

    public String getTiming()
    {
        return timing;
    }
    public String getWorkshopName()
    {
        return workshopName;
    }

    public String getFee()
    {
        return fee;
    }

    public String getIntro()
    {
        return Intro;
    }




    public void setTiming(String timing)
    {
        this.timing = timing;
    }

    public void setWorkshopName(String name)
    {
        this.workshopName = name;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public void setIntro(String Intro)
    {
        this.Intro = Intro;
    }


}
