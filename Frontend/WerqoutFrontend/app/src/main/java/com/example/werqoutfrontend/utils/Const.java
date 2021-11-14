package com.example.werqoutfrontend.utils;

/**
 * This class stores all of the urls necessary to make requests to the controllers on the backend
 * @author Colin
 * @author Angelo
 */
public class Const {
    /**
     * The url used for requesting information about an athlete
     */
    public static final String URL_JSON_REQUEST_ATHLETES = "http://coms-309-034.cs.iastate.edu:8080/athletes";
    /**
     * The url used for requesting information about a coach
     */
    public static final String URL_JSON_REQUEST_COACHES = "http://coms-309-034.cs.iastate.edu:8080/coaches/";

    /**
     * The url used for requesting information about a team
     */
    public static final String URL_JSON_REQUEST_TEAMS = "http://coms-309-034.cs.iastate.edu:8080/teams";
    /**
     * The url used for requesting information about a gym owner
     */
    public static final String URL_JSON_REQUEST_GYMOWNER = "http://coms-309-034.cs.iastate.edu:8080/gymowner";
    /**
     * The url used for requesting information about users
     */
    public static final String URL_JSON_REQUEST_USERS = "http://coms-309-034.cs.iastate.edu:8080/";
    /**
     * The url used for requesting weather information
     */
    public static final String WEATHER_API = "http://api.openweathermap.org/data/2.5/weather?q=" +
            "Chicago&appid=0aed3275c5290a61a86f326016ddefab";
    /**
     * The url used for requesting the default profile image
     */
    public static final String DEFAULT_PROFILE_PICTURE = "https://prd-wret.s3.us-west-2.amazonaws.com" +
            "/assets/palladium/production/s3fs-public/thumbnails/image/placeholder-profile_1.png";
    /**
     * A url that is used for testing in conjunction with postman
     */
    public static final String POSTMAN_TEST_URL = "https://17a252f8-4402-4aee-8eb4-f65bdd901a56.mock.pstmn.io/";
    /**
     * The current url being used for requests
     */
    public static String CURRENT_URL = "";
}
