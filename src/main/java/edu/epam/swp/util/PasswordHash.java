package edu.epam.swp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PasswordHash class is used to hash password.
 * @author romab
 */
public class PasswordHash {
    private static final Logger logger = LogManager.getLogger(PasswordHash.class);
    private static final String MD5_ALGORITHM = "MD5";
    private static final int RADIX = 16;
    private static final int LENGTH = 32;

    private PasswordHash() {}

    /**
     * Creates hash based on password.
     * @param password String containing the password.
     * @return String containing the hash.
     */
    public static String createHash(String password)
    {
        String hashText;
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            hashText = number.toString(RADIX);
            while (hashText.length() < LENGTH) {
                hashText = "0" + hashText;
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error occurred while creating hash",e);
            hashText = "";
        }
        return hashText;
    }

}
