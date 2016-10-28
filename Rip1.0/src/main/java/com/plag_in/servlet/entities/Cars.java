package com.plag_in.servlet.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Владимир on 18.09.2016.
 */


public class Cars {

    private String mMark;
    private String mModel;
    private String mColor;
    private Integer mYearOfCreate;

    public Cars(String mark, String model, String color, Integer yearOfCreate) {
        mMark = mark;
        mModel = model;
        mColor = color;
        mYearOfCreate = yearOfCreate;
    }

    public String getMark() {
        return mMark;
    }

    public void setMark(String mark) {
        this.mMark = mark;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        this.mModel = model;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public Integer getYearOfCreate() {
        return mYearOfCreate;
    }

    public void setYearOfCreate(Integer dateOfCreate) {
        mYearOfCreate = dateOfCreate;
    }
}
