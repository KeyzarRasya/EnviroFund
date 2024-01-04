package enviro.fund.production.service;

import enviro.fund.production.dto.UserDto;
import enviro.fund.production.exception.TransactionalException;
import enviro.fund.production.model.*;
import enviro.fund.production.repository.CharityRepository;
import enviro.fund.production.repository.EducationRepository;
import enviro.fund.production.repository.UserRepository;
import enviro.fund.production.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(User user){
        user.setMoney(BigDecimal.ZERO);
        user.setRenting(false);
        user.setPoint(0);
        userRepository.save(user);
    }

    public List<User> allUSer(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        Iterator<User> userIterator = users.iterator();
        while(userIterator.hasNext()){
            User user = userIterator.next();
            userDtos.add(new UserDto(user));
        }
        return users;
    }

    public void addSaldo(String username, BigDecimal amount){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("not found");
        User user = optionalUser.get();
        user.setMoney(user.getMoney().add(amount));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()) throw new UsernameNotFoundException("Not found");
        return userOptional.get();
    }


    @Transactional(rollbackOn = TransactionalException.class)
    public void donate(String username, BigDecimal money, Long charityid)throws TransactionalException{
        Optional<User> useropt = userRepository.findByUsername(username);
        Optional<Charity> charityOpt = charityRepository.findById(charityid);
        if(useropt.isEmpty() && charityOpt.isEmpty()) throw new TransactionalException("Username not found");
        User user = useropt.get();
        Charity charity = charityOpt.get();
        if (user.getMoney().compareTo(money)<0){
            throw new TransactionalException("Saldo tidak mencukupi");
        }
        if (charity.getCharityAmount().compareTo(charity.getFundedMoney()) <= 0){
            charity.setComplete(true);
            charityRepository.save(charity);
            throw new TransactionalException("Donasi sudah terpenuhi");
        }
      charity.setFundedMoney(charity.getFundedMoney().add(money));
      user.setPoint(money.divide(BigDecimal.valueOf(10)).intValue());
      user.setMoney(user.getMoney().subtract(money));
      userRepository.save(user);
      charityRepository.save(charity);
    }


    public void rent(String username, Long vehicleId) throws TransactionalException{
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
        if(optionalUser.isEmpty() && optionalVehicle.isEmpty()){
            throw new TransactionalException("error");
        }
        User user = optionalUser.get();
        Vehicle vehicle = optionalVehicle.get();

        if(user.getPoint() < vehicle.getCost() && user.isRenting() ){
            throw new TransactionalException("Transaction Failure");
        }

        user.setPoint(user.getPoint() - vehicle.getCost());
        user.setVehicle(vehicle);
        user.setRenting(true);
        vehicle.setRenter(user);
        vehicle.setIsAvailable(false);

        userRepository.save(user);
        vehicleRepository.save(vehicle);

    }

    public void addInfographic(String author, Education education) {
        Optional<User> userOptional = userRepository.findByUsername(author);
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("Not found");
        education.setAuthor(userOptional.get());
        educationRepository.save(education);
    }

    public void addKuis(Kuis kuis, Long  edu)throws TransactionalException{
        Optional<Education> optionalEducation = educationRepository.findById(edu);
        if(optionalEducation.isEmpty()){
            throw new TransactionalException("no such education");
        }

        Education education = optionalEducation.get();
        education.getKuis().add(kuis);
        educationRepository.save(education);
    }

    public void createCharity(String username, Charity charity){
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()) throw new UsernameNotFoundException("Not found");
        charity.setHost(userOptional.get());
        charity.setVerified(false);
        charity.setComplete(false);
        charity.setFundedMoney(BigDecimal.ZERO);
        charityRepository.save(charity);
    }

    public List<Charity> verifiedCharity(){
        List<Charity> charities = charityRepository.findAll();
        Iterator<Charity> iterator = charities.iterator();
        List<Charity> verified = new ArrayList<>();
        while(iterator.hasNext()){
            Charity charity = iterator.next();
            if(charity.isVerified()){
                verified.add(charity);
            }
        }
        return verified;
    }

    public void rentVehicle(String username, Long vehicleId){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("No such username");
        }
        User user = optionalUser.get();
        Vehicle vehicle = optionalVehicle.get();


        user.setPoint(user.getPoint() - vehicle.getCost());

        vehicleRepository.save(vehicle);
        userRepository.save(user);
    }

    public List<Vehicle> vehicleList(){
        return vehicleRepository.findAll();
    }

}
