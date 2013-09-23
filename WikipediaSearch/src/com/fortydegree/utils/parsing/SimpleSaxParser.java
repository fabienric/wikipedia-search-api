package com.fortydegree.utils.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class SimpleSaxParser extends DefaultHandler {

	protected InputSource sourceToParse;
	protected StringBuffer currentString = new StringBuffer(); // use to store
																// text nodes

	protected boolean validating = false;
	protected SimpleHandler h;

	public SimpleSaxParser(String file, SimpleHandler h) {
		this.h = h;
		try {
			sourceToParse = new InputSource(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public SimpleSaxParser(File inputToBeParsed, SimpleHandler h) throws FileNotFoundException {
		this(new FileInputStream(inputToBeParsed), h);
	}

	public SimpleSaxParser(InputStream inputToBeParsed, SimpleHandler h) {
		this.h = h;
		sourceToParse = new InputSource(inputToBeParsed);
	}

	public void setEncoding(String encoding) {
		sourceToParse.setEncoding(encoding);
	}

	public void parse() throws SAXException, IOException {
		if (getJavaVersion() < 1.5) {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(this.validating);
			SAXParser saxParser;
			try {
				saxParser = factory.newSAXParser();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				throw new Error(e);
			}
			saxParser.parse(sourceToParse, this);
		} else {// >1.5

			XMLReader xr = XMLReaderFactory.createXMLReader();

			xr.setContentHandler(this);
			if (!validating) {
				xr.setFeature("http://xml.org/sax/features/validation", false);// no
																				// validation
				xr.setFeature("http://apache.org/xml/features/validation/dynamic", false);// no
																							// validation
				xr.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			}

			xr.parse(sourceToParse);
		}

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		String name = localName;
		if (name.length() == 0)
			name = qName;
		name = name.toLowerCase();

		currentString = new StringBuffer();

		// call the method on the current handler
		h.start(name, attributes);

	}

	public void characters(char[] chars, int start, int end) throws SAXException {
		currentString.append(new String(chars, start, end));
	}

	// remove tabs, and transform multiples spaces in only one
	// it uses a simple automate of 2 states
	// is also remove all return \n and \r
	private String transform(StringBuffer value) {
		StringBuffer newString = new StringBuffer();
		int state = 0;// state of the automate
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (state) {
			case 0:
				if (c == ' ') {
					state = 1;
					newString.append(c);
				} else if (c == '\t' || c == '\r' || c == '\n') {
					// state=0;
				} else {
					// state=0;
					newString.append(c);
				}
				break;
			case 1:
				if (c == ' ') {
					// state=1;
				} else if (c == '\t' || c == '\r' || c == '\n') {
					// state=1;
				} else {
					state = 0;
					newString.append(c);
				}
				break;
			}
		}
		if (newString.length() == 1 && newString.charAt(0) == ' ')// only 1 ' '
			return "";
		return newString.toString();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		String name = localName;
		if (name.length() == 0)
			name = qName;
		name = name.toLowerCase();

		h.end(name);

		// try to call a text method
		String text = transform(currentString);

		h.text(name, text);
		currentString = new StringBuffer();

	}

	public void setValidating(boolean validating) {
		this.validating = validating;
	}

	public static double getJavaVersion() {
		String version = System.getProperty("java.specification.version");
		return Double.parseDouble(version);
	}
}
