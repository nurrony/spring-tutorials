package info.nmrony.tutorial.emailer.configs;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder().group("Apis").pathsToMatch("/api/**").build();
    }

    @Bean
    public OpenAPI apiInfo() {
        // final String securitySchemeName = "bearerAuth";
        // @formatter:off
        return new OpenAPI()
            .info(buildApiInformation());
            // .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            // .components(
            //     new Components()
            //         .addSecuritySchemes(securitySchemeName,
            //             new SecurityScheme()
            //                 .name(securitySchemeName)
            //                 .type(SecurityScheme.Type.HTTP)
            //                 .scheme("bearer")
            //                 .bearerFormat("JWT")
            //         )
            // );
        // @formatter:on
    }

    private Info buildApiInformation() {
        // @formatter:off
        return new Info()
            .version("v1.0.0")
            .title("Email Client")
            .description("Email Client documentation")
            .contact(new Contact().email("pro.nmrony@gmail.com").name("Email Client API Support"));
        // @formatter:on
    }

}
