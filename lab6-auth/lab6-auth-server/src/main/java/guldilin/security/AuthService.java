package guldilin.security;

import guldilin.exceptions.AuthError;

/**
 * Service for user authorization.
 */
public interface AuthService {
    /**
     * Auth user by login and password.
     *
     * @param login user login
     * @param password user password
     * @throws AuthError if login or password is not correct
     */
    void authUser(String login, String password) throws AuthError;
}
