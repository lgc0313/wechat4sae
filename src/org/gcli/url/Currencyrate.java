package org.gcli.url;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class Currencyrate {


	public static ArrayList<String> getHtmlList(String url) {
		final ArrayList<String> result = new ArrayList<String>();
		Parser parser = null;
		NodeList nodeList = null;
		try {
			parser = new Parser(url);
			parser.setEncoding("GBK");
			nodeList = parser.parse(new NodeFilter() {
				public boolean accept(Node node) {
					Node need = node;
					if (getStringsByRegex(node.getText())) {
						result.add(need.toPlainTextString());
						return true;
					}
					return false;
				}
			});
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean getStringsByRegex(String txt) {
		String regex = "tr class='greybg'";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(txt);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
