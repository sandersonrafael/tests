package com.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

public class DemoRepeatedTest {

    SimpleMath math;

    @BeforeEach
    void beforeEachMethod() {
        math = new SimpleMath();
    }

    @AfterEach
    void afterEachMethod() {
        System.out.println("Running @AfterEach method");
    }

    // RepeatedTest é utilizado para realizar o teste repetidas vezes, para verificar se o resultado
    // não será alterado em diferentes tentativas
    @RepeatedTest(3) // recebe como parâmetro o número de repetições
    void testDivision_WhenFirstNumberIsDivisedByZero_ShouldThrowArithmeticException(
        RepetitionInfo repetitionInfo, // pode ser passado como parâmetro: obtém infos do @RepeatedTest
        TestInfo testInfo // obtém informações do teste também
    ) {
        System.out.println("\nRunning testDivision_WhenFirstNumberIsDivisedByZero_ShouldThrowArithmeticException method");
        System.out.println(repetitionInfo.getCurrentRepetition());
        System.out.println(testInfo.getTestMethod().get().getName());
        System.out.println(testInfo.getTestMethod().get());

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
}
