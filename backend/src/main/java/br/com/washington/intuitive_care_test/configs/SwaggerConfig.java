package br.com.washington.intuitive_care_test.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API TESTES DE NIVELAMENTO",
                version = "v1",
                description = "Esta aplicação tem o objetivo de simular os desafios do teste de nivelamento da Intuitive Care.",
                contact = @Contact(name = "Washington Luiz", url = "https://linkedin.com/in/washington1995luiz", email = "washington1995luiz@gmail.com"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
public class SwaggerConfig {
}
