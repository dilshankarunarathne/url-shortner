package adfly;

public class AdflyApiExample {
	public static void main(String [] args) {
		AdflyApiWrapper api = new AdflyApiWrapper();
		
		// Url Groups examples.
//		System.out.println(formatJson(api.getGroups()));
		
		// Expand examples.
//		System.out.println(formatJson(api.expand(
//			new String[] {"http://adf.ly/D", "http://adf.ly/E", "http://q.gs/4"},
//			new String[] {"3", "1A", "1C"}))
//		);
//		System.out.println(formatJson(api.expand(null, new String[] {"1F"})));
		
		// Shorten examples.
//		System.out.println(formatJson(api.shorten(
//			new String[] {"http://docs.python.org/library/json.html", "http://www.doughellmann.com/PyMOTW/hmac/"},
//			null, "banner", 13L
//		)));
		//System.out.println(formatJson(api.shorten(
		//	new String[] {"http://rest.elkstein.org/2008/02/using-rest-in-java.html"},
		//	"foo.barz", "banner", null
		//)));
		//System.out.println(formatJson(api.shorten("http://stackoverflow.com/questions/724043/http-url-address-encoding-in-java")));
		
		// Url examples.
		//System.out.println(formatJson(api.getUrls()));
		System.out.println(formatJson(api.getUrls(1, "htmlbook")));
		//System.out.println(formatJson(api.updateUrl(136L, null, null, "一些中国", null, " опи +сание для фейсбука", "FB i+mage")));
		//System.out.println(formatJson(api.updateUrl(136L, null, "banner", null, 13L, null, null)));
		//System.out.println(formatJson(api.deleteUrl(136L)));
	}
	
	private static String formatJson(String jsonStr) {
		StringBuilder sb = new StringBuilder();
		int jsonStrLen = jsonStr.length();
		
		int indentLevel = 0;
		for (int i = 0; i < jsonStrLen; i++) {
			char c = jsonStr.charAt(i);
			
			if ('{' == c) {
				indentLevel++;
			} else if ('}' == c) {
				indentLevel--;
			}
			
			if ('{' == c || ',' == c) {
				sb.append(c);
				sb.append("\n");
				for (int j = 0; j < indentLevel; j++) {
					sb.append("\t");
				}
			} else if ('}' == c) {
				sb.append("\n");
				for (int j = 0; j < indentLevel; j++) {
					sb.append("\t");
				}
				sb.append(c);
			} else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
}