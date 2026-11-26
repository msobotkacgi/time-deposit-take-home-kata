package org.ikigaidigital.adapter.in.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "XA Bank Time Deposit API",
                version = "1.0.0",
                description = "Retrieve every time deposit and apply the monthly balance update to all deposits stored in the database."
        ),
        servers = @Server(url = "http://localhost:8080", description = "Local application")
)
public class TimeDepositOpenApiConfiguration {
}
