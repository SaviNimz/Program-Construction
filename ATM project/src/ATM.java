import java.util.Scanner;
public class ATM extends ABC {

    public void runAtm(int pinNUm,client current_client){

        System.out.println("Welcome !");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your pin: ");

        int pin = scanner.nextInt();

        if(pin == pinNUm){
            System.out.println("You have following accounts in our bank Select one account to proceed");
            current_client.showAccounts();

            // take the user input for account number
            Scanner account = new Scanner(System.in);
            int acc = scanner.nextInt();
            Account selected = current_client.Accounts.get(acc);

            System.out.println("MAIN MENU");
            System.out.println("1 : View Balance");
            System.out.println("2 : Withdraw Money");
            System.out.println("3 : Deposit money");
            System.out.println("Exit");


            Scanner choice = new Scanner(System.in);
            int Choice = scanner.nextInt();

            if(Choice == 1){
                selected.getBalance();
            }
            else if(Choice==2){
                System.out.println("Enter the amount you want to withdraw");
                Scanner withdraw = new Scanner(System.in);
                int value = scanner.nextInt();
                selected.Withdraw(value);
            }
            else if(Choice==3){
                System.out.println("Enter the amount to be deposited");
                Scanner deposit = new Scanner(System.in);
                int value = scanner.nextInt();
                selected.Deposit(value);
            }

        }else{
            System.out.println("Please enter valid pin number");
        }
    }
}
