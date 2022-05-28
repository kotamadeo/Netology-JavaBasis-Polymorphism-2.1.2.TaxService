package com.gmail.at.kotamadeo.taxType;

import java.math.BigDecimal;

public class VATTaxType extends TaxType {
    public VATTaxType(String type) {
        super(type);
    }

    @Override
    public BigDecimal calculateTax(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.18"));
    }

    @Override
    public double calculateTax(double amount) {
        return amount * 0.18;
    }
}
