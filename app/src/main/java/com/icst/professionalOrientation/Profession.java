package com.icst.professionalOrientation;

import android.text.SpannableString;

public class Profession
{
    private SpannableString name;
    private SpannableString briefDescription;
    private SpannableString profFirst;
    private SpannableString profSecond;
    private SpannableString profThird;


    public Profession(SpannableString name, SpannableString briefDescription, SpannableString profFirst , SpannableString profSecond, SpannableString profThird)
    {
        this.name = name;
        this.briefDescription = briefDescription;
        this.profFirst = profFirst ;
        this.profSecond = profSecond;
        this.profThird = profThird;
    }

    public SpannableString getName()
    {
        return this.name;
    }

    public void setName(SpannableString name)
    {
        this.name = name;
    }

    public SpannableString getDescription()
    {
        return this.briefDescription;
    }

    public void setDescription(SpannableString description)
    {
        this.briefDescription = description;
    }

    public SpannableString getProfFirst()
    {
        return this.profFirst;
    }

    public void setProfFirst(SpannableString profFirst)
    {
        this.profFirst = profFirst;
    }

    public SpannableString getProfSecond()
    {
        return this.profSecond;
    }

    public void setProfSecond(SpannableString profSecond)
    {
        this.profSecond = profSecond;
    }

    public SpannableString getProfThird()
    {
        return this.profThird;
    }

    public void setProfThird(SpannableString profThird)
    {
        this.profThird = profThird;
    }
}
