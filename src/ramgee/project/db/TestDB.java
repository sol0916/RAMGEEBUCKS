package ramgee.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TestDB {
	
	public static void main(String[] args) {
		
		
		String url = DBProperties.URL;
	    String uid = DBProperties.UID;
	    String upw = DBProperties.UPW;
	    
	    try {
            Connection conn = DriverManager.getConnection(url, uid, upw);
            System.out.println("success");
        } catch (Exception e) {
            System.out.println("CLASS FOR NAME ERR");
            e.printStackTrace();
        }
	    
	}

}
