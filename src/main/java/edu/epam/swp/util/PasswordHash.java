package edu.epam.swp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
    private static final Logger logger = LogManager.getLogger(PasswordHash.class);
    private static final String MD5_ALGORITHM = "MD5";
    private static final int RADIX = 16;
    private static final int LENGTH = 32;

    private PasswordHash() {}

    public static String createHash(String password)
    {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashText = number.toString(RADIX);
            while (hashText.length() < LENGTH) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error occurred while creating hash",e);
            throw new RuntimeException("Error occurred while creating hash",e);
        }
    }

}
