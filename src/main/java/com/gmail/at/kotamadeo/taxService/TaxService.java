package com.gmail.at.kotamadeo.taxService;

import com.gmail.at.kotamadeo.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxService {

    public void payOut(double taxAmount) {
        System.out.printf("%sНалог в размере %.2f руб. оплачен!%s%n", Utils.ANSI_GREEN, taxAmount, Utils.ANSI_RESET);
    }

    public void payOut(BigDecimal taxAmount) {
        System.out.printf("%sНалог в размере %s руб. оплачен!%s%n", Utils.ANSI_GREEN,
                taxAmount.setScale(2, RoundingMode.HALF_UP), Utils.ANSI_RESET);
    }
}
