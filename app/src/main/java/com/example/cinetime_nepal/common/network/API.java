package com.example.cinetime_nepal.common.network;

public class API {
    public static String baseUrl = "https://cinetimenepal.whoamie.com/api/";
    public static String signupUrl=baseUrl+"auth/signup";
    public static String loginUrl=baseUrl+"auth/login";
    public static String updateUrl=baseUrl+"auth/update";
    public static String changePwdUrl=baseUrl+"auth/changepwd";
    private static String baseUrlMovie  = "https://cinetimenepal.whoamie.com/api/";
    public static String upcomingMovieUrl  =baseUrlMovie+ "v1/movies/upcoming";
    public static String nowShowingMovieUrl  =baseUrlMovie+ "v1/movies/nowshowing";
    public static String showtimeMovieUrl  =baseUrlMovie+"v1/showtimes/showtime";
    public static String getMovieReviews  =baseUrlMovie+"reviews";
}
