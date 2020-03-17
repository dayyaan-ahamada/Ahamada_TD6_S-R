import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class KeyDES {
    private static final String KEY_GEN_DES_MODE = "DES";
    private static final int KEY_GEN_DES_BYTE = 56;
    public static SecretKey getDESKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_GEN_DES_MODE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyGenerator.init(KEY_GEN_DES_BYTE);
        return keyGenerator.generateKey();
    }
}
