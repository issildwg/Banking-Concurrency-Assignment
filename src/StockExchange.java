import java.util.ArrayList;
import java.util.HashMap;

public class StockExchange {
    private static float numberOfShares;
    private static HashMap<Company, Float> companies = new HashMap <Company, Float>();
    private ArrayList<Client> clients = new ArrayList<Client>();

    public void StockExchange(){

    }

    private static boolean registerCompany(Company company, float numberOfShares){
        if (companies.containsKey(company)){
            return false;
        }
        companies.put(company, numberOfShares);
        return true;
    }

    private static boolean deregisterCompany(Company company){
        if (companies.containsKey(company)){
            companies.remove(company);
            return true;
        }
        return false;
    }

    private boolean addClient(Client client){
        if(clients.contains(client)){
            return false;
        }
        clients.add(client);
        return true;
    }

    public boolean removeClient(Client client){
        if(clients.contains(client)){
            clients.remove(client);
            return true;
        }
        return false;
    }

}
