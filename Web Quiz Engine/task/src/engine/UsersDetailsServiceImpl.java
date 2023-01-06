package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsersRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Users users = userRepo.findByEmail(email);
            return new UsersDetailsImpl(users);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Not found: " + email);
        }
    }

}
