package com.gmail.at.kotamadeo.taxType;

import java.math.BigDecimal;

public class ProgressiveTaxType extends TaxType {
    public ProgressiveTaxType(String type) {
        super(type);
    }

    @Override
    public BigDecimal calculateTax(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(100_000)) == -1) {
            return amount.multiply(new BigDecimal("0.1"));
        } else {
            return amount.multiply(new BigDecimal("0.15"));
        }
    }

    @Override
    public double calculateTax(double amount) {
        if (amount < 100_000) {
            return amount * 0.1;
        } else {
            return amount * 0.15;
        }
    }
}
