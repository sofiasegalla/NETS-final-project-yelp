import java.util.ArrayList;

public class YelpTester {

    public static void main(String[] args) {
        YelpAPI yelp = new YelpAPI();
        yelp.getUserInput("Philadelphia, PA", "restaurants", 5);
        yelp.getResults();
        ArrayList<YelpBusiness> businesses = yelp.getBusinesses();
        for (YelpBusiness business : businesses) {
            System.out.println(business.toString());
            System.out.println();
        }
        
        Itinerary i = new Itinerary(businesses);
        i.makeGraph();
        i.dijkstra(i.getWorst(), i.getBest());
    }
}
