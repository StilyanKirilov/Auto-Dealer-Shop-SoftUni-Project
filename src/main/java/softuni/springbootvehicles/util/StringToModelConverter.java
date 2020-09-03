package softuni.springbootvehicles.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import softuni.springbootvehicles.model.entity.Model;
import softuni.springbootvehicles.service.BrandService;

@Component
public class StringToModelConverter implements Converter<String, Model> {

    private final BrandService brandService;

    @Autowired
    public StringToModelConverter(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public Model convert(String modelId) {
        if (modelId != null && modelId.trim().length() > 0) {
            return brandService.getModelById(Long.parseLong(modelId.trim())).orElse(null);
        }
        return null;
    }
}
