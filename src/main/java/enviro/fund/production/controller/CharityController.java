package enviro.fund.production.controller;

import enviro.fund.production.model.Charity;
import enviro.fund.production.service.AdminService;
import enviro.fund.production.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/charity")
public class CharityController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<?> createCharity(
            Authentication authentication,
            @RequestBody Charity charity
            ){
        userService.createCharity(authentication.getName(), charity);
        return ResponseEntity.ok("OKE");
    }

    @GetMapping("/list/non/verified")
    private List<Charity> nonVerified(){
        return adminService.notVerifiedCharity();
    }

    @GetMapping("/list/verified")
    private List<Charity> verifiedCharity(){
        return userService.verifiedCharity();
    }

    @PostMapping("/verify/{charityId}")
    @PreAuthorize("ROLE_ADMIN")
    public ResponseEntity<?> verifyCharity(
            @PathVariable Long charityId
    ){
        adminService.verify(charityId);
        return ResponseEntity.ok("Verify Success");
    }

    @GetMapping("/delete/all")
    public ResponseEntity<?> delete(){
        adminService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
