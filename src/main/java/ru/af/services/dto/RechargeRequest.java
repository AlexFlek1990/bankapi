package ru.af.services.dto;

import java.math.BigDecimal;

public class RechargeRequest {
//    private long id;
    private BigDecimal fund;

    public RechargeRequest() {
    }

    public RechargeRequest(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getFund() {
        return fund;
    }
}
