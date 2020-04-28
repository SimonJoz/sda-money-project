package com.company.enums;

public enum MyColor {
    RESET("\033[0m"),
    RED("\033[0;31m"),

    RED_BOLD("\033[1;31m"),
    MAGENTA("\u001B[1;35m"),
    YELLOW_BOLD("\033[1;33m"),
    BLUE_BOLD("\033[1;34m"),
    GREEN_BOLD("\033[1;32m"),
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
