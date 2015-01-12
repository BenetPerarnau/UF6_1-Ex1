package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.ConectorBBDD;
import Model.Departamento;

public class DepartamentoDAO implements InterfaceDAO<Departamento> {
	
	private static final String SQL_CREATE="CREATE TABLE DEPARTAMENTOS "
										+ "(COD VARCHAR(5), PRIMARY KEY(COD), NOMBRE VARCHAR(20) NOT NULL)";
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
	
	private static final ConectorBBDD cnn=ConectorBBDD.saberEstado();//aplicamos Singleton

	@Override
	public boolean create() throws SQLException{
		PreparedStatement ps;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_CREATE);
			if(ps.executeUpdate()>0){
				return true;
			}
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}
	@Override
	public boolean insert(Departamento c) throws SQLException {
		PreparedStatement ps;
		try{
		ps=cnn.getConexion().prepareStatement(SQL_INSERT);
		ps.setString(1, c.getCod());
		ps.setString(2, c.getName());
		
		if(ps.executeUpdate()>0){
			return true;
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();//cerrar conexion al final de la instrucción si o si
		}	
		return false;
	}

	@Override
	public boolean delete(Object key) throws SQLException {
		PreparedStatement ps;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);
			ps.setString(1, key.toString());
			if(ps.executeUpdate()>0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean update(Departamento c) throws SQLException {
		PreparedStatement ps;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getName());
			ps.setString(2, c.getCod());
			if(ps.executeUpdate()>0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public Departamento read(Object key) throws SQLException {
		PreparedStatement ps;
		Departamento d=null;
		ResultSet r;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			r=ps.executeQuery();
			while(r.next()){
				d= new Departamento(r.getString(1), r.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return d;
	}

	@Override
	public ArrayList<Departamento> readAll() throws SQLException {
		PreparedStatement ps;
		ArrayList<Departamento> array=null;
		ResultSet r;
		try{
			array=new ArrayList<Departamento>();
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new Departamento(r.getString(1), r.getString(2)));
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return array;
	}
	


}
