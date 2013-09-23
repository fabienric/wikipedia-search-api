package com.fortydegree.wikipedia;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.fortydegree.utils.conversion.Converters;

public class WebService {

	protected String endpoint;

	public WebService(String endpoint) {
		this.endpoint = endpoint;
	}

	public InputStream get(List<String> parameters) throws WebServiceException {
		try {
			String urlString = getURL(parameters);
			URL url = new URL(urlString);
			return url.openStream();

		} catch (Exception e) {
			throw new WebServiceException(e);
		}
	}

	public String getURL(List<String> parameters) throws WebServiceException {
		if (null == endpoint || endpoint.isEmpty()) {
			throw new WebServiceException("Endpoint must not be empty");
		}

		if (null != parameters && parameters.size() % 2 > 0) {
			throw new WebServiceException("The number of parameters must be even");
		}

		StringBuffer url = new StringBuffer();
		url.append(endpoint);
		if (null != parameters) {
			url.append("?");
			for (int i = 0; i < parameters.size(); i += 2) {
				if (i > 0) {
					url.append("&");
				}
				url.append(parameters.get(i));
				url.append("=");
				url.append(parameters.get(i + 1));
			}
		}

		return Converters.URL.encode(url.toString());
	}

}
