package softuni.springbootvehicles.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.springbootvehicles.util.StringToEnumConverterFactory;
import softuni.springbootvehicles.util.StringToModelConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToModelConverter stringToModelConverter;

    @Autowired
    public WebConfig(StringToModelConverter stringToModelConverter) {
        this.stringToModelConverter = stringToModelConverter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/offers");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToModelConverter);
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }
}
