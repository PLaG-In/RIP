package com.plag_in.servlet.controllers;

import com.plag_in.servlet.entities.Cars;
import com.plag_in.servlet.utils.DBManager;
import com.plag_in.servlet.utils.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Владимир on 28.10.2016.
 */
public class CarsController {

    private static final int MAX_MARK_MODEL_COLOR_LEN = 50;
    private static final int MIN_YEAR = 1768;


    public static void addCar(final String mark, final String model, final String color, final Integer yearOfCreate){
        Calendar calendar = Calendar.getInstance();
        int yearToday = calendar.get(Calendar.YEAR);

        if (model.length() > 0 && model.length() <= MAX_MARK_MODEL_COLOR_LEN
                && color.length() > 0 && color.length() <= MAX_MARK_MODEL_COLOR_LEN
                && mark.length() > 0 && mark.length() <= MAX_MARK_MODEL_COLOR_LEN
                && yearOfCreate >= MIN_YEAR && yearOfCreate <= yearToday){
            try {
                DBManager.addCar(new Cars(mark, model, color, yearOfCreate));
            } catch (SQLException ex) {
                Logger.log(Logger.LogLevel.CRITICAL, ex);
            } catch (ClassNotFoundException ex) {
                Logger.log(Logger.LogLevel.ERROR, ex);
            }
        }
    }

    public static ArrayList<Cars> getAllCars() {
        @SuppressWarnings("unchecked")
        ArrayList<Cars> drinkList = new ArrayList<>();
        try {
            drinkList = DBManager.getAllCars();
        } catch (SQLException ex) {
            Logger.log(Logger.LogLevel.CRITICAL, ex);
        } catch (ClassNotFoundException ex) {
            Logger.log(Logger.LogLevel.ERROR, ex);
        }
        return drinkList;
    }
}
