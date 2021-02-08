package jaya.currencyconverter.dto;

import lombok.Data;

@Data
public class HttpResponseDTO {

    private int status;
    private String message;
    private boolean error;
    private Object data;

    public HttpResponseDTO(){}

    public HttpResponseDTO(int status, String message, boolean error, Object data){
        this.message = message;
        this.status = status;
        this.error = error;
        this.data = data;
    }
    
}
