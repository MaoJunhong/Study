package dictqueryenter.tracker;

import org.osgi.service.component.ComponentContext;

import dictquery.query.QueryService;

public class LookupQueryService {
	
	public void activate(ComponentContext ctxt) {
		QueryService dsQs = (QueryService)ctxt.locateService("dsQs");
		if (dsQs != null) {
			System.out.println(String.format("look up ds querry %s, res: %s", "test1",
					dsQs.queryWord("test1")));
		} else {
			System.out.println("look up dsQs = null");
		}
	}
}
