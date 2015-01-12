package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.ConectorBBDD;
import Model.Empleado;

public class EmpleadoDAO implements InterfaceDAO<Empleado>{
	private static final String SQL_CREATE="CREATE TABLE EMPLEADOS"
			+ " (COD VARCHAR(5), PRIMARY KEY(COD), NOMBRE VARCHAR(20) NOT NULL, APELLIDO VARCHAR(20) NOT NULL,"
			+ " APELLIDO2 VARCHAR(20) NOT NULL, DIRECCION VARCHAR(50), TELEFONO VARCHAR(9), F_NACIMIENTO DATE, "
			+ "ESTADO_CIVIL VARCHAR(20), SUELDO FLOAT NOT NULL)";
	private static final String SQL_INSERT="INSERT INTO EMPLEADOS "
											+ "(COD, NOMBRE, APELLIDO, APELLIDO2, DIRECCION, TELEFONO, "
											+ "F_NACIMIENTO, ESTADO_CIVIL, SUELDO) "
											+ "VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM EMPLEADOS "
			 								+ "WHERE COD = ?";
	private static final String SQL_UPDATE="UPDATE EMPLEADOS "
											+ "NOMBRE = ?, APELLIDO = ?, APELLIDO2 = ?, DIRECCION = ?, TELEFONO = ?, "
											+ "F_NACIMIENTO = ?, ESTADO_CIVIL = ?, SUELDO = ? "
											+ "WHERE COD = ?";
	private static final String SQL_READ="SELECT * FROM EMPLEADOS WHERE COD = ?";
	private static final String SQL_READALL="SELECT * FROM EMPLEADOS";

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
	public boolean insert(Empleado c) throws SQLException {
		PreparedStatement ps;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setString(1, c.getCod());
			ps.setString(2, c.getName());
			ps.setString(3, c.getApe());
			ps.setString(4, c.getApe2());
			ps.setString(5, c.getDirec());
			ps.setString(6, c.getTelef());
			ps.setDate(7, c.getFecha_nacimiento());
			ps.setString(8, c.getEstado_civil());
			ps.setFloat(9, c.getSueldo());
			
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
	public boolean update(Empleado c) throws SQLException {
		PreparedStatement ps;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getName());
			ps.setString(2, c.getApe());
			ps.setString(3, c.getApe2());
			ps.setString(4, c.getDirec());
			ps.setString(5, c.getTelef());
			ps.setDate(6, c.getFecha_nacimiento());
			ps.setString(7, c.getEstado_civil());
			ps.setFloat(8, c.getSueldo());
			ps.setString(9, c.getCod());
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
	public Empleado read(Object key) throws SQLException {
		PreparedStatement ps;
		Empleado em=null;
		ResultSet r;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			r=ps.executeQuery();
			while(r.next()){
				em=new Empleado(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),
								r.getString(6),r.getDate(7),r.getString(8),r.getFloat(9));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return em;
	}

	@Override
	public ArrayList<Empleado> readAll() throws SQLException {
		PreparedStatement ps;
		ArrayList<Empleado> array=null;
		ResultSet r;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new Empleado(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),
										r.getString(6),r.getDate(7),r.getString(8),r.getFloat(9)));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cnn.cerrarConexion();
		}
		return array;
	}

}
