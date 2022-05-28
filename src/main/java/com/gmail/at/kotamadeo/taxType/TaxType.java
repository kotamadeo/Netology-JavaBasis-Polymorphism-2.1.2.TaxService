package com.gmail.at.kotamadeo.taxType;

import java.math.BigDecimal;

public class TaxType {
    protected String type;

    public TaxType(String type) {
        this.type = type;
    }

    public BigDecimal calculateTax(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.0));
    }

    public double calculateTax(double amount) {
        return 0.0;
    }

    @Override
    public String toString() {
        return type;
    }
}
