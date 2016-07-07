package com.singtel.groupit.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class GroupITSharedPreferences extends BaseSharedPreferences {

    private static final String USER_TOKEN_1 = "user_token_1";
    private static final String USER_TOKEN_2 = "user_token_2";
    private static final String USER_TOKEN_3 = "user_token_3";

    public GroupITSharedPreferences(SharedPreferences pref) {
        super(pref);
    }

    // Login account info
    /*
        need split token to 3 strings to fix:
        Input too long: cannot operate on 534 bytes of data with 256-byte RSA key
     */
    private boolean checkUserTokenCreated(Context context) {
        return KeystoreUtil.generatePrivateKey(context, USER_TOKEN_1)
                && KeystoreUtil.generatePrivateKey(context, USER_TOKEN_2)
                && KeystoreUtil.generatePrivateKey(context, USER_TOKEN_3)
                ;
    }

    public String getUserToken(Context context) {
        String token = "";
        if (checkUserTokenCreated(context)) {
            // whole token for AES
//            token = getString(USER_TOKEN_1);
//            if (!TextUtils.isEmpty(token)) {
//                token = KeystoreUtil.decryptString(USER_TOKEN_1, token);
//            }

            // split sub-strings for RSA
            String token1 = getString(USER_TOKEN_1);
            String token2 = getString(USER_TOKEN_2);
            String token3 = getString(USER_TOKEN_3);
            if (!TextUtils.isEmpty(token1) && !TextUtils.isEmpty(token2) && !TextUtils.isEmpty(token3)) {
                token = KeystoreUtil.decryptString(USER_TOKEN_1, token1)
                        + KeystoreUtil.decryptString(USER_TOKEN_2, token2)
                        + KeystoreUtil.decryptString(USER_TOKEN_3, token3);

            }
        }
        LogUtils.i(this, "getUserToken: " + token.length() + ": " + token);
        return token;
    }

    public boolean saveUserToken(Context context, String token) {
        LogUtils.i(this, "saveUserToken: " + token.length() + ": " + token);
        if (checkUserTokenCreated(context)) {
            // whole token for AES
//            String encryptedToken = KeystoreUtil.encryptString(USER_TOKEN_1, token);
//            if (!TextUtils.isEmpty(encryptedToken)) {
//                submitString(USER_TOKEN_1, encryptedToken);
//                return true;
//            }

            // split sub-strings for RSA
            int partIndex = token.length() / 3;
            String encryptedToken1 = KeystoreUtil.encryptString(USER_TOKEN_1, token.substring(0, partIndex));
            String encryptedToken2 = KeystoreUtil.encryptString(USER_TOKEN_2, token.substring(partIndex, partIndex*2));
            String encryptedToken3 = KeystoreUtil.encryptString(USER_TOKEN_3, token.substring(partIndex*2, token.length()));
            if (!TextUtils.isEmpty(encryptedToken1) && !TextUtils.isEmpty(encryptedToken2) && !TextUtils.isEmpty(encryptedToken3)) {
                submitString(USER_TOKEN_1, encryptedToken1);
                submitString(USER_TOKEN_2, encryptedToken2);
                submitString(USER_TOKEN_3, encryptedToken3);
                return true;
            }
        }
        return false;
    }
}
