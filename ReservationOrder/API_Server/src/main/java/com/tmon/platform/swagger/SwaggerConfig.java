package com.tmon.platform.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("com.tmon.platform.api.controller"))
        		.paths(PathSelectors.any())		//.paths(PathSelectors.ant("/*"))
        		.build()
        		.apiInfo(apiInfo())
        		.useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, 
                		newArrayList(
                				new ResponseMessageBuilder()
                				.code(501)
                				.message("501 message")
                				.responseModel(new ModelRef("com.tom.platform.api.CustomException"))
                				.build(), 
                				new ResponseMessageBuilder()
                				.code(404)
                				.message("page not found")
                				.build()
                				)
                		);
    }

    
    private ApiInfo apiInfo() {
    	 ApiInfo apiInfo = new ApiInfo("TMON PlatForm",
    			"예약배송 시스템",
    			"API version",
    			"TermsOfServiceUrl", 
    			"PlatFormInter@tmon.co.kr",
    			null,
    			null);
        
        return apiInfo;
    }
}
