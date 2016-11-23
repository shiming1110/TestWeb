package com.demo.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
 
public class JsonUtil {
	
    public static void main(String[] args) {
    	Map m = new HashMap();
    	m.put("a", "123");
    	m.put("b", "345");
    	List list = new ArrayList();
    	list.add(m);
    	JSONObject jsonObject = JSONObject.fromObject(m);
		JSONArray jsonArray = JSONArray.fromObject(list);
    	System.out.println(jsonArray.toString());
    }
	
	public static JSONArray mapList2JSON(List<Map<Object, Object>> maps){
		
		//创建转换说明对象  
        JsonConfig config = new JsonConfig();  
          
        //设置java.sql.Timestamp对应的转换方法  
        config.registerJsonValueProcessor(java.sql.Timestamp.class,new JsonValueProcessor(){  
            @Override  
            public Object processArrayValue(Object value, JsonConfig config) {  
                return process(value);  
            }  
              
            @Override  
            public Object processObjectValue(String arg0, Object value,  
                    JsonConfig arg2) {  
                return process(value);  
            }  
              
            private Object process(Object value) {  
                try {  
                      
                    if (value instanceof java.sql.Timestamp) {  
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//                        System.out.println("sdf.format( value):"+sdf.format( value));  
                        return sdf.format( value);  
                    }  
                    return value == null ? "" : value.toString();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    return "";  
                }  

            }  
        }); 
        
		JSONArray jsonArray = JSONArray.fromObject(maps,config);

		return jsonArray;
	}
	
	
//	public static map2JSON(){
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		JSONObject jsonObject = JSONObject.fromObject(map);
//
//		System.out.println(jsonObject);
//	}
	

	
	
 
    @SuppressWarnings("rawtypes")
    public void test() {       
        Map m = this.testJson("jsonString");
        System.out.println(((Map) ((List) m.get("test")).get(0)).get("test_title"));
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map testJson(String str) {
        JSONObject json = JSONObject.fromObject(str);
        Iterator<?> it = json.keySet().iterator();
        Map map = new HashMap();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = json.getString(key);
            if (this.isString(value)) {
                map.put(key, value);
            }
            if (this.isJson(value)) {
                map.put(key, this.testJson(value));
            }
            if (this.isJsonArray(value)) {
                map.put(key, this.testJsonArray(value));
            }
 
        }
        return map;
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map parseJson(String str) {
        JSONObject json = JSONObject.fromObject(str);
        Iterator<?> it = json.keySet().iterator();
        Map map = new HashMap();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = json.getString(key);
            if (this.isString(value)) {
                map.put(key, value);
            }
            if (this.isJson(value)) {
                map.put(key, this.testJson(value));
            }
            if (this.isJsonArray(value)) {
                map.put(key, this.testJsonArray(value));
            }
 
        }
        return map;
    }
    
	public List testJsonArray(String str) {
        JSONArray jsonArr = JSONArray.fromObject(str);
        List list = new ArrayList();
        for (Object json : jsonArr) {
            String jsonStr = json.toString();
            if(this.isString(jsonStr)){
                list.add(jsonStr);
            }
            if(this.isJson(jsonStr)){
                list.add(this.testJson(jsonStr.toString()));
            }
            if(this.isJsonArray(jsonStr)){
                list.add(this.testJsonArray(jsonStr.toString()));
            }   
        }
        return list;
    }
 
    public boolean isJson(String s) {
        boolean flag = true;
        try {
            JSONObject.fromObject(s);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
 
    public boolean isJsonArray(String s) {
        boolean flag = true;
        try {
            JSONArray.fromObject(s);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
 
    public boolean isString(String s) {
        return !this.isJson(s) && !this.isJsonArray(s);
    }
 

 
}
