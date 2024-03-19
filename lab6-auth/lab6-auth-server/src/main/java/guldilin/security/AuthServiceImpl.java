package guldilin.security;

import guldilin.config.PropertyKey;
import guldilin.exceptions.AuthError;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;

/**
 * Auth service implementation.
 */
@NoArgsConstructor
@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    /**
     * {@inheritDoc}
     */
    public void authUser(final String login, final String password) throws AuthError {
        var configUser = PropertyKey.AUTH_USER.lookupValue();
        var configPass = PropertyKey.AUTH_PASSWORD.lookupValue();
        var loggedIn = configUser.equals(login) && configPass.equals(password);
        if (!loggedIn) throw new AuthError();
    }
}
