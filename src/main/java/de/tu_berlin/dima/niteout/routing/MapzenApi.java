package de.tu_berlin.dima.niteout.routing;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.utils.URIBuilder;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

/**
 * Created by aardila on 1/28/2017.
 */
abstract class MapzenApi {

    protected final String apiKey;
    protected final String service;
    //private final String urlFormat = "https://%s.mapzen.com/%s?json=%s&api_key=%s";
    private final String urlFormat = "https://%s.mapzen.com/%s";

    protected MapzenApi(String service, String apiKey) {
        assert !service.isEmpty();
        assert !apiKey.isEmpty();

        this.service = service;
        this.apiKey = apiKey;
    }

    protected JsonObject getResponse(String endpoint, JsonObject jsonObject) throws IOException, URISyntaxException {
        final String url = getUrl(endpoint, jsonObject);
        return getResponse(url);
    }

    protected JsonObject getResponse(String endpoint, LinkedHashMap<String, String> queryString) throws IOException, URISyntaxException {
        final String url = getUrl(endpoint, queryString);
        return getResponse(url);
    }

    private JsonObject getResponse(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        JsonReader jsonReader = Json.createReader(response.body().charStream());
        JsonObject out = jsonReader.readObject();

        return out;
    }

    protected String getUrl(String endpoint, JsonObject jsonObject) throws URISyntaxException {
        assert jsonObject != null;

        LinkedHashMap<String, String> queryString = new LinkedHashMap<>();
        queryString.put("json", jsonObject.toString());
        queryString.put("api_key", this.apiKey);
        return getUrl(endpoint, queryString);
        //return String.format(urlFormat, service, endpoint, jsonObject, apiKey);
    }

    protected String getUrl(String endpoint, LinkedHashMap<String, String> queryString) throws URISyntaxException {
        String baseUrl = String.format(urlFormat, service, endpoint);
        URIBuilder builder = new URIBuilder(baseUrl);
        for (String key : queryString.keySet()) {
            builder.addParameter(key, queryString.get(key));
        }
        URI uri = builder.build();
        return uri.toString();
    }
}