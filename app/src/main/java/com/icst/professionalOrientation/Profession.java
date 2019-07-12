package com.icst.professionalOrientation;

public class Profession
{
    private String name;
    private String briefDescription;
    private String profFirst;
    private String profSecond;
    private String profThird;
    private String profFourth;


    public Profession(String name, String briefDescription, String profFirst , String profSecond, String profThird, String profFourth)
    {
        this.name = name;
        this.briefDescription = briefDescription;
        this.profFirst = profFirst ;
        this.profSecond = profSecond;
        this.profThird = profThird;
        this.profFourth = profFourth;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return this.briefDescription;
    }

    public void setDescription(String description)
    {
        this.briefDescription = description;
    }

    public String getProfFirst()
    {
        return this.profFirst;
    }

    public void setProfFirst(String profFirst)
    {
        this.profFirst = profFirst;
    }

    public String getProfSecond()
    {
        return this.profSecond;
    }

    public void setProfSecond(String profSecond) { this.profSecond = profSecond; }

    public String getProfThird()
    {
        return this.profThird + " руб. в месяц (по данным портала https://moikrug.ru на 1-е полугодие 2019 года для квалификации middle)";
    }

    public void setProfThird(String profThird)
    {
        this.profThird = profThird;
    }

    public String getProfFourth()
    {
        return this.profFourth;
    }

    public void setProfFourth(String profFourth)
    {
        this.profFourth = profFourth;
    }
}
