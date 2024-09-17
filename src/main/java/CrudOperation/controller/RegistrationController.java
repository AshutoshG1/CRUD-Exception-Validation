package CrudOperation.controller;

import CrudOperation.dto.LoginDto;
import CrudOperation.dto.RegistrationDto;
import CrudOperation.dto.TokenResponse;
import CrudOperation.entity.Registration;
import CrudOperation.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/crud")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody RegistrationDto registrationDto){
        Registration registration = registrationService.addUser(registrationDto);
        if(registration!=null){
            return new ResponseEntity<>("Registration is successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){

        String token = registrationService.verifyLogin(loginDto);

        if(token!=null){
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid credentials ", HttpStatus.UNAUTHORIZED);
    }


    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations(){
        List<Registration> allRegistrations = registrationService.getAllRegistrations();

        return new ResponseEntity(allRegistrations,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistration(@PathVariable long id){
        registrationService.deleteRegistration(id);
        return new ResponseEntity<>("Registration is deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRegistration(@PathVariable long id, @RequestBody RegistrationDto registrationDto){
        registrationService.updateRegistration(id,registrationDto);
        return new ResponseEntity<>("Registration id updated", HttpStatus.OK);
    }
}
