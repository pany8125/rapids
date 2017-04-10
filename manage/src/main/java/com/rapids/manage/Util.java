package com.rapids.manage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author David on 17/2/24.
 */
public class Util {
    public static String toSHA1(byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(md.digest(convertme));
    }
}
