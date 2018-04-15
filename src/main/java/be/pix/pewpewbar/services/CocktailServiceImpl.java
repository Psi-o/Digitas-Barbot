package be.pix.pewpewbar.services;


import be.pix.pewpewbar.com.JsscCommunication;
import be.pix.pewpewbar.data.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CocktailServiceImpl implements CocktailService {

    private FileManager fm;

    private JsscCommunication jssc;

    private String port;

    @Override
    public String getActivePort(){
        return port;
    }

    @Autowired
    public CocktailServiceImpl(FileManager fm, JsscCommunication jssc) {
        this.fm = fm;
        this.jssc = jssc;
        port = "COM4";
    }


    @Override
    public String[] getPortList(){
        return jssc.pokePorts();
    }


    @Override
    public void setComPort(String port){
        this.port = port;
        jssc.openNewPort(port);
    }


    @Override
    public String[] getCocktailNames() {
        return fm.getFilelist();
    }

    @Override
    public void deleteCocktailBecauseJeromeDoesNotLikeIt(String cocktailName) {
        fm.deleteFileBecauseNoOneLikesIt(cocktailName);
    }

    @Override
    public void addCocktails(MultipartFile[] files) {
        fm.store(files);
    }

    @Override
    public void executeScript(String script) {
        String[] lines = script.split("\\r?\\n");
        for(String s:lines) {
            jssc.sendMessageOverPort(port, s);
        }
    }

    @Override
    public void setBaudrate(int baudrate){
        jssc.setBaudRate(baudrate);
    }


    @Override
    public void executeScriptFromFile(String fileName) {
        List<String> ss = fm.getStringsFromFile(fileName);
        for (String s : ss) {
            System.out.println("Executing line" + s);
            jssc.sendMessageOverPort(port, s);
        }
    }



}
