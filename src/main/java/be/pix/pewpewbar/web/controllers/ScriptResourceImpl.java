package be.pix.pewpewbar.web.controllers;

import be.pix.pewpewbar.services.CocktailService;
import be.pix.pewpewbar.web.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/scripts")
@Controller
public class ScriptResourceImpl implements ScriptResource {

    private CocktailService cocktailService;

    @Autowired
    public ScriptResourceImpl(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }


    @Override
    @RequestMapping("/exec")
    public BaseResponse executeScript(@RequestParam("name") String script) {
        cocktailService.executeScript(script);
        return new BaseResponse<>("Executing script");
    }

    @Override
    @RequestMapping("/execFromFile")
    public BaseResponse executeScriptFromFile(@RequestParam("name") String fileName) {
        cocktailService.executeScriptFromFile(fileName);
        return new BaseResponse<>("Executing script from file.");
    }
}
