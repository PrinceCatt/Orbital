package Controllers;

import entity.User;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
    @GetMapping
    public int index(User user) {
        return user.getId();
    }
}
