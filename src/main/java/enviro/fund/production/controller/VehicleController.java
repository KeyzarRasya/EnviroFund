package enviro.fund.production.controller;

import enviro.fund.production.model.Vehicle;
import enviro.fund.production.service.AdminService;
import enviro.fund.production.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle){
        adminService.addVehicle(vehicle);
        return ResponseEntity.ok("Added Vehicle");
    }

    @GetMapping("/all")
    public List<Vehicle> listVehicle(){
        return userService.vehicleList();
    }

    @PostMapping("/rent/{username}/{vehicleId}")
    public ResponseEntity<?> rentVehicle(
            @PathVariable String username,
            @PathVariable Long vehicleId
    ){
        userService.rentVehicle(username, vehicleId);
        return ResponseEntity.ok("Success Rent");
    }


}
