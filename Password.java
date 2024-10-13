package userForm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    // Basic hash using String.hashCode() - Not secure for production use!
    public static int hashPassword(String password) {
        return password.hashCode();
    }
}
