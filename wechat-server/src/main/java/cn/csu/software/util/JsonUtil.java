package cn.csu.software.util;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/27
 */

import com.alibaba.fastjson.JSONObject;

/**
 * @title : cn.csu.software.util
 * @author : oranges
 * @date : 2019/10/27 15:51
 * @description : 
 */
public class JsonUtil {

    public static String toJson(Object object) {
        String json = JSONObject.toJSONString(object);
        return json;
    }
}
