package br.com.erudio.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class OrderServiceTest {

    OrderService service = new OrderService();
    UUID defaultUuid = UUID.fromString("4c554118-daf1-43c7-baf4-baf7dc8497b7");
    LocalDateTime defaultDate = LocalDateTime.of(2024, 1, 9, 14, 30, 35);

    @Test
    void testShouldIncludeRandomOrderId_When_NoOrderIdExists() {
        // Essa situação do mockito inline é utilizada quando precisamos mockar o resultado de um método que é invocado
        // dentro de outro método de uma classe, nesse caso, o méotod OrderService.createOrder() usa o método
        // UUID.randomUUID(), sendo um valor aleatório. Neste caso, fixamos o resultado que ele resultará sempre

        // Given / Arrange
        try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
            mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);

            // When / Act
            Order result = service.createOrder("MacBook Pro", 2L, null);

            // Then / Assert
            assertEquals(defaultUuid.toString(), result.getId());
        }
    }

    @Test
    void testShouldIncludeCurrentTime_When_NoOrderIdExists() {
        // Given / Arrange
        try (MockedStatic<LocalDateTime> mockedUuid = mockStatic(LocalDateTime.class)) {
            mockedUuid.when(LocalDateTime::now).thenReturn(defaultDate);

            // When / Act
            Order result = service.createOrder("MacBook Pro", 2L, null);

            // Then / Assert
            assertEquals(defaultDate, result.getCreationDate());
        }
    }
}
