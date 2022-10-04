import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Logger {
    public static void main(String[] args) throws IOException {
//        System.out.println("Logger");
        File file = new File("textfile.txt");
        file.createNewFile(); // If file does not exist, creates a new one
        Path path = Paths.get("./textfile.txt");

        try (Scanner s = new Scanner(System.in)) {
            String inp = s.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
            Files.writeString(path, currentTime + " [START] Logging Started \n", StandardOpenOption.APPEND);

            while(!inp.equalsIgnoreCase("loggerquit")) {
                String[] inputs = inp.split(" ");
                if (inputs.length == 2) {
                    if (inputs[0].equalsIgnoreCase("password")) {
                        currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
                        Files.writeString(path, currentTime + " [PASSWORD] passkey updated - " + inputs[1] + "\n", StandardOpenOption.APPEND);
                    } else if (inputs[0].equalsIgnoreCase("encrypt")) {
                        currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
                        Files.writeString(path, currentTime + " [ENCRYPT] string encrypted - " + inputs[1] + "\n", StandardOpenOption.APPEND);
                    } else if (inputs[0].equalsIgnoreCase("decrypt")) {
                        currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
                        Files.writeString(path, currentTime + " [DECRYPT] string decrypted - " + inputs[1] + "\n", StandardOpenOption.APPEND);
                    }
                } else {
                    System.out.println("failure INVALID_DRIVER_INP");
                }

                inp = s.nextLine();
            }

            currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
            Files.writeString(path, currentTime + " [STOP] Logging Stopped \n", StandardOpenOption.APPEND);
        } catch (Exception ex) {
            System.out.println("failure INPUT_ERROR");
        }

    }
}