package com.plag_in.servlet.utils;

import com.plag_in.servlet.entities.Cars;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Владимир on 28.10.2016.
 */
public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/db_cars";
    private static final String USER = "root";
    private static final String PASSWORD = "qwerty789";

    private static final String TABLE_NAME = "cars";
    private static final String FIELD_MARK = "mark";
    private static final String FIELD_MODEL = "model";
    private static final String FIELD_COLOR = "color";
    private static final String FIELD_YEAR = "year";

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static Statement getStatement() throws ClassNotFoundException, SQLException {
        return getConnection().createStatement();
    }

    public static ArrayList<Cars> getAllCars() throws ClassNotFoundException, SQLException {
        Statement statement = getStatement();
        ArrayList<Cars> result = new ArrayList<>();
        ResultSet queryResult = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
        try {
            while (queryResult.next()) {
                Cars drink = new Cars(
                        queryResult.getString(FIELD_MARK),
                        queryResult.getString(FIELD_MODEL),
                        queryResult.getString(FIELD_COLOR),
                        queryResult.getInt(FIELD_YEAR)
                );
                result.add(drink);
            }
        } finally {
            queryResult.close();
            statement.close();
        }
        return result;
    }

    public static void addCar(final Cars cars) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s) values (?, ?, ?, ?)",
                TABLE_NAME, FIELD_MARK, FIELD_MODEL, FIELD_COLOR, FIELD_YEAR);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try {
            preparedStatement.setString(1, cars.getMark());
            preparedStatement.setString(2, cars.getModel());
            preparedStatement.setString(3, cars.getColor());
            preparedStatement.setInt(4, cars.getYearOfCreate());
            preparedStatement.execute();
        } finally {
            preparedStatement.close();
            connection.close();
        }
    }
}
