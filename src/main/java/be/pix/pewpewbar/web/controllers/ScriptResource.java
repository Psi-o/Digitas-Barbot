package be.pix.pewpewbar.web.controllers;

import be.pix.pewpewbar.web.dto.BaseResponse;

public interface ScriptResource {

    BaseResponse executeScript(String script);

    BaseResponse executeScriptFromFile(String fileName);



}
