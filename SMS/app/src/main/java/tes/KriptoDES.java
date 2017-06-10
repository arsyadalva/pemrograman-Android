package tes;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class KriptoDES {

    public static String enkrip(String pesan, String key){
        try {
            SecretKeySpec SK = new SecretKeySpec(key.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, SK);
            byte[] encrypted = cipher.doFinal(pesan.getBytes());
            return Base64.encodeToString(encrypted, Base64.NO_PADDING);
        } catch (Exception e) {
            return "ERROR:" + e.getMessage();
        }
    }

   public static String dekrip(String pesan, String key){
        try {
            SecretKeySpec SK = new SecretKeySpec(key.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, SK);
            byte[] decrypted = cipher.doFinal(Base64.decode(pesan, Base64.NO_PADDING));
            return new String(decrypted);
        } catch (Exception e) {
            return "ERROR";
        }
    }

}
