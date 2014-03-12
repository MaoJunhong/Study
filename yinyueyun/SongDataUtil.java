package com.yeetrack.yinyueyun;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-10-24
 * Time: 下午11:47
 * To change this template use File | Settings | File Templates.
 * 解析包含音乐格式、码率的文本，返回码率和对应的格式
 */
public class SongDataUtil
{
    public static Map<Integer, String> getRateAndFormat(String jsonResult)
    {
        int start = jsonResult.indexOf("data");
        if(start == -1)
            return null;

        String[] strings = jsonResult.substring(start+4).split("\"rate\":");
        if(strings.length < 2)
            return null;
        Map<Integer, String> songDataMap = new TreeMap<Integer, String>();
        for(int i=1;i<= strings.length-1;i++)
        {
            String temp = strings[i].trim();
            //取出码率
            int rateEnd = temp.indexOf(",");
            String rate = temp.substring(0, rateEnd);
            //取出格式
            int formatStart = temp.indexOf("\"format\" : \"");
            int formatEnd = temp.indexOf("\"", formatStart+12);
            String format = temp.substring(formatStart+12, formatEnd);
            songDataMap.put(Integer.decode(rate), format);
        }

        return songDataMap;
    }
}
