package com.rmn.toolkit.credits.query.model.projection;

import com.rmn.toolkit.credits.query.model.Payment;

import java.util.List;

public interface PayGraphView {
    List<Payment> getPaymentList();
}
