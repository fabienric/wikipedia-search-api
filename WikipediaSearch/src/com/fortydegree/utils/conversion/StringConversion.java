package com.fortydegree.utils.conversion;

public abstract class StringConversion {

	public StringConversion() {

	}

	public String encode(String str) {
		if (str == null)
			return null;
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (isNormal(c))
				result += c;
			else
				result += convert(c);
		}
		return result;
	}

	protected abstract String convert(char s);

	protected abstract boolean isNormal(char c);

}
