package com.example.gracia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de Swagger (OpenAPI)
 * Permite generar documentacion automatica de los endpoints REST del microservicio.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Define la informacion principal que se mostrara en Swagger UI.
     * Incluye titulo, version, descripcion, contacto y licencia.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        // Titulo y version del microservicio
                        .title("Student CRUD API - Gracia Garcia")
                        .version("1.0.0")
                        
                        // Descripcion breve de lo que hace el servicio
                        .description("Microservicio REST para gestion de estudiantes con operaciones CRUD completas. "
                                + "Incluye endpoints GET, POST, PUT y DELETE para la entidad Student. "
                                + "Base de datos H2 en memoria con logs detallados.")
                        
                        // Informacion de contacto del responsable
                        .contact(new Contact()
                                .name("Gracia Garcia")
                                .email("graciagarcia@example.com")
                                .url("https://github.com/graciagarcia"))
                        
                        // Tipo de licencia del software
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}
