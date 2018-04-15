package be.pix.pewpewbar.web.dto;

public class BaseResponse<T> {

    private T responseObject;

    public BaseResponse(T object){
        this.responseObject = object;
    }


    public T getResponseObject() {
        return responseObject;
    }

}
