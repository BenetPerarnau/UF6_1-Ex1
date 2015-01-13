package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.DepartamentoDAO;
import DAO.EmpleadoDAO;
import Exceptions.NotNull;
import Exceptions.Sueldo;
import Model.Departamento;
import Model.Empleado;

/*
 * Ejercicio M03-UF6.1 -01 (ENTREGABLE, fecha límite entrega: 25 ENERO 23:50H)
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
 */
public class Test {

	public static void main(String[] args) {
		byte op=0;
		byte op2=0;
		do{
			menuP();//mostrar el menu principal
			try{
				BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
				op=Byte.parseByte(stdin.readLine());
				DepartamentoDAO dep;
				EmpleadoDAO emp;
				switch(op){
				case 1://"1. Crear ambas Tablas."
					try {
						dep=new DepartamentoDAO();
						emp=new EmpleadoDAO();
						dep.create();//crea la tabla Departamentos
						emp.create();//crea la tabla Empleados
						System.out.println("Se han creado las dos tablas correctamente.");
						
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
					break;
				case 2://"2. Insertar registros aleatorios en ambas tablas."
					try{
						dep=new DepartamentoDAO();
							String cod_dep="00007";
							String name_dep="Contabilidad";
						emp=new EmpleadoDAO();
							String cod_emp="00009";
							String name_emp="Benet";
							String ape_emp="Perarnau";
							String ape2_emp="Aguilar";
							String direc_emp="C/Saragossa nº7";
							String telef_emp="656546354";
							Date date_emp=new Date((2015-1900),0,12);
							String estado_emp="Ciutadano";
							float sueldo_emp=10000;
							String departamento="00004";
						
						if(dep.insert(new Departamento(cod_dep,name_dep))){
							System.out.println("Se ha insertado en la tabla Departamentos:\n"+cod_dep+" "+name_dep);
						}
						
						if(emp.insert(new Empleado(cod_emp,name_emp,ape_emp,ape2_emp,direc_emp,telef_emp,date_emp,
													estado_emp,sueldo_emp,departamento))){
							System.out.println("Se ha insertado en la tabla Empleados:\n"
											+cod_emp+" "+name_emp+" "+ape_emp+" "+ape2_emp+" "+direc_emp+" "+telef_emp+""
													+ " "+date_emp+" "+estado_emp+" "+sueldo_emp+" "+departamento);
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
					break;
				case 3:// "3. Realice las siguientes consultas."
					do{
					menuS();
					try{
						op2=Byte.parseByte(stdin.readLine());
						switch(op2){
						case 1://"1. Datos del empleado con el máximo salario (incluyendo el nombre del departamento al que pertenece)."
							try{
							emp=new EmpleadoDAO();
							dep=new DepartamentoDAO();
							ArrayList<Empleado> array=emp.buscarEmpleSueldoMax();//retorna una lista con el empleado o empleados con el sueldo más grande.
							System.out.println("Empleado o Empleados con el máximo Salrio:");
							for(int i=0; i<array.size(); i++){
								System.out.print((i+1)+") "+array.get(i).toString());
								Departamento departamento=dep.read(array.get(i).getDepartamento());//La tabla Empleados tiene una columna que corresponde al número de departamento al que pertenece, entonces utilizamos este número para buscar en la tabla Departamentos y el nombre de éste.
								System.out.println("Departamento de => "+departamento.getName());
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
								e.printStackTrace();
							} catch (Sueldo e) {
								e.printStackTrace();
							}
							break;
						case 2://"2. Datos de los empleados de un determinado departamento."
							System.out.print("Nombre del Departamento => ");
							String depBuscar=stdin.readLine();
							try {
								dep=new DepartamentoDAO();
								emp=new EmpleadoDAO();
								String cod=dep.buscarCodDeUnNombre(depBuscar);
								if(cod.length()!=0){
									System.out.println("El departamento "+depBuscar+" corresponde al cod => "+cod);
									ArrayList<Empleado>array=emp.buscarEmpleadoPorDepartamento(cod);
									for(Empleado fila:array){
										System.out.println(fila.toString());
									}
								}else{
									System.out.println("El Departamento "+depBuscar+" no existe.");
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
								e.printStackTrace();
							} catch (Sueldo e) {
								e.printStackTrace();
							}
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
				case 4: //"4. Salir."
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
			
		}while(op!=4);
		

	}
	
	public static void menuP(){
		System.out.println("------------------ Menu Principal ------------------");
		System.out.println("| 1. Crear ambas Tablas.                           |");
		System.out.println("| 2. Insertar registros aleatorios en ambas tablas.|");
		System.out.println("| 3. Realice las siguientes consultas.             |");
		System.out.println("| 4. Salir.                                        |");
		System.out.println("----------------------------------------------------");
		System.out.print("OP  => ");
	}
	public static void menuS(){
		System.out.println("-----------------------------------------Menu Secundario------------------------------------------------");
		System.out.println("| 1. Datos del empleado con el máximo salario (incluyendo el nombre del departamento al que pertenece).|");
		System.out.println("| 2. Datos de los empleados de un determinado departamento.                                            |");
		System.out.println("| 3. Atràs.                                                                                            |");
		System.out.println("--------------------------------------------------------------------------------------------------------");
		System.out.print("OP  => ");
	}
}
