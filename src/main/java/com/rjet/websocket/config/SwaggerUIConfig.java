package com.rjet.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//extends WebMvcConfigurationSuppor
@EnableSwagger2
@Configuration
public class SwaggerUIConfig {

    @Bean
    public Docket customerDocket(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rjet.websocket")) //扫描的包路径
                .build();
    }

    public ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("websocket api接口文档")
                .version("1.0.1")
                .build();
    }


}
