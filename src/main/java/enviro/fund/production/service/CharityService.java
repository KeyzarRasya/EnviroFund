package enviro.fund.production.service;

import enviro.fund.production.exception.TransactionalException;
import enviro.fund.production.model.Charity;
import enviro.fund.production.model.User;
import enviro.fund.production.repository.CharityRepository;
import enviro.fund.production.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CharityService {
    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackOn = UsernameNotFoundException.class)
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

    @Transactional(rollbackOn = TransactionalException.class)
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
}
