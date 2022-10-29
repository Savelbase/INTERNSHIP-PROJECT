package com.rmn.toolkit.credits.query.testUtil;

public interface EndpointUrlAndConstants {
    String TEST_VALUE = "test";

    String URL = "/api/v1/credits";
    String GET_ALL_CREDITS = URL;
    String GET_ALL_CREDITS_PRODUCTS = String.format("%s/products", URL);
    String GET_CREDIT_PRODUCT_BY_ID = String.format("%s/products/%s", URL, TEST_VALUE);
    String GET_CREDIT_PRODUCT_AGREEMENT_BY_ID = String.format("%s/products/%s/agreement", URL, TEST_VALUE);
    String GET_ALL_CREDITS_ORDERS_BY_CLIENT_ID = String.format("/api/v1/clients/%s/credits/orders", TEST_VALUE);
    String GET_CREDIT_ORDER_BY_ID = String.format("%s/orders/%s", URL, TEST_VALUE);
}
