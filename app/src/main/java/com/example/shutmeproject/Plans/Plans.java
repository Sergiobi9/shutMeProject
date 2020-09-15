package com.example.shutmeproject.Plans;

public class Plans {

    private final String FREE_PLAN = "FREE_PLAN", STARTER_PLAN = "STARTER_PLAN", PRO_PLAN = "PRO_PLAN";
    private final double FREE_PLAN_PRICE = 0, STARTER_PLAN_PRICE = 4.99, PRO_PLAN_PRICE = 9.99;

    public Plans() {}

    public String getFREE_PLAN() {
        return FREE_PLAN;
    }

    public String getSTARTER_PLAN() {
        return STARTER_PLAN;
    }

    public String getPRO_PLAN() {
        return PRO_PLAN;
    }

    public double getFREE_PLAN_PRICE() {
        return FREE_PLAN_PRICE;
    }

    public double getSTARTER_PLAN_PRICE() {
        return STARTER_PLAN_PRICE;
    }

    public double getPRO_PLAN_PRICE() {
        return PRO_PLAN_PRICE;
    }
}
