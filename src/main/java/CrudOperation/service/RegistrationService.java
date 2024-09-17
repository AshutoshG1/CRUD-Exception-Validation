package CrudOperation.service;

import CrudOperation.dto.LoginDto;
import CrudOperation.dto.RegistrationDto;
import CrudOperation.entity.Registration;

import java.util.List;

public interface RegistrationService {

    Registration addUser(RegistrationDto registrationDto);

    String verifyLogin(LoginDto loginDto);

    void deleteRegistration(long id);

    void updateRegistration(long id, RegistrationDto registrationDto);

    List<Registration> getAllRegistrations();
}
