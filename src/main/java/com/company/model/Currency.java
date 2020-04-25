package com.company.model;

public enum Currency {
    PLN(0, "PLN"),
    USD(1, "USD"),
    EUR(2, "EUR"),
    GBP(3, "GBP"),
    NO_SUCH_CURRENCY(5, "NO SUCH CURRENCY !!!");

    private final int id;
    private final String desc;

    Currency(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static Currency getCurrencyByInt(int id) {
        if (id >= Currency.values().length) {
            return Currency.NO_SUCH_CURRENCY;
        }
        return Currency.values()[id];
    }

    public static void printCurrencies() {
        for (int i = 0; i < Currency.values().length - 1; i++) {
            Currency currency = getCurrencyByInt(i);
            System.out.printf("%d -- %s\n", currency.id, currency.desc);
        }
    }
}
