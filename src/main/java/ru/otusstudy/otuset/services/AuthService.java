package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.models.dto.requests.LoginDto;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public boolean doLogin(LoginDto loginDto) {
        try {
            OtusetUser otusetUser = userService.loadUserByUsername(loginDto.getLogin());
            if (passwordEncoder.matches(loginDto.getPassword(), otusetUser.getPassword())) {
                return true;
            } else {
                throw new BadCredentialsException("Неверный пароль");
            }
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            throw new BadCredentialsException(format("Не верный пароль или пользователь %s не найден",
                    loginDto.getLogin()));
        }
    }
}
