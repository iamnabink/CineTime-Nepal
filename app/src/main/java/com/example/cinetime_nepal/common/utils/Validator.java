package com.example.cinetime_nepal.common.utils;

public class Validator {
    public static Boolean isEmailValid(String email){
        String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        return email.matches(emailRegex);
    }
}
