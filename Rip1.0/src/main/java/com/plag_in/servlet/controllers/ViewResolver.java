package com.plag_in.servlet.controllers;

import com.plag_in.servlet.entities.Cars;
import com.plag_in.servlet.utils.Logger;
import com.plag_in.servlet.utils.html_templator.HtmlTemplateException;
import com.plag_in.servlet.views.AllCarsView;
import com.plag_in.servlet.views.CarAddingView;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;


/**
 * Created by Владимир on 28.10.2016.
 */
public class ViewResolver {

    private final ServletContext mServletContext;

    public ViewResolver(final ServletContext context) {
        mServletContext = context;
    }

    public void showAddingView(final PrintWriter writer) {
        Logger.log(Logger.LogLevel.ANALYTICS, "showAddingView");
        try {
            writer.print(new CarAddingView(mServletContext).toHtmlText());
        } catch (HtmlTemplateException ex) {
            Logger.log(Logger.LogLevel.ERROR, ex);
        } catch (IOException ex) {
            Logger.log(Logger.LogLevel.ERROR, ex);
        }
    }

    public void showCarsView(final PrintWriter writer, final Collection<Cars> cars){
        Logger.log(Logger.LogLevel.ANALYTICS, "showCarsView");
        try {
            writer.print(new AllCarsView(mServletContext, cars).toHtmlText());
        } catch (HtmlTemplateException ex) {
            Logger.log(Logger.LogLevel.ERROR, ex);
        } catch (IOException ex) {
            Logger.log(Logger.LogLevel.ERROR, ex);
        }
    }
}
