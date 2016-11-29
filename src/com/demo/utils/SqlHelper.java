package com.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SqlHelper{
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		
		String url = "jdbc:mysql://9.197.37.44:3306/jri?serverTimezone=UTC&useSSL=true" ;     
	    String username = "root" ;
	    String password = "password" ;
	    
//		String url = PropsUtils.getDBProperty("jdbc_url");
//	    String username = PropsUtils.getDBProperty("jdbc_username");
//	    String password = PropsUtils.getDBProperty("jdbc_password");
	    
	    Connection conn = null;
	    
	    int MAX_ERR_CNT = 10;
	    int errCnt = 0;
	    
	    while(errCnt < MAX_ERR_CNT){
		    try{    
		    	conn = DriverManager.getConnection(url , username , password ) ; 
//		    	System.out.println("DB connection success.");       
		    }catch(SQLException se){    
		    	System.out.println("数据库连接失败！");    
		    	se.printStackTrace() ;    
		    }
	        if (conn != null) {
	            break;
	        }else{
	        	errCnt ++ ;
	        	try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }

		return conn; 
	}
	
	public static boolean execSql(String sql){
		Connection con = null;
		Statement stmt = null;
		boolean rc = false;
		try {
			con = SqlHelper.getConnection();
			stmt = con.createStatement() ;
			rc = stmt.execute(sql);
			return rc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();

				if (con != null)
					con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static boolean hasRecord(String sql){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = SqlHelper.getConnection();
			stmt = con.createStatement() ;
			rs = stmt.executeQuery(sql);
			
			try {
				if (rs!=null && rs.next()) {
					return true;
				}

				else {
					return false;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();

				if (con != null)
					con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static int executeUpdate(String sqlString,String[] arrs) {
		Connection con = null;
		PreparedStatement perstmt = null;
		int rc = -1;
		System.out.println("SQL=" + sqlString);
		System.out.println("Parameter=" + Arrays.toString(arrs));
		try {
			con = SqlHelper.getConnection();
	        perstmt =  con.prepareStatement(sqlString);
	        for (int i=1; i < (arrs.length + 1) ; i++){
	        	perstmt.setString(i,arrs[i-1]);
	        }
			rc = perstmt.executeUpdate();
			return rc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (perstmt != null)
					perstmt.close();

				if (con != null)
					con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public static List<Map<Object,Object>> getList(String sqlString) throws SQLException {
		Connection con = null;
		
		con = SqlHelper.getConnection();
		System.out.println("SQL="+sqlString);
		
        Statement st = con.createStatement();
        ResultSet rSet = st.executeQuery(sqlString);
        ResultSetMetaData rData = rSet.getMetaData();
        List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
        while (rSet.next()) {
            Map<Object,Object> obj = new HashMap<Object,Object>();
            for (int i = 1; i <= rData.getColumnCount(); i++) {
                obj.put(rData.getColumnName(i).toLowerCase(), rSet.getObject(i));
            }
            list.add(obj);
        }
        rSet.close();

		if (con != null)
			con.close();
        System.out.println("[LOG:"+list.size()+"  row in list]");
        return list;
    }
	
	public static List<Map<Object,Object>> getList(String sqlString,String[] arrs) throws SQLException {
		Connection con = null;
		
		con = SqlHelper.getConnection();
		System.out.println("SQL="+sqlString);

        PreparedStatement perstmt =  con.prepareStatement(sqlString);
    	
        for (int i=1; i < (arrs.length + 1) ; i++){
        	perstmt.setString(i,arrs[i-1]);
        }

        ResultSet rSet = perstmt.executeQuery();
        ResultSetMetaData rData = rSet.getMetaData();
        List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
        while (rSet.next()) {
            Map<Object,Object> obj = new HashMap<Object,Object>();
            for (int i = 1; i <= rData.getColumnCount(); i++) {
                obj.put(rData.getColumnName(i).toLowerCase(), rSet.getObject(i));
            }
            list.add(obj);
        }
        rSet.close();

		if (con != null)
			con.close();
        System.out.println("[LOG:"+list.size()+"  row in list]");
        return list;
    }
	
	
	public static void main(String[] args){
		System.out.println(SqlHelper.execSql("create table dddd(gg varchar(10))"));
	}

	
}
