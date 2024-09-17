package CrudOperation.service.Impl;

import CrudOperation.dto.LoginDto;
import CrudOperation.dto.RegistrationDto;
import CrudOperation.entity.Registration;
import CrudOperation.repository.RegistrationRepository;
import CrudOperation.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private JWTService jwtService;

    public Registration addUser(RegistrationDto registrationDto){
        Registration user = new Registration();
        user.setName(registrationDto.getName());
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setMobile(registrationDto.getMobile());
        //one way of encrypting
        //user.setPassword(new BCryptPasswordEncoder(). encode(user.getPassword()));
        user.setPassword(BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt(10)));
       // user.setUserRole(propertyUserDto.getUserRole());
        Registration savedUser = registrationRepository.save(user);
        return savedUser;
    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<Registration> opUser = registrationRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()){
            Registration user = opUser.get();
            //return BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword());
            if(BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
                return jwtService.generateToken(user);
            }
        }
        return null;
    }

    @Override
    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public void updateRegistration(long id, RegistrationDto registrationDto) {
        Registration registration = registrationRepository.getById(id);
        registration.setName(registrationDto.getName());
        registration.setEmail(registrationDto.getEmail());
        registration.setMobile(registrationDto.getMobile());
        registration.setUsername(registrationDto.getUsername());
        registration.setPassword(BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt(10)));
        registrationRepository.save(registration);

    }

    @Override
    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations;
    }


}
