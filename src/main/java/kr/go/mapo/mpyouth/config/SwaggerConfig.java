package kr.go.mapo.mpyouth.config;

import com.fasterxml.classmate.TypeResolver;
import kr.go.mapo.mpyouth.payload.request.RequestPage;
import kr.go.mapo.mpyouth.payload.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final TypeResolver typeResolver;

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.OAS_30)
                .ignoredParameterTypes(java.sql.Date.class)
//                .forCodeGeneration(true)
                .additionalModels(
                        typeResolver.resolve(ErrorResponse.class)
                )
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(RequestPage.class))
                )
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)

                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .directModelSubstitute(LocalDateTime.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kr.go.mapo.mpyouth.api"))
                .paths(PathSelectors.any())
                .build();
    }


//    @Bean
//    public OpenAPI swaggerV3(){
//        return new OpenAPI()
//                .info(new Info().title("")
//                        .description("")
//                        .version("")
//                        .license(new License().name("").url("")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("")
//                        .url(""));
//    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("마포유스 테스트 API 타이틀")
                .description("마포유스 테스트 API 상세소개 및 사용법 등")
                .contact(new Contact("마포유스", "mapoyouth.futureuri.com", "mapoyouth@futureemail.com"))
                .version("1.0")
                .build();
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

}
