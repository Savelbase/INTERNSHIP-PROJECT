package com.rmn.toolkit.cards.command.testUtil;

public interface EndpointUrlAndConstants {
    String TEST_VALUE = "test";
    String CLIENT_ID = "0d3a68a1-5919-4914-bc20-839fae2480ac";
    String CARD_PRODUCT_ID = "85e73ea5-c904-45d5-8787-1bda24d5db9e";

    String CARD_ORDER_URL = "/api/v1/cards/orders";

    String CARD_URL = "/api/v1/cards";
    String CHANGE_CARD_DAILY_LIMIT = String.format("%s/limit", CARD_URL);
    String CHANGE_CARD_STATUS = String.format("%s/status", CARD_URL);
}
