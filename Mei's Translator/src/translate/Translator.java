package translate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

/**
 * @author Zhang Liang
 * 
 *         2013-9-25
 */
public class Translator {

	private static String url = "http://openapi.baidu.com/public/2.0/bmt/translate";
	// private static String api_key = "KduRfVzitY1q3ZmzbewnePHp";
	private static String api_key = "LpCUjbQOLyiOuYp3HGaQO13j";

	public static String Translate(String from, String to, String ques) {
		if (ques.compareTo("") == 0 | ques == null) {
			return "";
		}

		ques = ques.replaceAll("\r\n", "#");

		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		new BasicNameValuePair("from", from);
		formParams.add(new BasicNameValuePair("from", from));
		formParams.add(new BasicNameValuePair("to", to));
		formParams.add(new BasicNameValuePair("client_id", api_key));
		formParams.add(new BasicNameValuePair("q", ques));

		String str = "";
		try {
			HttpPost post = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
			post.setEntity(entity);

			HttpClient client = HttpClients.createDefault();
			HttpResponse response = client.execute(post);

			str = EntityUtils.toString(response.getEntity());
			// System.out.println(str);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		BaiduTrans bt = gson.fromJson(str, BaiduTrans.class);
		String res = "";
		for (TransResult tr : bt.getTrans_result()) {
			res += tr.getDst();
		}

		res = res.replaceAll("#", "\r\n");
		// System.out.println(from + "->" + to + ": ");
		// System.out.println("ques: " + ques);
		// System.out.println("result: " + res);
		return res;
	}

	public static void main(String[] args) {
		System.out.println(Translator.Translate("en", "zh", "Thank you"));
	}

}