package com.rmn.toolkit.authorization.dto.request;

public class RegexConstants {
    public static final String MOBILE_PHONE_REGEX = "\\p{Digit}{10}";
    public static final String PASSWORD_REGEX = "((?=.*\\p{Upper})(?=.*\\p{Lower})(?=.*\\p{Digit})|" +
            "(?=.*\\p{Upper})(?=.*\\p{Lower})(?=.*[ \\p{Punct}])|" +
            "(?=.*\\p{Upper})(?=.*\\p{Digit})(?=.*[ \\p{Punct}])|" +
            "(?=.*\\p{Lower})(?=.*\\p{Digit})(?=.*[ \\p{Punct}]))" +
            "[\\p{Upper}\\p{Lower}\\p{Digit} \\p{Punct}]{6,}";
    public static final String PIN_CODE = "\\p{Digit}{6}";
}
