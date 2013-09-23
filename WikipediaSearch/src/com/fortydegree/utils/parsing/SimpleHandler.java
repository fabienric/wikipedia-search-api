package com.fortydegree.utils.parsing;


import org.xml.sax.Attributes;

public interface SimpleHandler {
	
	public void text(String name,String text);
	public void start(String name,Attributes attrs);
	public void end(String name);

}
