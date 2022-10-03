
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Encryptor {
    public static void main(String[] args) throws IOException {
//        System.out.println("Encryptor");

        Path path = Paths.get("./textfile.txt");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);

        String inp;
        try (Scanner s = new Scanner(System.in)) {
            inp = s.nextLine();

            while (!inp.equalsIgnoreCase("encryptorquit")) {
                if (inp.equalsIgnoreCase("encrypt")) {
                    currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
                    Files.writeString(path, currentTime + " Reached Encryptor \n", StandardOpenOption.APPEND);
                }

                inp = s.nextLine();
            }
        } catch (Exception ex) {
            System.out.println("[INPUT] Error in Encryptor");
            ex.printStackTrace();
        }
    }

    public static String encrypt(String value, String key) {
        String encryptedValue = "";
        value = value.toUpperCase();
        for (int i=0, j=0 ; i<value.length() ; i++) {
            char letter = value.charAt(i);
            encryptedValue += (char)(((letter - 65) + (key.charAt(j)-65)) % 26 + 65);
            j = ++j % key.length();
        }
        return encryptedValue;
    }

    public static String decrypt(String value, String key) {
        String decryptedValue = "";
        value = value.toUpperCase();
        for (int i = 0, j = 0; i < value.length(); i++) {
            char letter = value.charAt(i);
            decryptedValue += (char)((letter - key.charAt(j) + 26) % 26 + 65);
            j = ++j % key.length();
        }
        return decryptedValue;
    }
}