package com.uhasoft.demo.provider.controller;

import com.uhasoft.demo.provider.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Weihua
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public User findById(@PathVariable String id){
        return new User()
            .setId(id)
            .setName("user" + id)
            .setEmail("user" + id + "@abc.com");
    }
}
