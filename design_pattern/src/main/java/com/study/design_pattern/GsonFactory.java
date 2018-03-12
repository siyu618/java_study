package com.study.design_pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
    private static final GsonBuilder gsonBuilder = (new GsonBuilder()).disableHtmlEscaping();
    private static final Gson gsonNonPretty;
    private static final Gson gsonPretty;
    private static final Gson gson;

    public GsonFactory() {
    }

    public static Gson getPrettyGson() {
        return gsonPretty;
    }

    public static Gson getNonPrettyGson() {
        return gsonNonPretty;
    }

    public static Gson getDefaultGson() {
        return gson;
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new RuntimeException("Clone for class " + this.getClass() + " Disabled");
    }

    static {
        gsonNonPretty = gsonBuilder.create();
        gsonPretty = gsonBuilder.setPrettyPrinting().create();
        gson = new Gson();
    }
}