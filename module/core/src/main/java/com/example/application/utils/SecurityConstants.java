package com.example.application.utils;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String SIGN_IN_URL = "/login";
    public static final String ROLE = "ROLE_";

    //Exception
    public static final String EXCEPTION_DELETE = "User was deleted";
    public static final String EXCEPTION_NOT_EXIST = "User not exist";
    public static final String EXCEPTION_EXIST = "User exist";
    public static final String EXCEPTION_ACCOUNT = "Password/Usernam is wrong";

    //Action Successful
    public static final String APPROVE_SUCCESSFUL = "Approve successful";
    public static final String DELETE_SUCCESSFUL = "Delete successful";


}
