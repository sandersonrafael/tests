package br.com.erudio.mockito.static_with_params;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class MyUtilsTest {

    @Test
    void shouldMockStaticMethodTestWithParams() {
        // Para mockar métodos que são acionados dentro de métodos (similar ao arquivo OrderServiceTest, mas com params)
        // Utilizamos:
        try(MockedStatic<MyUtils> mockedStatic = mockStatic(MyUtils.class)) {
            // configurar parâmetros -> o método eq(String) significa que essa verificação ocorre quando for passado esse arg
            // anyBoolean() -> quando for passado o argumento anterior com qualquer boolean no segundo argumento
            // .thenReturn(String) -> garante que o retorno mockado será sempre o informado
            mockedStatic.when(() -> MyUtils.getWelcomeMessage(eq("Erudio"), anyBoolean()))
                .thenReturn("Howdy Erudio!");

            String result = MyUtils.getWelcomeMessage("Erudio", false);

            assertEquals("Howdya Erudio!", result);
        }
    }
}
