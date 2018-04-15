package be.pix.pewpewbar.web.controllers;


import be.pix.pewpewbar.services.CocktailService;
import be.pix.pewpewbar.web.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cocktails")
public class CocktailResourceImpl implements CocktailResource {


    private CocktailService cocktailService;

    @Autowired
    public CocktailResourceImpl(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @Override
    @RequestMapping("/list")
    public BaseResponse getCocktails() {
        return new BaseResponse<>(cocktailService.getCocktailNames());
    }

    /**
     * Uploads some files to a folder
     * @param files Files that'll be uploaded.
     */
    @Override
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse uploadNewCocktails(@RequestParam("files") MultipartFile[] files) {
        if(files.length==0) {
            return new BaseResponse<>("No files in object");
        }
        cocktailService.addCocktails(files);
        return new BaseResponse<>("Files added");
    }

    @Override
    @RequestMapping("/deleteScript")
    @ResponseBody
    public BaseResponse deleteScript(@RequestParam("name") String scriptName) {
         cocktailService.deleteCocktailBecauseJeromeDoesNotLikeIt(scriptName);
         return new BaseResponse<>("Script deleted");
    }
}
