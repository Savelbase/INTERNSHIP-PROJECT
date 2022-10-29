package com.rmn.toolkit.authorization.testUtil;

public interface EndpointUrlAndConstants {
    String TEST_VALUE = "test";
    Integer TEST_INT_VALUE = 0;
    String MOBILE_PHONE = "9012345678";
    String PASSWORD = "Qwerty1!";
    String INCORRECT_PASSWORD = "Qwerty1";
    String PIN_CODE = "123456";
    String INCORRECT_PIN_CODE = "000000";
    String USER_ID = "67c52684-1070-42a8-826e-672463d0919a";

    String URL = "/api/v1/auth";
    String LOGIN_WITH_PHONE_AND_PASSWORD = String.format("%s/login/password", URL);
    String LOGIN_WITH_PHONE_AND_PIN = String.format("%s/login/pin", URL);
    String REFRESH_TOKEN = String.format("%s/refresh", URL);
    String LOGOUT = String.format("%s/logout", URL);
}
