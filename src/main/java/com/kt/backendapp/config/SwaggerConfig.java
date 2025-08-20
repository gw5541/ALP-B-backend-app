// package com.kt.backendapp.config;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.Contact;
// import io.swagger.v3.oas.models.servers.Server;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.util.List;

// /**
//  * Swagger 설정
//  */
// @Configuration
// public class SwaggerConfig {
    
//     @Bean
//     public OpenAPI openAPI() {
//         return new OpenAPI()
//                 .info(new Info()
//                         .title("ALP-B Backend API")
//                         .description("인구 통계 및 사용자 관리 API")
//                         .version("1.0.0")
//                         .contact(new Contact()
//                                 .name("KT Team")
//                                 .email("support@kt.com")))
//                 .servers(List.of(
//                         new Server()
//                                 .url("http://localhost:8080")
//                                 .description("Local Development Server")
//                 ));
//     }
// }
