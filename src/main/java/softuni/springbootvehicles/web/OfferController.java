package softuni.springbootvehicles.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.springbootvehicles.model.entity.Favourites;
import softuni.springbootvehicles.model.entity.Offer;
import softuni.springbootvehicles.service.BrandService;
import softuni.springbootvehicles.service.OfferService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/offers")
@Slf4j
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;
    private final Favourites favourites;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService, Favourites favourites) {
        this.offerService = offerService;
        this.brandService = brandService;
        this.favourites = favourites;
    }

    @ModelAttribute("favs")
    public Collection<Offer> addAttributes() {
        return this.favourites.getAllOffers();
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
    public String createOffer(@Valid @ModelAttribute("offer") Offer offer,
                              BindingResult binding,
                              RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            log.error("Error creating offer: {}", binding.getAllErrors());
            return redirectToAddOfferFormGet(offer, binding, redirectAttributes);
        }
        try {
            offerService.createOffer(offer);
        } catch (Exception ex) {
            log.error("Error creating offer", ex);
            return redirectToAddOfferFormGet(offer, binding, redirectAttributes);
        }
        return "redirect:/offers";
    }

    @GetMapping("/details/{offerId}")
    public String getOfferDetails(@PathVariable("offerId") Long offerId, Model model) {
        model.addAttribute("offer", this.offerService.getOfferById(offerId));
        return "offer-details";
    }

    @GetMapping("/favs/add/{offerId}")
    public String addFavourite(@PathVariable("offerId") Long offerId, Model model) {
        Offer offer = this.offerService.getOfferById(offerId);
        favourites.addOffer(offer);
        model.addAttribute("offer", offer);
        return "offer-details";
    }

    @GetMapping("/favs/remove/{offerId}")
    public String removeFavourite(@PathVariable("offerId") Long offerId) {
        this.favourites.removeOfferById(offerId);
        return "favs";
    }


    @GetMapping("/favs")
    public String getFavourites() {
        return "favs";
    }

    //private utility method
    private String redirectToAddOfferFormGet(Offer offer,
                                             BindingResult binding,
                                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("offer", offer);
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offer", binding);
        return "redirect:add";
    }
}