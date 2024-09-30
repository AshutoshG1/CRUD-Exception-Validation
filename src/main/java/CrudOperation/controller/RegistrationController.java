package CrudOperation.controller;

import CrudOperation.entity.Registration;
import CrudOperation.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/crud")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> createRegistration(@Valid @RequestBody Registration registration){
        registrationService.createRegistration(registration);
        return new ResponseEntity<>("Registration is done", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistration(){
        List<Registration> allRegistrations = registrationService.getAllRegistrations();
        return new ResponseEntity<>(allRegistrations, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistration(@PathVariable long id){
        registrationService.deleteRegistration(id);
        return new ResponseEntity<>("Registrtion is deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRegistration(@PathVariable long id, @Valid @RequestBody Registration registration){
        registrationService.updateRegistration(id, registration);
        return new ResponseEntity<>("Registration is updated", HttpStatus.CREATED);
    }
}
