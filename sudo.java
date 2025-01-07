import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.util.*;
public class sudo {
public String HOSTNAME;
public boolean sudo = false;
public String USERNAME = "user"; 
public int LOGIN_COUNTER = 0;
public String DIR = "~";
public String SUFFIX = "[" + DIR + "]";
String[] FileSystem = {"bin", "boot", "dev", "etc", "home", "lib", "lib64", "media", "mnt", "opt", "proc", "root", "run", "sbin", "srv", "sys", "tmp", "usr", "var"};
public static final String RESET = "\033[0m";
public static final String RED = "\033[0;31m";
public static final String CYAN = "\033[0;36m";
public static final String GREEN = "\033[0;32m";
public static final String LIME = "\033[38;2;0;255;0m";
public static final String PURPLE = "\033[38;5;93m";

    public void HOST(boolean isroot) {
        if(isroot) {
            System.out.print(RED+"root"+"@"+HOSTNAME+":"+SUFFIX+"$ "+CYAN);
        } else {
            System.out.print(PURPLE+USERNAME+"@"+HOSTNAME+":"+SUFFIX+"$ "+CYAN);
        }
    }

    public void CHANGE_HOST(String INPUT) {
        String INPUT1 = INPUT.substring(0, 8);
        if (INPUT.length()!=10&&INPUT.charAt(9)!=' ')
            if(INPUT1.equals("hostname")&&INPUT.length()>8)
                HOSTNAME = (INPUT.substring(8)).trim();
        else
            System.out.println("Command not found: " + INPUT);
        
    }

    public void whoami(boolean IsRoot) {
        if (IsRoot) 
            System.out.println("root");
        else 
            System.out.println(USERNAME);
    }

    public void FILESYSTEM() {
        for (String folders : FileSystem) System.out.print(folders + " ");
        System.out.println();
    }

    public void CHANGE_DIR(String dir) {
        boolean found = false;
        if (!dir.matches("(?i)[a-z]:")) {
            for (String folder : FileSystem) {
                if (folder.equals(dir)) {
                    DIR = dir;
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("bash: cd: " + dir + ": No such file or directory");
            }
            SUFFIX = "[" + DIR + "]";
        }
        else {
            PROCESS_BUILDER("explorer.exe "+dir);
        }
    }

    public void SUDO(String sudocmd) {
        String cmd = sudocmd.substring(5);
        if ((cmd.substring(0, 7)).equals("adduser")) {
            USERNAME = cmd.substring(8);
        }
        else if (cmd.equals("apt update")) {
            UPDATE();
        }
        else if (cmd.equals("apt upgrade")) {
            UPGRADE();
        }
        else {
            System.out.println("Command not found: " + sudocmd);
        }
    }

    public boolean CHECK_PASSWORD() {
        Scanner PASS = new Scanner(System.in);
        System.out.print("Enter password for sudo : ");
        String ENTERED_PASS = PASS.nextLine();
        if(ENTERED_PASS.equals(PASSWORD))
            return true;
        else
            return false;
    }

    public void OPEN_URL(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI(url);
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ROOT() {
        Scanner LOL = new Scanner(System.in);
        if(LOGIN_COUNTER==0)
            COWSAY("Made by Hrishav");
        HOST(sudo);
        String INPUT = LOL.nextLine();
        
        try {
            if (INPUT.startsWith("hostname")) {
                CHANGE_HOST(INPUT);
            } else if (INPUT.equals("whoami")) {
                whoami(sudo);
            }
            else if (INPUT.equals("su")) {
                sudo = true;
            } 
            else if (INPUT.equals("exit")) {
                if (sudo)
                    sudo = false;
                else
                    System.out.println("No more processes left to exit.");
            } 
            else if (INPUT.equals("ls")) {
                FILESYSTEM();
            }
            else if ((INPUT.substring(0, 2)).equals("cd")) {
                if(INPUT.length() == 2) {
                    DIR = "~";
                    SUFFIX = "[" + DIR + "]";
                }
                else if (INPUT.length()>3) {
                    String dir = INPUT.substring(3);
                    CHANGE_DIR(dir);
                }
                else {
                    System.out.println("bash: cd: " + INPUT.charAt(3) + ": No such file or directory");
                }
            }
            else if (INPUT.equals("pwd")) {
                System.out.println(DIR);
            }
            else if (INPUT.length()>5&&(INPUT.substring(0, 4)).equals("sudo")) {
                if (CHECK_PASSWORD())
                    SUDO(INPUT);
                else 
                    System.out.println("Incorrect Password!");
            }
            else if (INPUT.startsWith("www.youtube.com")) {
                if(INPUT.length()>15) {
                    String SEARCH_TERM = INPUT.substring(16).replace(" ", "+");
                    OPEN_URL("www.youtube.com/results?search_query="+SEARCH_TERM);
                }
                else
                    OPEN_URL(INPUT);
            }
            else if((INPUT.startsWith("cowsay"))&&INPUT.length()>7&&INPUT.charAt(6)==' ') {
                    String message = INPUT.substring(7);
                    COWSAY(message);
            }
            else if(INPUT.equals("rickroll")) {
                OPEN_URL("www.youtube.com/watch?v=xvFZjo5PgG0");
            }
            else if (INPUT.equals("cmd")) {
                PROCESS_BUILDER("cmd");
            }
            else if(INPUT.equals("chrome")) {
                PROCESS_BUILDER("chrome");
            }
            else if(INPUT.equals("settings")) {
                PROCESS_BUILDER("ms-settings:");
            }
            else if(INPUT.equals("brave")) {
                PROCESS_BUILDER("brave");
            }
            else if(INPUT.equals("wp")) {
                PROCESS_BUILDER("whatsapp:");
            }
            else if(INPUT.equals("python")) {
                PROCESS_BUILDER("python");
            }
            else if(INPUT.equals("clear")) {
                CLEAR();
            }
            else if(INPUT.equals("clear")) {
                CLEAR();
            } else if(INPUT.startsWith("heck")) {
                HECK(INPUT.substring(5));
            }
            else {
                if(INPUT.length()==0) 
                    ROOT();
                else
                    System.out.println("Command not found: " + INPUT);
            }
        } catch (Exception e) {
            if(INPUT.length() != 0) 
                System.out.println("Command not found: " + INPUT);
        } finally {
            LOGIN_COUNTER++;
            ROOT();
        }
         
        LOGIN_COUNTER++;
        ROOT();
    }

    {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            HOSTNAME = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            HOSTNAME = "Unknown Host"; 
        }
    }

    public void UPDATE() {
        System.out.println("Hit:1 http://archive.java.com/java focal InRelease\n"
                 + "Hit:2 http://archive.java.com/java focal-updates InRelease\n"
                 + "Hit:3 http://archive.java.com/java focal-backports InRelease\n"
                 + "Hit:4 http://archive.java.com/java focal-security InRelease\n"
                 + "Reading package lists... Done\n"
                 + "Building dependency tree\n"
                 + "Reading state information... Done\n"
                 + "All packages are up to date.");
    }

    public String PASSWORD = "java";
  
    public void UPGRADE() {
        System.out.println("Reading package lists... Done\n"
                 + "Building dependency tree\n"
                 + "Reading state information... Done\n"
                 + "Calculating upgrade... Done\n"
                 + "The following packages will be upgraded:\n"
                 + "  bash curl libc6 libssl1.1\n"
                 + "4 upgraded, 0 newly installed, 0 to remove and 0 not upgraded.\n"
                 + "Need to get 1,234 kB of archives.\n"
                 + "After this operation, 0 B of additional disk space will be used.\n"
                 + "Do you want to continue? [Y/n]\n"
                 + "Get:1 http://archive.java.com/java focal-updates/main amd64 bash amd64 5.0-6ubuntu1 [1,234 kB]\n"
                 + "Fetched 1,234 kB in 2s (567 kB/s)\n"
                 + "Selecting previously unselected package bash.\n"
                 + "(Reading database ... 123,456 files and directories currently installed.)\n"
                 + "Preparing to unpack .../bash_5.0-6ubuntu1_amd64.deb ...\n"
                 + "Unpacking bash (5.0-6ubuntu1) over (5.0-5ubuntu1) ...\n"
                 + "Setting up bash (5.0-6ubuntu1) ...\n"
                 + "Processing triggers for libc-bin (2.31-0ubuntu9.9) ...\n"
                 + "Processing triggers for man-db (2.9.4-2) ...\n"
                 + "Processing triggers for libc-bin (2.31-0ubuntu9.9) ...\n"
                 + "ldconfig deferred processing now taking place.\n");
    }

    public void COWSAY(String msg) {
        System.out.println(" _____________________ ");
        System.out.println("< " + msg + " >");
        System.out.println(" --------------------- ");
        System.out.println("        \\   ^__^ ");
        System.out.println("         \\  (oo)\\_______ ");
        System.out.println("            (__)\\       )\\/\\ ");
        System.out.println("                ||----w | ");
        System.out.println("                ||     || ");
    }

    public void CLEAR() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void PROCESS_BUILDER(String process) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start "+process);
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HECK(String toHeck) {
        System.out.println("IMPOSTOR DETECTED: HACKING " + toHeck.toUpperCase());
        TYPEWRITER("Connecting to " + toHeck + "...");
        PROGRESS_BAR();
        TYPEWRITER("Bypassing security protocols...");
        PROGRESS_BAR();
        TYPEWRITER("Accessing confidential data...");
        PROGRESS_BAR();
        TYPEWRITER("Uploading malware...");
        PROGRESS_BAR();
        TYPEWRITER("HACK COMPLETE. " + toHeck.toUpperCase() + " HAS BEEN COMPROMISED.");
    }

    private void TYPEWRITER(String message) {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    private void PROGRESS_BAR() {
        String anim = "|/-\\";
        for (int i = 0; i <= 100; i++) {
            System.out.print("\rPROGRESS: " + i + "% " + anim.charAt(i % anim.length()));
            try {
                Thread.sleep(50); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print("\rPROGRESS: 100% Done!\n");
    }

    public static void main(String[] args) { 
        sudo TERMINAL = new sudo();
        TERMINAL.ROOT();
    }
}
