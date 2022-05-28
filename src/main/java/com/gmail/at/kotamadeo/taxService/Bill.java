package com.gmail.at.kotamadeo.taxService;

import com.gmail.at.kotamadeo.taxType.TaxType;

import java.math.BigDecimal;

public class Bill {
    private final TaxType taxType;
    private final TaxService taxService;
    private double amountDouble;
    private BigDecimal amountBigDecimal;
    private double amountTax;

    public Bill(double amountDouble, TaxType taxType, TaxService taxService) {
        this.amountDouble = amountDouble;
        this.taxService = taxService;
        this.taxType = taxType;
    }

    public Bill(BigDecimal amountBigDecimal, TaxType taxType, TaxService taxService) {
        this.amountBigDecimal = amountBigDecimal;
        this.taxService = taxService;
        this.taxType = taxType;
    }

    public void payTaxesDouble() {
        double taxAmount = taxType.calculateTax(amountDouble);
        taxService.payOut(taxAmount);
        amountTax = taxAmount;
    }

    public void payTaxesBigDecimal() {
        BigDecimal taxAmount = taxType.calculateTax(amountBigDecimal);
        taxService.payOut(taxAmount);
        amountTax = taxAmount.doubleValue();
    }

    @Override
    public String toString() {
        if (amountDouble != 0.0) {
            return "Сумма дохода: " + amountDouble + " руб. тип налога: " + taxType + ", сумма налога: " + amountTax;
        } else if (amountBigDecimal != null) {
            return "Сумма дохода: " + amountBigDecimal + " руб. тип налога: " + taxType + ", сумма налога: " +
                    amountTax;
        } else {
            return "";
        }
    }
}
