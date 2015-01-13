package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Exceptions.NotNull;
import Exceptions.Sueldo;

public interface InterfaceDAO <CualquierCosa>{

		public boolean create() throws SQLException;
		public boolean insert(CualquierCosa c)throws SQLException;
		public boolean delete(Object key)throws SQLException;
		public boolean update(CualquierCosa c)throws SQLException;		
		public CualquierCosa read(Object key)throws SQLException,NotNull, Sueldo;		
		public ArrayList<CualquierCosa> readAll()throws SQLException,NotNull, Sueldo;
}
