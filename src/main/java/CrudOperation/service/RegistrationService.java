package CrudOperation.service;

import CrudOperation.entity.Registration;

import java.util.List;

public interface RegistrationService {
    void createRegistration(Registration registration);

    List<Registration> getAllRegistrations();

    void deleteRegistration(long id);

    void updateRegistration(long id, Registration registration);
}
