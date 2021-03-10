import java.util.ArrayList;
import java.util.HashMap;

public class StockExchange {
    private static float numberOfShares;
    private static HashMap<Company, Float> companies = new HashMap <Company, Float>(); //float = numberOfShares
    private ArrayList<Client> clients = new ArrayList<Client>();

    public StockExchange(){
        this.companies = new HashMap<Company, Float>();
        this.clients = new ArrayList<Client>();
    }

    public static boolean registerCompany(Company company, float numberOfShares){ //available shares
        if (!companies.containsKey(company)){
            System.out.println(company.getName() + " has been registered");
            companies.put(company, numberOfShares);
            return true;
        }
        return false;
    }

    public static boolean deregisterCompany(Company company){
        if (companies.containsKey(company)){
            companies.remove(company);
            return true;
        }
        return false;
    }

    public boolean addClient(Client client){
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

    public ArrayList<Client> getClients(){
        return clients;
    }

    public HashMap<Company, Float> getCompanies(){
        return companies;
    }


    public void setPrice(Company company, float startPrice) {
       company.setPrice(startPrice);
    }

    public void changePriceBy(Company company, float newPrice){
        company.setPrice(company.getPrice() + newPrice);
    }


}
