package localdictquery.impl;

import java.util.concurrent.ConcurrentHashMap;

import dictquery.query.QueryService;

public class LocalDictQuery implements QueryService {

	private static final ConcurrentHashMap<String, String> dict = new ConcurrentHashMap<>();
	static {
		dict.put("test1", "testing1");
		dict.put("test2", "testing2");
	}

	@Override
	public String queryWord(String word) {
		String res = dict.get(word);
		if (res == null) {
			return "N/A";
		}
		
		return res;
	}

}
