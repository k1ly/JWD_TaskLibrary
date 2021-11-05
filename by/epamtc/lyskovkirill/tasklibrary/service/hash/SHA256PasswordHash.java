package by.epamtc.lyskovkirill.tasklibrary.service.hash;

import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;

import java.security.MessageDigest;

public class SHA256PasswordHash {

    public static String computeHash(String password) throws ServiceException {
        String passwordHash = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes());
            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : hash) {
                hashBuilder.append(b);
            }
            passwordHash = hashBuilder.toString();
        } catch (Exception e) {
            throw new ServiceException("Error during password hashing", e);
        }
        return passwordHash;
    }
}
