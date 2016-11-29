package com.demo.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.utils.SqlHelper;

public class DeptInfo {
	

	public Map<String, Object> getStaff(String id,String limit,String pageSize) {
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

        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("dataCount", ((Map)rc.get(0)).values().toArray()[0]);
        resultMap.put("data", rl);

        
		return resultMap;
	}

	
	public Map<String, Object> setStaff(Staff staff) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
		String id = staff.getId();
		
		List<String> arrListId = new ArrayList<String>();
		if (id ==null || id.trim().equals("")){
			return resultMap;
		}else{
			arrListId.add(id.trim());
		}
		
		String sqlcount = "select count(Id) from staff where id=?";
		String insertSql = "insert into staff values (?,?,?,?,?,?,?,?,?);";
		String updateSql = "update staff set"
				+ " Name=?,Email=?,NotesId=?,Department=?,Band=?,JobRole=?,ExternalTel=?,MobileTel=?"
				+ " where id=?;";

		List<Map<Object, Object>> countList = new ArrayList<Map<Object, Object>>();
		try {
			countList  = SqlHelper.getList(sqlcount,arrListId.toArray(new String[0]));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int count = Integer.valueOf(String.valueOf(((Map)countList.get(0)).values().toArray()[0]));
		
		String invokeSql;

		List<String> arrList = new ArrayList<String>();
		
		if (count > 0){
			invokeSql= updateSql;

			arrList.add(staff.getName());
			arrList.add(staff.getEmail());
			arrList.add(staff.getNotesId());
			arrList.add(staff.getDepartment());
			arrList.add(staff.getBand());
			arrList.add(staff.getJobRole());
			arrList.add(staff.getExternalTel());
			arrList.add(staff.getMobileTel());
			arrList.add(staff.getId());
			
		}else{
			invokeSql= insertSql;
			
			arrList.add(staff.getId());
			arrList.add(staff.getName());
			arrList.add(staff.getEmail());
			arrList.add(staff.getNotesId());
			arrList.add(staff.getDepartment());
			arrList.add(staff.getBand());
			arrList.add(staff.getJobRole());
			arrList.add(staff.getExternalTel());
			arrList.add(staff.getMobileTel());
		}
		
		int rc;
		String msg = "";
		
		rc = SqlHelper.executeUpdate(invokeSql, arrList.toArray(new String[0]));
        

    	if(invokeSql.equals(updateSql)){
            if (rc > 0){
            	msg = "*更新成功!";
            }else{
            	msg = "*更新失败!";
            }
    	}else if(invokeSql.equals(insertSql)){
            if (rc > 0){
            	msg = "*追加成功!";
            }else{
            	msg = "*追加失败!";
            }
    	}
        
        resultMap.put("code", rc);
        resultMap.put("msg", msg);

		return resultMap;
		
	}
	
	public Map<String, Object> deleteStaff(String id) {
		String sql = "delete from staff where id=?;";
		
		try {
			id = URLDecoder.decode(id,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int rc;
		String msg;
		List<String> arrList = new ArrayList<String>();
		arrList.add(id);
		
		rc = SqlHelper.executeUpdate(sql, arrList.toArray(new String[0]));
        
        if (rc > 0){
        	msg = "*删除成功!";
        }else{
        	msg = "*删除失败!";
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("code", rc);
        resultMap.put("msg", msg);

		return resultMap;
		
	}
	
}
