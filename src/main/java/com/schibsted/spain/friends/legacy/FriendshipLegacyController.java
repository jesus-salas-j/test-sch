package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.application.FriendshipService;
import com.schibsted.spain.friends.application.UserService;
import com.schibsted.spain.friends.domain.Password;
import com.schibsted.spain.friends.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/friendship")
public class FriendshipLegacyController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendshipService friendshipService;

    @PostMapping("/request")
    ResponseEntity requestFriendship(
            @RequestParam("usernameFrom") String usernameFrom,
            @RequestParam("usernameTo") String usernameTo,
            @RequestHeader("X-Password") String password
    ) {

        try {
            Password encriptedPassword = new Password(password);
            userService.validate(new User(usernameFrom, encriptedPassword));
            friendshipService.requestFriendship(usernameFrom, usernameTo);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/accept")
    ResponseEntity acceptFriendship(
            @RequestParam("usernameFrom") String usernameFrom,
            @RequestParam("usernameTo") String usernameTo,
            @RequestHeader("X-Password") String password
    ) {

        try {
            Password encriptedPassword = new Password(password);
            userService.validate(new User(usernameFrom, encriptedPassword));
            friendshipService.acceptFriendShip(usernameFrom, usernameTo);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/decline")
    ResponseEntity declineFriendship(
            @RequestParam("usernameFrom") String usernameFrom,
            @RequestParam("usernameTo") String usernameTo,
            @RequestHeader("X-Password") String password
    ) {

        try {
            Password encriptedPassword = new Password(password);
            userService.validate(new User(usernameFrom, encriptedPassword));
            friendshipService.declineFrienshipRequest(usernameFrom, usernameTo);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    ResponseEntity listFriends(
            @RequestParam("username") String username,
            @RequestHeader("X-Password") String password
    ) {
        Collection<String> friends;

        try {
            Password encriptedPassword = new Password(password);
            userService.validate(new User(username, encriptedPassword));
            friends = friendshipService.getFriends(username);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(friends, HttpStatus.OK);
    }

}
