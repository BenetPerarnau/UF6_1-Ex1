package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import DAO.DepartamentoDAO;
import DAO.EmpleadoDAO;
import Exceptions.NotNull;
import Exceptions.Sueldo;
import Model.Departamento;
import Model.Empleado;

/*
   Ejercicio M03-UF6.1 -01 (ENTREGABLE, fecha límite entrega: 25 ENERO 23:50H)
 
	Crear una base de datos en MySQL. La base de datos tendrá 2 tablas DEPARTAMENTOS Y EMPLEADOS.
		 DEPARTAMENTOS: código único por departamento y el nombre (no nulo)
		 EMPLEADOS: código único de empleado, nombre y apellidos (no nulo), dirección,
					  teléfono, fecha de nacimiento, si está casado o no y sueldo que percibe (no nulo y mayor que 0)
		Implementa una aplicación Java que:
			1. Cree ambas tablas
			2. Inserte registros aleatorios en ambas tablas
			3. Realice las siguientes consultas
				a. Datos del empleado con el máximo salario (incluyendo el nombre del departamento al que pertenece)
				b. Datos de los empleados de un determinado departamento
	
	Ejercicio M03-UF6.1 -02.
	
	Amplia la aplicación JAVA anterior de forma que permita dar de baja y dar de alta EMPLEADOS.
	El programa recibirá de la línea de comandos los valores a insertar/eliminar (también puedes hacer una interfaz gráfica).
	Antes de insertar un nuevo registro se debe comprobar que el departamento exista en la tabla departamentos, 
	si no existe no se podrá insertar el nuevo empleado.
	Si no se inserta un nuevo empleado, visualizar el motivo (departamento inexistente,
	número de empleado duplicado, salario no válido, etc).
	Para eliminar, el argumento que se recibe del usuario es el código único de empleado. 
	Si no es posible la eliminación, se debe visualizar el motivo por el que no se ha realizado la eliminación.
 */
public class Test {

	public static void main(String[] args) {
		byte op=0;
		do{
			menuP();//mostrar el menu principal
			try{
				BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
				op=Byte.parseByte(stdin.readLine());
				switch(op){
				case 1://"1. Crear ambas Tablas."
					crearTablas();
					break;
				case 2://"2. Insertar registros aleatorios en ambas tablas."
					insertAleatorio();
					break;
				case 3:// "3. Realice las siguientes consultas."
					byte op2=0;
					do{
					menuS();//mostrar el menu secuandario de consultas
					try{
						op2=Byte.parseByte(stdin.readLine());
						switch(op2){
						case 1://"1. Datos del empleado con el máximo salario (incluyendo el nombre del departamento al que pertenece)."
							maxSueldoEmpleado();
							break;
						case 2://"2. Datos de los empleados de un determinado departamento."
							searchEmpleadosPorDeparta(stdin);
							break;
						case 3://"3. Atràs."
							System.out.println("Volviendo al menu principal.");
							break;
							default:
								System.out.println("El valor introducido no corresponde a ninguna opción valida.");
								break;
						}
					}catch(NumberFormatException e){
						System.out.println("Se espera un valor numerico.");
					} catch (IOException e) {
						e.printStackTrace();
					}
					}while(op2!=3);
					break;
				case 4://4. Dar de Baja Empleado
					deleteEmpleado(stdin);
					break;
				case 5://5. Dar de Alta Empleado
					inserEmpleado(stdin);
					break;
				case 6: //"6. Salir."
					System.out.println("Bye.");
					break;
					default:
						System.out.println("El valor introducido no corresponde a ninguna opción valida.");
						break;
				}				
			}catch(NumberFormatException e){
				System.out.println("Se espera un valor numerico.");
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}while(op!=6);
	}

	public static void crearTablas(){//case 1:
		try {
			DepartamentoDAO dep=new DepartamentoDAO();
			EmpleadoDAO emp=new EmpleadoDAO();
			if(!dep.exist() && !emp.exist()){
				dep.create();//crea la tabla Departamentos si no existe
				emp.create();//crea la tabla Empleados si no existe
				System.out.println("Se han creado las dos tablas correctamente.");
			}else{
				System.out.println("Las tablas Empleados y Departamentos ya existen en la BBDD.");
			}						
		} catch (SQLException e) {
			System.out.println("ERROR!!");
			while(e!=null){
				System.out.println("SQL State => "+e.getSQLState());
				System.out.println("Error Code => "+e.getErrorCode());
				System.out.println("Message => "+e.getMessage());
				Throwable t=e.getCause();
				while(t!=null){
					System.out.println("Cause => "+t);
					t=t.getCause();
				}
				e=e.getNextException();
			}
		}
	}
	public static void insertAleatorio(){//case2
		try{
			DepartamentoDAO dep=new DepartamentoDAO();
			EmpleadoDAO emp=new EmpleadoDAO();
			if(dep.exist() && emp.exist()){
				for(int i=0; i<100; i++){
				String cod_dep=i+"";
				String name_dep="Departamento "+i;
			
				String cod_emp=i+"";
				String name_emp="Nombre "+i;
				String ape_emp="Apellido1 "+i;
				String ape2_emp="Apellido 2 "+i;
				String direc_emp="Direccion "+i;
				String telef_emp="656546354";
				SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd");
				java.sql.Date newFecha=null;
				try {
				java.util.Date  utilDate =  formatoDeFecha.parse("2015/01/22");
				newFecha=new java.sql.Date(utilDate.getTime());
				} catch (ParseException e) {
				System.out.println("Error al dar formato a la fecha.");
				}
				
				String estado_emp="Casado";
				float sueldo_emp=(1000+i);
				String departamento=i+"";
			
			if(dep.insert(new Departamento(cod_dep,name_dep))){
				System.out.println("Se ha insertado en la tabla Departamentos:\n"+cod_dep+" "+name_dep);
			}else{
				//salta la excepcion y es controlada en el bloque catch
			}
			if(emp.insert(new Empleado(cod_emp,name_emp,ape_emp,ape2_emp,direc_emp,telef_emp,newFecha,
										estado_emp,sueldo_emp,departamento))){
				System.out.println("Se ha insertado en la tabla Empleados:\n"
								+cod_emp+" "+name_emp+" "+ape_emp+" "+ape2_emp+" "+direc_emp+" "+telef_emp+""
										+ " "+newFecha+" "+estado_emp+" "+sueldo_emp+" "+departamento);
			}else{
				//salta la excepcion y es controlada en el bloque catch
			}
			//
			}
		}else{
			System.out.println("No se pueden insertar reg. No existen las tablas Empleados y Departamentos");
		}
		}catch(SQLException e){
			System.out.println("ERROR!!");
			while(e!=null){
				System.out.println("SQL State => "+e.getSQLState());
				System.out.println("Error Code => "+e.getErrorCode());
				System.out.println("Message => "+e.getMessage());
				Throwable t=e.getCause();
				while(t!=null){
					System.out.println("Cause => "+t);
					t=t.getCause();
				}
				e=e.getNextException();
			}
		} catch (NotNull e1) {
			System.out.println(e1.getMessage());
		} catch (Sueldo e2) {
			System.out.println(e2.getMessage());
		}
	}
	public static void maxSueldoEmpleado(){//case 3.1
		try{
			EmpleadoDAO emp=new EmpleadoDAO();
			DepartamentoDAO dep=new DepartamentoDAO();
			if(dep.exist()){
				ArrayList<Empleado> array=emp.buscarEmpleSueldoMax();//retorna una lista con el empleado o empleados con el sueldo más grande.
				if(array.size()>0){
					System.out.println("Empleado/s con el máximo Salrio:(Max salario encontrado:"+array.get(0).getSueldo()+"€ )");
					for(int i=0; i<array.size(); i++){
						System.out.print((i+1)+") "+array.get(i).toString());
						Departamento departamento=dep.read(array.get(i).getDepartamento());//La tabla Empleados tiene una columna que corresponde al número de departamento al que pertenece, entonces utilizamos este número para buscar en la tabla Departamentos y el nombre de éste.
						System.out.println("Departamento de => "+departamento.getName());
					}
				}else{
					System.out.println("En la tabla Empleados no existe ningun registro.");
				}
			}else{
				System.out.println("No existe la tabla Departamentos y por lo tanto no pueden existir Empleados!\nPorque cada empleado hace referencia al cod de un registro de la tabla Departamentos.");
			}
			}catch(SQLException e){
				System.out.println("ERROR!!");
				while(e!=null){
					System.out.println("SQL State => "+e.getSQLState());
					System.out.println("Error Code => "+e.getErrorCode());
					System.out.println("Message => "+e.getMessage());
					Throwable t=e.getCause();
					while(t!=null){
						System.out.println("Cause => "+t);
						t=t.getCause();
					}
					e=e.getNextException();
				}
			} catch (NotNull e) {
				System.out.println(e.getMessage());
			} catch (Sueldo e) {
				System.out.println(e.getMessage());
			}
	}
	public static void searchEmpleadosPorDeparta(BufferedReader stdin) throws IOException{//case 3.2
		try {
			DepartamentoDAO dep=new DepartamentoDAO();
			if(dep.exist()){
				ArrayList<Departamento> lista=dep.readAll();
				if(lista.size()>0){
					System.out.println("Registros en la tabla Departamentos:");
					for(Departamento d:lista){
						System.out.println(d.toString());
					}
					System.out.print("Nombre del Departamento => ");
					String depBuscar=stdin.readLine();
					EmpleadoDAO emp=new EmpleadoDAO();
					String cod=dep.buscarCodDeUnNombre(depBuscar);
					if(cod.length()!=0){
						ArrayList<Empleado>array=emp.buscarEmpleadoPorDepartamento(cod);
						if(array.size()>0){
							System.out.println("Empleado/s que corresponde/n a este departamento: ");
							for(Empleado fila:array){
								System.out.println(fila.toString());
							}
						}else{
							System.out.println("No hay empleados en el departamento de "+depBuscar);
						}
					}else{
						System.out.println("El Departamento de "+depBuscar+" no existe.");
					}
				}else{
					System.out.println("No existen registros en la tabla Departamentos.");
				}				
			}else{
				System.out.println("No existe la tabla Departamentos.");
			}
		} catch (SQLException e) {
			System.out.println("ERROR!!");
			while(e!=null){
				System.out.println("SQL State => "+e.getSQLState());
				System.out.println("Error Code => "+e.getErrorCode());
				System.out.println("Message => "+e.getMessage());
				Throwable t=e.getCause();
				while(t!=null){
					System.out.println("Cause => "+t);
					t=t.getCause();
				}
				e=e.getNextException();
			}
		} catch (NotNull e) {
			System.out.println(e.getMessage());
		} catch (Sueldo e) {
			System.out.println(e.getMessage());
		}
	}
	public static void deleteEmpleado(BufferedReader stdin) throws IOException{// case 4
		try {
			EmpleadoDAO emp=new EmpleadoDAO();
			if(emp.exist()){
			//mostramos todos los empleados para asi ver los cod para poder borrar mas comodamente
			ArrayList<Empleado> array=emp.readAll();
			if(array.size()>0){
				for(Empleado fila:array){
					System.out.println(fila.toString());
				}
				System.out.print("Cod del empleado a borrar => ");
				if(emp.delete(stdin.readLine())){
					System.out.println("El usuario ha sido borrado Correctamente. ");
				}else{
					System.out.println("No existe ningun empleado con este codigo.");
				}
			}else{
				System.out.println("No existen registros en la tabla Empleados pada poder borrar.");
			}
			}else{
				System.out.println("La tabla Empleados no existe.");
			}
		} catch (SQLException e) {
			System.out.println("ERROR!!");
			while(e!=null){
				System.out.println("SQL State => "+e.getSQLState());
				System.out.println("Error Code => "+e.getErrorCode());
				System.out.println("Message => "+e.getMessage());
				Throwable t=e.getCause();
				while(t!=null){
					System.out.println("Cause => "+t);
					t=t.getCause();
				}
				e=e.getNextException();
			}
		} catch (NotNull e) {
			System.out.println(e.getMessage());
		} catch (Sueldo e) {
			System.out.println(e.getMessage());
		}
	}
	public static void inserEmpleado(BufferedReader stdin) throws IOException{//case 5
		try{
			EmpleadoDAO emp=new EmpleadoDAO();
			DepartamentoDAO dep=new DepartamentoDAO();
			if(emp.exist() && dep.exist()){
				System.out.println("Formulario nuevo Empleado:");
				System.out.print("Cod => ");
				String newCod=stdin.readLine();
				System.out.print("Nombre => ");
				String newName=stdin.readLine();
				System.out.print("Apellido => ");
				String newApe=stdin.readLine();
				System.out.print("Apellido 2 => ");
				String newApe2=stdin.readLine();
				System.out.print("Direccion => ");
				String newDirec=stdin.readLine();
				System.out.print("Telefono => ");
				String newTelef=stdin.readLine();		
				System.out.print("Fecha de nacimiento 'yyyy/mm/dd' => ");
				SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd");
				java.sql.Date newFecha=null;
				try {
				java.util.Date  utilDate =  formatoDeFecha.parse(stdin.readLine());
				newFecha=new java.sql.Date(utilDate.getTime());
				} catch (ParseException e) {
				System.out.println(e.getMessage());
				}	
					System.out.print("Estado Civil => ");
					String newEstado=stdin.readLine();
					System.out.print("Sueldo => ");
					float newSueldo=Float.parseFloat(stdin.readLine());
					System.out.print("Cod Departamento => ");
					String newCodDep=stdin.readLine();
			
					if(emp.insert(new Empleado(newCod,newName,newApe,newApe2,newDirec,newTelef,newFecha,
							newEstado,newSueldo,newCodDep))){
						System.out.println("Empleado insertado en la BBDD correctamente.");
					}else{
						//no se ha podido insertar! se encargan los bloques catch de mostrar el pq no
					}
			}else{
				System.out.println("No se puede Dar de alta porque no existen las tablas.");
			}
			}catch(NumberFormatException e){
				System.out.println("Error al dar formato numerico al String => "+e.getMessage());
			}catch (SQLException e) {
				System.out.println("ERROR!!");
				while(e!=null){
					System.out.println("SQL State => "+e.getSQLState());
					System.out.println("Error Code => "+e.getErrorCode());
					System.out.println("Message => "+e.getMessage());
					Throwable t=e.getCause();
					while(t!=null){
						System.out.println("Cause => "+t);
						t=t.getCause();
					}
					e=e.getNextException();
				}
			} catch (NotNull e) {
				System.out.println(e.getMessage()+"\nNo se ha insertago el Empleado en la BBDD.");
			} catch (Sueldo e) {
				System.out.println(e.getMessage()+"\nNo se ha insertago el Empleado en la BBDD.");
			}
	}
	public static void menuP(){//menu principal
		System.out.println("------------------ Menu Principal ------------------");
		System.out.println("| 1. Crear ambas Tablas.                           |");
		System.out.println("| 2. Insertar registros aleatorios en ambas tablas.|");
		System.out.println("| 3. Realizar consultas.                           |");
		System.out.println("| 4. Dar de Baja Empleado.                         |");
		System.out.println("| 5. Dar de Alta Empleado.                         |");
		System.out.println("| 6. Salir.                                        |");
		System.out.println("----------------------------------------------------");
		System.out.print("OP  => ");
	}
	public static void menuS(){//menu secundario
		System.out.println("---------------------------------------- Menu Consultas ------------------------------------------------");
		System.out.println("| 1. Datos del empleado con el máximo salario (incluyendo el nombre del departamento al que pertenece).|");
		System.out.println("| 2. Datos de los empleados de un determinado departamento.                                            |");
		System.out.println("| 3. Atràs.                                                                                            |");
		System.out.println("--------------------------------------------------------------------------------------------------------");
		System.out.print("OP  => ");
	}
}
