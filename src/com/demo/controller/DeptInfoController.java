package com.demo.controller;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.demo.model.dao.DBAccess;
import com.demo.model.service.DeptInfoService;
import com.demo.model.vo.Staff;

@Path("/")
public class DeptInfoController {

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
		DeptInfoService deptInfo = new DeptInfoService();
		
		Map<String, Object> resultMap = deptInfo.getStaff(id, null, null);
		
		return Response.ok(resultMap).build();
	}
	
	@GET
	@Path("/staff/{id}/{limit}/{pageSize}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaffByIdWithPage(@PathParam("id") String id
			,@PathParam("limit") String limit
			,@PathParam("pageSize") String pageSize) {
		DeptInfoService deptInfo = new DeptInfoService();
		
		Map<String, Object> resultMap = deptInfo.getStaff(id, limit, pageSize);
		
		return Response.ok(resultMap).build();
	}

	@PUT
	@Path("/staff")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setStaff(Staff staff) {
		DeptInfoService deptInfo = new DeptInfoService();
		
		Map<String, Object> resultMap = deptInfo.setStaff(staff);
        
		return Response.ok(resultMap).build();
		
	}
	
	@DELETE
	@Path("/staff/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStaffById(@PathParam("id") String id) {
		DeptInfoService deptInfo = new DeptInfoService();
		
		Map<String, Object> resultMap = deptInfo.deleteStaff(id);
		
		return Response.ok(resultMap).build();
	}
	
}
