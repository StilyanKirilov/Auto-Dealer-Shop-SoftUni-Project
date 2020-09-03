package softuni.springbootvehicles.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.springbootvehicles.model.entity.Offer;
import softuni.springbootvehicles.service.BrandService;
import softuni.springbootvehicles.service.OfferService;

import javax.servlet.http.HttpSession;
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

    @GetMapping
    public String getOffers(Model offers) {
        offers.addAttribute("offers", this.offerService.getOffers());
        return "offers";
    }

    @GetMapping("/add")
    public String getOfferForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            redirectAttributes.addAttribute("redirectUrl", "/offers/add");
            return "redirect:/auth/login";
        }
        if (!model.containsAttribute("offer")) {
            model.addAttribute("offer", new Offer());
        }
        model.addAttribute("brands", brandService.getBrands());

        return "offer-add";
    }

    @PostMapping("/add")
    public String createOffer(@Valid @ModelAttribute("offer") Offer offer, BindingResult binding, RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            log.error("Error creating offer: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("offer", offer);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offer", binding);
            return "redirect:add";
        }
        try {
//            offer.setModel(this.brandService.getBrandById(1L).getModels().get(0));
            offerService.createOffer(offer);
        } catch (Exception ex) {
            log.error("Error creating offer", ex);
            redirectAttributes.addFlashAttribute("offer", offer);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offer", binding);
            return "redirect:add";
        }
        return "redirect:/offers";
    }

    @GetMapping("/details/{offerId}")
    public String getOfferDetails(@PathVariable("offerId") Long offerId, Model model) {
        model.addAttribute("offer", this.offerService.getOfferById(offerId));
        return "offers-details";
    }

}
