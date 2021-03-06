package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.application.FriendshipService;
import com.schibsted.spain.friends.application.UserService;
import com.schibsted.spain.friends.application.exceptions.InvalidCredentialsException;
import com.schibsted.spain.friends.domain.Password;
import com.schibsted.spain.friends.domain.User;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            User user = new User(usernameFrom, password);
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
            User user = new User(usernameFrom, password);
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
            User user = new User(usernameFrom, password);
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
            if (userService.exists(username, new Password(password))) {
                friends = friendshipService.getFriends(username);
            }
            else {
                throw new InvalidCredentialsException(username);
            }

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(friends, HttpStatus.OK);
    }

}
