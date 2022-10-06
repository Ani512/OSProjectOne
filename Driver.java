import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        System.out.println("Ani's Driver");
        List<String> history = new ArrayList<>();
        String password = null; // stores the password after the encryptor processes the password
        try {
            // Creating a new process called logger
            Process logger = Runtime.getRuntime().exec(new String[] {"java", "Logger.java"});
//            System.out.println("L: " + logger.pid());
            OutputStream loggerOut = logger.getOutputStream();

            // Creating a new process called encryptor
            Process encryptor = Runtime.getRuntime().exec(new String[] {"java", "Encryptor.java"});
//            System.out.println("E: " + encryptor.pid());
            OutputStream encryptorOut = encryptor.getOutputStream();
            InputStream encryptorIn = encryptor.getInputStream();

            try (PrintStream toLogger = new PrintStream(loggerOut)) {
                PrintStream toEncryptor = new PrintStream(encryptorOut);
                Scanner fromEncryptor = new Scanner(encryptorIn);
//                Opening IN and OUT streams for the respective processes to read and write

                String inp = commands();
                while (!inp.equalsIgnoreCase("quit") && !inp.equalsIgnoreCase("6")) {

//                    code for handling password
                    if (inp.equalsIgnoreCase("password") || inp.equalsIgnoreCase("1")) {
                        Scanner s = new Scanner(System.in);
                        System.out.print("\tDo you want to use a string from history? (yes | no): ");
                        String hist = s.nextLine();
                        if (hist.equalsIgnoreCase("no")) {
                            System.out.print("\t\tEnter PassKey: ");
                            String tempPass = s.nextLine();
                            if (tempPass.length() > 0) {
                                toEncryptor.println("password " + tempPass);
                                toEncryptor.flush();
                                String[] resultP = fromEncryptor.nextLine().split(" ");
                                if (resultP[0].equalsIgnoreCase("success")) {
                                    password = tempPass.toUpperCase();
                                    System.out.println("\t\t\tPassKey Updated: " + password);
                                    toLogger.println("password " + password);
                                    toLogger.flush();
                                } else if (resultP[0].equalsIgnoreCase("failure")) {
                                    password = "";
                                    System.out.println("Error Setting PassKey");
                                    toLogger.println("password ERROR_SETTING_PASSKEY");
                                    toLogger.flush();
                                } else {
                                    password = "";
                                    System.out.println("Error Setting PassKey");
                                }
                            } else {
                                System.out.println("\t\t\tInvalid PassKey");
                                continue;
                            }
                        } else if (hist.equalsIgnoreCase("yes")) {
                            if (history.size()>0) {
                                System.out.println("\t\tHistory: ");
                                for (int i=0 ; i<history.size() ; i++) {
                                    System.out.println("\t\t\t" + i + ": " + history.get(i));
                                }
                                System.out.print("\t\t\t\tEnter Choice (numeric key of the choice): ");
                                if (s.hasNextInt()) {
                                    int choice = s.nextInt();
                                    if (choice>=0 && choice<history.size()) {
                                        toEncryptor.println("password " + history.get(choice));
                                        toEncryptor.flush();
                                        String[] resultP = fromEncryptor.nextLine().split(" ");
                                        if (resultP[0].equalsIgnoreCase("success")) {
                                            password = history.get(choice).toUpperCase();
                                            System.out.println("\t\t\tPassKey Updated: " + password);
                                            toLogger.println("password " + password);
                                            toLogger.flush();
                                        } else if (resultP[0].equalsIgnoreCase("failure")) {
                                            password = "";
                                            toLogger.println("password ERROR_SETTING_PASSWORD");
                                            toLogger.flush();
                                        } else {
                                            password = "";
                                            System.out.println("Error In Password Logic");
                                        }
                                    } else {
                                        System.out.println("\t\t\t\tInvalid Choice");
                                        continue;
                                    }
                                } else {
                                    System.out.println("\t\t\tEnter the numeric key of the choice");
                                    continue;
                                }
                            } else {
                                System.out.println("\t\tHistory is empty :(");
                                continue;
                            }
                        } else {
                            System.out.println("\t\tInvalid Choice");
                            continue;
                        }
                    }

//                    code for handling encryption
                    else if (inp.equalsIgnoreCase("encrypt") || inp.equalsIgnoreCase("2")) {
                        if (password != null && password.length() > 0) {
                            Scanner s = new Scanner(System.in);
                            System.out.print("\tDo you want to use a string from history? (yes | no): ");
                            String hist = s.nextLine();
                            if (hist.equalsIgnoreCase("no")) {
                                System.out.print("\t\tEnter string to Encrypt: ");
                                String valueToEncrypt = s.nextLine();
                                toEncryptor.println("encrypt " + valueToEncrypt);
                                toEncryptor.flush();

                                String[] resultE = fromEncryptor.nextLine().split(" ");
                                if (resultE[0].equalsIgnoreCase("success")) {
                                    if (!history.contains(valueToEncrypt)) { history.add(valueToEncrypt); }
                                    if (!history.contains(resultE[1])) { history.add(resultE[1]); }
                                    System.out.println("\t\t\tEncrypted Value: " + resultE[1]);
                                    toLogger.println("encrypt " + valueToEncrypt);
                                    toLogger.flush();
                                } else if (resultE[0].equalsIgnoreCase("failure")) {
                                    System.out.println("\t\t\tFailure in Encryption");
                                    toLogger.println("encrypt " + resultE[1]);
                                    toLogger.flush();
                                } else {
                                    System.out.println("Something Wrong Happened");
                                    continue;
                                }
                            } else if (hist.equalsIgnoreCase("yes")) {
                                if (history.size() > 0) {
                                    System.out.println("\t\tHistory: ");
                                    for (int i = 0; i < history.size(); i++) {
                                        System.out.println("\t\t\t" + i + ": " + history.get(i));
                                    }
                                    System.out.print("\t\t\t\tEnter Choice (numeric key of the choice): ");
                                    if (s.hasNextInt()) {
                                        int choice = s.nextInt();
                                        if (choice >= 0 && choice < history.size()) {
                                            String valueToEncrypt = history.get(choice);
                                            toEncryptor.println("encrypt " + valueToEncrypt);
                                            toEncryptor.flush();

                                            String[] resultE = fromEncryptor.nextLine().split(" ");
                                            if (resultE[0].equalsIgnoreCase("success")) {
                                                System.out.println("\t\t\tEncrypted Value: " + resultE[1]);
                                                if (!history.contains(resultE[1])) { history.add(resultE[1]); }
                                                toLogger.println("encrypt " + valueToEncrypt);
                                                toLogger.flush();
                                            } else if (resultE[0].equalsIgnoreCase("failure")) {
                                                System.out.println("\t\t\tFailure in Encryption");
                                                toLogger.println("encrypt " + resultE[1]);
                                                toLogger.flush();
                                            } else {
                                                System.out.println("Something Wrong Happened");
                                                continue;
                                            }
                                        } else {
                                            System.out.println("\t\t\t\tInvalid Choice");
                                            continue;
                                        }
                                    } else {
                                        System.out.println("\t\t\tEnter the numeric key of the choice");
                                        continue;
                                    }
                                } else {
                                    System.out.println("\t\tHistory is empty :(");
                                    continue;
                                }
                            } else {
                                System.out.println("\tInvalid choice!");
                                continue;
                            }
                        } else {
                            System.out.println("\tPassKey not Set!");
                        }
                    }

//                    code for handling decryption
                    else if (inp.equalsIgnoreCase("decrypt") || inp.equalsIgnoreCase("3")) {
                        if (password != null && password.length() > 0) {
                            Scanner s = new Scanner(System.in);
                            System.out.print("\tDo you want to use a string from history? (yes | no): ");
                            String hist = s.nextLine();
                            if (hist.equalsIgnoreCase("no")) {
                                System.out.print("\t\tEnter string to Decrypt: ");
                                String valueToDecrypt = s.nextLine();
                                toEncryptor.println("decrypt " + valueToDecrypt);
                                toEncryptor.flush();

                                String[] resultD = fromEncryptor.nextLine().split(" ");
                                if (resultD[0].equalsIgnoreCase("success")) {
                                    if (!history.contains(valueToDecrypt)) { history.add(valueToDecrypt); }
                                    if (!history.contains(resultD[1])) { history.add(resultD[1]); }
                                    System.out.println("\t\t\tDecrypted Value: " + resultD[1]);
                                    toLogger.println("decrypt " + valueToDecrypt);
                                    toLogger.flush();
                                } else if (resultD[0].equalsIgnoreCase("failure")) {
                                    System.out.println("\t\t\tFailure in Decryption");
                                    toLogger.println("decrypt " + resultD[1]);
                                    toLogger.flush();
                                } else {
                                    System.out.println("Something Wrong Happened");
                                    continue;
                                }
                            } else if (hist.equalsIgnoreCase("yes")) {
                                if (history.size() > 0) {
                                    System.out.println("\t\tHistory: ");
                                    for (int i = 0; i < history.size(); i++) {
                                        System.out.println("\t\t\t" + i + ": " + history.get(i));
                                    }
                                    System.out.print("\t\t\t\tEnter Choice (numeric key of the choice): ");
                                    if (s.hasNextInt()) {
                                        int choice = s.nextInt();
                                        if (choice >= 0 && choice < history.size()) {
                                            String valueToDecrypt = history.get(choice);
                                            toEncryptor.println("decrypt " + valueToDecrypt);
                                            toEncryptor.flush();

                                            String[] resultD = fromEncryptor.nextLine().split(" ");
                                            if (resultD[0].equalsIgnoreCase("success")) {
                                                System.out.println("\t\t\tDecrypted Value: " + resultD[1]);
                                                if (!history.contains(resultD[1])) { history.add(resultD[1]); }
                                                toLogger.println("decrypt " + valueToDecrypt);
                                                toLogger.flush();
                                            } else if (resultD[0].equalsIgnoreCase("failure")) {
                                                System.out.println("\t\t\tFailure in Decryption");
                                                toLogger.println("decrypt " + resultD[1]);
                                                toLogger.flush();
                                            } else {
                                                System.out.println("Something Wrong Happened");
                                                continue;
                                            }
                                        } else {
                                            System.out.println("\t\t\t\tInvalid Choice");
                                            continue;
                                        }
                                    } else {
                                        System.out.println("\t\t\tEnter the numeric key of the choice");
                                        continue;
                                    }
                                } else {
                                    System.out.println("\t\tHistory is empty :(");
                                    continue;
                                }
                            } else {
                                System.out.println("\tInvalid choice!");
                                continue;
                            }
                        } else {
                            System.out.println("\tPassKey not Set!");
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
                        System.out.print("Enter a command from the list of commands below. You may enter the number associated with the command or the actual command itself");
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
        System.out.println("1: passkey");
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
