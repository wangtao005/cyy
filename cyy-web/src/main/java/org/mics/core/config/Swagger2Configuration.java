package org.mics.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration{
	public static final String SWAGGER_SCAN_BASE_PACKAGE = "org.mics";
	public static final String VERSION = "1.0.0";
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .select()
                   .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) 
                   .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                   .build();
	}
	
	private ApiInfo apiInfo() {
       return new ApiInfoBuilder()
                   .title("接口文档") //设置文档的标题
                   .description("接口文档") // 设置文档的描述
                   .version(VERSION) // 设置文档的版本信息-> 1.0.0 Version information
                   .termsOfServiceUrl("http://www.baidu.com") // 设置文档的License信息->1.3 License information
                   .build();
	}
}

