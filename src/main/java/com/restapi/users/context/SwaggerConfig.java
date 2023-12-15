package com.restapi.users.context;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Rest CRUD users", version = "v1",
        description = "This API can persist any users with their data", contact =
@Contact(name = "Andrés Luna", url = "www.noweb.com",
        email = "andres.benjamin.luna@gmail.com"),
        license = @License(name = "The Unlicensed", url = "API license URL")))
public class SwaggerConfig {

}
