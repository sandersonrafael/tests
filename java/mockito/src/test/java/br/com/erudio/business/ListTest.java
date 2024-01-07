package br.com.erudio.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ListTest {

    @Test
    void testMockingList_WhenSizeIsCalled_SouldReturnMultipleValues() {
        // Criando mock da própria interface List do java.utils
        List<?> list = mock(List.class);

        // definindo qual será o retorno quando chamar o list.size()
        // Pode ser definido mais de uma vez para múltiplas invocações do método
        when(list.size()).thenReturn(10).thenReturn(20).thenReturn(30);

        assertEquals(10, list.size());
        assertEquals(20, list.size());
        assertEquals(30, list.size());
    }

    @Test
    void testMockingList_WhenGetIsCalled_SouldReturnErudio() {
        // Criando mock da própria interface List do java.utils
        var list = mock(List.class);

        // definindo qual será o retorno quando chamar o list.size()
        // Pode ser definido mais de uma vez para múltiplas invocações do método
        when(list.get(0)).thenReturn("Erudio");

        assertEquals("Erudio", list.get(0));
        assertNull(list.get(1));
    }

    @Test
    void testMockingList_WhenGetIsCalledWithArgumentMatcher_SouldReturnErudio() {
        var list = mock(List.class);

        // Argument Matcher -> Nesse caso, anyInt() do mockito -> Quer dizer que para qualquer inteiro chamado, retorna esse valor
        // Existem vários outros Argument Matchers, como anyByte, anyBoolean, anyString() etc.
        when(list.get(anyInt())).thenReturn("Erudio");

        assertEquals("Erudio", list.get(0));
        assertEquals("Erudio", list.get(1));
        assertEquals("Erudio", list.get(anyInt()));
    }

    @Test
    void testMockingList_When_ThrowsException() {
        var list = mock(List.class);

        // thenThrow lança uma exception definida
        when(list.get(anyInt())).thenThrow(new RuntimeException("Foo Bar!!"));
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> list.get(0),
            () -> "Deve lançar uma RuntimeException"
        );

        assertEquals(exception.getMessage(), "Foo Bar!!", () -> "Mensagem de erro diferente do esperado");
    }
}
