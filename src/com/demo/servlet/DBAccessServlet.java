package com.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DBAccessServlet
 */
//@WebServlet("/DBAccessServlet")
public class DBAccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/sinastock?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	
	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "password"; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBAccessServlet() {
        super();
    }
    
    public static String dbAccess(String id){
    	StringBuffer s = new StringBuffer();
    	if (id == null){
			id = "600000";
		}
		
		Connection conn = null;
		Statement stmt = null;

		String title = "Servlet Mysql 测试";
		String docType = "<!DOCTYPE html>\n";
		s.append(docType +
		"<html>\n" +
		"<head><title>" + title + "</title></head>\n" +
		"<body bgcolor=\"#f0f0f0\">\n" +
		"<h1 align=\"center\">" + title + "</h1>\n");
		try{
			// 注册 JDBC 驱动器
			Class.forName(JDBC_DRIVER);
			
			// 打开一个连接
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			// 执行 SQL 查询
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM sinastock.gsjj where gpdm = '"+ id +"';";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while(rs.next()){
				// 通过字段检索
				String  gpdm  = rs.getString("gpdm");
				String  gsmc  = rs.getString("gsmc");
				String  gsywmc  = rs.getString("gsywmc");
				String  sssc  = rs.getString("sssc");
				String  ssrq  = rs.getString("ssrq");
				String  fxjg  = rs.getString("fxjg");
				String  zcjs  = rs.getString("zcjs");
				String  clrq  = rs.getString("clrq");
				String  zczb  = rs.getString("zczb");
				String  jglx  = rs.getString("jglx");
				String  zzxs  = rs.getString("zzxs");
				String  dshms  = rs.getString("dshms");
				String  gsdh  = rs.getString("gsdh");
				String  dmdh  = rs.getString("dmdh");
				String  gscz  = rs.getString("gscz");
				String  dmcz  = rs.getString("dmcz");
				String  gsdzyx  = rs.getString("gsdzyx");
				String  dmdzyx  = rs.getString("dmdzyx");
				String  gswz  = rs.getString("gswz");
				String  yzbm  = rs.getString("yzbm");
				String  xxplwz  = rs.getString("xxplwz");
				String  zqjcgmls  = rs.getString("zqjcgmls");
				String  zcdz  = rs.getString("zcdz");
				String  bgdz  = rs.getString("bgdz");
				String  ggjj  = rs.getString("ggjj");
				String  jyfw  = rs.getString("jyfw");
				
				// 输出数据
				s.append("<br />股票代码: " + gpdm + "\n"+ "\n");
				s.append("<br />公司名称: " + gsmc+ "\n");
				s.append("<br />公司英文名称: " + gsywmc+ "\n");
				s.append("<br />上市市场: " + sssc+ "\n");
				s.append("<br />上市日期: " + ssrq+ "\n");
				s.append("<br />发行价格: " + fxjg+ "\n");
				s.append("<br />主承销商: " + zcjs+ "\n");
				s.append("<br />成立日期: " + clrq+ "\n");
				s.append("<br />注册资本: " + zczb+ "\n");
				s.append("<br />机构类型: " + jglx+ "\n");
				s.append("<br />组织形式: " + zzxs+ "\n");
				s.append("<br />董事会秘书: " + dshms+ "\n");
				s.append("<br />公司电话: " + gsdh+ "\n");
				s.append("<br />董秘电话: " + dmdh+ "\n");
				s.append("<br />公司传真: " + gscz+ "\n");
				s.append("<br />董秘传真: " + dmcz+ "\n");
				s.append("<br />公司电子邮箱: " + gsdzyx+ "\n");
				s.append("<br />董秘电子邮箱: " + dmdzyx+ "\n");
				s.append("<br />公司网址: " + gswz+ "\n");
				s.append("<br />邮政编码: " + yzbm+ "\n");
				s.append("<br />信息披露网址: " + xxplwz+ "\n");
				s.append("<br />证券简称更名历史: " + zqjcgmls+ "\n");
				s.append("<br />注册地址: " + zcdz+ "\n");
				s.append("<br />办公地址: " + bgdz+ "\n");
				s.append("<br />公司简介: " + ggjj+ "\n");
				s.append("<br />经营范围: " + jyfw+ "\n");
				s.append("<br />"+ "\n");
			}
				s.append("</body></html>"+ "\n");

			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch(Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}finally{
			// 最后是用于关闭资源的块
			try{
				if(stmt!=null)
				stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
				conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
    	
    	return s.toString();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String id = request.getParameter("id");
		if (id == null){
			
		}else{
			id = URLDecoder.decode(id,"UTF-8");
		}
		
		System.out.println("sessionid=" + request.getSession().getId());
		// 设置响应内容类型
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(dbAccess(id));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
