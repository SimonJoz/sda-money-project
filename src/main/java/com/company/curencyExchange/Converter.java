package com.company.curencyExchange;

import com.company.model.Money;

interface Converter {
    Money convert(Money money);
}
