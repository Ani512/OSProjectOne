import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        System.out.println("Ani's Driver");
        List<String> history = new ArrayList<>();
        List<String> passHistory = new ArrayList<>();
        String password = null;
        try {
            Process logger = Runtime.getRuntime().exec(new String[] {"java", "Logger.java"});
            System.out.println("L: " + logger.pid());
            OutputStream loggerOut = logger.getOutputStream();

            Process encryptor = Runtime.getRuntime().exec(new String[] {"java", "Encryptor.java"});
            System.out.println("E: " + encryptor.pid());
            OutputStream encryptorOut = encryptor.getOutputStream();
            InputStream encryptorIn = encryptor.getInputStream();

            try (PrintStream toLogger = new PrintStream(loggerOut)) {
                PrintStream toEncryptor = new PrintStream(encryptorOut);
                Scanner fromEncryptor = new Scanner(encryptorIn);

                String inp = commands();
                while (!inp.equalsIgnoreCase("quit") && !inp.equalsIgnoreCase("6")) {
                    if (inp.equalsIgnoreCase("password") || inp.equalsIgnoreCase("1")) {
                        Scanner s = new Scanner(System.in);
                        System.out.print("\tDo you want to use a Password(KEY) from history? (yes | no): ");
                        String hist = s.nextLine();
                        if (hist.equalsIgnoreCase("no")) {
                            System.out.print("\t\tEnter Password(KEY): ");
                            String tempPass = s.nextLine();
                            if (tempPass.length() > 0) {
                                password = tempPass;
                                passHistory.add(password);
                                System.out.println("\t\t\tPassword(KEY) Updated: " + password);
                                toLogger.println("password " + password);
                                toLogger.flush();
                            } else {
                                System.out.println("\t\t\tInvalid Password(KEY)");
                                continue;
                            }
                        } else if (hist.equalsIgnoreCase("yes")) {
                            if (passHistory.size()>0) {
                                System.out.println("\t\tPrevious Passwords(KEYS) stored in history: ");
                                for (int i=0 ; i<passHistory.size() ; i++) {
                                    System.out.println("\t\t\t" + i + ": " + passHistory.get(i));
                                }
                                System.out.print("\t\t\t\tEnter Choice (numeric key of the choice): ");
                                int choice = s.nextInt();
                                if (choice>=0 && choice<passHistory.size()) {
                                    password = passHistory.get(choice);
                                    System.out.println("\t\t\tPassword(KEY) Updated: " + password);
                                    toLogger.println("password " + password);
                                    toLogger.flush();
                                } else {
                                    System.out.println("\t\t\t\tInvalid Password(KEY)");
                                    continue;
                                }
                            } else {
                                System.out.println("\t\tNo Passwords(Keys) saved in history :(");
                                continue;
                            }
                        }
                    }

                    else if (inp.equalsIgnoreCase("encrypt") || inp.equalsIgnoreCase("2")) {
                        if (passHistory.size()>0) {
                            Scanner s = new Scanner(System.in);
                            System.out.print("\tEnter string to Encrypt: ");
                            String valueToEncrypt = s.nextLine();
                            toEncryptor.println("encrypt " + valueToEncrypt + " " + password);
                            toEncryptor.flush();

                            String[] resultE = fromEncryptor.nextLine().split(" ");
                            if (resultE[0].equalsIgnoreCase("success")) {
                                System.out.println("\t\tEncrypted Value: " + resultE[1]);
                                toLogger.println("encrypt " + resultE[1]);
                                toLogger.flush();
                            } else if (resultE[0].equalsIgnoreCase("failure")) {
                                System.out.println("Failure in Encryption");
                            }
                        } else {
                            System.out.println("Password(Key) not Set!");
                        }
                    }

                    else if (inp.equalsIgnoreCase("decrypt") || inp.equalsIgnoreCase("3")) {
                        if (passHistory.size()>0) {
                            Scanner s = new Scanner(System.in);
                            System.out.print("\tEnter string to decrypt: ");
                            String valueToDecrypt = s.nextLine();
                            toEncryptor.println("decrypt " + valueToDecrypt + " " + password);
                            toEncryptor.flush();

                            String[] resultD = fromEncryptor.nextLine().split(" ");
                            if (resultD[0].equalsIgnoreCase("success")) {
                                System.out.println("\t\tDecrypted Value: " + resultD[1]);
                                toLogger.println("decrypt " + resultD[1]);
                                toLogger.flush();
                            } else if (resultD[0].equalsIgnoreCase("failure")) {
                                System.out.println("Failure in Decryption");
                            }
                        } else {
                            System.out.println("Password(Key) not Set!");
                        }
                    }

                    else if (inp.equalsIgnoreCase("history") || inp.equalsIgnoreCase("4")) {
                        if (history.size()>0) {
                            getHistory(history);
                        } else {
                            System.out.println("\tHistory is Empty :( ");
                        }
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

                toEncryptor.println("encryptorquit");
                toEncryptor.flush();

                toLogger.println("loggerquit");
                toLogger.flush();
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
        System.out.println("\tCurrent Data in History: ");
        for (String record: history) {
            System.out.println("\t\t" + record);
        }
    }
}
