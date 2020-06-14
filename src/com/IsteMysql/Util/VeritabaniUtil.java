package com.IsteMysql.Util;
import java.sql.*; //sql içindeki her þeyi buraya import ediyor.

public class VeritabaniUtil {
	static Connection conn=null;
	public static Connection Baglan() {
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/proje?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root","1234");
			
			return conn;
			
		} catch (Exception e) {
			//System.out.println(e.getMessage().toString());
			System.out.println("db hata");
			return null;	
		}
	}

}
