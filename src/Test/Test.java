package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import DAO.DepartamentoDAO;
import DAO.EmpleadoDAO;

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
				switch(op){
				case 1://"1. Crear ambas Tablas."
					DepartamentoDAO d=new DepartamentoDAO();
					EmpleadoDAO em=new EmpleadoDAO();
					try {
						d.create();//crea la tabla Departamentos
						em.create();//crea la tabla Empleados
						System.out.println("Se han creado las dos tablas correctamente.");
						
					} catch (SQLException e) {
						System.out.println("Ya has creado las tablas anteriormente.");
					}
					break;
				case 2://"2. Insertar registros aleatorios en ambas tablas."
					break;
				case 3:// "3. Realice las siguientes consultas."
					do{
					menuS();
					op2=Byte.parseByte(stdin.readLine());
					switch(op2){
					case 1://"1. Datos del empleado con el máximo salario (incluyendo el nombre del departamento al que pertenece)."
						break;
					case 2://"2. Datos de los empleados de un determinado departamento."
						break;
					case 3://"3. Atràs."
						System.out.println("Volviendo al menu principal.");
						break;
						default:
							System.out.println("El valor introducido no corresponde a ninguna opción valida.");
							break;
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
		System.out.println("1. Crear ambas Tablas.");
		System.out.println("2. Insertar registros aleatorios en ambas tablas.");
		System.out.println("3. Realice las siguientes consultas.");
		System.out.println("4. Salir.");
		System.out.print("OP  => ");
	}
	public static void menuS(){
		System.out.println("1. Datos del empleado con el máximo salario (incluyendo el nombre del departamento al que pertenece).");
		System.out.println("2. Datos de los empleados de un determinado departamento.");
		System.out.println("3. Atràs.");
		System.out.print("OP  => ");
	}

}
