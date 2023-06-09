package com.hospital.booking.utils;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtils {
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
