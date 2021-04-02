package spring_course.my_bank_transactions.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import spring_course.my_bank_transactions.ApplicationLauncher;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan( basePackageClasses = ApplicationLauncher.class )
@PropertySource( "classpath:/application.properties" )
public class MyBankTransactionsApplicationConfiguration implements WebMvcConfigurer {

   @Bean
   public MethodValidationPostProcessor getMethodValidationPostProcessor() {
      return new MethodValidationPostProcessor();
   }

   @Bean
   public LocalValidatorFactoryBean validator()
   {
      return  new LocalValidatorFactoryBean();
   }

   @Override
   public void configureMessageConverters( List< HttpMessageConverter< ? > > converters ) {
      final var builder = new Jackson2ObjectMapperBuilder().indentOutput( true )
                                                           .serializationInclusion( JsonInclude.Include.NON_NULL )
                                                           .modules( new JavaTimeModule() )
                                                           .featuresToDisable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS );

      converters.add( new MappingJackson2HttpMessageConverter( builder.build() ) );
      converters.add( new MappingJackson2XmlHttpMessageConverter( builder.createXmlMapper( true ).build() ) );
   }

   @Bean
   public SpringResourceTemplateResolver getTemplateResolver()
   {
      final var templateResolver = new SpringResourceTemplateResolver();
      templateResolver.setCacheable( false );
      templateResolver.setPrefix( "classpath:/templates/" );
      return templateResolver;
   }

   @Bean
   public SpringTemplateEngine getTemplateEngine()
   {
      final var templateEngine = new SpringTemplateEngine();
      templateEngine.setTemplateResolver( getTemplateResolver() );
      templateEngine.setEnableSpringELCompiler( true );
      return templateEngine;
   }

   @Bean
   public ThymeleafViewResolver getViewResolver()
   {
      final var viewResolver = new ThymeleafViewResolver();
      viewResolver.setTemplateEngine( getTemplateEngine() );
      viewResolver.setOrder( 1 );
      viewResolver.setViewNames( new String[] { "*.html", "*.xhtml" } );
      return viewResolver;
   }
}