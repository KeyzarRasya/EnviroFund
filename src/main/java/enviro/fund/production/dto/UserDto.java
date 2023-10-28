package enviro.fund.production.dto;

import enviro.fund.production.model.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDto {
    private Long id;
    private String username;
    private BigDecimal money;
    private int point;
    private boolean isRenting;
    private String role;

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.money = user.getMoney();
        this.point = user.getPoint();
        this.isRenting = user.isRenting();
        this.role = user.getRole();
    }
}
