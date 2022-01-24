
import java.util.Map;
import com.google.gson.JsonArray;


public class YahooSearch extends SerpApiSearch {

    public YahooSearch(Map<String, String> parameter, String apiKey) {
        super(parameter, apiKey, "yahoo");
    }

    public YahooSearch() {
        super("yahoo");
    }

    public YahooSearch(Map<String, String> parameter) {
        super(parameter, "yahoo");
    }

    @Override
    public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
        throw new SerpApiSearchException("location is not supported for Baidu");
    }
}