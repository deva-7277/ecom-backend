package com.project.ecommerce.service;

import com.project.ecommerce.config.MessageStrings;
import com.project.ecommerce.exceptions.AuthenticationFailException;
import com.project.ecommerce.model.AuthenticationToken;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository repository;

    // save the confirmation token
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    // get token of the User
    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    // get Uer from the token
    public User getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (Objects.nonNull(authenticationToken)) {
            if (Objects.nonNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    // check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException {
        if (!Objects.nonNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_PRESENT);
        }
        if (!Objects.nonNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_VALID);
        }
    }
}
