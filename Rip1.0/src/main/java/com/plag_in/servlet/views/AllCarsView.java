package com.plag_in.servlet.views;

/**
 * Created by Владимир on 28.10.2016.
 */

import com.plag_in.servlet.entities.Cars;
import com.plag_in.servlet.utils.html_templator.HtmlTemplate;
import com.plag_in.servlet.utils.html_templator.HtmlTemplateException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AllCarsView extends View {

    private static final String PATH_TO_HTML =
            "web-inf/classes/html/pages/AllCarsPage.html_t";
    private static final String PATH_TO_DRINK_INFO_VIEW =
            "web-inf/classes/html/views/CarInfoView.html_t";
    private static final String PATH_TO_EMPTY_DRINKS_VIEW =
            "web-inf/classes/html/views/EmptyCarsView.html_t";

    private final ServletContext mContext;

    public AllCarsView(final ServletContext context, final Collection<Cars> cars)
            throws IOException, HtmlTemplateException {
        mContext = context;
        HtmlTemplate.Builder builder = HtmlTemplate.newBuilder(mContext.getRealPath(PATH_TO_HTML));
        ArrayList<String> pageParameters = new ArrayList<>();
        if (cars.size() == 0) {
            pageParameters.add(HtmlTemplate.
                    newBuilder(mContext.getRealPath(PATH_TO_EMPTY_DRINKS_VIEW)).
                    build().getHtmlText());
        } else {
            for (Cars car : cars) {
                pageParameters.add(createDrinkView(car));
            }
        }
        builder.addMultipleParameter(pageParameters);
        mHtmlText = builder.build().getHtmlText();
    }

    private String createDrinkView(final Cars car)
            throws IOException, HtmlTemplateException {
        return HtmlTemplate.newBuilder(mContext.getRealPath(PATH_TO_DRINK_INFO_VIEW))
                .addSingleParameter(car.getMark())
                .addSingleParameter(car.getModel())
                .addSingleParameter(car.getColor())
                .addSingleParameter(car.getYearOfCreate().toString())
                .build()
                .getHtmlText();
    }
}
