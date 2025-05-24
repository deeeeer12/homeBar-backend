package homebar.com.dto;

import homebar.com.entity.User;
import lombok.Data;

@Data
public class userRegisterDTO extends User {

    private String openId;

}
