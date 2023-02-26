import java.util.*;

public class Bank {
    private static Scanner scanner = new Scanner(System.in);
    private User loggedUser;
    private List<User> users;
    public static void main(String[] args) {
        User.scanner = scanner;
        Account.scanner = scanner;
        new Bank();
    }

    public Bank() {
        users = new ArrayList();
        loggedUser = null;

        System.out.println("Welcome to Bank X");
        System.out.println("Please login or signup to start");

        int selection;
        boolean status;
        while(true) {
            status = false;
            System.out.println();
            try {
                selection = promptOptions();
                System.out.println();
                if (selection == 0) { continue; }
                status = processSelection(selection);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input entered");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Something went wrong :(");
                e.printStackTrace();
            }
            if (!status) { continue; }
            System.out.println("Success");
        }
    }

    private int promptOptions() {
        int selection = 0;
        if (loggedUser == null) {
            System.out.println("1-Login");
            System.out.println("2-Signup");
            System.out.print("Enter your selection: ");
            selection = scanner.nextInt();
            if (selection < 1 || selection > 2) {
                System.out.println("Invalid option selected");
                return 0;
            }
        } else {
            System.out.println("3-Display accounts");
            System.out.println("4-Create account");
            System.out.println("5-Display details");
            System.out.println("6-Logout");
            System.out.print("Enter your selection: ");
            selection = scanner.nextInt();
            if (selection < 3 || selection > 6) {
                System.out.println("Invalid option selected");
                return 0;
            }
        }
        return selection;
    }

    private boolean processSelection(int selection) {
        boolean status = false;
        switch (selection) {
            case 1:
                status = login();
                break;
            case 2:
                status = signup();
                break;
            case 3:
                status = displayAccounts();
                break;
            case 4:
                status = addAccount();
                break;
            case 5:
                status = displayUserDetails();
                break;
            case 6:
                status = logout();
                break;
            default:
                System.out.println("Invalid option");
        }
        return status;
    }

    private boolean login() {
        String username, password;
        System.out.println("To exit, enter 'exit' or '0'");
        System.out.print("Enter your username: ");
        username = scanner.next();
        if (isExitCodeEntered(username)) { return false; }
        System.out.print("Enter your password: ");
        password = scanner.next();
        if (isExitCodeEntered(password)) { return false; }

        for (User user : users) {
            // We found a user with the same username and password
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedUser = user;
                return true;
            } else if (user.getUsername().equals(username)) {
                // We found a user with the same username but different password
                System.out.println("Incorrect username or password");
                return false;
            }
        }
        // We did not find a user with the same username
        System.out.println("Incorrect username or password");
        return false;
    }

    private boolean signup() {
        User newUser;
        String firstName, lastName, username, password;
        System.out.println("Enter 0 or exit to exit signup");
        System.out.print("Enter your first name: ");
        firstName = scanner.next();
        if (isExitCodeEntered(firstName)) { return false; }
        System.out.print("Enter your last name: ");
        lastName = scanner.next();
        if (isExitCodeEntered(lastName)) { return false; }
        System.out.print("Enter your username: ");
        username = scanner.next();
        if (isExitCodeEntered(username)) { return false; }
        System.out.print("Enter your password: ");
        password = scanner.next();
        if (isExitCodeEntered(password)) { return false; }

        Map user = new HashMap();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("username", username);
        user.put("password", password);

        newUser = new User(user);
        users.add(newUser);
        loggedUser = newUser;
        return true;
    }

    private boolean displayAccounts() {
        Account account = loggedUser.getAccount();
        if (account == null) { return false; }
        int selection;
        boolean status = false;
        selection = account.promptOptions();
        if (selection == 0) { return false; }
        return account.processSelection(selection);
    }

    private boolean addAccount() {
        return loggedUser.addAccount();
    }

    private boolean displayUserDetails() {
        System.out.println("You are logged in as " + loggedUser.getFullName());
        return true;
    }

    private boolean logout() {
        loggedUser = null;
        return true;
    }

    private boolean isExitCodeEntered(String value) {
        return value.toLowerCase().equals("exit") || value.equals("0");
    }
}
