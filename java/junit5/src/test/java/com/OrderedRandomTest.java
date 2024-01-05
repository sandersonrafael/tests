package com;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// @Order(n) -> Se utilizado na Classe, define a ordem que são executadas as classes nos testes (não os métodos)
// Para isto, é necessário criar um arquivo de properties no resources de teste conforme exemplo neste material

// @TestMethodOrder(MethodOrderer.Random.class) // Torna a execução dos métodos totalmente randômica
// @TestMethodOrder(MethodOrderer.MethodName.class) // Explicita que a ordenação será de acordo com os nomes
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ordenar pela annotation @Order(n)
public class OrderedRandomTest {

    /* Ordenação -> Por padrão, a ordenação ocorre pelos nomes dos métodos */

    @Test
    @Order(1) // Só funciona se houver a annotation @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    void testB() {
        System.out.println("Running test B");
    }

    @Test
    @Order(2)
    void testA() {
        System.out.println("Running test A");
    }

    @Test
    void testD() {
        System.out.println("Running test D");
    }

    @Test
    void testC() {
        System.out.println("Running test C");
    }
}
