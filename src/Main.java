import javax.xml.bind.SchemaOutputResolver;
import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        StockExchange se = new StockExchange();

        Company apple = new Company("apple", 100, 0, 25);
        Company samsung = new Company("samsung", 100, 100, 15);
        Company windows = new Company("windows", 100, 100, 21);
        Company intel = new Company("intel", 100, 100, 43);


        se.registerCompany(apple, apple.getAvailableShares());
        se.registerCompany(samsung, samsung.getAvailableShares());
        se.registerCompany(windows, windows.getAvailableShares());
        se.registerCompany(intel, intel.getAvailableShares());


        Client sue = new Client("sue", 5000, 10, 100, new HashMap<Company, Float>(), se);
        Client jim = new Client("jim", 5, 8, 75, new HashMap<Company, Float>(), se);
        Client clarke = new Client("clarke", 600, 2, 65, new HashMap<Company, Float>(), se);
        Client hubert = new Client("hubert", 1000, 12, 40, new HashMap<Company, Float>(), se);

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

        se.changePriceBy(windows, -15);

        Thread one = new Thread(sue);
        Thread two = new Thread(jim);
        Thread three = new Thread(clarke);
        Thread four = new Thread(hubert);
            one.setName("sue");
            two.setName("jim");
            three.setName("clarke");
            four.setName("hubert");

        one.start();//opens thread one              REMOVE SLEEP WHEN SUBMITTING - they might not like it
        Thread.sleep(1000);
        two.start();
        Thread.sleep(1000);
        three.start();
        Thread.sleep(1000);
        four.start();
        Thread.sleep(1000);
        one.join();     //closes thread one
        two.join();
        three.join();
        four.join();


        se.deregisterCompany(apple);
        se.removeClient(sue);
        hubert.deposit(78);
        clarke.withdraw(12);


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
