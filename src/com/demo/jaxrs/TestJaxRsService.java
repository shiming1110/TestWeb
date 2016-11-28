package com.demo.jaxrs;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.demo.model.DeptInfo;
import com.demo.model.Staff;
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
	@Path("/staff")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaff(){
		return getStaffById("");
	}

	@GET
	@Path("/staff/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaffById(@PathParam("id") String id) {
		DeptInfo deptInfo = new DeptInfo();
		
		JSONObject resultObj = deptInfo.getStaff(id, null, null);
		
		return Response.ok(resultObj.toString()).build();
	}
	
	@GET
	@Path("/staff/{id}/{limit}/{pageSize}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaffByIdWithPage(@PathParam("id") String id
			,@PathParam("limit") String limit
			,@PathParam("pageSize") String pageSize) {
		DeptInfo deptInfo = new DeptInfo();
		
		JSONObject resultObj = deptInfo.getStaff(id, limit, pageSize);
		
		return Response.ok(resultObj.toString()).build();
	}

	@PUT
	@Path("/staff")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setStaff(Staff staff) {
		String sql = "insert into staff values (?,?,?,?,?,?,?,?,?);";
		int rc;
		String msg;
		List<String> arrList = new ArrayList<String>();
		arrList.add(staff.getId());
		arrList.add(staff.getName());
		arrList.add(staff.getEmail());
		arrList.add(staff.getNotesId());
		arrList.add(staff.getDepartment());
		arrList.add(staff.getBand());
		arrList.add(staff.getJobRole());
		arrList.add(staff.getExternalTel());
		arrList.add(staff.getMobileTel());
		rc = SqlHelper.executeUpdate(sql, arrList.toArray(new String[0]));

        JSONObject jo = new JSONObject();
        
        if (rc > 0){
        	msg = "*更新成功!";
        }else{
        	msg = "*更新失败!";
        }

        jo.element("code", rc);
        jo.element("msg", msg);
        
		return Response.ok(jo.toString()).build();
		
	}
	
}
