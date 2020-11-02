package com.github.dylanz666.controller;

import com.github.dylanz666.domain.User;
import com.github.dylanz666.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : dylanz
 * @since : 10/31/2020
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public @ResponseBody User getUserByName(@RequestParam String userName) {
        return userService.getUserByName(userName);
    }

    @PutMapping("")
    public @ResponseBody
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("")
    public @ResponseBody String deleteUser(@RequestBody User user) {
        userService.deleteUser(user);

        return "success";
    }

    @GetMapping("/multiCaching")
    public @ResponseBody User getUserByNameAndDoMultiCaching(@RequestParam String userName) {
        return userService.getUserByNameAndDoMultiCaching(userName);
    }
}
