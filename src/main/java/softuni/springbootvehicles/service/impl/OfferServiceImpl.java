package softuni.springbootvehicles.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.springbootvehicles.exception.EntityNotFoundException;
import softuni.springbootvehicles.exception.InvalidEntityException;
import softuni.springbootvehicles.model.entity.Brand;
import softuni.springbootvehicles.model.entity.Model;
import softuni.springbootvehicles.model.entity.Offer;
import softuni.springbootvehicles.model.entity.User;
import softuni.springbootvehicles.repository.BrandRepository;
import softuni.springbootvehicles.repository.OfferRepository;
import softuni.springbootvehicles.repository.UserRepository;
import softuni.springbootvehicles.service.OfferService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, BrandRepository brandRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public Collection<Offer> getOffers() {
        return this.offerRepository.findAll();
    }

    @Override
    public Offer getOfferById(Long id) {
        return this.offerRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Offer with ID=%s not found.", id)));
    }

    @Override
    public Offer createOffer(@Valid Offer offer) {
        Long sellerId;
        if (offer.getSeller() != null && offer.getSeller().getId() != null) {
            sellerId = offer.getSeller().getId();
        } else {
            sellerId = offer.getSellerId();
        }
        if (sellerId != null) {
            User author = userRepository.findById(sellerId)
                    .orElseThrow(() ->
                            new InvalidEntityException(String.format("Seller with ID=%s does not exist.", sellerId)));
            offer.setSeller(author);
        }
        Long modelId;
        if (offer.getModel() != null && offer.getModel().getId() != null) {
            modelId = offer.getModel().getId();
        } else {
            modelId = offer.getModelId();
        }

        if (modelId != null) {
            Model model = this.findModelById(modelId)
                    .orElseThrow(() -> new InvalidEntityException("Model with ID=" + modelId + " does not exist."));
            offer.setModel(model);
        }

        if (offer.getCreated() == null) {
            offer.setCreated(new Date());
        }
        offer.setModified(offer.getCreated());
        return offerRepository.save(offer);
    }

    @Override
    @Transactional
    public Offer updateOffer(Offer offer) {
        offer.setModified(new Date());
        Offer old = this.getOfferById(offer.getId());
        if (old == null) {
            throw new EntityNotFoundException(String.format("Offer with ID =%s not found.", offer.getId()));
        }
        if (offer.getSeller() != null && offer.getSeller().getId() != old.getSeller().getId()) {
            throw new InvalidEntityException("Seller or article could not be changed.");
        }
        offer.setSeller(old.getSeller());
        return offerRepository.save(offer);
    }

    @Override
    public Offer deleteOffer(Long id) {
        Offer old = this.getOfferById(id);
        this.offerRepository.deleteById(id);
        return old;
    }

    @Override
    public long getOffersCount() {
        return this.offerRepository.count();
    }

    @Override
    @Transactional
    public List<Offer> createOffersBatch(List<Offer> offers) {
        return offers.stream()
                .map(offer -> createOffer(offer)).collect(Collectors.toList());
    }

    @Override
    public Optional<Model> findModelById(Long modelId) {
        List<Brand> brands = this.brandRepository.findAll();
        Optional<Model> model = Optional.empty();
        for (Brand brand : brands) {
            for (Model model1 : brand.getModels()) {
                if (model1.getId().equals(modelId)) {
                    model = Optional.of(model1);
                }
            }
        }
        return model;
    }
}