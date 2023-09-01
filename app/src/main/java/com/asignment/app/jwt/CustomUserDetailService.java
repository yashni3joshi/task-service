package com.asignment.app.jwt;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {
    /**
     * @param username :-  the username identifying the user whose data is required.
     * @return :-Match the username and password if not Match Throw exception
     * @throws UsernameNotFoundException :-Username Not Found Exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("Yash")){
            return new User("Yash","password123",new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User Not Found");
        }
    }


    /**
     *    @Autowired
     *     private UserRepository userRepository;
     *
     *     @Override
     *     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     *         User user = userRepository.findByUsername(username);
     *         if (user == null) {
     *             throw new UsernameNotFoundException("User Not Found");
     *         }
     *         return new org.springframework.security.core.userdetails.User(
     *             user.getUsername(), user.getPassword(), new ArrayList<>()
     *         );
     *     }
     */
}
