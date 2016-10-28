package com.plag_in.servlet.views;

/**
 * Created by Владимир on 28.10.2016.
 */
public abstract class View {

    protected String mHtmlText;

    public View()
    {
    }

    public String toHtmlText() {
        return mHtmlText;
    }
}

