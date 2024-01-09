package br.com.erudio.mockito.constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

public class CheckoutServiceTest {

    @Test
    void testMockObjectConstruction() {
        // Utilizado para mockar constructors de alguma classe

        // Given / Arrange
        try(MockedConstruction<PaymentProcessor> mocked = mockConstruction(
            PaymentProcessor.class,
            (mock, context) -> {
                when(mock.chargeCustomer(anyString(), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
            }
        )) {
            CheckoutService service = new CheckoutService();

            // When / Act
            BigDecimal result = service.purchaseProduct("MacBook Pro", "42");

            // Then / Assert
            assertEquals(BigDecimal.TEN, result);
            assertEquals(1, mocked.constructed().size());
        }
    }
}
