package com.arrays;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class ArrayCompareTest {

    @Test
    void test() {
        int[] numbers = { 25, 8, 21, 32, 3 };
        int[] expectedArray = { 3, 8, 21, 25, 32 };

        Arrays.sort(numbers);

        assertArrayEquals(numbers, expectedArray);
    }

    @Test
    // teste de performance com timeout -> escolhe determinado tempo e se executar num tempo maior,
    // resulta em erro no teste
    // @Timeout(1)
    @Timeout(value = 15, unit = TimeUnit.MILLISECONDS)
    void testSortPerformance() {
        int[] numbers = { 25, 8, 21, 32, 3 };

        for (int i = 0; i < 214748; i++) {
            numbers[0] = i;
            Arrays.sort(numbers);
        }
    }
}
