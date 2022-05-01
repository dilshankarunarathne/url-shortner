package adfly ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Simple REST client.
 */
public class AdflySimpleRestClient {
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	
	private String scheme;
	private String host;
	
	public AdflySimpleRestClient(String scheme, String host) {
		this.scheme = scheme;
		this.host = host;
	}
	
	/**
	 * Performs POST request on specified url resource.
	 * @param url Target resource
	 * @param params Query parameters
	 * @return Server response
	 */
	public String doPost(String url, Map<String, Object> params) {
		return exec(POST, url, params);
	}
	
	/**
	 * Performs GET request on specified url resource.
	 * @param url Target resource
	 * @param params Query parameters
	 * @return Server response
	 */
	public String doGet(String url, Map<String, Object> params) {
		return exec(GET, url, params);
	}
	
	/**
	 * Performs PUT request on specified url resource.
	 * @param url Target resource
	 * @param params Query parameters
	 * @return Server response
	 */
	public String doPut(String url, Map<String, Object> params) {
		return exec(PUT, url, params);
	}
	
	/**
	 * Performs DELETE request on specified url resource.
	 * @param url Target resource
	 * @param params Query parameters
	 * @return Server response
	 */
	public String doDelete(String url, Map<String, Object> params) {
		return exec(DELETE, url, params);
	}
	
	private String exec(String type, String path, Map<String, Object> params) {
		String query = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		if (type.equals(GET) || type.equals(DELETE)) {
			query = buildQuery(params);
		}
		
		HttpURLConnection connection = connect(path, query);
		
		try {
			connection.setRequestMethod(type);
			
			if (type.equals(POST) || type.equals(PUT)) {
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				OutputStream out = connection.getOutputStream();
				Writer writer = new OutputStreamWriter(out, "UTF-8");
				
				// Write POST/PUT body payload.
				writer.write(buildQuery(params));
				writer.close();
				out.close();
			}
			
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new IOException(connection.getResponseMessage());
			}
			
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			br.close();
			connection.disconnect();
			
			return sb.toString();
		} catch (ProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private HttpURLConnection connect(String path, String query) {
		HttpURLConnection connection = null;
		
		try {
			connection = (HttpURLConnection)constructUrl(path, query).openConnection();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		connection.setInstanceFollowRedirects(false);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setReadTimeout(10000);
		connection.setConnectTimeout(3000);
		
		return connection;
	}
	
	private URL constructUrl(String path, String query) {
		try {
			return new URL(String.format(
				"%s://%s%s%s",
				scheme, host, path, (query != null && !query.isEmpty()) ? ("?" + query) : ""));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Builds url-encoded query string using specified parameters.
	 * The encoding should be performed per RFC 1738 (http://www.faqs.org/rfcs/rfc1738)
	 * which implies that spaces are encoded as plus (+) signs.
	 * @param params
	 * @return Encoded query string
	 */
	public static String buildQuery(Map<String, Object> params) {
		if (null == params || 0 == params.size()) {
			return null;
		}
		
		int keysCount = params.keySet().size();
		int i = 0;
		String[] pieces = null;
		
		if (keysCount > 0) {
			pieces = new String[keysCount];
		}
		
		for (String key : params.keySet()) {
			try {
				pieces[i++] = String.format(
					"%s=%s", 
					URLEncoder.encode(key, "UTF-8"), 
					URLEncoder.encode(String.valueOf(params.get(key)), "UTF-8")
				);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		
		return implode("&", pieces);
	}
	
	/**
	 * Join String values with another String.
	 * @param glue
	 * @param pieces
	 * @return
	 */
	private static String implode(String glue, String... pieces) {
		int k = pieces != null ? pieces.length : 0;
		
		if (k == 0) {
			return null;
		}
		
		StringBuilder out = new StringBuilder();
		out.append(pieces[0]);
		
		for (int x = 1; x < k; ++x) {
			out.append(glue).append(pieces[x]);
		}
		
		return out.toString();
	}
}