package me.karunarathne.adfly.RestAPI;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class APIWrapper {
    /**
     * Two possible authentication types.
     */
    public enum AuthType {
        BASIC,
        HMAC
    }

    public static final String BASE_SCHEME = "https";
    public static final String BASE_HOST = "api.adf.ly";
    // TODO: Replace following constant value with your public api key.
    public static final String PUBLIC_KEY = "";
    // TODO: Replace following constant value with your user id.
    public static final long USER_ID = 0;
    // TODO: Replace following constant value with your secret key.
    private static final String SECRET_KEY = "";

    private RestClient rest;

    public APIWrapper() {
        rest = new RestClient(BASE_SCHEME, BASE_HOST);
    }

    public String getGroups(int page) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("_page", page);
        prepareParams(APIWrapper.AuthType.HMAC, params);
        return rest.doGet("/v1/urlGroups", params);
    }

    public String getGroups() {
        return getGroups(1);
    }

    public String expand(String[] urls, String[] hashes) {
        Map<String, Object> params = new HashMap<String, Object>();

        int i = 0;

        if (null != urls) {
            if (urls.length > 1) {
                for (String url : urls) {
                    params.put(String.format("url[%d]", i++), url);
                }
            } else if (urls.length == 1) {
                params.put("url", urls[0]);
            }
        }

        i = 0;

        if (null != hashes) {
            if (hashes.length > 1) {
                for (String hash : hashes) {
                    params.put(String.format("hash[%d]", i++), hash);
                }
            } else if (hashes.length == 1) {
                params.put("hash", hashes[0]);
            }
        }

        prepareParams(params);
        return rest.doGet("/v1/expand", params);
    }

    public String shorten(String[] urls, String domain, String advertType, Long groupId) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (null != domain) {
            params.put("domain", domain);
        }
        if (null != advertType) {
            params.put("advert_type", advertType);
        }
        if (null != groupId) {
            params.put("group_id", groupId);
        }

        int i = 0;
        for (String url : urls) {
            params.put(String.format("url[%d]", i++), url);
        }

        prepareParams(params);
        return rest.doPost("/v1/shorten", params);
    }

    public String shorten(String url) {
        return shorten(new String[] {url}, null, null, null);
    }

    public String getUrls(int page, String searchStr) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("_page", page);
        params.put("q", searchStr);
        prepareParams(APIWrapper.AuthType.HMAC, params);
        return rest.doGet("/v1/urls", params);
    }

    public String getUrls() {
        return getUrls(1, null);
    }

    public String getUrls(int page) {
        return getUrls(page, null);
    }

    public String updateUrl(Long id, String url, String advertType,
                            String title, Long groupId, String fbDescription, String fbImage) {

        Map<String, Object> params = new HashMap<String, Object>();
        if (null != url) params.put("url", url);
        if (null != advertType) params.put("advert_type", advertType);
        if (null != title) params.put("title", title);
        if (null != groupId) params.put("group_id", groupId);
        if (null != fbDescription) params.put("fb_description", fbDescription);
        if (null != fbImage) params.put("fb_image", fbImage);

        prepareParams(APIWrapper.AuthType.HMAC, params);
        return rest.doPut(String.format("/v1/urls/%d", id), params);
    }

    public String deleteUrl(Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        prepareParams(APIWrapper.AuthType.HMAC, params);
        return rest.doDelete(String.format("/v1/urls/%d", id), params);
    }

    /**
     * Populates request parameters with required parameters.
     * Such as _user_id, _api_key, etc.
     * @param authType
     * @param params
     */
    private void prepareParams(APIWrapper.AuthType authType, Map<String, Object> params) {
        params.put("_user_id", USER_ID);
        params.put("_api_key", PUBLIC_KEY);

        if (authType.equals(APIWrapper.AuthType.BASIC)) {

        } else if (authType.equals(APIWrapper.AuthType.HMAC)) {
            // Get current unix timestamp (UTC time).
            params.put("_timestamp", new Date().getTime() / 1000);
            params.put("_hash", computeHash(params));
        }
    }

    private void prepareParams(Map<String, Object> params) {
        prepareParams(APIWrapper.AuthType.BASIC, params);
    }

    private String computeHash(Map<String, Object> params) {
        // Sort query parameters by names using byte ordering.
        // So 'param[10]' comes before 'param[2]'.
        // TreeMap constructed from current parameters
        // will be used for such purpose.
        TreeMap<String, Object> sortedParams = new TreeMap<String, Object>(params);
        // Url encode parameters.
        String query = RestClient.buildQuery(sortedParams);

        try {
            Mac mac = Mac.getInstance("hmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "hmacSHA256");
            mac.init(secretKey);
            byte[] digest = mac.doFinal(query.getBytes());
            StringBuilder hashBuilder = new StringBuilder();

            for (byte b : digest) {
                // Format result as hexadecimal integer.
                hashBuilder.append(String.format("%02x", b));
            }

            return hashBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
