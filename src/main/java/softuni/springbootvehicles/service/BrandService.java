package softuni.springbootvehicles.service;

import softuni.springbootvehicles.model.entity.Brand;

import java.util.Collection;
import java.util.List;

public interface BrandService {

    Collection<Brand> getBrands();

    Brand getBrandById(Long id);

    Brand createBrand(Brand brand);

    Brand updateBrand(Brand brand);

    Brand deleteBrand(Long id);

    long getBrandsCount();

    Brand getBrandByName(String name);

    List<Brand> createBrandsBatch(List<Brand> brands);
}
