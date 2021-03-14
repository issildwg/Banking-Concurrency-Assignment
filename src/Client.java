import java.util.ArrayList;
import java.util.HashMap;

public class Client implements Runnable{
    private HashMap<Company, Float> shares;
    private float sellHigh;
    private float buyLow;
    private float balance;
    private boolean holdBackSell;
    private boolean holdBackBuy;
    private String name;

    StockExchange se;


    public Client() {
    }

    public Client(String name, float balance, float buyLow, float sellHigh, HashMap shares, StockExchange se) {
        this.name = name;
        this.balance = balance;
        this.buyLow = buyLow;
        this.sellHigh = sellHigh;
        this.shares = shares;
        this.se = se;
    }

    public String getName(){
        return name;
    }

    public float getBalance(){
        return balance;
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
        if(balance < (company.getPrice() * numberOfShares)) {   // too little money
            System.out.println(Thread.currentThread().getName() + " has insufficient funds");
        }else if(company.getAvailableShares() < numberOfShares){    //not enough shares
            System.out.println(company.getName() + " have insufficient shares");
        } else if (balance >= (company.getPrice() * numberOfShares) && company.getAvailableShares() >= numberOfShares && !onHoldBuy()) { //have funds, available shares them, not on hold
            holdBackBuy = true;

            shares.put(company, numberOfShares);    //doesnt add to previous shares

            balance = balance - (company.getPrice() * numberOfShares);
            float updatedShares = company.getAvailableShares() - numberOfShares;
            company.setAvailableShares(updatedShares);      //this shouldn't be updated if it is being updated in se

            se.getCompanies().put(company, updatedShares);
            System.out.println(Thread.currentThread().getName() + " just bought " + numberOfShares + " Stocks from " + company.getName() + ", " + se.getCompanies().get(company) + " Stocks remaining in the Stock Exchange");

            holdBackBuy = false;
            return true;
        } else {
            while (onHoldBuy()) {
                System.out.println("Error! Stock cant be bought at this time, please hold");
                wait();
                buy(company, numberOfShares);
            }
        }
        notifyAll();
        return false;
    }

    public synchronized boolean sell(Company company, float numberOfShares) throws InterruptedException {
        if(shares.getOrDefault(company, 0f) == 0) {
            System.out.println(Thread.currentThread().getName() + " has insufficient shares");
        } else if(shares.get(company) < numberOfShares        ){
            System.out.println(Thread.currentThread().getName() + " has insufficient shares");
        }else if (shares.get(company) >= numberOfShares && !onHoldSell()){ //available shares me, not on hold
            holdBackSell = true;

            shares.remove(company, numberOfShares);

            balance = balance + company.getPrice()*numberOfShares;
            float updatedShares = numberOfShares + company.getAvailableShares();
            company.setAvailableShares(updatedShares);      //this shouldn't be updated if it is being updated in se

            se.getCompanies().put(company, updatedShares);

            System.out.println(Thread.currentThread().getName() + " just sold " + numberOfShares + " back to " + company.getName() + ", " + se.getCompanies().get(company) + " Stocks remaining in the Stock Exchange");

            holdBackSell = false;
            return true;
        } else {
            while (onHoldSell()) {
                System.out.println("Error! Stock cant be sold at this time, please hold");
                wait();
                sell(company, numberOfShares);
            }
        }
        notifyAll();
        return false;
    }

    public boolean buyLow(Company company, float numberOfShares) throws InterruptedException {      //removed float buyLow - necessary??
        if(company.getPrice() <= buyLow){
            System.out.println(Thread.currentThread().getName() + " is trying to buy low: " + numberOfShares + " stocks from " + company.getName());
            buy(company, numberOfShares);
            return true;
        }
        return false;
    }

    public boolean sellHigh(Company company, float numberOfShares) throws InterruptedException {    //removed float sellHigh - necessary??
        if(company.getPrice() >= sellHigh){
            System.out.println(Thread.currentThread().getName() + " is trying to sell high: " + numberOfShares + " stocks from " + company.getName());
            sell(company, numberOfShares);
            return true;
        }
        return false;
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

        for (Company company :  se.getCompanies().keySet()){
            try {
                buyLow(company, 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                sellHigh(company, 5);
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
        }   //buy rand at rand price


        try {
            float z = Math.round(getRandomInt(10));
            System.out.println(Thread.currentThread().getName() + " is trying to sell " + z + " stocks to " + y.getName());
            sell(y, z);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   //sell rand at rand price

        System.out.println(Thread.currentThread().getName() + " £" +  balance);
    }
}
