package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Exceptions.NotNull;
import Model.ConectorBBDD;
import Model.Departamento;

public class DepartamentoDAO implements InterfaceDAO<Departamento> {
	
	private static final String SQL_CREATE="CREATE TABLE DEPARTAMENTOS "
										+ "(COD VARCHAR(5) PRIMARY KEY, "
										+ "NOMBRE VARCHAR(20) NOT NULL)";
	private static final String SQL_INSERT="INSERT INTO DEPARTAMENTOS "
										+ "(COD, NOMBRE) "
										+ "VALUES(?,?)";
	private static final String SQL_DELETE="DELETE FROM DEPARTAMENTOS "
					 				 	+ "WHERE COD = ?";
	private static final String SQL_UPDATE="UPDATE DEPARTAMENTOS "
				     					+ "NOMBRE = ? "
				     					+ "WHERE COD = ?";
	private static final String SQL_READ="SELECT * FROM DEPARTAMENTOS WHERE COD = ?";
	private static final String SQL_READALL="SELECT * FROM DEPARTAMENTOS";
	
	private static final String SQL_BUSCAR_COD_DE_NAME="SELECT COD FROM DEPARTAMENTOS WHERE NOMBRE = ?";
	
	private static final ConectorBBDD cnn=ConectorBBDD.saberEstado();//aplicamos Singleton

	@Override
	public boolean create() throws SQLException{
		PreparedStatement ps=null;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_CREATE);
			if(ps.executeUpdate()>0){
				return true;
			}
		}finally{
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return false;
	}
	@Override
	public boolean insert(Departamento c) throws SQLException {
		PreparedStatement ps=null;
		try{
		ps=cnn.getConexion().prepareStatement(SQL_INSERT);
		ps.setString(1, c.getCod());
		ps.setString(2, c.getName());
		
		if(ps.executeUpdate()>0){
			return true;
		}
		}finally{
			if(ps!=null)ps.close();
			cnn.cerrarConexion();//cerrar conexion al final de la instrucción si o si
		}	
		return false;
	}

	@Override
	public boolean delete(Object key) throws SQLException {
		PreparedStatement ps=null;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);
			ps.setString(1, key.toString());
			if(ps.executeUpdate()>0){
				return true;
			}
		}finally{
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean update(Departamento c) throws SQLException {
		PreparedStatement ps=null;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getName());
			ps.setString(2, c.getCod());
			if(ps.executeUpdate()>0){
				return true;
			}
		}finally{
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public Departamento read(Object key) throws SQLException, NotNull {
		PreparedStatement ps=null;
		Departamento d=null;
		ResultSet r=null;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			r=ps.executeQuery();
			while(r.next()){
				d= new Departamento(r.getString(1), r.getString(2));
			}
		}finally{
			if(r!=null)r.close();
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return d;
	}

	@Override
	public ArrayList<Departamento> readAll() throws SQLException, NotNull {
		PreparedStatement ps=null;
		ArrayList<Departamento> array=null;
		ResultSet r=null;
		try{
			array=new ArrayList<Departamento>();
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new Departamento(r.getString(1), r.getString(2)));
			}			
		}finally{
			if(r!=null)r.close();
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return array;
	}
	public boolean exist(){		
		PreparedStatement ps=null;
		ResultSet r=null;
		boolean existe=false;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
			if(ps==null){return existe;}
			r=ps.executeQuery();			
			existe=true;//si llega ha esta linea es pq ha encontrado la tabla en la bbdd si no pasara al bloque catch retornando false
			
		} catch (SQLException e) {
			return existe;
		}finally{
				try {
					if(ps!=null)ps.close();
					if(r!=null)r.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}		
		}
		return existe;
	}
//
//metodos que no carga la interficie
//	
	//metodo que retorna el codigo de un determinado nombre de departamento. 
	public String buscarCodDeUnNombre(String name_departameto) throws SQLException{
		PreparedStatement ps=null;
		ResultSet r=null;
		String resultado="";
		try{
			ps=cnn.getConexion().prepareStatement(SQL_BUSCAR_COD_DE_NAME);
			ps.setString(1, name_departameto);
			r=ps.executeQuery();
			while(r.next()){
				resultado=r.getString(1);
			}
		}finally{
			if(r!=null)r.close();
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		
		return resultado;
	}
	
}
