package com.ciaj.boot.component.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/1 14:50
 * @Description: 类描述：配置swagger2信息
 *
 * @Api：用在请求的类上，表示对类的说明
 *
 *     tags "说明该类的作用，可以在UI界面上看到的注解"
 *     value "该 参数 没什么意义，在UI界面上也看到，所以不需要配置"
 *
 * @ApiOperation：用在请求的方法上，说明方法的用途、作用
 *
 *     value="说明方法的用途、作用"
 *     notes="方法的备注说明"
 *
 * @ApiImplicitParams：用在请求的方法上，表示一组参数说明
 *
 * @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
 *
 *     value：参数的汉字说明、解释
 *     required：参数是否必须传
 *     paramType：参数放在哪个地方
 *
 *     header –> 请求参数的获取：@RequestHeader
 *     query –> 请求参数的获取：@RequestParam
 *     path（用于restful接口）–> 请求参数的获取：@PathVariable
 *     body（不常用）
 *     form（不常用）
 *
 *     dataType：参数类型，默认String，其它值dataType="Integer"
 *     defaultValue：参数的默认值
 *
 * @ApiResponses：用在请求的方法上，表示一组响应
 *
 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
 *
 *     code：数字，例如400
 *     message：信息，例如"请求参数没填好"
 *     response：抛出异常的类
 *
 * @ApiModel：主要有两种用途：
 *
 *     用于响应类上，表示一个返回响应数据的信息
 *     入参实体：使用@RequestBody这样的场景， 请求参数无法使用@ApiImplicitParam注解进行描述的时候
 *
 * @ApiModelProperty：用在属性上，描述响应类的属性
 *
 * swagger-ui.html
 */
//@EnableWebMvc // 启用Mvc，非springboot框架需要引入注解@EnableWebMvc
@Configuration  // 让Spring来加载该类配置
@EnableSwagger2 // 启用Swagger2
public class Swagger2Config {
	@Value("${swagger.title}")
	private String title = "RESTful APIs";
	@Value("${swagger.description}")
	private String description = "RESTful 接口文档";
	@Value("${swagger.name}")
	private String name = "revolver";
	@Value("${swagger.url}")
	private String url = "www.ciaj.com";
	@Value("${swagger.email}")
	private String email = "595009116@qq.com";
	@Value("${swagger.version}")
	private String version = "1.0.0";

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				// 扫描指定包中的swagger注解
				 .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				// 扫描所有有注解的api，用这种方式更灵活
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.contact(new Contact(name, url, email))
				.version(version)
				.build();
	}
}
