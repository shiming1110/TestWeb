package com.demo.jaxrs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.demo.model.core.DBAccess;
import com.demo.utils.JsonUtil;
import com.demo.utils.SqlHelper;

import net.sf.json.JSONObject;

@Path("/")
public class TestJaxRsService {

	@GET
	@Path("/ping")
	public String ping() {
		return "pong";
	}

	@GET
	@Path("/query")
	public Response query() {
		return Response.ok(DBAccess.dbAccess(null)).build();
	}

	@GET
	@Path("/staff/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaff(@PathParam("id") String id) {
		String sql = "select Id,Name,Email,NotesId,Department,Band,JobRole,ExternalTel,MobileTel from staff";
		String sqlcount = "select count(Id) from staff";
		
		String condition = "";
		List<String> arrList = new ArrayList<String>();
		if (id ==null || id.trim().equals("")){
			
		}else{
//			condition = " where id like '%" + id.trim() + "%'";
			condition = " where id like ?";
			arrList.add("%"+ id.trim()+"%");
		}
		
		sql += condition;
		sqlcount += condition;
		
		List<Map<Object, Object>> rl = new ArrayList<Map<Object, Object>>();
		List<Map<Object, Object>> rc = new ArrayList<Map<Object, Object>>();
		try {
//			rl = SqlHelper.getList(sql);
			rl = SqlHelper.getList(sql,arrList.toArray(new String[0]));
			
			rc  = SqlHelper.getList(sqlcount,arrList.toArray(new String[0]));
		} catch (SQLException e) {
			e.printStackTrace();
		}

//		for (Object o : rl) {
//			System.out.println(o.toString());
//		}

        JSONObject jo = new JSONObject();
        
        jo.element("dataCount", ((Map)rc.get(0)).values().toArray()[0]);
        jo.element("data", JsonUtil.mapList2JSON(rl));
        
		return Response.ok(jo.toString()).build();
	}

}
