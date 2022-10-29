package com.rmn.toolkit.mediastorage.query.testUtil;

public interface EndpointUrlAndConstants {
    String TEST_VALUE = "test";
    String CLIENT_ID = "67c52684-1070-42a8-826e-672463d0919a";
    String GET_FILE_BY_CLIENT_ID = String.format("/api/v1/clients/%s/avatar", CLIENT_ID);
}
