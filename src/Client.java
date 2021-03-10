import java.util.ArrayList;
import java.util.HashMap;

public class Client implements Runnable{
    private HashMap<Company, Float> shares;
    private float sellHigh;
    private float buyLow;
    private float balance;
    private boolean holdBackSell;
    private boolean holdBackBuy;

    StockExchange se = new StockExchange();


    public Client() {
    }

    public Client(float balance, float buyLow, float sellHigh, HashMap shares, StockExchange se) {
        this.balance = balance;
        this.buyLow = buyLow;
        this.sellHigh = sellHigh;
        this.shares = shares;
        this.se = se;
    }

    public HashMap<Company, Float> getStocks(){
        return shares;
    }

//who fucking knows - definitely double check this bad boy
    public boolean setStocks(Company company, float numberOfShares){
        if(shares.containsKey(company)){ //if hashmap contains this company
            shares.put(company, numberOfShares);
            return true;
        }
        return false;
    }


    public boolean onHoldBuy(){
        return holdBackBuy;
    }
    public boolean onHoldSell(){
        return holdBackSell;
    }

    public synchronized boolean buy(Company company, float numberOfShares) throws InterruptedException {
        if (balance >= company.getPrice() * numberOfShares && company.getAvailableShares() >= numberOfShares && !onHoldBuy()) {
            holdBackBuy = true;
            shares.put(company, numberOfShares);
            balance = balance - company.getPrice()*numberOfShares;
            company.setAvailableShares(company.getAvailableShares() - numberOfShares);
            return true;
        } else {
            System.out.println("Error! Stock cant be bought at this time, please hold");
            while (onHoldBuy()) {
                wait();
            }
            buy(company, numberOfShares);
        }
        holdBackBuy = false;
        notifyAll();
        return false;
    }

    public synchronized boolean sell(Company company, float numberOfShares) throws InterruptedException {
        if(shares.get(company) <= numberOfShares && !onHoldSell()){
            holdBackSell = true;
            shares.remove(company, numberOfShares);
            company.setAvailableShares(numberOfShares + company.getAvailableShares());
            balance = balance + company.getPrice()*numberOfShares;
            return true;
        } else {
            System.out.println("Error! Stock cant be sold at this time, please hold");
            while (onHoldSell()) {
                wait();
            }
            sell(company, numberOfShares);
        }
        holdBackSell = false;
        notifyAll();
        return false;
    }

    public boolean buyLow(Company company, float numberOfShares, float buyLow) throws InterruptedException {
        if(company.getPrice() <= buyLow){
            buy(company, numberOfShares);
            return true;
        }
        return false;
    }

    public boolean sellHigh(Company company, float numberOfShares, float sellHigh) throws InterruptedException {
        if(company.getPrice() >= sellHigh){
            sell(company, numberOfShares);
            return true;
        }
        return false;
    }

    public boolean deposit(float amount){
        if(amount > 0){
            balance = balance + amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(float amount){
        if(amount < 0){
            balance = balance - amount;
            return true;
        }
        return false;
    }


    @Override
    public void run() {
        System.out.println("This is " + Thread.currentThread().getName());

        {      //sets stocks of buyer to 0 ... will break it later on but for testing purposes
       /* for (Company company: se.getCompanies().keySet()) {
            setStocks(company,0f);
        }   */ }



        try {
            Thread.sleep(1000);

            //loop through companies to find the company

            /*if(se.apple){
                System.out.println(se.getCompanies().contains);
                //buy(apple,100f);
            }*/


            buy(, 12);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // System.out.println("shares bought, remaining shares: " + .getAvailableShares());

    }
}
