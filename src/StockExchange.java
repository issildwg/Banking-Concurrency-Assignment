import java.util.ArrayList;
import java.util.HashMap;

public class StockExchange {
    private static float numberOfShares;
    private static HashMap<Company, Float> companies = new HashMap<>(); //float = numberOfShares
    private ArrayList<Client> clients;

    public StockExchange(){
        this.companies = new HashMap<>();
        this.clients = new ArrayList<>();
    }

    public static boolean registerCompany(Company company, float numberOfShares){ //available shares
        if (!companies.containsKey(company)){
            companies.put(company, numberOfShares);
            System.out.println(company.getName() + " has been registered to the Stock Exchange with " + companies.get(company) + " shares");
            return true;
        }
        return false;
    }

    public static boolean deregisterCompany(Company company){
        if (companies.containsKey(company)){
            companies.remove(company);
            System.out.println(company.getName() + " has been removed from Stock Exchange");
            return true;
        }
        return false;
    }

    public boolean addClient(Client client){
        if(clients.contains(client)){
            return false;
        }
        clients.add(client);
        System.out.println(client.getName() + " has been added to the Stock Exchange");
        return true;
    }

    public boolean removeClient(Client client){
        if(clients.contains(client)){
            clients.remove(client);
            System.out.println(client.getName() + " has been removed from Stock Exchange");

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
        System.out.println(company.getName() + " has changed Stock price to: £" + company.getPrice());
    }


}
