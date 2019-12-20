package com.example.cinetime_nepal.common.network;

public class API {
    public static String baseUrl = "https://cinetimenepal.whoamie.com/api/";
    public static String signupUrl=baseUrl+"auth/signup";
    public static String loginUrl=baseUrl+"auth/login";
    public static String updateUrl=baseUrl+"auth/update";
    public static String changePwdUrl=baseUrl+"auth/changepwd";
    private static String baseUrlMovie  = "https://cinetimenepal.whoamie.com/api/v1/";
    public static String upcomingMovieUrl  =baseUrlMovie+ "movies/upcoming";
    public static String nowShowingMovieUrl  =baseUrlMovie+ "movies/nowshowing";
    public static String showtimeMovieUrl  =baseUrlMovie+"showtimes/showtime";
    public static String getMovieReviews  ="reviews";
}
