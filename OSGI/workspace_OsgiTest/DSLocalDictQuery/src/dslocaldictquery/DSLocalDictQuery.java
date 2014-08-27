package dslocaldictquery;

import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.ComponentContext;

import dictquery.query.QueryService;

public class DSLocalDictQuery implements QueryService {

	private static final ConcurrentHashMap<String, String> dict = new ConcurrentHashMap<>();
	static {
		dict.put("test1", "ds_testing1");
		dict.put("test2", "ds_testing2");
	}

	@Override
	public String queryWord(String word) {
		String res = dict.get(word);
		if (res == null) {
			return "N/A";
		}

		return res;
	}

	protected void activate(ComponentContext context) {
		System.out.println("DSLocalDictQuery activate");
	}

	public void deactivate(ComponentContext context) throws Exception {
		System.out.println("DSLocalDictQuery deactivate");
	}
}
