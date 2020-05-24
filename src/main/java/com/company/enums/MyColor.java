package com.company.enums;

public enum MyColor {
    RESET("\033[0m"),
    YELLOW_BOLD("\033[1;33m"),
    BLUE_BOLD("\033[1;34m"),
    MAGENTA_BOLD("\033[1;35m"),
    CYAN_BOLD("\033[1;36m");

    private final String code;

    MyColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}