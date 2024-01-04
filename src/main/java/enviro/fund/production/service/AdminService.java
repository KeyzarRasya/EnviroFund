package enviro.fund.production.service;

import enviro.fund.production.exception.TransactionalException;
import enviro.fund.production.model.Charity;
import enviro.fund.production.model.Vehicle;
import enviro.fund.production.repository.CharityRepository;
import enviro.fund.production.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public void addVehicle(Vehicle vehicle){
        if(vehicle.getType().name().equals("MOTORCYCLE")) vehicle.setCost(50000);
        else if(vehicle.getType().name().equals("BIKE")) vehicle.setCost(25000);
        else if (vehicle.getType().name().equals("ELECTRICCAR")) vehicle.setCost(10000);
        vehicle.setIsAvailable(true);
        vehicleRepository.save(vehicle);
    }

    public List<Charity> notVerifiedCharity(){
        List<Charity> charities = charityRepository.findAll();
        Iterator<Charity> iterator = charities.iterator();
        List<Charity> notVerified = new ArrayList<>();
        while(iterator.hasNext()){
            Charity charity = iterator.next();
            if(!charity.isVerified()){
                notVerified.add(charity);
            }

        }
        return notVerified;
    }
    public void verify(Long charityId)throws TransactionalException {
        Optional<Charity> optionalCharity = charityRepository.findById(charityId);
        if(optionalCharity.isEmpty()){
            throw new TransactionalException("no such charity");
        }
        Charity charity = optionalCharity.get();
        if(charity.isVerified()) throw new TransactionalException("Already verified");
        charity.setVerified(true);
        charityRepository.save(charity);
    }

    public void deleteAll(){
        charityRepository.deleteAll();
    }
}
