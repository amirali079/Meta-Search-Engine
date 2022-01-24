import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SerpApiSearchException {
//        if(args.length != 1) {
//            System.out.println("Usage: app <secret api key>");
//            System.exit(1);
//        }

        String location = "Austin,Texas";
        System.out.println("find the first Coffee in " + location);

        // parameters
        Map<String, String> parameter = new HashMap<>();
        parameter.put("q", "Flower");
        parameter.put("p", "Flower");

        //parameter.put("location", location);
        //parameter.put(GoogleSearch.SERP_API_KEY_NAME, "4ce90e04512431ab9b387af84703adc43e5ad03c4ea1d4fe151a0aa0b562498c");

        // Create search
        //GoogleSearch.serp_api_key_default = "4ce90e04512431ab9b387af84703adc43e5ad03c4ea1d4fe151a0aa0b562498c";

        GoogleSearch googleSearch = new GoogleSearch(parameter);
        YahooSearch yahooSearch = new YahooSearch(parameter);
        BingSearch bingSearch = new BingSearch(parameter);

        try {
            // Execute search
            JsonObject data_googleSearch = googleSearch.getJson();
            JsonObject data_yahooSearch = yahooSearch.getJson();
            JsonObject data_bingSearch = bingSearch.getJson();

            // Decode response
            JsonArray results_googleSearch = (JsonArray) data_googleSearch.get("organic_results");
            JsonArray results_yahooSearch = (JsonArray) data_yahooSearch.get("organic_results");
            JsonArray results_bingSearch = (JsonArray) data_bingSearch.get("organic_results");

            System.out.println("----------------google----------------------");
            System.out.println(results_googleSearch.toString());
            System.out.println("------------------yahoo--------------------");
            System.out.println(results_yahooSearch.toString());
            System.out.println("------------------bing--------------------");
            System.out.println(results_bingSearch.toString());

//            JsonObject first_result = results.get(0).getAsJsonObject();
//            System.out.println("first coffee: " + first_result.get("title").getAsString() + " in " + location);
        } catch (SerpApiSearchException e) {
            System.out.println("oops exception detected!");
            e.printStackTrace();
        }
    }
}
