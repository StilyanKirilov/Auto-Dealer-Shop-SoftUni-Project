package softuni.springbootvehicles.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.springbootvehicles.exception.EntityNotFoundException;
import softuni.springbootvehicles.model.entity.Brand;
import softuni.springbootvehicles.repository.BrandRepository;
import softuni.springbootvehicles.repository.UserRepository;
import softuni.springbootvehicles.service.BrandService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Collection<Brand> getBrands() {
        return this.brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {
        return this.brandRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Brand with ID=%s not found.", id)));
    }

    @Override
    public Brand createBrand(@Valid Brand brand) {

        if (brand.getCreated() == null) {
            brand.setCreated(new Date());
        }
        brand.setModified((brand.getCreated()));
        return brandRepository.save(brand);
    }

    @Override
    @Transactional
    public Brand updateBrand(Brand brand) {
        brand.setModified(new Date());
        Brand old = getBrandById(brand.getId());
        if (old == null) {
            throw new EntityNotFoundException(String.format("Brand with Name=%s not found.", brand.getName()));
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand deleteBrand(Long id) {
        Brand old = this.getBrandById(id);
        this.brandRepository.deleteById(id);
        return old;
    }

    @Override
    public long getBrandsCount() {
        return this.brandRepository.count();
    }

    @Override
    public Brand getBrandByName(String name) {
        return this.brandRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException(String.format("Brand with name=%s not found", name)));
    }

    @Transactional
    public List<Brand> createBrandsBatch(List<Brand> brands) {

        return brands.stream()
                .map(brand -> createBrand(brand)).collect(Collectors.toList());
    }
}
