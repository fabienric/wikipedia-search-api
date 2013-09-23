package com.fortydegree.utils.conversion;

public class URLConversion extends StringConversion {

	@Override
	protected String convert(char c) {
		return "%" + Integer.toHexString((int) c);
	}

	@Override
	protected boolean isNormal(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_' || c == '-'
				|| c == '=' || c == '?' || c == '!' || c == '.' || c == '~' || c == '\'' || c == '(' || c == ')'
				|| c == '*' || c == '&' || c == '#' || c == '/' || c == ':';
	}

}
