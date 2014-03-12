/**
 * 
 */
package com.yeetrack.yinyueyun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;

/**
 * @author victor
 * 
 */
public class MusicDownload {
	public static void main(String[] args) throws Exception {
		PropertyConfigurator
				.configure("/Users/swrd/Workspaces/workspace_test/HttpsTest/conf/log4j.properties");

		// 获取音乐id列表
		List<String> idList = new ArrayList<String>();

		CloseableHttpClient httpClient = HttpTool.getHttpClient();

		HttpGet get = new HttpGet(
				"http://yinyueyun.baidu.com/data/cloud/collection?type=song&start=0&size=2000&_="
						+ System.currentTimeMillis());

		CloseableHttpResponse response = httpClient.execute(get);

		String result = EntityUtils.toString(response.getEntity(), "utf-8");
		get.releaseConnection();

		StringBuffer songIds = new StringBuffer();
		int start = result.indexOf("\"id\":");
		int end = -1;
		if (start != -1)
			end = result.indexOf(",", start);
		while (start != -1 && end != -1) {
			songIds.append(result.substring(start + 5, end) + ",");
			idList.add(result.substring(start + 5, end));
			start = result.indexOf("\"id\":", end);
			if (start != -1)
				end = result.indexOf(",", start);
		}
		String songidString = songIds.substring(0, songIds.length() - 1);

		// 获取音乐的详细信息
		HttpPost songInfoPost = new HttpPost(
				"http://yinyueyun.baidu.com/data/cloud/songinfo");
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("songIds", songidString));
		params.add(new BasicNameValuePair("type", ""));
		params.add(new BasicNameValuePair("rate", ""));
		params.add(new BasicNameValuePair("pt", "0"));
		params.add(new BasicNameValuePair("flag", ""));
		params.add(new BasicNameValuePair("s2p", ""));
		params.add(new BasicNameValuePair("prerate", ""));
		params.add(new BasicNameValuePair("bwt", ""));
		params.add(new BasicNameValuePair("dur", ""));
		params.add(new BasicNameValuePair("bat", ""));
		params.add(new BasicNameValuePair("bp", ""));
		params.add(new BasicNameValuePair("pos", ""));
		params.add(new BasicNameValuePair("auto", ""));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params,
				"utf-8");
		songInfoPost.setEntity(formEntity);
		CloseableHttpResponse songInfoResponse = httpClient
				.execute(songInfoPost);
		String jsonResult = EntityUtils.toString(songInfoResponse.getEntity());
		songInfoPost.releaseConnection();

		SongInfoEntity songInfoEntity = JSON.parseObject(jsonResult,
				SongInfoEntity.class);

		// 下载音乐
		HttpGet songGet = null;
		for (SongEntity song : songInfoEntity.getData().getSongList()) {

			// 需要根据song id来获取每首歌曲存在的码率
			HttpGet songRateGet = new HttpGet(
					"http://yinyueyun.baidu.com/data/cloud/download?songIds="
							+ song.getSongId());
			CloseableHttpResponse songRateResponse = httpClient
					.execute(songRateGet);
			Map<Integer, String> rateMap = SongDataUtil
					.getRateAndFormat(EntityUtils.toString(songRateResponse
							.getEntity()));
			if (rateMap == null || rateMap.size() == 0)
				continue;
			// 可以遍历treemap，来下载多种格式的音乐，这里只下载最优质的
			Integer rateMax = null;
			String format = null;
			Iterator<Integer> it = rateMap.keySet().iterator();
			while (it.hasNext()) {
				// it.next()得到的是key，tm.get(key)得到obj
				rateMax = it.next();
			}
			format = rateMap.get(rateMax);

			// 开始下载音乐
			// http://yinyueyun.baidu.com/data/cloud/downloadsongfile?songIds=1239120&rate=320&format=mp3
			String url = "http://yinyueyun.baidu.com/data/cloud/downloadsongfile?songIds="
					+ song.getSongId()
					+ "&rate="
					+ rateMax.toString()
					+ "&format=" + format;
			while (true) {
				System.err.println(url);
				songGet = new HttpGet(url);
				songGet.addHeader(
						"User-Agent",
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");

				CloseableHttpResponse songResponse = httpClient
						.execute(songGet);

				if (songResponse.getStatusLine().toString().contains("302")) {
					// 获取重定向url,该url是最终的音乐资源地址
					for (Header header : songResponse.getAllHeaders()) {
						if ("Location".equals(header.getName())) {
							url = header.getValue();
						}
					}
				} else if (songResponse.getStatusLine().toString()
						.contains("200")) {
					break;
				}
				songGet.releaseConnection();
			}

			InputStream inputStream = null;
			FileOutputStream fileOutputStream = null;

			HttpGet mp3Get = new HttpGet(url);
			CloseableHttpResponse mp3Response = httpClient.execute(mp3Get);

			System.out.println(url + "---");
			System.out.println(song.getSongName() + "--->正在下载...");
			inputStream = mp3Response.getEntity().getContent();
			fileOutputStream = new FileOutputStream(new File(
					"/Users/swrd/Downloads/music/" + song.getSongName() + "-"
							+ song.getArtistName() + "." + format));

			byte[] bytes = new byte[102400];
			int len = 0;
			while ((len = inputStream.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, len);
				fileOutputStream.flush();
			}

			System.out.println(song.getSongName() + "---下载完毕");
			fileOutputStream.close();
			// songGet.reset();

		}
		httpClient.close();

	}
}
