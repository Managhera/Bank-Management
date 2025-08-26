import java.util.Scanner;

// User class-Encapsulation 
class User {
    String username;
    String password;
    String email;
    double balance;

    // para.. Constructor
    User(String username, String password, String email) 
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = 0.0; 
    }
}

class bank {

    User[] users = new User[20];
    int countOfuser = 0, i;

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
            if (users[i].username.equals(username) && users[i].password.equals(password)) 
            {
                logged_in = true;
                System.out.println("Login successful!");
                balance();// call balance deposit and withdraw
                break;
            }
        }

        if (!logged_in) 
        {
            System.out.println("Login failed.");
        }
    }

        // method for balance
    void balance() {
        System.out.println("1. Deposit  2. Withdraw");
        int option = sc.nextInt();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        if (option == 1) 
        {
            users[i].balance = users[i].balance + amount;
            System.out.println("Deposit Amount is = " + amount);
        } 
        else if (option == 2) 
        {
            if (amount <= users[i].balance) {
                users[i].balance = users[i].balance - amount;
                System.out.println("Withdraw Amount is = " + amount);
            } else {
                System.out.println("Current balance is Low!");
            }
        } 
        else 
        {
            System.out.println("Invalid transaction");
        }

        System.out.println("Current Balance is : " + users[i].balance);

    }
}