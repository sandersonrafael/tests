package com.math;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Teste da calculadora")
public class SimpleMathTest2 {

    SimpleMath math;

    @BeforeEach
    void beforeEachMethod() {
        math = new SimpleMath();
    }

    @AfterEach
    void afterEachMethod() {
        System.out.println("Running @AfterEach method");
    }

    /* Testes parametrizados (recebem parâmetros em vez de obterem-se dentro do corpo dos métodos) */
    /* Parâmetros são definidos através das annotations | @ParameterizedTest em vez de @Test */
    @ParameterizedTest // Define que trata-se de um método que recebe parâmetros

    // @MethodSource("testDivisionInputParameters") // Método que provê os argumentos
    // @MethodSource() // se não for passado parâmetro, o methodSource é um outro overload do método

    /* @CsvSource({ // Similar ao MethodSource, mas obtém dados de um arquivo CSV ou da própria annotation
        "6.2, 2, 3.1", // Cada parâmetro equivale a uma linha de um CSV
        "71, 14, 5.07",
        "18.3, 3.1, 5.90"
    }) */
    // Obtendo CSV através de um arquivo CSV -> arquivo precisa estar no diretório resources
    @CsvFileSource(resources = "/testDivision.csv")
    void testDivision(double firstNumber, double secondNumber, double expected) {
        double actual = math.division(firstNumber, secondNumber);

        System.out.println("Test " + firstNumber + " / " + secondNumber + " = " + expected);

        assertEquals(
            actual,
            expected,
            2D, // para comparações numéricas, podemos usar o argumento delta, que define
            // a tolerância de casas decimais que o compilador irá analisar
            () -> firstNumber + " / " + secondNumber + " não resultou " + expected
        );
    }

    static Stream<Arguments> testDivision() {
    // static Stream<Arguments> testDivisionInputParameters() {
        // para cada parâmetro do Stream, o método executa uma vez
        return Stream.of(
            Arguments.of(6.2, 2.0, 3.1),
            Arguments.of(21.0, 3.0, 7.0),
            Arguments.of(36.0, 4.0, 9.0)
        );
    }

    @ParameterizedTest
    // Valores passados como parâmetros em vez de arquivos ou métodos
    @ValueSource(strings = { "Luan", "Felipe", "" })
    void testValueSource(String firstName) {
        System.out.println(firstName);
        assertNotNull(firstName);
    }
}
