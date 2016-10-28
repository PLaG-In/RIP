package com.plag_in.servlet.views;

import com.plag_in.servlet.utils.html_templator.HtmlTemplate;
import com.plag_in.servlet.utils.html_templator.HtmlTemplateException;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Created by Владимир on 28.10.2016.
 */
public class CarAddingView extends View {

    private static final String PATH_TO_HTML = "web-inf/classes/html/pages/AddCarPage.html_t";

    public CarAddingView(final ServletContext context) throws IOException, HtmlTemplateException {
        String realPath = context.getRealPath(PATH_TO_HTML);
        mHtmlText = HtmlTemplate
                .newBuilder(realPath)
                .build()
                .getHtmlText();
    }
}
