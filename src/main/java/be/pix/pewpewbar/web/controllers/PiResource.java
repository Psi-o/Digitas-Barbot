package be.pix.pewpewbar.web.controllers;

import be.pix.pewpewbar.web.dto.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface PiResource {
    @RequestMapping("/selectComPort")
    @ResponseBody
    BaseResponse selectComPort(@RequestParam("com") String com);

    @RequestMapping("/ports")
    @ResponseBody
    BaseResponse getPortList();

    @RequestMapping("/baudrate")
    @ResponseBody
    BaseResponse setBaudrate(@RequestParam("baudrate") int baudRate);
}
