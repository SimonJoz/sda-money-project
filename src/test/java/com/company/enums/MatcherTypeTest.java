package com.company.enums;

import com.company.exceptions.NoSuchMatcherException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.company.enums.MatcherType.getMatcherTypeById;
import static org.junit.jupiter.api.Assertions.*;

class MatcherTypeTest {

    @ParameterizedTest
    @MethodSource("supplyTypes")
    public void should_return_correct_matcher_type(int id, MatcherType expected) throws NoSuchMatcherException {
        MatcherType actual = getMatcherTypeById(id);
        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @ValueSource(ints = {5,6,7,Integer.MAX_VALUE})
    public void should_throw_NoSuchMatcherException(int id){
        Exception exception = assertThrows(NoSuchMatcherException.class,
                () -> getMatcherTypeById(id));
        assertNull(exception.getMessage());

    }
    private static Stream<Arguments> supplyTypes() {
        return Stream.of(
                Arguments.of(0, MatcherType.FIRST_EXACT_PRICE),
                Arguments.of(1, MatcherType.ANY_HIGHEST_PRICE),
                Arguments.of(2, MatcherType.ANY_LOWEST_PRICE),
                Arguments.of(3, MatcherType.LOWEST_PRICE_IN_CURRENCY),
                Arguments.of(4, MatcherType.HIGHEST_PRICE_IN_CURRENCY));
    }
}