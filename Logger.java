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
        File file = new File("logfile.txt");
        file.createNewFile(); // If file does not exist, creates a new one
        Path path = Paths.get("./logfile.txt");

        Scanner s = new Scanner(System.in);
        String inp = s.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String currentTime = LocalDateTime.now().format(formatter);
            Files.writeString(path, currentTime + " [START] Logging Started \n", StandardOpenOption.APPEND);

            while(!inp.equalsIgnoreCase("loggerquit")) {
                String[] inputs = inp.split(" ");
                if (inputs.length == 2) {
                    if (inputs[0].equalsIgnoreCase("password")) {
                        currentTime = LocalDateTime.now().format(formatter);
                        Files.writeString(path, currentTime + " [PASSWORD] passkey - " + inputs[1] + "\n", StandardOpenOption.APPEND);
                    } else if (inputs[0].equalsIgnoreCase("encrypt")) {
                        currentTime = LocalDateTime.now().format(formatter);
                        Files.writeString(path, currentTime + " [ENCRYPT] string - " + inputs[1] + "\n", StandardOpenOption.APPEND);
                    } else if (inputs[0].equalsIgnoreCase("decrypt")) {
                        currentTime = LocalDateTime.now().format(formatter);
                        Files.writeString(path, currentTime + " [DECRYPT] string - " + inputs[1] + "\n", StandardOpenOption.APPEND);
                    } else if (inputs[0].equalsIgnoreCase("result")) {
                        currentTime = LocalDateTime.now().format(formatter);
                        Files.writeString(path, currentTime + " [RESULT] - " + inputs[1] + "\n", StandardOpenOption.APPEND);
                    } else {
                        currentTime = LocalDateTime.now().format(formatter);
                        Files.writeString(path, currentTime + " [FAILURE] Unexpected Input\n", StandardOpenOption.APPEND);
                    }
                } else {
                    currentTime = LocalDateTime.now().format(formatter);
                    Files.writeString(path, currentTime + " [FAILURE] Error in Logging\n", StandardOpenOption.APPEND);
                }

                inp = s.nextLine();
            }

            currentTime = LocalDateTime.now().format(formatter);
            Files.writeString(path, currentTime + " [STOP] Logging Stopped \n", StandardOpenOption.APPEND);
        } catch (Exception ex) {
            System.out.println("failure INPUT_ERROR");
        }

    }
}