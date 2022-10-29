package com.rmn.toolkit.user.registration.dto.request;

public class RegexConstants {
    public static final String MOBILE_PHONE_REGEX = "\\p{Digit}{10}";
    public static final String PASSPORT_NUMBER_REGEX = "[[A-ZА-ЯЁ][a-zа-яё][\\p{Digit}][ \\p{Punct}]]{1,20}";
    public static final String RF_PASSPORT_NUMBER_REGEX = "\\p{Digit}{10}";
    public static final String VERIFICATION_CODE_REGEX = "\\p{Digit}{6}";
    public static final String USER_NAME_REGEX = "[[A-ZА-ЯЁ][a-zа-яё][\\p{Digit}][ \\p{Punct}]]{2,30}";
    public static final String PASSWORD_REGEX = "((?=.*\\p{Upper})(?=.*\\p{Lower})(?=.*\\p{Digit})|" +
            "(?=.*\\p{Upper})(?=.*\\p{Lower})(?=.*[ \\p{Punct}])|" +
            "(?=.*\\p{Upper})(?=.*\\p{Digit})(?=.*[ \\p{Punct}])|" +
            "(?=.*\\p{Lower})(?=.*\\p{Digit})(?=.*[ \\p{Punct}]))" +
            "[\\p{Upper}\\p{Lower}\\p{Digit} \\p{Punct}]{6,}";
    public static final String QUESTION_AND_ANSWER_REGEX = "[[A-ZА-ЯЁ][a-zа-яё][\\p{Digit}][ \\p{Punct}]]{1,50}";
    public static final String TRUE_VALUE_REGEX = "^true$";
}
