package com.rmn.toolkit.bankinfoapplication.model.type;

import lombok.Getter;

public enum ServiceName {
    DEPOSIT("3dab3c06-0842-11ed-861d-0242ac120002"),
    DEPOSIT_WITHOUT_CARD("42167b84-0842-11ed-861d-0242ac120002"),
    CURRENCY_EXCHANGE("486d093a-0842-11ed-861d-0242ac120002"),
    WITHDRAW("4c4f3794-0842-11ed-861d-0242ac120002"),
    TRANSFER("509a924e-0842-11ed-861d-0242ac120002"),
    PAY("5630ba26-0842-11ed-861d-0242ac120002"),
    CONSULTATION("5b00cd5c-0842-11ed-861d-0242ac120002"),
    RAMP("60cb24e4-0842-11ed-861d-0242ac120002"),
    EXOTIC_CURRENCY_EXCHANGE("65dd8580-0842-11ed-861d-0242ac120002"),
    INSURANCE("6a13eac2-0842-11ed-861d-0242ac120002");

    @Getter
    private String id ;
    ServiceName(String id){
        this.id = id;
    }
}
