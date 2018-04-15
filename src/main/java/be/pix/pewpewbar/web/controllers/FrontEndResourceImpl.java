package be.pix.pewpewbar.web.controllers;

import be.pix.pewpewbar.services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Use another front-end
 */
@Deprecated
@Controller
public class FrontEndResourceImpl implements FrontEndResource {


    private CocktailService cocktailService;

    @Autowired
    public FrontEndResourceImpl(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @Override
    @RequestMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("activePort",cocktailService.getActivePort());
        model.addAttribute("ports",cocktailService.getPortList());

        model.addAttribute("scripts",cocktailService.getCocktailNames());
        return "Index";
    }
}
