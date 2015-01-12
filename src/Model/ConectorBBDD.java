package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConectorBBDD {
	
	public static ConectorBBDD instancia; //aplicar Singleton
	private static Connection conexion;
	
	public static Connection getConexion() {
		return conexion;
	}
	private ConectorBBDD(){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");//Driver
			
			conexion=DriverManager.getConnection("jdbc:mysql://localhost:8889/uf6_1-Ex1","root","root");
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			System.out.println("error 1");
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("error 2");
		}
	}

	
	public synchronized static ConectorBBDD saberEstado(){//singleton
		//la unica forma de hacer una conexion es invocando a este metodo
		if(instancia==null){
			instancia=new ConectorBBDD();
			
		}
		return instancia;
	}
	public static void cerrarConexion(){
		if(instancia!=null)
			instancia=null;
	}

}
