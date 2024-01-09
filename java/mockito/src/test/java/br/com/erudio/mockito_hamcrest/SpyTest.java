package br.com.erudio.mockito_hamcrest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SpyTest {

    /* Diferença do método spy e do método mock -> o método spy permite dinamicidade (alterações) no objeto mockado
       enquanto o método mock não permite, apresentando erro se for tentado modificar o objeto | ex: ArrayList.add(item)
       Basicamente -> O spy é um mock "parcial"
    */

    @Test
    void testV1() {
        // Give / Arrange
        // Em vez de utilizar o mock, pode-se utilizar o spy
        @SuppressWarnings("unchecked")
        List<String> mockArrayList = /* mock */spy(ArrayList.class);

        // When / Act && Then / Assert
        assertEquals(0, mockArrayList.size());

        when(mockArrayList.size()).thenReturn(5); // o valor resultará em 5, mesmo adicionando novos itens
        mockArrayList.add("Foo-Bar");
        assertEquals(5, mockArrayList.size());
    }

    @Test
    void testV2() {
        @SuppressWarnings("unchecked")
        List<String> spyArrayList = spy(ArrayList.class);

        spyArrayList.add("Foo-Bar");
        assertEquals(1, spyArrayList.size());

        spyArrayList.remove("Foo-Bar");
        assertEquals(0, spyArrayList.size());
    }

    @Test
    void testV3() {
        @SuppressWarnings("unchecked")
        List<String> spyArrayList = spy(ArrayList.class);
        assertEquals(0, spyArrayList.size());
        when(spyArrayList.size()).thenReturn(5);
        assertEquals(5, spyArrayList.size());
    }

    @Test
    void testV4() {
        @SuppressWarnings("unchecked")
        List<String> spyArrayList = spy(ArrayList.class);
        spyArrayList.add("Foo-Bar");
        spyArrayList.add("Foo-Barr");

        verify(spyArrayList).add("Foo-Bar"); // Verificar se em algum momento o método add("Foo-Bar") foi invocado
        verify(spyArrayList, never()).remove("Foo-Bar"); // Verificar nunca o método foi chamado com a string "Foo-Bar"
        verify(spyArrayList, never()).clear(); // Verificar nunca o método foi chamado com qualquer string
    }
}
