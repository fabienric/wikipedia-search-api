package com.fortydegree.wikipedia;

public class WikipediaParsingResult {

	public boolean redirect = false;
	public String content;

	public WikipediaParsingResult(String content) {
		if (content.startsWith("#REDIRECT")) {
			redirect = true;
			int i = content.indexOf("[[");
			int j = content.indexOf("]]");
			this.content = content.substring(i+2, j);
		} else {
			redirect = false;
			this.content = content;
		}
	}
}
