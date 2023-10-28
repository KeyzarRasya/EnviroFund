package enviro.fund.production.service;

import enviro.fund.production.model.Vehicle;
import enviro.fund.production.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public void addVehicle(Vehicle vehicle){
        if(vehicle.getType().name().equals("MOTORCYCLE")) vehicle.setCost(50000);
        else if(vehicle.getType().name().equals("BIKE")) vehicle.setCost(25000);
        else if (vehicle.getType().name().equals("ELECTRICCAR")) vehicle.setCost(10000);
        vehicleRepository.save(vehicle);
    }
}
