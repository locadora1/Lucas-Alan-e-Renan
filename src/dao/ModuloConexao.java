package dao;

import java.sql.*;

public class ModuloConexao {
	
	public static Connection conector() {
		java.sql.Connection conexao = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/locadora";
		String user = "root";
		String password = "rootroot";
		
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			System.out.println(conexao);
			return(conexao);
			
		} catch (Exception e) {
			System.out.println(e);
			return(null);
		}
	}
	
}


