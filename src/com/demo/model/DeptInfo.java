package com.demo.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.demo.utils.JsonUtil;
import com.demo.utils.SqlHelper;

import net.sf.json.JSONObject;

public class DeptInfo {

	public JSONObject getStaff(String id,String limit,String pageSize) {
		String sql = "select Id,Name,Email,NotesId,Department,Band,JobRole,ExternalTel,MobileTel from staff";
		String sqlcount = "select count(Id) from staff";

		
		try {
			id = URLDecoder.decode(id,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String condition = "";
		List<String> arrList = new ArrayList<String>();
		if (id ==null || id.trim().equals("")){
			
		}else{
			condition = " where id like ?";
			arrList.add(id.trim());
		}
		
		
        if("".equals(limit) || limit == null || "NaN".equals(limit)){
        	limit = "0";
        } 
        
        if("".equals(pageSize) || pageSize == null || "NaN".equals(pageSize)){
        	pageSize = "20";
        } 
        
        String sqlLimit = " limit " + limit + "," + pageSize;
		
		sql = sql + condition + sqlLimit;
		sqlcount += condition;
		
		List<Map<Object, Object>> rl = new ArrayList<Map<Object, Object>>();
		List<Map<Object, Object>> rc = new ArrayList<Map<Object, Object>>();
		try {
			rl = SqlHelper.getList(sql,arrList.toArray(new String[0]));
			
			rc  = SqlHelper.getList(sqlcount,arrList.toArray(new String[0]));
		} catch (SQLException e) {
			e.printStackTrace();
		}

        JSONObject jo = new JSONObject();
        
        jo.element("dataCount", ((Map)rc.get(0)).values().toArray()[0]);
        jo.element("data", JsonUtil.mapList2JSON(rl));
        
		return jo;
	}
	
	
}
