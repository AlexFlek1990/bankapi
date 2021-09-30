package ru.af.services.dto;

import java.math.BigDecimal;

public class RechargeRequest {
    private long id;
    private BigDecimal fund;

    public RechargeRequest(long id, BigDecimal fund) {
        this.id = id;
        this.fund = fund;
    }

    public BigDecimal getFund() {
        return fund;
    }
}
