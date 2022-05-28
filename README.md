# **Задача № 2 Задача от бухгалтеров**

## **Цель**:

1. Бухгалтерская программа должна уметь проводить операции c различными агентами, как c физическими/юридическими лицами, так и с иностранными компаниями. С некоторых операций налог платить не нужно, некоторые облагаются подоходным налогом, с некоторых необходимо уплатить ```НДС```. Необходимо расширить функциональность класса **Bill** возможностью работы с различными системами налогообложения.
2. Создать нескольких счетов и расчет налогов для них.

### *Пример*:

``` Пример 1
Выберите вариант реализации:
0 или выход: чтобы выйти;
1. Double;
2. BigDecimal.
1

Эта программа способна оплатить налог в следующих видах налогов: НДС, прогрессивный налог, подоходный налог.
Возможные команды программы:
0 или выход: чтобы выйти из программы.
1: чтобы ввести сумму доходов и тип налога, чтобы получить счет на оплату.
2: чтобы  удалить счет на оплату.
3: чтобы  заплатить налоги.
4: чтобы  получить квитанцию об оплате.
>>>>>>>
1
Введите сумму налога и тип налога (НДС, подоходный или прогрессивный) через пробел:
300000 прогрессивный

3
Налог в размере 45000,00 руб. оплачен!

4
1. Сумма дохода: 300000.0 руб. тип налога: прогрессивный, сумма налога: 45000.0

2
Выберите счет на оплату, который хотите удалить 
1. Сумма дохода: 300000.0 руб. тип налога: прогрессивный, сумма налога: 45000.0
1
```

### **Моя реализация**:

1. Реализация осуществлена в парадигме ООП.
2. Создал структуру классов:

* **Program** - отвечающий за запуск программы, путем инициирования метода *start()* с инициированием внутри себя
  вспомогательных методов: 
  * *printMenu()* - выводит меню на экран; 
  * *bigDecimalRealization()* - реализация программы с использованием ```BigDecimal```; 
  * *doubleRealization()* - реализация программы с использованием ```double```; 
  * *printBillsList()* - выводит список счетов на оплату.

#### Класс **Program**:
``` java
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
```

* **Bill** - описывающий работу счета об оплате. Имеет следующие важные ```void``` методы: *payTaxesDouble()*, который моделирует оплату счета для ```double``` реализации и *payTaxesBigDecimal()*,который моделирует оплату счета для ```BigDecimal```. Также класс имеет переопределенный метод *toString()*;

#### Класс **Bill**:
``` java   
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
        if (amountDouble != 0) {
            return "Сумма дохода: " + amountDouble + " руб. тип налога: " + taxType + ", сумма налога: " + amountTax;
        } else if (amountBigDecimal != null) {
            return "Сумма дохода: " + amountBigDecimal + " руб. тип налога: " + taxType + ", сумма налога: " + amountTax;
        } else {
            return "";
        }
    }
}
```

* **TaxType** - класс описывающий работу систем налогообложения. Являеется суперклассом для классов: **IncomeTaxType, VATTaxType и ProgressiveTaxType**. Имеет два перегруженных метода *calculateTax()* и переопреденный метод *toString()*;

### Класс **TaxType**:
``` java
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
```
* **IncomeTaxType** - подоходный налог, реализованный посредством переопределения методов **TaxType**;
* **VATTaxType** - НДС, реализованный посредством переопределния методов ***TaxType**;
* **ProgressiveTaxType** - прогрессивный налог, реализованный посредством переопределния методов ***TaxType**;
*  **TaxService** - моделирующий работу налоговой службы. Имеет два перегруженных ```void``` метода *payOut()*.

2. Использовал кодирование цвета текста (ANSI).

#### Класс **Utils**:
``` java
    public class Utils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printDelim() {
        System.out.println(ANSI_GREEN + "*********************************************" + ANSI_RESET);
    }
    }
```

3. Использовал ```try-catch```, чтобы избежать падение программы в исключения.

#### Метод *main()* в классе **Main**:
``` java
public class Main {
    public static void main(String[] args) {
        var program = new Program();
        program.start();
    }
}
```

## *Вывод в консоль*:

* меню:
``` 
Эта программа способна оплатить налог в следующих видах налогов: НДС, прогрессивный налог, подоходный налог.
Возможные команды программы:
0 или выход: чтобы выйти из программы.
1: чтобы ввести сумму доходов и тип налога, чтобы получить счет на оплату.
2: чтобы  удалить счет на оплату.
3: чтобы  заплатить налоги.
4: чтобы  получить квитанцию об оплате.
>>>>>>>
```