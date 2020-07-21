package softuni.springbootvehicles.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.springbootvehicles.service.BrandService;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String getBrands(Model brands) {
        brands.addAttribute("brands", brandService.getBrands()); // set model data
        return "brands";
    }

}
