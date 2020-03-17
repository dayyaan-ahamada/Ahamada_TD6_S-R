import javax.crypto.*;
import java.io.*;
import java.security.*;

public class App
{
    private String messageCode = "Raffinés sont les hommes attirés par l’abominable..";;
    private SecretKey keyDES;
    private PublicKey publicKeyRSA;
    private PrivateKey privateKeyRSA;

    private static byte[] cryptDES(byte[] message, int mode, Key keyDES, String cypherMode) {
        Cipher cipher;
        byte[] newMessage;
        try {
            cipher = Cipher.getInstance(cypherMode);
            cipher.init(mode, keyDES);
            newMessage = cipher.doFinal(message);
        } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException |
                BadPaddingException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
        return newMessage;
    }

    public static byte[] encryptDES(String messageClair, SecretKey keyDES) {
        return cryptDES(messageClair.getBytes(), Cipher.ENCRYPT_MODE, keyDES, "DES");
    }

    public static String decryptDES(byte[] messageCode, SecretKey keyDES) {
        return new String(cryptDES(messageCode, Cipher.DECRYPT_MODE, keyDES, "DES"));
    }

    public static byte[] encryptRSA(String messageClair, PublicKey publicKeyRSA) {
        return cryptDES(messageClair.getBytes(), Cipher.ENCRYPT_MODE, publicKeyRSA, "RSA");
    }

    public static byte[] encryptRSA(byte[] messageClair, PublicKey publicKeyRSA) {
        return cryptDES(messageClair, Cipher.ENCRYPT_MODE, publicKeyRSA, "RSA");
    }

    public static String decryptRSA(byte[] messageCode, PrivateKey privateKeyRSA) {
        return new String(cryptDES(messageCode, Cipher.DECRYPT_MODE, privateKeyRSA, "RSA"));
    }

    public static byte[] decryptByteRSA(byte[] messageCode, PrivateKey privateKeyRSA) {
        return cryptDES(messageCode, Cipher.DECRYPT_MODE, privateKeyRSA, "RSA");
    }
    public void EX1(){
        System.out.println("\n1. Génère un couple de clés RSA et les affiche.");
        KeyPair keyPairRSA = KeyRSA.getRSAKeys();
        if (keyPairRSA != null) {
            publicKeyRSA = keyPairRSA.getPublic();
        }
        if (keyPairRSA != null) {
            privateKeyRSA = keyPairRSA.getPrivate();
        }
        System.out.print("\tClé RSA privée : ");
        for (byte b : privateKeyRSA.getEncoded()) System.out.print(b);
        System.out.print("\n\tClé RSA publique : ");
        for (byte b : publicKeyRSA.getEncoded()) System.out.print(b);

        System.out.println("\n\n2. Code et décode un message de votre choix");

        byte[] messageRSACode = encryptRSA(messageCode, publicKeyRSA);
        System.out.print("\tMessage codé : ");
        for (byte b : messageRSACode) System.out.print(b);
        String messageRSADecode = decryptRSA(messageRSACode, privateKeyRSA);
        System.out.println("\n\tMessage décodé : " + messageRSADecode);
    }

    public void EX2() {
        System.out.println("\n1 - Création d'un couple de clés RSA ");
        KeyPair keyPairRSA = KeyRSA.getRSAKeys();
        if (keyPairRSA != null) {
            publicKeyRSA = keyPairRSA.getPublic();
        }
        if (keyPairRSA != null) {
            privateKeyRSA = keyPairRSA.getPrivate();
        }
        System.out.print("\tClé RSA privée : ");
        for (byte b : privateKeyRSA.getEncoded()) System.out.print(b);
        System.out.print("\n\tClé RSA publique : ");
        for (byte b : publicKeyRSA.getEncoded()) System.out.print(b);

        System.out.println("\n\n2 - Création d'une clé secrète DES");
        keyDES = KeyDES.getDESKey();
        System.out.print("\tClé DES : ");
        for (byte b : keyDES.getEncoded()) System.out.print(b);

        System.out.println("\n\n3 - Codage de la clé secrète avec la clé publique RSA");
        byte [] cryptedKeyDES = encryptRSA(keyDES.getEncoded(),publicKeyRSA);
        System.out.print("\tClé secrète codée : ");
        for (byte b : cryptedKeyDES) System.out.print(b);

        System.out.println("\n\n4 - Décodage de la clé secrète avec la clé privée RSA ");
        byte[] decryptedPrivateKeyRSA = decryptByteRSA(cryptedKeyDES,privateKeyRSA);
        System.out.print("\tClé secrète décodée : ");
        for (byte b : decryptedPrivateKeyRSA) System.out.print(b);

        System.out.println("\n\n5 - Lecture d'un fichier texte à coder");
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/toEncryptFile"));
            BufferedWriter out = new BufferedWriter(new FileWriter("src/toEncryptFile",true));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("\tContenu du ficher : " + line);

                System.out.println("\n6 - Codage du fichier avec la clé secrète DES");
                byte[] cryptedTxt = encryptDES(line, keyDES);
                System.out.print("\tFichier codé : ");
                for (byte b : cryptedTxt) System.out.print(b);

                System.out.println("\n\n7 - Décodage et écriture du fichier codé, avec la clé secrète DES ");
                String decryptedTxt = decryptDES(cryptedTxt, keyDES);
                out.write("\n\nJe suis d'accord!");
                System.out.println("Ouvrir le fichier toEncryptFile");
            }
            in.close();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        App app = new App();
        System.out.print("\n--------------------------------EXERCICE 1---------------------------------------");
        app.EX1();
        System.out.print("\n--------------------------------EXERCICE 2---------------------------------------");
        app.EX2();
        System.out.println("\n--------------------------------FIN-------------------------------------------");
    }
}
