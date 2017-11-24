package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Locale;

// Remove this config in production environment, because of caching
@Configuration
public class ThymeleafConfig extends WebMvcConfigurerAdapter {
  @Bean
  public ITemplateResolver defaultTemplateResolver() {
    AbstractConfigurableTemplateResolver resolver = new FileTemplateResolver();
    resolver.setSuffix(".html");
    resolver.setPrefix("src/main/resources/templates/");
    resolver.setTemplateMode("HTML5");
    resolver.setCharacterEncoding("UTF-8");
    resolver.setCacheable(false);
    return resolver;
  }

  //http://www.baeldung.com/spring-boot-internationalization
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(Locale.forLanguageTag("sv_SE"));
    return slr;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
  }
}