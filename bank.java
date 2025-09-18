import java.util.Scanner;

//try catch Exception
class LoginFailedException extends Exception {
    public LoginFailedException(String msg) {
        super(msg);
    }
}

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}

//protect username ,email with Encapsulation
class person {
    protected String username;
    protected String email;

    public person(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getusername() { return username; }
    public String getemail() { return email; }
}

//Extend person use Encapsulation
class User extends person implements ILogin {
    private String password;
    private BankAccount account;
    private String type;
    private loan loan;

    public User(String username, String password, String email, String type) {
        super(username, email);
        this.password = password;
        this.type = type;

        if (type.equals("saving")) {
            this.account = new SavingAccount(this);
        } else if (type.equals("current")) {
            this.account = new CurrentAccount(this);
        }
    }

    public String getpassword() { return password; }
    public BankAccount getaccount() { return account; }
    public String gettype() { return type; }
    public loan getLoan() { return loan; }
    public void setLoan(loan loan) { this.loan = loan; }

  
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

// Interfaces
interface ILogin {
    boolean login(String username, String password);
}

interface ITransaction {
    void deposit(double amount);
    void withdraw(double amount);
}

// Abstract account class
abstract class BankAccount implements ITransaction {
    protected String accountNumber;
    protected double balance;
    protected User owner;

    public BankAccount(User owner) {
        this.owner = owner;
        this.accountNumber = "AC" + System.currentTimeMillis();
        this.balance = 0.0;
    }

    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public User getOwner() { return owner; }
}

// Saving account
class SavingAccount extends BankAccount {
    public SavingAccount(User owner) {
        super(owner);
    }

    public void deposit(double amount) {
        if (amount >= 0) {
            balance += amount;
            System.out.println("Deposited in Saving: " + amount);
        } else {
            System.out.println("Invalid deposit!");
        }
    }

    public void withdraw(double amount) {
        try {
            if (amount < 0) {
                throw new NumberFormatException();
            } else if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance in Saving Account!");
            } else {
                balance -= amount;
                System.out.println("Withdrawn from Saving: " + amount);
            }
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
    }
}

// Current account
class CurrentAccount extends BankAccount {
    public CurrentAccount(User owner) {
        super(owner);
    }

    public void deposit(double amount) {
        if (amount >= 0) {
            balance += amount;
            System.out.println("Deposited in Current: " + amount);
        } else {
            System.out.println("Invalid deposit!");
        }
    }

    public void withdraw(double amount) {
        try {
            if (amount < 0) {
                throw new NumberFormatException();
            } else if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance in Current Account!");
            } else {
                balance -= amount;
                System.out.println("Withdrawn from Current: " + amount);
            }
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
    }
}

// Loan class
class loan {
        private double principal;
        private double interestRate;
        private int termMonths;
        private double monthlyPayment;
        private double remainingBalance;
        private boolean active;

        public loan(double principal, double interestRate, int termMonths) {
            this.principal = principal;
            this.interestRate = interestRate;
            this.termMonths = termMonths;
            this.remainingBalance = principal;
            this.active = true;
            calculateMonthlyPayment();
        }

        private void calculateMonthlyPayment() {
            double monthlyRate = (interestRate / 100) / 12;
            if (monthlyRate > 0) {
                monthlyPayment = (principal * monthlyRate) /
                                (1 - Math.pow(1 + monthlyRate, -termMonths));
            } else {
                monthlyPayment = principal / termMonths;
            }
        }

        public void makePayment() {
            if (!active) {
                System.out.println("Loan already repaid!");
                return;
            }
            remainingBalance -= monthlyPayment;
            System.out.println("Paid: " + monthlyPayment + " | Remaining: " + remainingBalance);
            if (remainingBalance <= 0) {
                active = false;
                System.out.println("🎉 Loan fully paid off!");
            }
        }
        public double getMonthlyPayment() { return monthlyPayment; }
        public boolean isActive() { return active; }
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

    String username, email, password,type;

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

    do {
        System.out.print("Enter account type (saving/current): ");
        type = sc.nextLine().trim().toLowerCase();
    } while (!(type.equals("saving") || type.equals("current")));


    // If all are valid → register user
    users[countOfuser] = new User(username, password, email ,type);
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
        
        try {
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

          throw new LoginFailedException(" Login failed: Invalid username or password.");
        } catch (LoginFailedException e) {
            System.out.println(e.getMessage());
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
            System.out.println("5. Apply Loan");
            System.out.println("6. Pay Loan");
            System.out.println("7. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            BankAccount account = currentUser.getaccount(); //get logged in user's account

            switch (choice) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double deposit = sc.nextDouble();
                        account.deposit(deposit);
                        break;

                    case 2:
                        System.out.print("Enter withdraw amount: ");
                        double withdraw = sc.nextDouble();
                        account.withdraw(withdraw);
                        break;

                    case 3:
                        System.out.println("Current Balance: " + account.getBalance());
                        break;

                    case 4:
                        showAccountDetails(account);
                        break;

                    case 5:
                        applyLoan();
                        break;

                    case 6:
                        payLoan(account);
                        break;

                    case 7:
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
        System.out.println("Account Type   : " + account.getOwner().gettype());
        System.out.println("Owner Username : " + account.getOwner().getusername());
        System.out.println("Owner Email    : " + account.getOwner().getemail());
        System.out.println("Balance        : " + account.getBalance());
    }

        private void applyLoan() {
        System.out.print("Enter principal amount: ");
        double principal = sc.nextDouble();
        System.out.print("Enter annual interest rate (%): ");
        double rate = sc.nextDouble();
        System.out.print("Enter term in months: ");
        int term = sc.nextInt();

        loan loan = new loan(principal, rate, term);
        currentUser.setLoan(loan);
        System.out.println("Loan approved! Monthly Payment: " + loan.getMonthlyPayment());
    }

    void payLoan(BankAccount account) {
        loan loan = currentUser.getLoan();
        if (loan != null && loan.isActive()) {
            if (account.getBalance() >= loan.getMonthlyPayment()) {
                account.withdraw(loan.getMonthlyPayment());
                loan.makePayment();
            } else {
                System.out.println("Not enough balance to pay loan.");
            }
        } else {
            System.out.println("No active loan.");
        }
    }
}
