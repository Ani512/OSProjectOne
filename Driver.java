import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        System.out.println("Ani's Driver");
        List<String> history = new ArrayList<>();
        String password;
        try {
            Process logger = Runtime.getRuntime().exec(new String[] {"java", "Logger.java"});
            InputStream loggerInp = logger.getInputStream();
            OutputStream loggerOut = logger.getOutputStream();

            Process encryptor = Runtime.getRuntime().exec(new String[] {"java", "Encryptor.java"});
            InputStream encryptorInp = encryptor.getInputStream();
            OutputStream encryptorOut = encryptor.getOutputStream();

            try (Scanner fromMem = new Scanner(loggerInp)) {
                PrintStream toMem = new PrintStream(loggerOut);
                String inp = commands();
                while (!inp.equalsIgnoreCase("quit") && !inp.equalsIgnoreCase("6")) {
                    if (inp.equalsIgnoreCase("password") || inp.equalsIgnoreCase("1")) {
                        Scanner s = new Scanner(System.in);
                        System.out.print("\tEnter Password(KEY): ");
                        String tempPass = s.nextLine();
                        if (tempPass.length() > 0) {
                            password = tempPass;
                            System.out.println("\t\tPassword(KEY) Updated: " + password);
                            toMem.println("password");
                            toMem.flush();
                        } else {
                            System.out.println("\t\tInvalid Password(KEY)");
                        }
                    }

                    else if (inp.equalsIgnoreCase("history") || inp.equalsIgnoreCase("4")) {
                        if (history.size()>0) {
                            getHistory(history);
                        } else {
                            System.out.println("\tHistory is Empty :( ");
                        }
                        toMem.println("history");
                        toMem.flush();
                    }

                    else if (inp.equalsIgnoreCase("help") || inp.equalsIgnoreCase("5")) {
                        System.out.println("Enter a command from the list of commands below. You may enter the number associated with the command or the actual command itself");
                        // DO NOTHING AND GO TAKE INPUT AGAIN
                    }

                    else {
                        System.out.println("\tWrong Input");
                    }

                    inp = commands();
                }

                toMem.println("loggerquit");
                toMem.flush();

                toMem.println("encryptorquit");
                toMem.flush();
            }
            logger.waitFor();
        } catch(IOException ex) {
            System.out.println("Unable to run Logger");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // function to take user input
    public static String commands() {
        System.out.println("\nCommands:");
        System.out.println("1: password");
        System.out.println("2: encrypt");
        System.out.println("3: decrypt");
        System.out.println("4: history");
        System.out.println("5: help");
        System.out.println("6: quit");

        Scanner s = new Scanner(System.in);
        System.out.print("Enter Option: ");
        return s.nextLine();
    }

    // function to print history
    public static void getHistory(List<String> history) {
        for (String record: history) {
            System.out.println(record);
        }
    }

    public static void encrypt() {

    }
}
