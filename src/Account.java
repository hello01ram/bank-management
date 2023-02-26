import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {
    public static Scanner scanner;
    private static enum Type {
        SAVING, EVERYDAY, LOAN
    }
    private Type type;
    private double balance;
    private double limit;

    public static Account createAccount() {
        System.out.println("What account type would you like to create: ");
        displayAccountTypes();
        int inputType = 0;
        System.out.print("Enter account Type (0 to exit): ");
        inputType = scanner.nextInt();
        if (inputType == 0) { return null; }
        if (inputType < 1 || inputType > Type.values().length) {
            System.out.println("Invalid account type");
            return null;
        }
        return new Account(Type.values()[inputType]);
    }

    public static void displayAccountTypes() {
        int i = 1;
        for (Type account : Type.values()) {
            System.out.println(String.format("%d-%s", i++, account));
        }
    }

    public Account(Type type) {
        this.type = type;
        this.balance = 0;
        this.limit = 1000;
    }

    public int promptOptions() {
        System.out.println("1-Display account balance");
        System.out.println("2-Withdraw");
        System.out.println("3-deposit");

        int selection;
        do {
            System.out.print("Enter your selection (0 to exit): ");
            selection = scanner.nextInt();
            if (selection == 0) { return 0; }
            if (selection < 1 || selection > 3) {
                System.out.println("Invalid selection. Try again");
            }
        } while (selection < 1 || selection > 3);
        return selection;
    }

    public boolean processSelection(int selection) {
        boolean status = false;
        switch (selection) {
            case 1:
                status = displayBalance();
                break;
            case 2:
                status = withdraw();
                break;
            case 3:
                status = deposit();
                break;
            default:
                System.out.println("Failed. Selection out of range");
        }
        return status;
    }

    private boolean deposit() {
        double depositAmount;
        do {
            System.out.print("Enter deposit amount (0 to exit): ");
            depositAmount = scanner.nextDouble();
            if (depositAmount == 0) { return false; }
            if (depositAmount < 0) {
                System.out.println("Failed. Amount is invalid");
            } else if (depositAmount > limit) {
                System.out.println("Failed. Amount is more than your daily limit");
                displayDailyLimit();
            }
        } while(depositAmount < 0 || depositAmount > limit);

        balance += depositAmount;
        return true;
    }

    private boolean withdraw() {
        double withdrawAmount;
        do {
            System.out.print("Enter withdraw amount (0 to exit): ");
            withdrawAmount = scanner.nextDouble();
            if (withdrawAmount == 0) { return false; }
            if (withdrawAmount < 1) {
                System.out.println("Failed. Amount should be at least $1");
            } else if (withdrawAmount > balance) {
                System.out.println("Failed. Insufficient balance");
            } else if (withdrawAmount > limit) {
                System.out.println("Failed. Amount is more than your daily limit");
            }
        } while(withdrawAmount < 0 || withdrawAmount > balance || withdrawAmount > limit);

        balance -= withdrawAmount;
        return true;
    }

    public void displayAccountDetails() {
        System.out.println(String.format("%s account: Balance $%.2f.", type.toString(), balance));
    }

    public boolean displayBalance() {
        System.out.println(String.format("Your account balance is $%.2f.", balance));
        return true;
    }

    private void displayDailyLimit() {
        System.out.println("Your daily limit is: " + limit);
    }

}
