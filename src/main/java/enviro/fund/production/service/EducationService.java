package enviro.fund.production.service;

import enviro.fund.production.exception.TransactionalException;
import enviro.fund.production.model.Charity;
import enviro.fund.production.model.Education;
import enviro.fund.production.model.Kuis;
import enviro.fund.production.model.User;
import enviro.fund.production.repository.EducationRepository;
import enviro.fund.production.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private UserRepository userRepository;

    public void addInfographic(String author, Education education) {
        Optional<User> userOptional = userRepository.findByUsername(author);
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("Not found");
        education.setAuthor(userOptional.get());
        educationRepository.save(education);
    }

    public void addKuis(Kuis kuis, Long edu)throws TransactionalException{
            Optional<Education> optionalEducation = educationRepository.findById(edu);
            if(optionalEducation.isEmpty()){
                throw new TransactionalException("no such education");
            }

            Education education = optionalEducation.get();
            education.getKuis().add(kuis);
            educationRepository.save(education);
        }
    }


