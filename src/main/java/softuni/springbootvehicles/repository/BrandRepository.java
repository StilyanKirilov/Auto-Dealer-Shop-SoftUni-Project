package softuni.springbootvehicles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.springbootvehicles.model.entity.Brand;
import softuni.springbootvehicles.model.entity.Model;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String name);

//    @Query("SELECT Model FROM Brand b WHERE b.models. = :id")
//    Optional<Model> findModelById(@Param("id") Long id);

}
