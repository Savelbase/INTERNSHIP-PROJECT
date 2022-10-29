package com.rmn.toolkit.cards.query.testUtil;

public interface EndpointUrlAndConstants {
    String TEST_VALUE = "test";
    String TEST_ID = "test_id";

    String URL = "/api/v1/cards";

    String GET_CARD_PRODUCT_BY_CARD_ID = String.format("%s/products/%s", URL, TEST_VALUE);
    String GET_CARD_PRODUCTS = String.format("%s/products", URL);
    String GET_CARD_BY_CARD_ID = String.format("%s/%s", URL, TEST_VALUE);
    String GET_CARD_REQUISITES_BY_CARD_ID = String.format("%s/%s/requisites", URL, TEST_VALUE);

    String GET_ALL_CARDS_ORDERS_BY_CLIENT_ID = String.format("/api/v1/clients/%s/cards/orders", TEST_VALUE);
    String GET_CARD_ORDER = String.format("%s/orders/%s", URL, TEST_VALUE);

    String GET_ALL_CARD_STATEMENTS = String.format("%s/statements", URL);
    String GET_ALL_CARD_RECEIPTS = String.format("%s/receipts", URL);
    String SEARCH_ALL_CARD_RECEIPTS_BY_TRANSACTION_TYPE = String.format("%s/receipts/search", URL);
}
