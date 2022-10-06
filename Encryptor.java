import java.util.Scanner;

public class Encryptor {
    public static void main(String[] args) {
//        System.out.println("Encryptor");
        String inp;
        String passKey = "";
        try (Scanner s = new Scanner(System.in)) {
            inp = s.nextLine();

            while (!inp.equalsIgnoreCase("encryptorquit")) {
                String[] inputs = inp.split(" ");
                // The TWO Inputs are: 1. type of input 2: value to use
                if (inputs.length == 2) {
                    if (inputs[0].equalsIgnoreCase("encrypt") && passKey.length()>0) {
                        String e = encrypt(inputs[1].toUpperCase(), passKey);
                        if (e!=null) {
                            System.out.println("success " + e);
                        } else {
                            System.out.println("failure ENCRYPTION_ERROR");
                        }
                    } else if (inputs[0].equalsIgnoreCase("decrypt") && passKey.length()>0) {
                        String e = decrypt(inputs[1].toUpperCase(), passKey);
                        if (e!=null) {
                            System.out.println("success " + e);
                        } else {
                            System.out.println("failure DECRYPTION_ERROR");
                        }
                    } else if (inputs[0].equalsIgnoreCase("password")) {
                        passKey = inputs[1].toUpperCase();
                        System.out.println("success PASSKEY_SET");
                    } else {
                        System.out.println("failure PASSKEY_NOT_SET");
                    }
                } else {
                    System.out.println("failure INVALID_DRIVER_INPUT");
                }

                inp = s.nextLine();
            }
        } catch (Exception ex) {
            System.out.println("failure INPUT_ERROR");
        }
    }

    // function to encrypt using virnere cipher
    public static String encrypt(String value, String key) {
        try {
            StringBuilder encryptedValue = new StringBuilder();
            value = value.toUpperCase();
            for (int i = 0, j = 0; i < value.length(); i++) {
                char letter = value.charAt(i);
                encryptedValue.append((char) (((letter - 65) + (key.charAt(j) - 65)) % 26 + 'A'));
                j = ++j % key.length();
            }
            return encryptedValue.toString();
        } catch (Exception ex) {
            System.out.println("failure ENCRYPTION_ERROR");
        }

        return null;
    }

    // function to decrypt using virnere cipher
    public static String decrypt(String value, String key) {
        try {
            StringBuilder decryptedValue = new StringBuilder();
            value = value.toUpperCase();
            for (int i = 0, j = 0; i < value.length(); i++) {
                char letter = value.charAt(i);
                decryptedValue.append((char) ((letter - key.charAt(j) + 26) % 26 + 'A'));
                j = ++j % key.length();
            }
            return decryptedValue.toString();
        } catch (Exception ex) {
            System.out.println("failure DECRYPTION_ERROR");
        }

        return null;
    }
}