package com.example.cinetime_nepal.common.network;

public class API {
    public static String baseUrl = "https://cinetimenepal.whoamie.com/api/";
//    public static String baseUrl = "http://192.168.1.77:8000/api/";
    public static String signupUrl=baseUrl+"auth/signup";
    public static String loginUrl=baseUrl+"auth/login";
    public static String updateUrl=baseUrl+"auth/update";
    public static String changePwdUrl=baseUrl+"auth/changepwd";
    public static String upcomingMovieUrl  =baseUrl+ "v1/movies/upcoming";
    public static String nowShowingMovieUrl  =baseUrl+ "v1/movies/nowshowing";
    public static String showtimeMovieUrl  =baseUrl+"v1/showtimes/showtime";
    public static String getMovieReviews  =baseUrl+"reviews";
    public static String getMoviesDetail =baseUrl+"v1/movieslist/movies";
}
