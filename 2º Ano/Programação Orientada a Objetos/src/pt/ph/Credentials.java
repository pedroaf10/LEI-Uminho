package pt.ph;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

/**
 * User Credentials class.
 */
public class Credentials implements Serializable {
    private final String email;
    private final String password;

    /**
     * UserCredentials constructor.
     * @param email User email.
     * @param password User password.
     */
    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
