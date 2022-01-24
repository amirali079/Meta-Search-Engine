
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.HashMap;

/**
 * SerpApiSearch wraps HTTP interaction with the service serpapi.com
 */
public class SerpApiSearch extends Exception {
    // Set of constant
    public static final String SERP_API_KEY_NAME = "api_key";

    // default static key
    public static final String serp_api_key_default = "4ce90e04512431ab9b387af84703adc43e5ad03c4ea1d4fe151a0aa0b562498c";

    // instance level key
    private String api_key;

    // current search engine
    private String engine;

    // persist query parameter
    public Map<String, String> parameter;

    // initialize gson
    private static Gson gson = new Gson();

    // https search implementation for Java 7+
    public SerpApiHttpClient search;

    public SerpApiSearch(Map<String, String> parameter, String api_key, String engine) {
        this.parameter = parameter;
        this.api_key = api_key;
        this.engine = engine;
    }


    public SerpApiSearch(Map<String, String> parameter, String engine) {
        this.parameter = parameter;
        this.engine = engine;
    }

    /***
     * Constructor with no parameter
     */
    public SerpApiSearch(String engine) {
        this.parameter = new HashMap<String, String>();
        this.engine = engine;
    }

    /*
     * Constructor
     *
     * @param serp_api_key
     */
    public SerpApiSearch(String serp_api_key, String engine) {
        this.api_key = serp_api_key;
        this.engine = engine;
    }


    public Map<String, String> buildQuery(String path, String output) throws SerpApiSearchException {
        // Initialize search if not done
        if (search == null) {
            this.search = new SerpApiHttpClient(path);
            this.search.setHttpConnectionTimeout(6000);
        } else {
            this.search.path = path;
        }

        // Set current programming language
        this.parameter.put("source", "java");

        // Set serp_api_key
        if (this.parameter.get(SERP_API_KEY_NAME) == null) {
            if (this.api_key != null) {
                this.parameter.put(SERP_API_KEY_NAME, this.api_key);
            } else if (getSerpApiKey() != null) {
                this.parameter.put(SERP_API_KEY_NAME, getSerpApiKey());
            } else {
                // throw new SerpApiSearchException(SERP_API_KEY_NAME + " is not defined");
            }
        }

        this.parameter.put("engine", this.engine);

        // Set output format
        this.parameter.put("output", output);

        return this.parameter;
    }

    public static String getSerpApiKey() {
        return serp_api_key_default;
    }


    public String getHtml() throws SerpApiSearchException {
        Map<String, String> query = buildQuery("/search", "html");
        return search.getResults(query);
    }

    public JsonObject getJson() throws SerpApiSearchException {
        Map<String, String> query = buildQuery("/search", "json");
        return asJson(search.getResults(query));
    }

    public JsonObject asJson(String content) {
        JsonElement element = gson.fromJson(content, JsonElement.class);
        return element.getAsJsonObject();
    }

    public SerpApiHttpClient getClient() {
        return this.search;
    }

    public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
        Map<String, String> query = buildQuery("/locations.json", "json");
        query.remove("output");
        query.remove(SERP_API_KEY_NAME);
        query.put("q", q);
        query.put("limit", limit.toString());
        String s = search.getResults(query);
        return gson.fromJson(s, JsonArray.class);
    }


    public JsonObject getSearchArchive(String searchID) throws SerpApiSearchException {
        Map<String, String> query = buildQuery("/searches/" + searchID + ".json", "json");
        query.remove("output");
        query.remove("q");
        return asJson(search.getResults(query));
    }

    public JsonObject getAccount() throws SerpApiSearchException {
        Map<String, String> query = buildQuery("/account", "json");
        query.remove("output");
        query.remove("q");
        return asJson(search.getResults(query));
    }

}
