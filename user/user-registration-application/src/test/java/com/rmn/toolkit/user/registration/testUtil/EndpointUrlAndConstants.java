package com.rmn.toolkit.user.registration.testUtil;

public interface EndpointUrlAndConstants {
    String TEST_VALUE = "test";
    String CLIENT_MOBILE_PHONE = "9012345678";
    String ADMIN_MOBILE_PHONE = "9077777777";
    String VERIFICATION_CODE = "123456";
    String PASSWORD = "Qwerty1!";
    String PASSPORT_NUMBER = "7777123456";
    String CLIENT_ID = "67c52684-1070-42a8-826e-672463d0919a";
    String VERIFICATION_CODE_ID = "82cd73ab-4355-466f-bd88-3d648358fd24";

    String URL = "/api/v1/sign-up/clients";
    String CHECK_MOBILE_PHONE_AND_GENERATE_TOKEN = String.format("%s/phone", URL);
    String CODE_GENERATION = String.format("%s/code/generation", URL);
    String CODE_VERIFICATION = String.format("%s/code/verification", URL);
    String SAVE_PASSWORD = String.format("%s/password", URL);
    String SAVE_PASSPORT_NUMBER = String.format("%s/%s/passport", URL, CLIENT_ID);
    String SAVE_FULL_NAME = String.format("%s/%s/full-name", URL, CLIENT_ID);
    String SAVE_SECURITY_QUESTION_ANSWER = String.format("%s/%s/security/question-answer", URL, CLIENT_ID);
    String ACCEPT_RBSS_RULES = String.format("%s/%s/rbss", URL, CLIENT_ID);

    String RULES_URL = "/api/v1/rules";
    String GET_PRIVACY_POLICY = String.format("%s/privacy-policy", RULES_URL);
    String GET_RBSS_RULES = String.format("%s/rbss", RULES_URL);
}
