package com.example.shutmeproject.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class TimeTable {

    private Integer id;
    private String name;
    private String startHour;
    private String finishHour;
    private ArrayList<String> arrayDaysOfTheWeeek;
    private ArrayList<String> arrayPackageNames;

    public TimeTable(){

    }

    public TimeTable(Integer id, String name, String startHour, String finishHour, ArrayList<String> arrayDaysOfTheWeeek,ArrayList<String> arrayPackageNames) {
        this.id = id;
        this.name = name;
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.arrayDaysOfTheWeeek = arrayDaysOfTheWeeek;
        this.arrayPackageNames = arrayPackageNames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(String finishHour) {
        this.finishHour = finishHour;
    }

    public ArrayList<String> getArrayDaysOfTheWeeek() {
        return arrayDaysOfTheWeeek;
    }

    public void setArrayDaysOfTheWeeek(ArrayList<String> arrayDaysOfTheWeeek) {
        this.arrayDaysOfTheWeeek = arrayDaysOfTheWeeek;
    }

    public ArrayList<String> getArrayPackageNames() {
        return arrayPackageNames;
    }

    public void setArrayPackageNames(ArrayList<String> arrayPackageNames) {
        this.arrayPackageNames = arrayPackageNames;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startHour='" + startHour + '\'' +
                ", finishHour='" + finishHour + '\'' +
                ", arrayDaysOfTheWeeek=" + arrayDaysOfTheWeeek +
                ", arrayPackageNames=" + arrayPackageNames +
                '}';
    }
}
