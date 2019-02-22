package com.dac.spring.constants;

public class CustomerSignUpConst {

    public static final String SUCCESS = "You signed up successfully";
    public static final String EMAIL_EXIST = "This account was signed up";
    public static final String PASSWORD_WRONG_FORMAT = "Password is not correct";
    public static final String REGEX = "^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*])(?=\\\\S+).*";
}
