/*
package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.Bean;

import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;

import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.springframework.context.annotation.Configuration;

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
}
*/