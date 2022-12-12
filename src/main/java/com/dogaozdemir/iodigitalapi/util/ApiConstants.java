package com.dogaozdemir.iodigitalapi.util;

public class ApiConstants {

    public static final String API_SEARCH_TED_SUMMARY = "Search Ted Talks with parameters";
    public static final String API_SEARCH_TED_DESCRIPTION = "Filter Ted Talks with author,likes,title and views parameters. If none of them selected,you will show max 30 results";
    public static final String API_SEARCH_TED_200 = "Ted Talks successfully listed";

    public static final String API_CREATE_TED_DESCRIPTION = "Saves Ted Talk to the database.";
    public static final String API_CREATE_TED_SUMMARY = "Create new Ted Talk information";
    public static final String API_CREATE_TED_201 = "Ted Talk successfully created";
    public static final String API_CREATE_TED_404 = "Not found the user";

    public static final String API_UPDATE_TED_DESCRIPTION = "Updates Ted Talk if id exist. Author,date,likes,link,title and views parameters are updatable";
    public static final String API_UPDATE_TED_SUMMARY = "Update Ted Talk";
    public static final String API_UPDATE_TED_200 = "Ted Talk successfully updated";
    public static final String API_UPDATE_TED_404 = "Ted Talk not found";

    public static final String API_DELETE_TED_DESCRIPTION = "Delete Ted Talk from database if id exists";
    public static final String API_DELETE_TED_SUMMARY = "Delete Ted Talk";
    public static final String API_DELETE_TED_200 = "Ted Talk successfully deleted";

}
