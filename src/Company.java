public class Company{
    private String name;
    private float totalNumberOfShares;
    private float availableNumberOfShares;
    private float price;


    public Company(String name, float totalNumberOfShares, float availableNumberOfShares, float price){
        this.name = name;
        this.totalNumberOfShares = totalNumberOfShares;
        this.availableNumberOfShares = availableNumberOfShares;
        this.price = price;
    }

    public Company(){

    }

    public synchronized void setName(String name){
        this.name = name;
    }

    public synchronized String getName(){
        return name;
    }

    public synchronized void setTotalShares(float totalNumberOfShares){
        this.totalNumberOfShares = totalNumberOfShares;
    }

    public synchronized float getTotalShares(){
        return totalNumberOfShares;
    }

    public synchronized void setAvailableShares(float availableNumberOfShares){
        if (availableNumberOfShares >= 0){
            this.availableNumberOfShares = availableNumberOfShares;
        } else {
            System.out.println("Available number of shares below 0");
        }
    }

    public synchronized float getAvailableShares(){
            return availableNumberOfShares;
    }

    public synchronized void setPrice(float price){
        if (price <= 0){
            System.out.println("Error! negative price cannot be accepted");
        } else {
            this.price = price;
        }
    }

    public synchronized float getPrice(){
        return price;
    }

}

