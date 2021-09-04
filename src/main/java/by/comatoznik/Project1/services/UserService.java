package by.comatoznik.Project1.services;

import by.comatoznik.Project1.entities.Role;
import by.comatoznik.Project1.entities.User;
import by.comatoznik.Project1.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService  implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findByUserName(userName);
        if(user==null || !user.isEnabled()){
            throw new UsernameNotFoundException(String.format("User '%s' not found",userName));
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role>roles){
        return roles.stream().map(r ->new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
