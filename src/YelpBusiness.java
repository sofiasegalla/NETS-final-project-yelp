public class YelpBusiness {
    private String name;
    private String address;
    private double lat;
    private double lon;
    private double rating; 
    private int rc; 

    public YelpBusiness(String name, String address, double lat, double lon, double r, int rc) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.rating = r;
        this.rc = rc;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public double getLatitude() {
        return this.lat;
    }

    public double getLongitude() {
        return this.lon;
    }
    public double getRating() {
         return this.rating;
     }

    public int getRc() { 
        return this.rc; 
    }

    public String toString() {
         return name + "\n" + address + "lat: " + lat + "\nlon: " + lon + "\nrating: " + rating
                + "\nreview count: " + rc;
    }
}
