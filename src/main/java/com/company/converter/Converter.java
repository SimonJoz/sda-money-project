package com.company.converter;

import com.company.model.Money;

public interface Converter {
    Money convert(Money money);
}
