package be.pix.pewpewbar.services;

import org.springframework.web.multipart.MultipartFile;


/**
 * Should probably separate this into different services..
 */
public interface CocktailService {


    void setComPort(String port);

    String[] getCocktailNames();

    void deleteCocktailBecauseJeromeDoesNotLikeIt(String cocktailName);

    void addCocktails(MultipartFile[] files);

    void executeScript(String script);

    void setBaudrate(int baudrate);

    void executeScriptFromFile(String fileName);

    String[] getPortList();

    String getActivePort();


}
