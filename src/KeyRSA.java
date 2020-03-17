import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyRSA {
    private static final int KEY_GEN_RSA_BYTE = 1024;
    private static final String KEY_GEN_RSA_MODE = "RSA";

    public static KeyPair getRSAKeys()
    {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_GEN_RSA_MODE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyPairGenerator.initialize(KEY_GEN_RSA_BYTE);
        return keyPairGenerator.genKeyPair();
    }
}
