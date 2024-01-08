package br.com.erudio.mockito_hamcrest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HamcresMatchersTest {

    /* Conhecendo um pouco mais sobre o Hamcrest e seus Matchers */

    @Test
    void testHamcrestMatchers() {
        // Given / Arrange
        List<Integer> scores = Arrays.asList(99, 100, 101, 105);

        // Then / Act
        assertThat(scores, hasSize(4)); // tamanho da lista
        assertThat(scores, hasItems(100, 101)); // se existem tais itens
        assertThat(scores, everyItem(greaterThan(50))); // se todos valores, são maiores que tal valor
        assertThat(scores, everyItem(lessThan(150))); // se todos valores, são menores que tal valor

        // Check Strings
        assertThat("", is(emptyString())); // se é, uma String vazia
        assertThat(null, is(emptyOrNullString())); // se é string vazia ou nulo

        // Check Arrays
        Integer[] myArray = { 1, 2, 3 };
        assertThat(myArray, arrayWithSize(3)); // tamanho do array
        assertThat(myArray, arrayContaining(1, 2, 3)); // contem tais itens (todos e na ordem)
        assertThat(myArray, arrayContainingInAnyOrder(3, 1, 2)); // todos em qualquer ordem
    }
}
