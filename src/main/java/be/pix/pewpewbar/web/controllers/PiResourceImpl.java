package be.pix.pewpewbar.web.controllers;


import be.pix.pewpewbar.services.CocktailService;
import be.pix.pewpewbar.com.JsscCommunication;
import be.pix.pewpewbar.web.dto.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Does everything related to the setup/ configuration of the pi/arduino communication
 */
@Controller
@RequestMapping("/pi")
public class PiResourceImpl implements PiResource {

    private static Logger LOGGER = LoggerFactory.getLogger(PiResource.class);

    private final JsscCommunication jsscCommunication;

    private final CocktailService cocktailService;


    @Autowired
    public PiResourceImpl(JsscCommunication jsscCommunication, CocktailService cocktailService) {
        this.jsscCommunication = jsscCommunication;
        this.cocktailService = cocktailService;
    }



    @RequestMapping("/")
    public @ResponseBody BaseResponse pingBar(@RequestParam("msg")String msg){
        return new BaseResponse<>(HttpStatus.I_AM_A_TEAPOT);
    }


    @Override
    @RequestMapping("/selectComPort")
    public @ResponseBody BaseResponse selectComPort(@RequestParam("com")String com){
        String[] availablePorts = cocktailService.getPortList();
        for(String s:availablePorts){
            if(s.equalsIgnoreCase(com)){
                cocktailService.setComPort(com);
                return new BaseResponse<>("Port set to:" + com);
            }
        }
        return new BaseResponse<>("Port not available");
    }


    @Override
    @RequestMapping("/ports")
    public @ResponseBody BaseResponse getPortList(){
        return new BaseResponse<>(cocktailService.getPortList());
    }

    @Override
    @RequestMapping("/baudrate")
    public @ResponseBody BaseResponse setBaudrate(@RequestParam("baudrate") int baudRate){
        cocktailService.setBaudrate(baudRate);
        return new BaseResponse<>("Baudrate set");
    }


}
