package softuni.springbootvehicles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springbootvehicles.model.entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
