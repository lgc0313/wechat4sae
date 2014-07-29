package org.gcli.url;

public class StaticPage {
	public static String getStaticPage(String surl) {
		String htmlContent = "";
		try {
			java.io.InputStream inputStream;
			java.net.URL url = new java.net.URL(surl);
			java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url
					.openConnection();
			connection.connect();
			inputStream = connection.getInputStream();
			byte[] bytes = new byte[1024 * 2000];
			int index = 0;
			int count = inputStream.read(bytes, index, 1024 * 2000);
			while (count != -1) {
				index += count;
				count = inputStream.read(bytes, index, 1);
			}
			htmlContent = new String(bytes, "UTF-8");
			connection.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return htmlContent.trim();
	}

}
