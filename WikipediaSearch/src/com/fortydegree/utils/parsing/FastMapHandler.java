package com.fortydegree.utils.parsing;


import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

public class FastMapHandler implements SimpleHandler{

	protected Map<String,String> current=new HashMap<String, String>();

	public void end(String name) {
		//nothing to do
	}

	public void start(String name, Attributes attrs) {
		for(int i=0;i<attrs.getLength();i++){
			current.put(attrs.getLocalName(i), attrs.getValue(i));
		}
	}

	public void text(String name, String text) {
		current.put(name, text);
	}

	
	
}
