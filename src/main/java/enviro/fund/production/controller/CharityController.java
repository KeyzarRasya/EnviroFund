package enviro.fund.production.controller;

import enviro.fund.production.model.Charity;
import enviro.fund.production.service.CharityService;
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
    private CharityService charityService;

    @GetMapping("/create")
    public ResponseEntity<?> createCharity(
            Authentication authentication,
            @RequestBody Charity charity
            ){
        charityService.createCharity(authentication.getName(), charity);
        return ResponseEntity.ok("OKE");
    }

    @GetMapping("/list/non/verified")
    private List<Charity> nonVerified(){
        return charityService.notVerifiedCharity();
    }

    @GetMapping("/list/verified")
    private List<Charity> verifiedCharity(){
        return charityService.verifiedCharity();
    }

    @PostMapping("/verify/{charityId}")
    @PreAuthorize("ROLE_ADMIN")
    public ResponseEntity<?> verifyCharity(
            @PathVariable Long charityId
    ){
        charityService.verify(charityId);
        return ResponseEntity.ok("Verify Success");
    }
}
