package CrudOperation.service.Impl;

import CrudOperation.entity.Registration;
import CrudOperation.exception.EntityNotFoundException;
import CrudOperation.repository.RegistrationRepository;
import CrudOperation.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public void createRegistration(Registration registration) {
        registrationRepository.save(registration);
    }

    @Override
    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations;

    }

    @Override
    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public void updateRegistration(long id, Registration registration) {
        Registration reg = registrationRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration with id " + id + " not found"));

        reg.setName(registration.getName());
        reg.setMobile(registration.getMobile());
        reg.setEmail(registration.getEmail());
        registrationRepository.save(reg);
    }
}
