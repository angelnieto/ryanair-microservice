package es.rmc.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** This class is used to set the necessary Swagger configuration for testing the microservice
 * 
 * @author rmc */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger.enabled}")
    private String enabled;

    /** The base package to scan by swagger */
    @Value("${swagger.basePackage}")
    private String basePackage;
    
	/** Swagger TAGS
	 * 
	 */
	  public static class TAGS
	  {
	      public static final String FLIGHTS = "FLIGHTS";
	  }

    /** Swagger builder creation
     * 
     * @return {@link Docket} builder object 
     */
    @Bean
    public Docket api() {
	
		// Common responses (same for GET; POST or other requestMethod)
		List<ResponseMessage> commonResponses = new ArrayList<>();
		commonResponses.add(new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Server error").build());
		commonResponses.add(new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("Incorrect request").build());
	
		// Post responses
		List<ResponseMessage> postResponses = new ArrayList<>();
		postResponses.add(new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).message("Media Type not supported at POST requests").build());
	
		// Get responses
		List<ResponseMessage> getResponses = new ArrayList<>();
		getResponses.add(new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).message("Media Type not supported at GET requests").build());

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build().pathMapping("/").apiInfo(apiInfo())
		.useDefaultResponseMessages(false).globalResponseMessage(RequestMethod.GET, ListUtils.sum(commonResponses, getResponses))
		.globalResponseMessage(RequestMethod.POST, ListUtils.sum(commonResponses, postResponses)).enable(getEnabled());

    }

    /** Returns creator information at Swagger User Interface main page */
    private ApiInfo apiInfo() {
	
		return new ApiInfoBuilder().title("Ryanair microservice Rest API")
            .description("Documentation about REST API for Ryanair microservice")
            .contact(new Contact("Ricardo Molina Cuesta", "https://www.lodulcedelavida.com/", "toorop.rmc@gmail.com"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }

    /** Returns if swagger api is enabled. Reads <code>swagger.enabled</code> value from properties */
    private boolean getEnabled() {
	return Boolean.parseBoolean(enabled);
    }

}
