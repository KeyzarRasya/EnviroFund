package enviro.fund.production.controller;

import enviro.fund.production.dto.UserDto;
import enviro.fund.production.model.User;
import enviro.fund.production.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.Getter;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    private SecurityContextRepository securityContextRepositor =
            new HttpSessionSecurityContextRepository();


    @GetMapping("/create")
    private ResponseEntity<?> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    private List<User> allUser(){
        return userService.allUSer();
    }

    @PostMapping("/donate/{charityId}/{amount}")
    public ResponseEntity<?> donateCharity(
            Authentication authentication,
            @PathVariable Long charityId,
            @PathVariable BigDecimal amount){
        System.out.println(amount);
        userService.donate(authentication.getName(), amount, charityId);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/topup/{amount}")
    public ResponseEntity<?> topUp(
            Authentication authentication,
            @PathVariable BigDecimal amount
    ){
        userService.addSaldo(authentication.getName(),  amount);
        return ResponseEntity.ok("Added");
    }

    @GetMapping("/authenticate/{username}/{password}")
    public ResponseEntity<?> authenticate(
            @PathVariable String username,
            @PathVariable String password,
            HttpServletRequest request,
            HttpServletResponse response){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepositor.saveContext(context, request,response);
        return ResponseEntity.ok(context.getAuthentication());
    }



}
