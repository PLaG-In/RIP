package com.plag_in.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private final static String sDataKeepName = "dataKeep";

    @Override
    public void init() throws ServletException {
        super.init();
        ArrayList<Cars> carList = new ArrayList<Cars>();
        getServletContext().setAttribute(sDataKeepName, carList);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        ServletContext context = req.getServletContext();
        Map<String, String[]> params = req.getParameterMap();
        if (params.isEmpty()) {
            showAddingForm(writer);
        } else if (params.get("method")[0].equals("showAllCars")) {
            showAllCars(context, writer);
        } else if (params.get("method")[0].equals("showAddingForm")) {
            showAddingForm(writer);
        } else {
            super.doGet(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        ServletContext context = req.getServletContext();
        Map<String, String[]> params = req.getParameterMap();
        if (params.get("method")[0].equals("addCar")) {
            addCar(
                    context,
                    params.get("mark")[0],
                    params.get("model")[0],
                    params.get("color")[0],
                    params.get("yearOfCreate")[0]);

            showAddingForm(writer);
        } else {
            super.doPost(req, resp);
        }
    }

    private void showAddingForm(PrintWriter writer) throws IOException {
        writer.print("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<body>\n");
        writer.println("<form action=\"showroom\" method=\"POST\">");
        writer.print("Car mark:<br>" +
                        "<input type=\"text\" name=\"mark\"><br>");
        writer.print("Car model:<br>" +
                        "<input type=\"text\" name=\"model\"><br>");
        writer.print("Color:<br>" +
                        "<input type=\"text\" name=\"color\"><br>");
        writer.print("Year of create: <br>" +
                        "<input type=\"year\" name=\"yearOfCreate\">\n" +
                        "<br>");
        writer.print("<input type=\"hidden\" name=\"method\" value=\"addCar\">\n" +
                        "<input type=\"submit\" value=\"Submit\">\n" +
                        "</form><br>");
        writer.println("<form action=\"showroom\" method=\"GET\">");
        writer.print("<input type=\"hidden\" name=\"method\" value=\"showAllCars\">\n" +
                        "<input type=\"submit\" value=\"Show all cars\">\n");
        writer.println("</form>\n" +
                        "</body>\n" +
                        "</html>");
    }

    private void showAllCars(ServletContext context, PrintWriter writer) throws IOException {
        @SuppressWarnings("unchecked")
        ArrayList<Cars> carList = (ArrayList<Cars>) context.getAttribute(sDataKeepName);
        String carsOutput = "";
        for (Cars car : carList) {
            carsOutput += "Mark : " + car.getMark() +
                         "<br>    Model : " + car.getModel() +
                         "<br>    Color : " + car.getColor() +
                         "<br>    Year of create : " + car.getYearOfCreate() +
                         "<br>";
        }
        if (carsOutput.length() == 0) {
            carsOutput = "<h1>No orders for now!</h1>";
        }

        writer.print(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<body>\n");
        writer.println(carsOutput);
        writer.println("<form action=\"showroom\" method=\"GET\">\n" +
                        "<input type=\"hidden\" name=\"method\" value=\"showAddingForm\">\n" +
                        "<input type=\"submit\" value=\"Show adding form\">\n" +
                        "</body>\n" +
                        "</html>");
    }

    private void addCar(ServletContext context, String mark, String model,  String color, String yearOfCreate) {
        Calendar calendar = Calendar.getInstance();
        int yearToday = calendar.get(Calendar.YEAR);
        int year;
        try {
            year = Integer.parseInt(yearOfCreate);
        } catch (NumberFormatException ex) {
            year = yearToday;
        }
        //1768 - год создания первого автомобиля
        if (model.length() > 0 && model.length() <= 50
                && color.length() > 0 && color.length() <= 50
                && mark.length() > 0 && mark.length() <= 20
                && year >= 1768 && year <= yearToday){
            @SuppressWarnings("unchecked")
            ArrayList<Cars> carList = (ArrayList<Cars>) context.getAttribute(sDataKeepName);
            carList.add(new Cars(mark, model, color, year));
            context.setAttribute(sDataKeepName, carList);
        }
    }

}