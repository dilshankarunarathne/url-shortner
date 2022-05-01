package me.karunarathne.adfly.app;

import me.karunarathne.adfly.RestAPI.APIWrapper;

import static me.karunarathne.adfly.JSONParse.FormatJSON.formatJson;

public class GetURL {
    private static APIWrapper api;

    public GetURL() {
        api = new APIWrapper();
    }

    public String getURLs () {
        return formatJson (api.getUrls(1, "teen")) ;
    }
}
