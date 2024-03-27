package kg.alatoo.demooauth.service;

import kg.alatoo.demooauth.config.CustomUserDetails;
import kg.alatoo.demooauth.entity.Role;
import kg.alatoo.demooauth.entity.User;
import kg.alatoo.demooauth.repository.RoleRepository;
import kg.alatoo.demooauth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService, UserService{
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(PasswordEncoder encoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
                userRepository.findByUsername(username).orElse(new User()));
    }

    @Override
    public User createUser(User newUser) {
        return createUserWithRole(newUser, "USER");
    }

    private User createUserWithRole(User newUser, String roleName) {
        //TODO: check for already existence username, if the user with username already exists throw Exception
        newUser.setId(null);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.getRoles().add(getOrCreateRole(roleName));
        return userRepository.save(newUser);
    }

    @Override
    public User createAdmin(User newAdmin) {
        return createUserWithRole(newAdmin, "ADMIN");
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    protected Role getOrCreateRole(String roleName) {
        Optional<Role> optionalRole = roleRepository.findById(roleName);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }
    @Override
    public boolean CreateFirstAdmin(){
        if (!userRepository.findByUsername("baiel1").isPresent()){
            User admin = new User();
            admin.setUsername("baiel1");
            admin.setFirstname("baiel1");
            admin.setLastname("baiel1");
            admin.setPassword("baiel1");
            createAdmin(admin);
            return true;
        }
        return false;
    }

}
