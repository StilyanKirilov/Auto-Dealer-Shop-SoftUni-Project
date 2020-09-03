package softuni.springbootvehicles.service;

import softuni.springbootvehicles.model.entity.Brand;
import softuni.springbootvehicles.model.entity.Model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BrandService {

    Collection<Brand> getBrands();

    Brand getBrandById(Long id);

    Brand createBrand(Brand brand);

    Brand updateBrand(Brand brand);

    Brand deleteBrand(Long id);

    long getBrandsCount();

    Brand getBrandByName(String name);

    List<Brand> createBrandsBatch(List<Brand> brands);

    Optional<Model> getModelById(Long id);
}
