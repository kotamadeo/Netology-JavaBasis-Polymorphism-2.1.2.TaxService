package com.gmail.at.kotamadeo.taxType;

import java.math.BigDecimal;

public class IncomeTaxType extends TaxType {
    public IncomeTaxType(String type) {
        super(type);
    }

    @Override
    public BigDecimal calculateTax(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.13"));
    }

    @Override
    public double calculateTax(double amount) {
        return amount * 0.13;
    }
}
