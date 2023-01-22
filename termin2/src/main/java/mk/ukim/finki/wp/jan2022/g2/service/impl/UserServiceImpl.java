//package mk.ukim.finki.wp.jan2022.g2.service.impl;
//
//import mk.ukim.finki.wp.jan2022.g2.model.User;
//import mk.ukim.finki.wp.jan2022.g2.model.exceptions.InvalidUserIdException;
//import mk.ukim.finki.wp.jan2022.g2.repository.UserRepository;
//import mk.ukim.finki.wp.jan2022.g2.service.UserService;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Service
//public class UserServiceImpl implements UserService,UserDetailsService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public User findById(Long id) {
//        return userRepository.findById(id).orElseThrow(InvalidUserIdException::new);
//    }
//
//    @Override
//    public List<User> listAll() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public User create(String username, String password, String role) {
//        return userRepository.save(new User(username,passwordEncoder.encode(password),role));
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Stream.of(new SimpleGrantedAuthority(user.getRole())).collect(Collectors.toList())
//        );
//    }
//}
package mk.ukim.finki.wp.jan2022.g2.service.impl;

        import mk.ukim.finki.wp.jan2022.g2.model.User;
        import mk.ukim.finki.wp.jan2022.g2.model.exceptions.InvalidUserIdException;
        import mk.ukim.finki.wp.jan2022.g2.repository.UserRepository;
        import mk.ukim.finki.wp.jan2022.g2.service.UserService;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.stream.Collectors;
        import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(InvalidUserIdException::new);
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(String username, String password, String role) {
        return userRepository.save(new User(username,passwordEncoder.encode(password),role));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Stream.of(new SimpleGrantedAuthority(user.getRole())).collect(Collectors.toList())
        );
    }
}
//@Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Stream.of(new SimpleGrantedAuthority(user.getRole())).collect(Collectors.toList())
//        );
//    }