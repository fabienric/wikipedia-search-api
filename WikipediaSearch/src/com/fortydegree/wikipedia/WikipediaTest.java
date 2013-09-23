package com.fortydegree.wikipedia;

import java.io.UnsupportedEncodingException;

public class WikipediaTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		if (args.length == 2) {
			// the endpoint, for example: http://fr.wikipedia.com/w/api.php
			String endpoint = args[0];
			// search term, for example: api
			String searchTerm = args[1];

			WikipediaClient wikipedia = new WikipediaClient(endpoint);
			String htmlPage = wikipedia.getPage(searchTerm);

			System.out.println(htmlPage);
			System.out.println(new String(htmlPage.getBytes("UTF-8"), "UTF-8"));
		}
	}
}
