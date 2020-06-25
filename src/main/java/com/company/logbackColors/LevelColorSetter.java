package com.company.logbackColors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

import static ch.qos.logback.core.pattern.color.ANSIConstants.*;


public class LevelColorSetter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        return switch (level.toInt()) {
            case Level.ERROR_INT, Level.WARN_INT -> BOLD + RED_FG;
            case Level.INFO_INT -> BOLD + YELLOW_FG;
            case Level.DEBUG_INT -> BOLD + CYAN_FG;
            default -> DEFAULT_FG;
        };
    }
}