package com.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Teste da calculadora")
public class SimpleMathTest {

    // Padrão de nomenclatura de testes:
    // test[System Under Test]_[Condition or State Change]_[Expected Result]

    /* Ciclo de vida dos testes unitários */
    /* Os testes são executadas em ordem aleatória, normalmente */
    /*
    Annotation @BeforeAll -> É geralmente utilizada para configurar os testes unitários como um
    método setup(), pois ela é executada antes dos demais testes (que possuem ordem aleatória)
    o método precisa ser estático também
    *//*
    Annotation @AfterAll -> É geralmente utilizada para fechar conexões, excluir bancos de dados
    etc, como o método cleanup(), pois é executado após a finalização de todos os testes unitários
    *//*
    Annotation @BeforeEach -> É utilizada antes de cada teste para executar algum método necessário
    *//*
    Annotation @AfterEach -> É utilizada depois de cada teste para executar algum método necessário
    */

    SimpleMath math;

    @BeforeAll
    static void setup() {
        System.out.println("Running @BeforeAll method");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Running @AfterAll method");
    }

    @BeforeEach
    void beforeEachMethod() {
        math = new SimpleMath();
        System.out.println("Running @BeforeEach method");
    }

    @AfterEach
    void afterEachMethod() {
        System.out.println("Running @AfterEach method");
    }

    @Test
    @DisplayName("Teste 6.2 + 2.0 = 8.2") // altera o nome exibido do teste
    void testSum_WhenSixDotTwoIsAddedByTwo_ShouldReturnEightDotTwo() {
        System.out.println("Running testSum_WhenSixDotTwoIsAddedByTwo_ShouldReturnEightDotTwo method");
        // BDD -> Behavior Driven Development

        /* AAA -> Arrange, Act & Assert */
        // Given -> Arrange

        double firstNumber = 6.2;
        double secondNumber = 2.0;
        double expected = 8.2;

        // When -> Act
        double actual = math.sum(firstNumber, secondNumber);

        // Then -> Assert
        assertEquals(
            expected, actual, () -> firstNumber + " + " + secondNumber + " não resultou " + expected
        ); // 3º parâmetro equivale à mensagem de falha || Usar lambda func para evitar consumo de memória
        assertNotEquals(9.2, actual);
        assertNotNull(actual);
    }

    @Test
    void testSubtraction() {
        System.out.println("Running testSubtraction method");

        double firstNumber = 6.2;
        double secondNumber = 2.0;

        double actual = math.subtraction(firstNumber, secondNumber);
        double expected = 4.2;

        assertEquals(actual, expected, () -> firstNumber + " - " + secondNumber + " não resultou " + expected);
    }

    @Test
    void testMultiplication() {
        System.out.println("Running testMultiplication method");

        double firstNumber = 6.2;
        double secondNumber = 2.0;

        double actual = math.multiplication(firstNumber, secondNumber);
        double expected = 12.4;

        assertEquals(actual, expected, () -> firstNumber + " * " + secondNumber + " não resultou " + expected);
    }

    @Test
    void testDivision() {
        System.out.println("Running testDivision method");

        double firstNumber = 6.2;
        double secondNumber = 2.0;

        double actual = math.division(firstNumber, secondNumber);
        double expected = 3.1;

        assertEquals(actual, expected, () -> firstNumber + " / " + secondNumber + " não resultou " + expected);
    }

    // @Disabled // Desabilita o teste enquanto estiver com essa annotation
    @Test
    void testDivision_WhenFirstNumberIsDivisedByZero_ShouldThrowArithmeticException() {
        System.out.println("Running testDivision_WhenFirstNumberIsDivisedByZero_ShouldThrowArithmeticException method");

        // Given
        double firstNumber = 6.2;
        double secondNumber = 0.0;

        String expectedMessage = "You can't divide any number for zero";

        // Then
        String actualMessage = assertThrows(ArithmeticException.class, () -> {
            // When
            math.division(firstNumber, secondNumber);
        }, () -> "Divisões por zero devem gerar exceções")
        .getMessage();

        assertEquals(expectedMessage, actualMessage, () -> "Mensagem diferente da esperada");
    }

    @Test
    void testMean() {
        System.out.println("Running testMean method");

        double firstNumber = 6.2;
        double secondNumber = 2.0;

        double actual = math.mean(firstNumber, secondNumber);
        double expected = 4.1;

        assertEquals(
            expected,
            actual,
            () -> "Média de " + firstNumber + " e " + secondNumber + " não resultou " + expected
        );
    }

    @Test
    void testSquareRoot() {
        System.out.println("Running testSquareRoot method");

        double number = 9;

        double actual = math.squareRoot(number);
        double expected = 3;

        assertEquals(
            expected,
            actual,
            () -> "Raiz quadrada de " + number + " não resultou " + expected
        );
    }
}
