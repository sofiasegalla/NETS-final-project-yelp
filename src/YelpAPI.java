import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import org.json.*;

public class YelpAPI {
    private String APIKey = "cypMxwOdTdjM3fRK1O7HklYO8AUuju-IkPNQQiH-W350V8tS6Zkg_z6Lbd3LRlhBDKX-E2s8sLIA9YRY7ho3aUmTUbs5KNHoiHJTHr4DpeM6MIp1zvug1iSQI2RkYnYx";

    private final String baseURL = "https://api.yelp.com/v3/businesses/search?";
    private String searchURL;
    private String results;

    /**
     * Takes in the user inputs for location, categories, and search limit, and uses this to
     * create a searchURL that we will use to query the Yelp API for businesses.
     *
     * @param String location
     * @param String categories
     * @param int limit
     * @return String searchURL
     */
    public String getUserInput(String location, String categories, int limit) {
        location = location.replaceAll(" ", "+");
        searchURL = baseURL + "location=" + location + "&categories=" + categories + "&limit=" + limit;
        return searchURL;
    }

    /**
     * Connects to Yelp API and returns the contents of the search.
     *
     * @return String results of Yelp API call
     */
    public String getResults() {
        URLGetter url = new URLGetter(searchURL);
        HttpURLConnection httpConnection = url.getHttpConnection();
        httpConnection.addRequestProperty("Authorization", "Bearer " + APIKey);
        ArrayList<String> contents = url.getContents();
        results = contents.get(0);
        return results;
    }

    /**
     * Converts results String into a JSONObject.
     * Iterates over the "businesses" field and creates a new YelpBusiness instance for each business.
     * For each business, we want the name, address, and latitude/longitude coordinates.
     *
     * @return ArrayList of YelpBusiness Objects
     */
    public ArrayList<YelpBusiness> getBusinesses() {
        JSONObject json = new JSONObject(results);
        JSONArray array = json.getJSONArray("businesses");
        ArrayList<YelpBusiness> businesses = new ArrayList<YelpBusiness>();
        for (Object obj : array) {
            JSONObject business = (JSONObject) obj;
            String name = (String) business.get("name");
            int rc = (int) business.get("review_count");
            double rating = ((BigDecimal) business.get("rating")).doubleValue();
            JSONArray addressArr = (JSONArray) ((JSONObject) business.get("location")).get("display_address");
            String address = "";
            for (Object addr : addressArr) {
                address = address + addr + "\n";
            }
            JSONObject coords = (JSONObject) business.get("coordinates");
            double lat = ((BigDecimal) coords.get("latitude")).doubleValue();
            double lon = ((BigDecimal) coords.get("longitude")).doubleValue();

            YelpBusiness yelpBusiness = new YelpBusiness(name, address, lat, lon, rating, rc);
            businesses.add(yelpBusiness);
        }

        return businesses;
    }
}
