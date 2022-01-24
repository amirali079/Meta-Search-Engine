
import java.util.Map;
import com.google.gson.JsonArray;


public class BingSearch extends SerpApiSearch {

    public BingSearch(Map<String, String> parameter, String apiKey) {
        super(parameter, apiKey, "bing");
    }

    public BingSearch() {
        super("bing");
    }

    public BingSearch(Map<String, String> parameter) {
        super(parameter, "bing");
    }

// end
}