package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    UsersRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Validated  @RequestBody Users users) {
        if (userRepo.findByEmail(users.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            users.setPassword(encoder.encode(users.getPassword()));
            userRepo.save(users);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
