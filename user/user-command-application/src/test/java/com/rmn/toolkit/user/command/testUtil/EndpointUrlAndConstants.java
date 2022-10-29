package com.rmn.toolkit.user.command.testUtil;

public interface EndpointUrlAndConstants {
    String TEST_VALUE = "test";
    String TEST_EMAIL = "admin@andersenlab.com";
    Integer TEST_INT_VALUE = 0;
    String CODE = "123456";
    String PASSWORD = "Qwerty1!";
    String PASSPORT_NUMBER = "7777123456";
    String USER_ID = "67c52684-1070-42a8-826e-672463d0919a";

    String URL = "/api/v1/users";
    String CREATE_PIN_CODE = String.format("%s/pin", URL);
    String CHANGE_EMAIL = String.format("%s/email", URL);
    String CHANGE_NOTIFICATIONS_STATUS = String.format("%s/notifications", URL);

    String CHANGE_SECURITY_QUESTION_ANSWER = String.format("%s/security/question-answer", URL);
    String CHANGE_SECURITY_PASSWORD = String.format("%s/security/password", URL);

    String DELETE_USER = String.format("%s/%s", URL, USER_ID);
    String APPROVE_BANK_CLIENT = String.format("%s/approval", URL);

    String PASSWORD_RECOVERY_URL = "/api/v1/password/recovery";
    String PASSWORD_RECOVERY_CODE_GENERATION = String.format("%s/code/generation", PASSWORD_RECOVERY_URL);
    String PASSWORD_RECOVERY_CODE_VERIFICATION = String.format("%s/code/verification", PASSWORD_RECOVERY_URL);
    String PASSWORD_RECOVERY = PASSWORD_RECOVERY_URL;

    String PIN_RESET_URL = "/api/v1/pin/reset";
    String PIN_RESET_CODE_GENERATION = String.format("%s/code/generation", PIN_RESET_URL);
    String PIN_RESET_CODE_VERIFICATION = String.format("%s/code/verification", PIN_RESET_URL);
    String PIN_RESET = PIN_RESET_URL;
}
