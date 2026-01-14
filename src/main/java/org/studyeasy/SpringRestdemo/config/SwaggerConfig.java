package org.studyeasy.SpringRestdemo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = "Demo API",
    version = "Verions 1.0",
    contact = @Contact(
      name = " : Manish Chaudhari", email = "manishdchaudhari1007@gmail.com"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    description = "Spring Boot RestFul API Demo by Manish"
  )
)
public class SwaggerConfig {
    
}
