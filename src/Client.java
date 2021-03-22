import java.util.EventListener;
import java.util.HashMap;

public class Client implements Runnable{
    private HashMap<Company, Float> shares;
    private float balance;
    private boolean holdBackSell;
    private boolean holdBackBuy;
    private String name;

    private static float lowLim;
    private static float highLim;
    private static float lowShares;
    private static float highShares;


    StockExchange se;


    public Client() {
        this.name = "Janet";
        this.balance = 4500;
        this.shares = new HashMap<>();
        this.se = Application.se;
    }

    public Client(String name, float balance, HashMap shares, StockExchange se) {
        this.name = name;
        this.balance = balance;
        this.shares = shares;
        this.se = se;
    }

    public String getName(){
        return name;
    }

    public float getBalance(){
        return balance;
    }

    public float getLowLim(){
        return lowLim;
    }

    public float getHighLim(){
        return highLim;
    }

    public float getLowShares(){
        return lowShares;
    }

    public float getHighShares(){
        return highShares;
    }


    public HashMap<Company, Float> getStocks(){
        return shares;
    }

    public void setStocks(Company company, float numberOfShares){
        if(shares.containsKey(company)){
            shares.put(company, numberOfShares);
        }
    }


    public boolean onHoldBuy(){
        return holdBackBuy;
    }
    public boolean onHoldSell(){
        return holdBackSell;
    }



    public synchronized boolean buy(Company company, float numberOfShares) throws InterruptedException {
        if(balance < (company.getPrice() * numberOfShares)) {               //balance too low
            System.out.println(Thread.currentThread().getName() + " has insufficient funds");
        }else if(company.getAvailableShares() < numberOfShares){            //not enough shares
            System.out.println(company.getName() + " have insufficient shares");
        } else if (balance >= (company.getPrice() * numberOfShares) && company.getAvailableShares() >= numberOfShares && !onHoldBuy()) {    //all good vibes - can buy
            holdBackBuy = true;
            shares.put(company, numberOfShares);
            balance = balance - (company.getPrice() * numberOfShares);
            float updatedShares = company.getAvailableShares() - numberOfShares;
            company.setAvailableShares(updatedShares);
            if (se.getCompanies().containsKey(company)) {
                se.getCompanies().put(company, updatedShares);
            }else{
                se.registerCompany(company, updatedShares);
            }
            System.out.println(Thread.currentThread().getName() + " just bought " + numberOfShares + " Stocks from " + company.getName() + ", " + se.getCompanies().get(company) + " Stocks remaining in the Stock Exchange");
            holdBackBuy = false;
            return true;
        } else {
            while (onHoldBuy()) {       //stock on hold
                System.out.println("Error! Stock cant be bought at this time, please hold");
                Thread.currentThread().wait();
                buy(company, numberOfShares);
            }
        }
        notifyAll();
        return false;
    }

    public synchronized boolean sell(Company company, float numberOfShares) throws InterruptedException {
        if(shares.getOrDefault(company, 0f) == 0) {
            System.out.println(Thread.currentThread().getName() + " has insufficient shares");
        } else if(shares.get(company) < numberOfShares){
            System.out.println(Thread.currentThread().getName() + " has insufficient shares");
        }else if (shares.get(company) >= numberOfShares && !onHoldSell()){
            holdBackSell = true;
            shares.remove(company, numberOfShares);
            balance = balance + company.getPrice()*numberOfShares;
            float updatedShares = numberOfShares + company.getAvailableShares();
            company.setAvailableShares(updatedShares);
            se.getCompanies().put(company, updatedShares);
            System.out.println(Thread.currentThread().getName() + " just sold " + numberOfShares + " back to " + company.getName() + ", " + se.getCompanies().get(company) + " Stocks remaining in the Stock Exchange");
            holdBackSell = false;
            return true;
        } else {
            while (onHoldSell()) {
                System.out.println("Error! Stock cant be sold at this time, please hold");
                Thread.currentThread().wait();
                sell(company, numberOfShares);
            }
        }
        notifyAll();
        return false;
    }

    public synchronized boolean buyLow(Company company, float numberOfShares, float limit) throws InterruptedException {
        this.lowLim = limit;
        this.lowShares = numberOfShares;

        if(limit >= company.getPrice()) {
            System.out.println(Thread.currentThread().getName() + " is trying to buy low: " + numberOfShares + " stocks from " + company.getName());
            buy(company, numberOfShares);
            return true;
        }
        return false;
       /*
        else{
            while(limit < company.getPrice()){

                Thread.currentThread().sleep(1);
                //its getting caught in here - WHAT DO I FUCKING DO TO FIX THIS SHIT?????????????
            }
            buyLow(company, numberOfShares, limit);
            return false;
        }
        */
    }

    public synchronized boolean sellHigh(Company company, float numberOfShares, float limit) throws InterruptedException {
        this.highLim = limit;
        this.highShares = numberOfShares;

        if(limit <= company.getPrice()){
            System.out.println(Thread.currentThread().getName() + " is trying to sell high: " + numberOfShares + " stocks back to " + company.getName());
            sell(company, numberOfShares);
            return true;
        }
        return false;
        /*
        else{
            while(limit > company.getPrice()){
                Thread.currentThread().sleep(1);
                //its getting caught in here - WHAT DO I FUCKING DO TO FIX THIS SHIT?????????????
            }
            sellHigh(company, numberOfShares, limit);
            return false;
        }
         */

    }

    public boolean deposit(float amount){
        if(amount > 0){
            balance = balance + amount;
            System.out.println(name + " deposited £" + amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(float amount){
        if(amount > 0){
            balance = balance - amount;
            System.out.println(name + " withdrew £" + amount);
            return true;
        }
        return false;
    }


    public int getRandomInt(int size){
        double x;
        do{
            x = Math.random()*10;
        } while(x >= size);

        return (int) x;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " £" + balance);
        Company y = se.getCompanies().keySet().toArray(new Company[0])[getRandomInt(se.getCompanies().size()-1)];

        //TODO i think i can wipe this section out if i follow the other idea - keep for now


        for (Company company :  se.getCompanies().keySet()){
            try {
                buyLow(company, 5, 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                sellHigh(company, 5, 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            float z = Math.round(getRandomInt(10));
            System.out.println(Thread.currentThread().getName() + " is trying to buy " + z + " stocks from " + y.getName());
            buy(y, z);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            float z = Math.round(getRandomInt(10));
            System.out.println(Thread.currentThread().getName() + " is trying to sell " + z + " stocks to " + y.getName());
            sell(y, z);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " £" +  balance);

    }
}
