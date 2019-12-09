package com.example.cinetime_nepal.common.network;

public class API {
    public static String baseUrl = "http://192.168.1.77:8000/api/auth/";
    public static String signupUrl=baseUrl+"signup";
    public static String loginUrl=baseUrl+"login";
    public static String updateUrl=baseUrl+"update";
    private static String baseUrlMovie  = "http://192.168.1.77:8000/api/v1/movies/";
    public static String upcomingMovieUrl  =baseUrlMovie+ "upcoming";
    public static String nowShowingMovieUrl  =baseUrlMovie+ "nowshowing";
}
