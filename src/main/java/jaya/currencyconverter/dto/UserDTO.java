package jaya.currencyconverter.dto;

import jaya.currencyconverter.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    
    private String username;
    private int id;
    private String accessToken;

    public UserDTO(){}

    public UserDTO(User user){
        this.id         = user.getId();
        this.username   = user.getUsername();
    }
}
