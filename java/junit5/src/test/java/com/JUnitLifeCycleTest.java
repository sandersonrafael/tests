package com;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// PER_METHOD é o valor padrão. Cada teste cria uma nova instância
// PER_CLASS faz com que seja criada somente uma instância da classe e nela são executados todos métodos
@TestInstance(Lifecycle.PER_CLASS)
public class JUnitLifeCycleTest {

    StringBuilder string = new StringBuilder();

    @AfterEach
    void AfterEach() {
        System.out.println("Valor atual é: " + string);
    }

    @Test
    @Order(1)
    void testB() {
        System.out.println("Running test B");
        string.append("1");
    }

    @Order(2)
    @Test
    void testA() {
        System.out.println("Running test A");
        string.append("2");
    }

    @Order(3)
    @Test
    void testD() {
        System.out.println("Running test D");
        string.append("3");
    }

    @Test
    @Order(4)
    void testC() {
        System.out.println("Running test C");
        string.append("4");
    }
}
