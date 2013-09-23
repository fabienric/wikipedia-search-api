package com.fortydegree.wikipedia;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.fortydegree.utils.parsing.FastMapHandler;
import com.fortydegree.utils.parsing.SimpleSaxParser;

public class WikipediaParser {

	private static class PageHandler extends FastMapHandler {
		public String getPageContent() {
			return current.get("rev");
		}
	}

	public static WikipediaParsingResult parse(InputStream file) throws SAXException, IOException {
		PageHandler handler = new PageHandler();
		SimpleSaxParser p = new SimpleSaxParser(file, handler);
		p.setEncoding("UTF-8");
		p.parse();
		String content = handler.getPageContent();

		if (content == null)
			return null;

		return new WikipediaParsingResult(content);
	}
}
