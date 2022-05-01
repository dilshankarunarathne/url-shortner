package me.karunarathne.adfly.app;

import me.karunarathne.adfly.RestAPI.APIWrapper;

public class Shorten {
    private static APIWrapper api;

    public Shorten() {
        api = new APIWrapper();
    }

    public Shorten (Long userId, String puk, String sk) {
        api = new APIWrapper();
    }

    private String shorten(String[] urls, String domain, String adType, Long groupId) {
        return api.shorten(urls, domain, adType, groupId);
    }

    /**
     * Shortens a given list of urls.
     * @param urls
     * @return  json output
     */
    public String shorten(String[] urls) {
        return shorten(urls, null, "banner", 13L);
    }
}
