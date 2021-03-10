import java.util.HashMap;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        StockExchange se = new StockExchange();

        Company apple = new Company("apple", 500, 100, 25);
        Company samsung = new Company("samsung", 73, 36, 15);
        Company windows = new Company("windows", 56, 43, 21);
        Company intel = new Company("intel", 23, 23, 43);

        se.registerCompany(apple, apple.getAvailableShares());
        se.registerCompany(samsung, apple.getAvailableShares());
        se.registerCompany(windows, windows.getAvailableShares());
        se.registerCompany(intel, intel.getAvailableShares());


        Client sue = new Client(5000, 10, 100, new HashMap<Company, Float>(), se);
        Client jim = new Client(2500, 8, 75, new HashMap<Company, Float>(), se);
        Client clarke = new Client(600, 2, 65, new HashMap<Company, Float>(), se);
        Client hubert = new Client(1000, 51, 98, new HashMap<Company, Float>(), se);

        Thread one = new Thread(sue);
        Thread two = new Thread(jim);
        Thread three = new Thread(clarke);
        Thread four = new Thread(hubert);
            one.setName("sue");
            two.setName("jim");
            three.setName("clarke");
            four.setName("hubert");

        one.start();    //opens thread one
        two.start();
        three.start();
        four.start();
        one.join();     //closes thread one
        two.join();
        three.join();
        four.join();

        System.out.println(se.getCompanies());

    }
}
