package factorylocaldictquery.impl;

import java.util.concurrent.ConcurrentHashMap;

import dictquery.query.QueryService;

public class FALocalDictQuery implements QueryService {

	private static final ConcurrentHashMap<String, String> dict = new ConcurrentHashMap<>();
	static {
		dict.put("test1", "fa_testing1");
		dict.put("test2", "fa_testing2");
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
