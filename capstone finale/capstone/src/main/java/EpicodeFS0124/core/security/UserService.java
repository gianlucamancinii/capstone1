package EpicodeFS0124.core.security;



import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final AuthenticationManager auth;
    private final JwtUtils jwt;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;


    public Optional<LoginResponseDTO> login(String username, String password) {
        try {
            //SI EFFETTUA IL LOGIN
            //SI CREA UNA AUTENTICAZIONE OVVERO L'OGGETTO DI TIPO AUTHENTICATION
            var a = auth.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            a.getAuthorities(); //SERVE A RECUPERARE I RUOLI/IL RUOLO

            //SI CREA UN CONTESTO DI SICUREZZA CHE SARA UTILIZZATO IN PIU OCCASIONI
            SecurityContextHolder.getContext().setAuthentication(a);

            var user = usersRepository.findOneByUsername(username).orElseThrow();
            var dto = LoginResponseDTO.builder()
                    .withUser(RegisteredUserDTO.builder()
                            .withId(user.getId())
                            .withFirstName(user.getFirstName())
                            .withLastName(user.getLastName())
                            .withEmail(user.getEmail())
                            .withRoles(user.getRoles())
                            .withUsername(user.getUsername())
                            .build())
                    .build();

            //UTILIZZO DI JWTUTILS PER GENERARE IL TOKEN UTILIZZANDO UNA AUTHENTICATION E LO ASSEGNA ALLA LOGINRESPONSEDTO
            dto.setToken(jwt.generateToken(a));

            return Optional.of(dto);
        } catch (NoSuchElementException e) {
            //ECCEZIONE LANCIATA SE LO USERNAME E SBAGLIATO E QUINDI L'UTENTE NON VIENE TROVATO
            log.error("User not found", e);
            throw new InvalidLoginException(username, password);
        } catch (AuthenticationException e) {
            //ECCEZIONE LANCIATA SE LA PASSWORD E SBAGLIATA
            log.error("Authentication failed", e);
            throw new InvalidLoginException(username, password);
        }
    }

    public RegisteredUserDTO register(RegisterUserDTO register) {
        if (usersRepository.existsByUsername(register.getUsername())) {
            throw new EntityExistsException("Utente gia' esistente");
        }
        if (usersRepository.existsByEmail(register.getEmail())) {
            throw new EntityExistsException("Email gia' registrata");
        }
        Roles roles = rolesRepository.findById(Roles.ROLES_USER).get();
        /*
        if(!rolesRepository.existsById(Roles.ROLES_USER)){
            roles = new Roles();
            roles.setRoleType(Roles.ROLES_USER);
        } else {
            roles = rolesRepository.findById(Roles.ROLES_USER).get();
        }

         */
        CustomUser u = new CustomUser();
        BeanUtils.copyProperties(register, u);
        u.setPassword(encoder.encode(register.getPassword()));
        u.getRoles().add(roles);
        usersRepository.save(u);
        RegisteredUserDTO response = new RegisteredUserDTO();
        BeanUtils.copyProperties(u, response);
        response.setRoles(List.of(roles));
//        emailService.sendWelcomeEmail(u.getEmail());

        return response;

    }

    public RegisteredUserDTO registerAdmin(RegisterUserDTO register) {
        if (usersRepository.existsByUsername(register.getUsername())) {
            throw new EntityExistsException("Utente gia' esistente");
        }
        if (usersRepository.existsByEmail(register.getEmail())) {
            throw new EntityExistsException("Email gia' registrata");
        }
        Roles roles = rolesRepository.findById(Roles.ROLES_ADMIN).get();
        CustomUser u = new CustomUser();
        BeanUtils.copyProperties(register, u);
        u.setPassword(encoder.encode(register.getPassword()));
        u.getRoles().add(roles);
        usersRepository.save(u);
        RegisteredUserDTO response = new RegisteredUserDTO();
        BeanUtils.copyProperties(u, response);
        response.setRoles(List.of(roles));
        return response;

    }


}
