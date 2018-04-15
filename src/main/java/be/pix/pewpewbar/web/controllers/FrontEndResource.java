package be.pix.pewpewbar.web.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Use another front-end
 */
@Deprecated
public interface FrontEndResource {

    @RequestMapping("/")
    String getIndex(Model model);
}
