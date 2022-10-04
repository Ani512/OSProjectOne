import java.util.Scanner;

public class Encryptor {
    public static void main(String[] args) {
//        System.out.println("Encryptor");
        String inp;
        try (Scanner s = new Scanner(System.in)) {
            inp = s.nextLine();

            while (!inp.equalsIgnoreCase("encryptorquit")) {
                String[] inputs = inp.split(" ");
                if (inputs.length == 3) {
                    if (inputs[0].equalsIgnoreCase("encrypt")) {
                        String e = encrypt(inputs[1].toUpperCase(), inputs[2]);
                        if (e!=null) {
                            System.out.println("success " + e);
                        } else {
                            System.out.println("failure ENCRYPTION_ERROR");
                        }
                    } else if (inputs[0].equalsIgnoreCase("decrypt")) {
                        String e = decrypt(inputs[1].toUpperCase(), inputs[2]);
                        if (e!=null) {
                            System.out.println("success " + e);
                        } else {
                            System.out.println("failure DECRYPTION_ERROR");
                        }
                    }
                } else {
                    System.out.println("failure INVALID_DRIVER_INP");
                }

                inp = s.nextLine();
            }
        } catch (Exception ex) {
            System.out.println("failure INPUT_ERROR");
        }
    }

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