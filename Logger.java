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
//                String[] inputs = inp.split(" ");
                 if (inp.equalsIgnoreCase("password")) {
                    currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
                    Files.writeString(path, currentTime + " [PASSWORD] passkey updated \n", StandardOpenOption.APPEND);
                } else if (inp.equalsIgnoreCase("encrypt")) {
                    currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
                    Files.writeString(path, currentTime + " [ENCRYPT] string encrypted \n", StandardOpenOption.APPEND);
                } else if (inp.equalsIgnoreCase("decrypt")) {
                     currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
                     Files.writeString(path, currentTime + " [DECRYPT] string decrypted \n", StandardOpenOption.APPEND);
                 }

                inp = s.nextLine();
            }

            currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
            Files.writeString(path, currentTime + " [STOP] Logging Stopped \n", StandardOpenOption.APPEND);
        } catch (Exception ex) {
            System.out.println("[INPUT] Error in Logger");
            ex.printStackTrace();
        }

    }
}

//+ inputs[1] + "\n"