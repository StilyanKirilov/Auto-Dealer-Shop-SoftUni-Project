package softuni.springbootvehicles.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.springbootvehicles.model.entity.Offer;
import softuni.springbootvehicles.service.BrandService;
import softuni.springbootvehicles.service.OfferService;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
@Slf4j
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }


    @GetMapping("/add")
    public String getOfferForm(Model model) {
        if (model.getAttribute("offer") == null) {
            model.addAttribute("offer", new Offer());
        }
        model.addAttribute("brands", brandService.getBrands());

        return "offer-add";
    }

    @GetMapping
    public String getOffers(Model offers) {
        offers.addAttribute("offers", this.offerService.getOffers());
        return "offers";
    }

    @PostMapping("/add")
    public String createOffer(@Valid @ModelAttribute("offer") Offer offer, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Error creating offer: {}", result.getAllErrors());
            return "offer-add";
        }
        try {
//            offer.setModel(this.brandService.getBrandById(1L).getModels().get(0));
            offerService.createOffer(offer);
        } catch (Exception ex) {
            log.error("Error creating offer", ex);
            return "offer-add";
        }
        return "redirect:/offers";
    }
}
