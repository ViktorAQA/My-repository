package com.project.demo.ui.security;


import com.project.demo.ui.base.TestFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


public class SecurityUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";


    private SecurityUtils()
    {
    }


    public static String encrypt(String input) throws TestFrameworkException
    {
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKeyFromPassword(), getIvParameterSpec());
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException |
                        InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            LOGGER.error("[SecurityUtils] ", e);
            throw new TestFrameworkException(" Cannot encrypt! Cause :" + e.getCause());
        }
    }


    public static String decrypt(String cipherText) throws TestFrameworkException
    {
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKeyFromPassword(), getIvParameterSpec());
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(plainText);
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException |
                        InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            LOGGER.error("[SecurityUtils] ", e);
            throw new TestFrameworkException(" Cannot encrypt! Cause :" + e.getCause());
        }
    }


    private static SecretKey getKeyFromPassword() throws NoSuchAlgorithmException, InvalidKeySpecException
    {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec("Hod!as1Dj@movete".toCharArray(), "Sodium Lithium".getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }


    private static IvParameterSpec getIvParameterSpec()
    {
        byte[] iv = {0, 1, 0, 2, 1, 3, 0, 4, 9, 5, 0, 6, 0, 7, 2, 8};
        return new IvParameterSpec(iv);
    }
}
