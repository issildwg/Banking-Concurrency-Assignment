public class Company{
    private String name;
    private float totalNumberOfShares;
    private float availableNumberOfShares;
    private float price;

    public Company(){
        this.name = "Sony";
        this.totalNumberOfShares = 100;
        this.availableNumberOfShares = 75;
        this.price = 45;
    }

    public Company(String name, float totalNumberOfShares, float availableNumberOfShares, float price){
        this.name = name;
        this.totalNumberOfShares = totalNumberOfShares;
        this.availableNumberOfShares = availableNumberOfShares;
        this.price = price;
    }



    public synchronized void setName(String newName){
        System.out.println(name + " changed name to: " + newName);
        this.name = newName;
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

    public synchronized float getPrice(){
        return price;
    }

    public synchronized void setPrice(float price){
        if (price <= 0){
            System.out.println("Error! negative price cannot be accepted");
        } else {
            this.price = price;
        }
        notifyAll();
    }


}

