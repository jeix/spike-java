
public class DeprettyJson {

	private static final String CR = "\r";
	private static final String NL = "\n";
	private static final String CRNL = CR + NL;

	public static String depretty(String json) {
		int ver = 2;
		if (ver == 1) {
			return json.replaceAll("[\\r\\n]", "").replaceAll("(  )+", "").replaceAll(": ", ":");
		}
		return json.replaceAll("\\n(  )+", "").replaceAll("[\\r\\n]", "").replaceAll(": ", ":");
	}

	public static void main(String[] args) {

		{
			String json = "{" + CRNL
						+ "    \"values\": []" + CRNL
						+ "  }," + CRNL
						+ "  \"page\": 0," + CRNL
						+ "  \"pageSize\": 10" + CRNL
						+ "}";
			String res = depretty(json);
			System.out.println("["+res+"]");
				//-> [{"values":[]},"page":0,"pageSize":10}]
		}

		{
			String json = "{" + CRNL
						+ "  \"conditions\": {" + CRNL
						+ "    \"values\": [" + CRNL
						+ "      {" + CRNL
						+ "        \"key\": \"provOrgCodes\"," + CRNL
						+ "        \"value\": [" + CRNL
						+ "          \"A16\"," + CRNL
						+ "          \"A08\"" + CRNL
						+ "        ]" + CRNL
						+ "      }," + CRNL
						+ "      {" + CRNL
						+ "        \"key\": \"catCodes\"," + CRNL
						+ "        \"value\": []" + CRNL
						+ "      }," + CRNL
						+ "      {" + CRNL
						+ "        \"key\": \"bothProvOrgCodesAndCatCodes\"," + CRNL
						+ "        \"value\": \"Y\"" + CRNL
						+ "      }" + CRNL
						+ "    ]" + CRNL
						+ "  }," + CRNL
						+ "  \"page\": 0," + CRNL
						+ "  \"pageSize\": 10" + CRNL
						+ "}";
			String res = depretty(json);
			System.out.println("["+res+"]");
				//-> [{"conditions":{"values":[{"key":"provOrgCodes","value":["A16","A08"]},{"key":"catCodes","value":[]},{"key":"bothProvOrgCodesAndCatCodes","value":"Y"}]},"page":0,"pageSize":10}]
		}

		{
			String json = "{" + CRNL
						+ "    \"values\": [\"  \"]" + CRNL
						+ "  }," + CRNL
						+ "  \"page\": 0," + CRNL
						+ "  \"pageSize\": 10" + CRNL
						+ "}";
			String res = depretty(json);
			System.out.println("["+res+"]");
				//-> [{"values":["  "]},"page":0,"pageSize":10}]
		}
	}
}
