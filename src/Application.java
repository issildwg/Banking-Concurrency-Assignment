import java.util.HashMap;

public class Application {
    public static StockExchange se = new StockExchange();

    public static void main(String args[]) throws InterruptedException {

        Company apple = new Company("apple", 100, 0, 25);
        Company samsung = new Company("samsung", 100, 100, 15);
        Company windows = new Company("windows", 100, 100, 21);
        Company intel = new Company("intel", 100, 100, 43);

        se.registerCompany(apple, apple.getAvailableShares());
        se.registerCompany(samsung, samsung.getAvailableShares());
        se.registerCompany(windows, windows.getAvailableShares());
        se.registerCompany(intel, intel.getAvailableShares());

        Client sue = new Client("sue", 5000, new HashMap<Company, Float>(), se);
        Client jim = new Client("jim", 5, new HashMap<Company, Float>(), se);
        Client clarke = new Client("clarke", 600, new HashMap<Company, Float>(), se);
        Client hubert = new Client("hubert", 1000, new HashMap<Company, Float>(), se);

        se.addClient(sue);
        se.addClient(jim);
        se.addClient(clarke);
        se.addClient(hubert);

        System.out.print("Companies in stock exchange:  ");

        for (Company company :  se.getCompanies().keySet()){
            System.out.print(company.getName() + ":" + se.getCompanies().get(company) + "  "); //name, shares
        }

        System.out.println("");
        System.out.print("Clients in stock exchange:  ");

        for(Client client : se.getClients()) {
            System.out.print(client.getName() + "  ");
        }

        System.out.println(" ");


        Thread one = new Thread(sue);
        Thread two = new Thread(jim);
        Thread three = new Thread(clarke);
        Thread four = new Thread(hubert);
            one.setName("sue");
            two.setName("jim");
            three.setName("clarke");
            four.setName("hubert");

       //TODO use a listener either here or after the thread starts

        one.start();
        Thread.sleep(1000);
        two.start();
        Thread.sleep(1000);
        se.changePriceBy(samsung, -10);
        three.start();
        Thread.sleep(1000);
        four.start();
        Thread.sleep(1000);

        se.changePriceBy(intel, -40);
        se.changePriceBy(apple, -15);
        se.changePriceBy(windows, -11);

        se.changePriceBy(windows, 15);

        one.join();
        two.join();
        three.join();
        four.join();


        se.deregisterCompany(windows);
     //   se.removeClient(sue);
        hubert.deposit(78);
        clarke.withdraw(12);
        apple.setName("Apple");


        //for each loop:   for (DataType variableName : Array)
        System.out.print("Companies in stock exchange: ");
        for (Company company :  se.getCompanies().keySet()){
            System.out.print(company.getName() + "(" + se.getCompanies().get(company) + ") "); //name, shares
        }

        System.out.println("");
        System.out.print("Clients in Stock Exchange: ");

        for(Client client : se.getClients()) {
            System.out.print(client.getName() + "(£" + client.getBalance() + ") ");
        }
        System.out.println(" ");
    }
}
