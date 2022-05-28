package com.gmail.at.kotamadeo.program;

import com.gmail.at.kotamadeo.taxService.Bill;
import com.gmail.at.kotamadeo.taxService.TaxService;
import com.gmail.at.kotamadeo.taxType.IncomeTaxType;
import com.gmail.at.kotamadeo.taxType.ProgressiveTaxType;
import com.gmail.at.kotamadeo.taxType.VATTaxType;
import com.gmail.at.kotamadeo.utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    private final Scanner scanner = new Scanner(System.in);
    private final TaxService taxService = new TaxService();
    private final List<Bill> billsDouble = new ArrayList<>();
    private final List<Bill> billsBigDecimal = new ArrayList<>();
    private String input;
    private String[] allInput;

    public void start() {
        while (true) {
            try {
                System.out.println(Utils.ANSI_BLUE + "Выберите вариант реализации:\n0 или выход: чтобы выйти." +
                        "\n1. Double.\n2. BigDecimal." + Utils.ANSI_RESET);
                input = scanner.nextLine();
                if ("выход".equalsIgnoreCase(input) || "0".equals(input)) {
                    scanner.close();
                    break;
                } else {
                    var operationNumber = Integer.parseInt(input);
                    switch (operationNumber) {
                        case 1:
                            doubleRealization();
                            break;
                        case 2:
                            bigDecimalRealization();
                            break;
                        default:
                            System.out.println(Utils.ANSI_RED + "Вы ввели неверный номер операции!" + Utils.ANSI_RESET);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(Utils.ANSI_RED + "Ошибка ввода!" + Utils.ANSI_RESET);
            }
        }
    }

    private static void printBillsList(List<Bill> list) {
        if (!list.isEmpty()) {
            for (var i = 0; i < list.size(); i++) {
                System.out.printf("%s%s. %s%s%n", Utils.ANSI_PURPLE, (i + 1), list.get(i), Utils.ANSI_RESET);
            }
        } else {
            System.out.println(Utils.ANSI_RED + "Список пуст!" + Utils.ANSI_RESET);
        }
    }

    private void doubleRealization() {
        while (true) {
            try {
                printMenu();
                input = scanner.nextLine();
                if ("выход".equalsIgnoreCase(input) || "0".equals(input)) {
                    break;
                } else {
                    var operationNumber = Integer.parseInt(input);
                    switch (operationNumber) {
                        case 1:
                            System.out.println(Utils.ANSI_BLUE + "Введите сумму налога и тип налога " +
                                    "(НДС, подоходный или прогрессивный) через пробел:" + Utils.ANSI_RESET);
                            allInput = scanner.nextLine().split(" ");
                            int taxAmount = Integer.parseInt(allInput[0]);
                            String taxTypeChoose = allInput[1];
                            if (taxTypeChoose.equalsIgnoreCase("подоходный")) {
                                billsDouble.add(new Bill(taxAmount, new IncomeTaxType("подоходный"),
                                        taxService));
                            } else if (taxTypeChoose.equalsIgnoreCase("НДС")) {
                                billsDouble.add(new Bill(taxAmount, new VATTaxType("НДС"), taxService));
                            } else if (taxTypeChoose.equalsIgnoreCase("прогрессивный")) {
                                billsDouble.add(new Bill(taxAmount, new ProgressiveTaxType("прогрессивный"),
                                        taxService));
                            } else {
                                System.out.println(Utils.ANSI_RED + "Неверный ввод типа налога!" + Utils.ANSI_RESET);
                            }
                            break;
                        case 2:
                            System.out.println(Utils.ANSI_BLUE + "Выберите счет на оплату, который хотите " +
                                    "удалить " + Utils.ANSI_RESET);
                            printBillsList(billsDouble);
                            input = scanner.nextLine();
                            billsDouble.remove(Integer.parseInt(input) - 1);
                            break;
                        case 3:
                            for (Bill payments : billsDouble) {
                                payments.payTaxesDouble();
                            }
                            break;
                        case 4:
                            System.out.println(Utils.ANSI_BLUE + "Список double квитанций об оплате:" +
                                    Utils.ANSI_RESET);
                            printBillsList(billsDouble);
                            break;
                    }
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println(Utils.ANSI_RED + "Ошибка ввода!" + Utils.ANSI_RESET);
            }
        }
    }

    private void bigDecimalRealization() {
        while (true) {
            try {
                printMenu();
                input = scanner.nextLine();
                if ("выход".equalsIgnoreCase(input) || "0".equals(input)) {
                    break;
                } else {
                    var operationNumber = Integer.parseInt(input);
                    switch (operationNumber) {
                        case 1:
                            System.out.println(Utils.ANSI_BLUE + "Введите сумму налога и тип налога " +
                                    "(НДС, подоходный или прогрессивный) через пробел:" + Utils.ANSI_RESET);
                            allInput = scanner.nextLine().split(" ");
                            int taxAmount = Integer.parseInt(allInput[0]);
                            String taxTypeChoose = allInput[1];
                            if (taxTypeChoose.equalsIgnoreCase("подоходный")) {
                                billsBigDecimal.add(new Bill(new BigDecimal(taxAmount),
                                        new IncomeTaxType("подоходный"), taxService));
                            } else if (taxTypeChoose.equalsIgnoreCase("НДС")) {
                                billsBigDecimal.add(new Bill(new BigDecimal(taxAmount), new VATTaxType("НДС"),
                                        taxService));
                            } else if (taxTypeChoose.equalsIgnoreCase("прогрессивный")) {
                                billsBigDecimal.add(new Bill(new BigDecimal(taxAmount),
                                        new ProgressiveTaxType("прогрессивный"), taxService));
                            } else {
                                System.out.println(Utils.ANSI_RED + "Неверный ввод типа налога!" + Utils.ANSI_RESET);
                            }
                            break;
                        case 2:
                            System.out.println(Utils.ANSI_BLUE + "Выберите счет на оплату, который хотите удалить " +
                                    Utils.ANSI_RESET);
                            printBillsList(billsBigDecimal);
                            input = scanner.nextLine();
                            billsBigDecimal.remove(Integer.parseInt(input) - 1);
                            break;
                        case 3:
                            for (Bill payments : billsBigDecimal) {
                                payments.payTaxesBigDecimal();
                            }
                            break;
                        case 4:
                            System.out.println(Utils.ANSI_BLUE + "Список BigDecimal квитанций об оплате:" +
                                    Utils.ANSI_RESET);
                            printBillsList(billsBigDecimal);
                            break;
                        default:
                            System.out.println(Utils.ANSI_RED + "Вы ввели неверный номер операции!" + Utils.ANSI_RESET);
                    }
                }
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println(Utils.ANSI_RED + "Ошибка ввода!" + Utils.ANSI_RESET);
            }
        }
    }

    private static void printMenu() {
        System.out.println(Utils.ANSI_YELLOW + "Эта программа способна оплатить налог в следующих видах налогов: " +
                "НДС, прогрессивный налог, подоходный налог." + Utils.ANSI_RESET);
        System.out.println(Utils.ANSI_PURPLE + "Возможные команды программы:" + Utils.ANSI_RESET);
        System.out.println("0 или выход: чтобы выйти из программы.");
        System.out.println("1: чтобы ввести сумму доходов и тип налога, чтобы получить счет на оплату.");
        System.out.println("2: чтобы  удалить счет на оплату.");
        System.out.println("3: чтобы  заплатить налоги.");
        System.out.println("4: чтобы  получить квитанцию об оплате.");
        System.out.print(">>>>>>>");
    }
}
