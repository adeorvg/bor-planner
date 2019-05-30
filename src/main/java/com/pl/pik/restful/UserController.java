package com.pl.pik.restful;

import com.pl.pik.model.User;
import com.pl.pik.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserRepository repository;

    @RequestMapping("/login")
    public boolean login(@RequestBody User user) {
        if (repository.findByUsername(user.getUsername()).size() > 0 && repository.findByUsername(user.getUsername()).get(0).getPassword().equals(user.getPassword()))
            return true;
        else
            return false;
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}
