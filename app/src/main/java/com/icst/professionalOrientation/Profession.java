package com.icst.professionalOrientation;

import android.text.SpannableString;

public class Profession
{
    private SpannableString name;
    private SpannableString briefDescription;
    private SpannableString competence;
    private SpannableString salary;
    private SpannableString links;


    public Profession(SpannableString name, SpannableString briefDescription, SpannableString competence, SpannableString salary, SpannableString links)
    {
        this.name=name;
        this.briefDescription = briefDescription;
        this.competence = competence;
        this.salary = salary;
        this.links = links;
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

    public SpannableString getCompetence()
    {
        return this.competence;
    }

    public void setCompetence(SpannableString competence)
    {
        this.competence = competence;
    }

    public SpannableString getSalary()
    {
        return this.salary;
    }

    public void setSalary(SpannableString salary)
    {
        this.salary = salary;
    }

    public SpannableString getLinks()
    {
        return this.links;
    }

    public void setLinks(SpannableString links)
    {
        this.links = links;
    }
}
