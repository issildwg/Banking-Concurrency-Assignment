import java.util.HashMap;

public class Client {
    private HashMap<Company, Float> shares;
    //private HashMap<String, String> capitalCities = new HashMap<String, String>();
    private float balance;

    private float sellHigh;
    private float buyLow;

    public Client() {
    }


//who fucking knows - definitely double check this bad boy
    public boolean setStocks(Company company, float numberOfShares){
        if(shares.containsKey(company)){
            shares.put(company, numberOfShares);
            return true;
        }
        return false;
    }

    public boolean buy(Company company, float numberOfShares) {
        if (balance >= company.getPrice() * numberOfShares && company.getAvailableShares() >= numberOfShares) {
            shares.put(company, shares.get(company) + numberOfShares);
            company.setAvailableShares(company.getAvailableShares() - numberOfShares);
            balance = balance - company.getPrice()*numberOfShares;
            return true;
        }
        return false;
    }

    public boolean sell(Company company, float numberOfShares){
        if(shares.get(company) <= numberOfShares){
            shares.remove(company, numberOfShares);
            company.setAvailableShares(numberOfShares + company.getAvailableShares());
            balance = balance + company.getPrice()*numberOfShares;
            return true;
        }
        return false;
    }

    public boolean buyLow(Company company, float numberOfShares, float buyLow){
        if(company.getPrice() <= buyLow){
            buy(company, numberOfShares);
            return true;
        }
        return false;
    }

    public boolean sellHigh(Company company, float numberOfShares, float sellHigh){
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
}
