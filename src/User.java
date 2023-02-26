import java.util.*;

public class User {
    public static Scanner scanner;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private List<Account> accounts;

    public User(Map user) {
        if (
            !user.containsKey("firstName") ||
            !user.containsKey("lastName") ||
            !user.containsKey("username") ||
            !user.containsKey("password")
        ) {
            throw new IllegalArgumentException("Object missing required properties!");
        }
        firstName = (String)user.get("firstName");
        lastName = (String)user.get("lastName");
        username = (String)user.get("username");
        password = (String)user.get("password");
        accounts = new ArrayList();

    }

    public void displayAccounts() {
        int accountIndex = 1;
        for (Account account: accounts) {
            System.out.print(accountIndex++ + "-");
            account.displayAccountDetails();
        }
    }

    public Account getAccount() {
        displayAccounts();
        int accountIndex;
        if (accounts.size() == 0) {
            System.out.println("No accounts yet");
            return null;
        }
        do {
            System.out.print("Enter account to start transaction (0 to exit): ");
            accountIndex = scanner.nextInt();
            if (accountIndex == 0) {
                return null;
            }
            if (accountIndex < 1 || accountIndex > accounts.size()) {
                System.out.println("Invalid input. Try again");
            }
        } while (accountIndex < 1 || accountIndex > accounts.size());

        return accounts.get(accountIndex-1);
    }

    public boolean addAccount() {
        Account account = Account.createAccount();
        if (account == null) { return false; }
        this.accounts.add(account);
        return true;
    }

    /* Setters and getters for properties */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() { return this.firstName + " " + this.lastName; }
}
