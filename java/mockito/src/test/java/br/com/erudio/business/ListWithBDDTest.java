package br.com.erudio.business;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ListWithBDDTest { /* Modificando para BDD com BDDMockito em vez de Mockito e o hamcrest */

    @Test
    void testMockingList_WhenSizeIsCalled_SouldReturnMultipleValues() {
        List<?> list = mock(List.class);

        given(list.size()).willReturn(10).willReturn(20).willReturn(30);

        assertThat(list.size(), is(10));
        assertThat(list.size(), is(20));
        assertThat(list.size(), is(30));
    }

    @Test
    void testMockingList_WhenGetIsCalled_SouldReturnErudio() {
        var list = mock(List.class);

        given(list.get(0)).willReturn("Erudio");

        assertThat(list.get(0), is("Erudio"));
        assertNull(list.get(1));
    }

    @Test
    void testMockingList_WhenGetIsCalledWithArgumentMatcher_SouldReturnErudio() {
        var list = mock(List.class);

        given(list.get(anyInt())).willReturn("Erudio");

        assertThat(list.get(0), is("Erudio"));
        assertThat(list.get(1), is("Erudio"));
        assertThat(list.get(anyInt()), is("Erudio"));
    }

    @Test
    void testMockingList_When_ThrowsException() {
        var list = mock(List.class);

        given(list.get(anyInt())).willThrow(new RuntimeException("Foo Bar!!"));

        // o assertThat do hamcrest não possui manipulação de exceptions. Usamos o mesmo assertThrows do mockito
        assertThrows(RuntimeException.class,() -> list.get(0),() -> "Deve lançar uma RuntimeException");
    }
}
