package com.singtel.groupit.util;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyInfo;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

/**
 * Created by lanna on 7/1/16.
 *
 */

public class KeystoreUtil {

//    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    // FIXME
    public static void test(Context context) {
        KeyInfo keyInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogUtils.i("keystore", "isInsideSecureHardware:" + (keyInfo == null ? "null" : keyInfo.isInsideSecureHardware()));
        }


        /*
         * Load the Android KeyStore instance using the the
         * "AndroidKeyStore" provider to list out what entries are
         * currently stored.
         */
        KeyStore ks = null;
        Enumeration<String> aliases = null;
        try {
            ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            aliases = ks.aliases();
        } catch (Exception e) {
            LogUtils.w("keystore", LogUtils.getStackTraceString(e));
        }

        String lastAlias = null;
        if (aliases != null) {
            while (aliases.hasMoreElements()) {
                lastAlias = aliases.nextElement();
                LogUtils.d("keystore", "get aliases:" + lastAlias);
            }
        }

        if (ks != null) {
            String alias = "password";
            String textNeedSecure = "lanna123"; // the inputted password
            createNewKeys(context, ks, alias);
            String encryptedText = encryptString(ks, alias, textNeedSecure);
            decryptString(ks, alias, encryptedText);
        }
    }


//    @TargetApi(Build.VERSION_CODES.M)
//    public static void generatePrivateKey(String keystoreAlias) {
//        try {
//            /*
//             * Generate a new EC key pair entry in the Android Keystore by
//             * using the KeyPairGenerator API. The private key can only be
//             * used for signing or verification and only with SHA-256 or
//             * SHA-512 as the message digest.
//             */
//            KeyPairGenerator kpg = KeyPairGenerator.getInstance(
//                    KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
//
//            kpg.initialize(new KeyGenParameterSpec.Builder(
//                    keystoreAlias,
//                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
//                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
//                    .build());
//
//            KeyPair kp = kpg.generateKeyPair();
//            LogUtils.i("keystore", "generatePrivateKey: privateKey=" + kp.getPrivate().toString()
//                    +", publicKey=" + kp.getPublic().toString());
//        } catch (Exception e) {
//            LogUtils.e("keystore", LogUtils.getStackTraceString(e));
//        }
//    }

    public static void createNewKeys(Context context, KeyStore keyStore, String alias) {
        try {
            // Create new key if needed
            if (!keyStore.containsAlias(alias)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore"); // "RSA" is to support 18+
                generator.initialize(spec);

                KeyPair kp = generator.generateKeyPair(); // creates the new pair of keys (Private and corresponding Public key)
                LogUtils.d("keystore", "generatePrivateKey: privateKey=" + kp.getPrivate().toString()
                        +", publicKey=" + kp.getPublic().toString());
            }
        } catch (Exception e) {
            LogUtils.w("keystore", LogUtils.getStackTraceString(e));
        }
    }


    public static byte[] signing(String alias, byte[] data) {
        try {
            /*
             * Use a PrivateKey in the KeyStore to create a signature over
             * some data.
             */
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(alias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                LogUtils.w("keystore", "Not an instance of a PrivateKeyEntry");
                return null;
            }
            Signature s = Signature.getInstance("SHA256withECDSA");
            s.initSign(((KeyStore.PrivateKeyEntry) entry).getPrivateKey());
            s.update(data);
            return s.sign();
        } catch (Exception e) {
            LogUtils.w("keystore", LogUtils.getStackTraceString(e));
        }
        return null;
    }


    public static boolean verifyKeystore(String alias, byte[] signature, byte[] data) {
        try {
            /*
             * Verify a signature previously made by a PrivateKey in our
             * KeyStore. This uses the X.509 certificate attached to our
             * private key in the KeyStore to validate a previously
             * generated signature.
             */
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(alias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                LogUtils.w("keystore", "Not an instance of a PrivateKeyEntry");
                return false;
            }
            Signature s = Signature.getInstance("SHA256withECDSA");
            s.initVerify(((KeyStore.PrivateKeyEntry) entry).getCertificate());
            s.update(data);
            boolean verified = s.verify(signature);
            LogUtils.d("keystore", "verifyKeystore: " + verified + " for " + alias);
            return verified;
        } catch (Exception e) {
            LogUtils.w("keystore", LogUtils.getStackTraceString(e));
        }
        return false;
    }


    /*
        TODO Supports
     */


    public static void deleteKey(KeyStore keyStore, String alias) {
        try {
            keyStore.deleteEntry(alias);
            LogUtils.d("keystore", "deleteEntry: done for alias=" + alias);
        } catch (KeyStoreException e) {
            LogUtils.w("keystore", LogUtils.getStackTraceString(e));
        }
    }


    public static String encryptString(KeyStore keyStore, String alias, String initialText) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

            // Encrypt the text
            if (initialText.isEmpty()) {
                LogUtils.w("keystore", "Invalid Initial Text");
                return null;
            }

            Cipher input =
//                    Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
                    Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
            input.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, input);
            cipherOutputStream.write(initialText.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte [] vals = outputStream.toByteArray();
            String encryptedText = Base64.encodeToString(vals, Base64.DEFAULT); // encrypted text
            LogUtils.d("keystore", "encryptString: alias: "+ alias + " text:\"" + initialText + "\"to:\n\"" + encryptedText + "\"");
            return encryptedText;
        } catch (Exception e) {
            LogUtils.w("keystore", LogUtils.getStackTraceString(e));
        }
        return null;
    }


    public static String decryptString(KeyStore keyStore, String alias, String encryptedText) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);

            // Decrypt the text
            Cipher output =
//                    Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
                    Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(Base64.decode(encryptedText, Base64.DEFAULT)), output);
            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            String decryptedText = new String(bytes, 0, bytes.length, "UTF-8"); // decrypted text
            LogUtils.d("keystore", "decryptString: alias=" + alias + " got \"" + decryptedText+"\"");
            return decryptedText;

        } catch (Exception e) {
            LogUtils.w("keystore", LogUtils.getStackTraceString(e));
        }
        return null;
    }

}
