package be.pix.pewpewbar.web.controllers;

import be.pix.pewpewbar.web.dto.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CocktailResource {

    BaseResponse getCocktails();

    BaseResponse uploadNewCocktails(MultipartFile[] multipartFiles);

    BaseResponse deleteScript(String scriptName);

}
