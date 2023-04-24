import java.util.ArrayList;
import java.util.HashMap;

public class ABC {

    public static ArrayList<client> clients = new ArrayList<client>();

    public static void main(String[] args){
        //first we need to initiate the client who uses the ATM

        client Savinu = new client(321,"Savinu","Sri Lanka","Engineer","Panadura",21,"Male");

        client Disini = new client(691,"Disini","Sri Lanka","Engineer","Moratuwa",21,"Female");

        // then lets create accounts for the first client
        Savinu.getAccount("Savings",1234,"USD","Panadura",100000);
        Savinu.getAccount("Savings",13221,"Rupees","Moratuwa",90000);

        Savinu.setPinNumber(991);
        Disini.setPinNumber(871);
        clients.add(Disini);
        clients.add(Savinu);

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Now a client uses the ATM");

        ATM current = new ATM();
        current.runAtm(991,Savinu);
    }

}


// define a class for client
class client{
    // define the given attributes of a client
    int id;
    String name;
    String nationality;
    String occupation;
    String address;
    int age;
    String gender;
    //because we can only use one currency This variable holds true if currency is already set
    Boolean currency_set = false;
    String chosen_currency;
    // create an arraylist to hold the accounts that the cliet has
    ArrayList<Account> Accounts = new ArrayList<Account>();

    //Let's use a hashmap to link the specific account with the loan object
    HashMap<Account, Loan> account_to_loan = new HashMap<>();

    private int pinNumber;

    // create a constructor

    public client(int id, String name, String nationality, String occupation, String address, int age, String gender) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.occupation = occupation;
        this.address = address;
        this.age = age;
        this.gender = gender;
    }

    // define a get account method for getting an account for the client

    public void getAccount(String type , int account_number,String Currency , String Branch, int Balance){

        System.out.println("Account Successfully created");
        if(currency_set){
            System.out.println("You have already chosen " + chosen_currency + " as your currency");
        }
        if (type == "Savings" && Currency != chosen_currency){
            SavingsAccount temp = new SavingsAccount(account_number,Currency,Branch,Balance);
            Accounts.add(temp);
            currency_set = true;
            chosen_currency = Currency;
        }
        if (type == "Current" && Currency != chosen_currency){
            CurrentAccount temp = new CurrentAccount(account_number,Currency,Branch,Balance);
            Accounts.add(temp);
            chosen_currency = Currency;
        }
    }

    // client must have an account to apply for a loan ( account array lst should not be null)
    // when this method is called client can apply for a loan
    void ApplyLoan(int number,int amount,int interest,int duration , String payment_method){
        Loan temp = new Loan(25,0.25,10,"cash");

        if(Accounts == null) {
            System.out.println("You should have an Account for apply a loan");
        }

        else{
            int count = 0;
            // we will fun a for loop to find the given loan number
            for (Account account : Accounts){
                if (count==number){
                    account_to_loan.put(account,temp);
                }else{
                    count ++;
                }
            }
        }
    }
    //this method will set the pin number for a client
    void setPinNumber(int pin){
        pinNumber = pin;
    }

    // this method will show the accounts as a list
    void showAccounts(){
        int accNum = 1;
        for(Object account:Accounts){
            String acc = Integer.toString(accNum);
            System.out.println("Account " + acc);
            accNum++;
        }
    }
}


// define a parent class for accounts
class Account{

    // define common attributes of all accounts

    public int AccountNumber;
    String currency;
    String branch;
    int balance;


    // define a constructor for a Account object
    public Account(int accountNumber, String currency, String branch, int balance) {
        AccountNumber = accountNumber;
        this.currency = currency;
        this.branch = branch;
        this.balance = balance;
    }
    // method to get balance
    public void getBalance() {
        int temp =  balance;
        String Temp = Integer.toString(temp);
        System.out.println("Your Balance is " + Temp);
    }
    //method to deposit money
    public void Deposit(int Amount){
        balance += Amount;
        System.out.println("Deposit sucesfull");
    }
    //method to withdraw money
    public void Withdraw(int Amount){
        if(Amount > balance){
            System.out.println("Not enough balance");
        }else{
            System.out.println("Withdraw successful");
            balance -= Amount;
        }
    }


}

// defining Savings accounts and Current account classes which inherits from the parent Account class

class SavingsAccount extends Account {

    private float interestRate = (float) 0.05;

    public SavingsAccount(int accountNumber, String currency, String branch, int balance) {
        super(accountNumber, currency, branch, balance);
    }

    // savings account has a method for calculation of interest

}

class CurrentAccount extends Account {
    public CurrentAccount(int accountNumber, String currency, String branch, int balance) {
        super(accountNumber, currency, branch, balance);
    }

}

// create a class for loan
class Loan{

    int Amount;
    double Interest;
    int duration;
    String paymentMethod;

    public Loan(int amount, double interest, int duration, String paymentMethod) {
        Amount = amount;
        Interest = interest;
        this.duration = duration;
        this.paymentMethod = paymentMethod;
    }
}