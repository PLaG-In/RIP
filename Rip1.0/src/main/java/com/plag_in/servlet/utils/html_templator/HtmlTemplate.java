package com.plag_in.servlet.utils.html_templator;

import com.sun.istack.internal.NotNull;
import com.plag_in.servlet.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Владимир on 28.10.2016.
 */
public final class HtmlTemplate {

    private static final String PARAM_INDICATOR = "%#";
    private static final String SINGLE_PARAM = PARAM_INDICATOR + "s";
    private static final String MULTIPLE_PARAM = PARAM_INDICATOR + "m";

    private ArrayList<String> mParsedTemplate;
    private final LinkedList<String> mSingleParameters;
    private final LinkedList<ArrayList<String>> mMultipleParameters;

    private HtmlTemplate() {
        mParsedTemplate = new ArrayList<>();
        mSingleParameters = new LinkedList<>();
        mMultipleParameters = new LinkedList<>();
    }

    public String getHtmlText() {
        return String.join("", mParsedTemplate);
    }

    @NotNull
    public static Builder newBuilder(final String templateFileName) throws IOException {
        return new HtmlTemplate().new Builder(templateFileName);
    }

    private void addTemplateString(final String templateString) {
        mParsedTemplate = parseTemplate(templateString);
    }

    private ArrayList<String> parseTemplate(String templateString) {
        ArrayList<String> parsedTemplate = new ArrayList<>();
        while (templateString.contains(PARAM_INDICATOR)) {
            int index = templateString.indexOf(PARAM_INDICATOR);
            parsedTemplate.add(templateString.substring(0, index));
            parsedTemplate.add(templateString.substring(index, index + SINGLE_PARAM.length()));
            templateString = templateString.substring(index + SINGLE_PARAM.length());
        }
        parsedTemplate.add(templateString);
        return parsedTemplate;
    }

    private void applyParameters() throws HtmlTemplateException {
        for (int i = 0; i < mParsedTemplate.size(); ++i) {
            if (mParsedTemplate.get(i).equals(SINGLE_PARAM)) {
                String param = mSingleParameters.poll();
                if (param == null) {
                    throw new HtmlTemplateException("Not enough single parameters");
                }
                mParsedTemplate.set(i, param);
            } else if (mParsedTemplate.get(i).equals(MULTIPLE_PARAM)) {
                ArrayList<String> params = mMultipleParameters.poll();
                if (params == null) {
                    throw new HtmlTemplateException("Not enough multiple parameters");
                }
                mParsedTemplate.set(i, String.join("", params));
            }
        }
        if (mSingleParameters.size() > 0) {
            throw new HtmlTemplateException("Too much single parameters");
        }
        if (mMultipleParameters.size() > 0) {
            throw new HtmlTemplateException("Too much multiple parameters");
        }
    }

    public class Builder {
        private Builder(final String templateFileName) throws IOException {
            HtmlTemplate.this.addTemplateString(FileUtils.readFileToString(templateFileName));
        }

        public Builder addSingleParameter(final String text) {
            HtmlTemplate.this.mSingleParameters.add(text);
            return this;
        }

        public Builder addSingleParameters(final ArrayList<String> params) {
            HtmlTemplate.this.mSingleParameters.addAll(params);
            return this;
        }

        public Builder addMultipleParameter(final ArrayList<String> params) {
            HtmlTemplate.this.mMultipleParameters.add(params);
            return this;
        }

        public Builder addMultipleParametes(final ArrayList<ArrayList<String>> params) {
            HtmlTemplate.this.mMultipleParameters.addAll(params);
            return this;
        }

        public HtmlTemplate build() throws HtmlTemplateException {
            HtmlTemplate.this.applyParameters();
            return HtmlTemplate.this;
        }
    }


}
