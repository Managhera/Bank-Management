import java.util.Scanner;

class User {
    private String username;
    private String password;
    private String email;
    private BankAccount account; // Each User HAS-A BankAccount

    // Constructor
    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.account = new BankAccount(this); // Create new account linked to this user
    }
    // Getters
    public String getusername() { return username; }
    public String getpassword() { return password; }
    public String getemail() { return email; }
    public BankAccount getaccount() { return account; }
}

//  BankAccount class with encapsulation
class BankAccount {
    private String accountNumber;
    private double balance;   //  private
    private User owner;

    // Constructor
    public BankAccount(User owner) {
        this.owner = owner;
        this.accountNumber = "AC" + System.currentTimeMillis(); // unique account no
        this.balance = 0.0;
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public User getOwner() { return owner; }

    // Setter (only Bank class will modify)
    public void setBalance(double balance) {
        this.balance = balance;
    }
}


class bank {

    User[] users = new User[20];
    int countOfuser = 0, i;
    User currentUser = null;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        bank obj_getdata = new bank(); // Create object to use instance methods
        obj_getdata.getdata();
    }

    // method of get data
    void getdata() 
    {
        while (true) 
        {
            System.out.println("Enter your choice For Bank Management \n 1. Register \n 2. Login \n 3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) 
            {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("logout Successfull");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }


// method of register
void registerUser() {

    String username, email, password;

    // Re-input username until valid
    do {
        System.out.print("Enter username: ");
        username = sc.nextLine().trim();
        if (username == null || !username.matches("^[A-Za-z0-9_]{3,}$")) {
            System.out.println("Username must be at least 3 characters (letters, numbers, underscore allowed).");  
        }
    } while (username == null || !username.matches("^[A-Za-z0-9_]{3,}$"));

    
    // Re-input email until valid
    do {
        System.out.print("Enter Email: ");
        email = sc.nextLine().trim();
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("Invalid email format.");  
        }
    } while (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));


    // Re-input password until valid
    do {
        System.out.print("Enter password: ");
        password = sc.nextLine().trim();
        if (password == null || !password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) {
            System.out.println("Password must be at least 8 characters and contain a digit & special character.");  
        }
    } while (password == null || !password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$"));


    // If all are valid → register user
    users[countOfuser] = new User(username, password, email);
    countOfuser++;

    System.out.println(" Registration successful!");
}


    // method of login
    void loginUser() 
    {
        boolean logged_in = false;

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        for (int i = 0; i < countOfuser; i++) 
        {
            if (users[i].getusername().equals(username) && users[i].getpassword().equals(password)) 
            {
                logged_in = true;
                currentUser = users[i];
                System.out.println("Login successful!");
                accountMenu();  
                return;
            }
        }

        if (!logged_in) 
        {
            System.out.println("Login failed.");
        }
    }

        // method for account
    private void accountMenu() {
        while (true) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Account Details");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            BankAccount account = currentUser.getaccount(); //get logged in user's account

            switch (choice) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double deposit = sc.nextDouble();
                        if (deposit > 0) {
                            account.setBalance(account.getBalance() + deposit);
                            System.out.println("Deposited: " + deposit);
                        } else {
                            System.out.println("Invalid deposit amount!");
                        }
                        break;

                    case 2:
                        System.out.print("Enter withdraw amount: ");
                        double withdraw = sc.nextDouble();
                        if (withdraw > 0 && withdraw <= account.getBalance()) {
                            account.setBalance(account.getBalance() - withdraw);
                            System.out.println("Withdrawn: " + withdraw);
                        } else {
                            System.out.println("Insufficient balance or invalid amount!");
                        }
                        break;

                    case 3:
                        System.out.println("Current Balance: " + account.getBalance());
                        break;

                    case 4:
                        showAccountDetails(account);
                        break;

                    case 5:
                        System.out.println("Logged out!");
                        return; // exit method
                    
                    default:
                        System.out.println("Invalid option");
                        break;
                }

        }
    }

    // Show account details
    private void showAccountDetails(BankAccount account) {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Owner Username : " + account.getOwner().getusername());
        System.out.println("Owner Email    : " + account.getOwner().getemail());
        System.out.println("Balance        : " + account.getBalance());
    }
}
