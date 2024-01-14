package br.com.erudio.integration.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integration.testcontainers.AbstractIntegrationTest;

// Necessário realizar essa definição para não pegar uma porta aleatória e sim a do application.yml
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// Para poder testar como teste de integração, é necessário extender a classe AbstractIntegrationTest criada
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testShouldDisplaySwaggerUiPage() {
        // Métodos obtidos pelo Restassured estaticamente
        String content = given() // Dado
            .basePath("/swagger-ui/index.html") // basePath é a url padrão da aplicação e seu argumento é o caminho que se deseja
            .port(TestConfigs.SERVER_PORT) // porta da aplicação
        .when() // assertions do próprio RestAssured -> quando
            .get() // método get
        .then() // então
            .statusCode(200) // verificar se status code é 200
        .extract() // extrair
            .body() // o corpo da resposta
                .asString(); // como String

        assertTrue(content.contains("Swagger UI"));
    }
}
