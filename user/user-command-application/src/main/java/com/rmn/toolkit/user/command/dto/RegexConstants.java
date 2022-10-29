package com.rmn.toolkit.user.command.dto;

public class RegexConstants {
    public static final String PASSPORT_NUMBER_REGEX = "[[A-ZА-ЯЁ][a-zа-яё][\\p{Digit}][ \\p{Punct}]]{1,20}";
    public static final String PASSWORD_REGEX = "((?=.*\\p{Upper})(?=.*\\p{Lower})(?=.*\\p{Digit})|" +
            "(?=.*\\p{Upper})(?=.*\\p{Lower})(?=.*[ \\p{Punct}])|" +
            "(?=.*\\p{Upper})(?=.*\\p{Digit})(?=.*[ \\p{Punct}])|" +
            "(?=.*\\p{Lower})(?=.*\\p{Digit})(?=.*[ \\p{Punct}]))" +
            "[\\p{Upper}\\p{Lower}\\p{Digit} \\p{Punct}]{6,}";
    public static final String CODE_REGEX = "\\p{Digit}{6}";
}
