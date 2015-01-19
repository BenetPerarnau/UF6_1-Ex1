package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Exceptions.NotNull;
import Exceptions.Sueldo;
import Model.ConectorBBDD;
import Model.Empleado;

public class EmpleadoDAO implements InterfaceDAO<Empleado>{
	private static final String SQL_CREATE="CREATE TABLE EMPLEADOS"
			+ " (COD VARCHAR(5), PRIMARY KEY(COD), NOMBRE VARCHAR(20) NOT NULL, APELLIDO VARCHAR(20) NOT NULL,"
			+ " APELLIDO2 VARCHAR(20) NOT NULL, DIRECCION VARCHAR(50), TELEFONO VARCHAR(9), F_NACIMIENTO DATE, "
			+ "ESTADO_CIVIL VARCHAR(20), SUELDO FLOAT NOT NULL, DEPARTAMENTO VARCHAR(5), "
			+ "FOREIGN KEY (DEPARTAMENTO) REFERENCES DEPARTAMENTOS (COD))";
	
	private static final String SQL_INSERT="INSERT INTO EMPLEADOS "
											+ "(COD, NOMBRE, APELLIDO, APELLIDO2, DIRECCION, TELEFONO, "
											+ "F_NACIMIENTO, ESTADO_CIVIL, SUELDO, DEPARTAMENTO) "
											+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE="DELETE FROM EMPLEADOS "
			 								+ "WHERE COD = ?";
	private static final String SQL_UPDATE="UPDATE EMPLEADOS "
											+ "NOMBRE = ?, APELLIDO = ?, APELLIDO2 = ?, DIRECCION = ?, TELEFONO = ?, "
											+ "F_NACIMIENTO = ?, ESTADO_CIVIL = ?, SUELDO = ?, DEPARTAMENTO = ? "
											+ "WHERE COD = ?";
	private static final String SQL_READ="SELECT * FROM EMPLEADOS WHERE COD = ?";
	private static final String SQL_READALL="SELECT * FROM EMPLEADOS";
	
	//    select nombre from tabla order by posicion asc
	//Esta es la más rápida y eficiente, pero tiene un inconveniente. 
	//Imaginemos que dos tienen el valor máximo. Sólo mostraría el primero.
	private static final String SQL_Empleado_Sueldo_Mayor="SELECT * FROM EMPLEADOS "
														+ "WHERE SUELDO = (SELECT MAX(SUELDO) FROM EMPLEADOS)";
	private static final String SQL_EMPLEADOS_SEGUN_DEPARTAMENTO="SELECT * FROM EMPLEADOS "
																+"WHERE DEPARTAMENTO = ?";
	
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
	public boolean insert(Empleado c) throws SQLException {
		PreparedStatement ps=null;
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
			ps.setString(10, c.getDepartamento());
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
	public boolean update(Empleado c) throws SQLException {
		PreparedStatement ps=null;
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
			ps.setString(9, c.getDepartamento());
			ps.setString(10, c.getCod());
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
	public Empleado read(Object key) throws SQLException, NotNull, Sueldo {
		PreparedStatement ps=null;
		Empleado em=null;
		ResultSet r=null;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			r=ps.executeQuery();
			while(r.next()){
				em=new Empleado(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),
								r.getString(6),r.getDate(7),r.getString(8),r.getFloat(9), r.getString(10));
			}
			
		}finally{
			if(r!=null)r.close();
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return em;
	}

	@Override
	public ArrayList<Empleado> readAll() throws SQLException, NotNull, Sueldo {
		PreparedStatement ps=null;
		ArrayList<Empleado> array=null;
		ResultSet r=null;
		try{
			array=new ArrayList<Empleado>();
			ps=cnn.getConexion().prepareStatement(SQL_READALL);
			r=ps.executeQuery();
			while(r.next()){
				if(r!=null){
				array.add(new Empleado(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),
										r.getString(6),r.getDate(7),r.getString(8),r.getFloat(9),r.getString(10)));
				}
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
	//metodo que devuelve tantos empleados como haya en la tabla que tengan el sueldo mayor
	public ArrayList<Empleado> buscarEmpleSueldoMax() throws SQLException, NotNull, Sueldo{
		PreparedStatement ps=null;
		ArrayList<Empleado> array=null;
		ResultSet r=null;
		try{
			array=new ArrayList<Empleado>();
			ps=cnn.getConexion().prepareStatement(SQL_Empleado_Sueldo_Mayor);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new Empleado(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),
										r.getString(6),r.getDate(7),r.getString(8),r.getFloat(9),r.getString(10)));
			}
			
		}finally{
			if(r!=null)r.close();
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return array;
	}
		//metodo que retornará una lista de los empleados que sean de un determinado departamento.
	public ArrayList<Empleado> buscarEmpleadoPorDepartamento(String num_dep_buscar)throws SQLException, NotNull, Sueldo{
		PreparedStatement ps=null;
		ArrayList<Empleado> array=null;
		ResultSet r=null;
		try{
			array=new ArrayList<Empleado>();
			ps=cnn.getConexion().prepareStatement(SQL_EMPLEADOS_SEGUN_DEPARTAMENTO);
			ps.setString(1, num_dep_buscar);
			r=ps.executeQuery();
			while(r.next()){
				if(r!=null){
				array.add(new Empleado(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),
										r.getString(6),r.getDate(7),r.getString(8),r.getFloat(9),r.getString(10)));
				}
			}
			
		}finally{
			if(r!=null)r.close();
			if(ps!=null)ps.close();
			cnn.cerrarConexion();
		}
		return array;
	}
}
