package com.fortydegree.wikipedia;


@SuppressWarnings("serial")
public class WebServiceException extends Exception {

	public WebServiceException(Exception e) {
		super(e);
	}

	public WebServiceException(String message) {
		super(message);
	}

}
