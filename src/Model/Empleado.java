package Model;

import java.sql.Date;

import Exceptions.NotNull;
import Exceptions.Sueldo;

public class Empleado {
	
	private String cod;
	private String name;
	private String ape;
	private String ape2;
	private String direc;
	private String telef;
	private Date fecha_nacimiento;
	private String estado_civil;
	private float sueldo;
	
	public Empleado(String cod, String name, String ape, String ape2, 
					String direc, String telef, Date fecha_nacimiento, String estado_civil, float sueldo) throws NotNull, Sueldo{
		setCod(cod);
		setName(name);
		setApe(ape);
		setApe2(ape2);
		setDirec(direc);
		setTelef(telef);
		setFecha_nacimiento(fecha_nacimiento);
		setEstado_civil(estado_civil);
		setSueldo(sueldo);
		
	}

	public String getCod() {return cod;}
	public String getName() {return name;}
	public String getApe() {return ape;}
	public String getApe2() {return ape2;}
	public String getDirec() {return direc;}
	public String getTelef() {return telef;}
	public Date getFecha_nacimiento() {return fecha_nacimiento;}
	public String getEstado_civil() {return estado_civil;}
	public float getSueldo() {return sueldo;}

	public void setCod(String cod) throws NotNull{
		if(cod.length()==0){
			throw new NotNull("El Codigo no puede estar vacio al crear un Empleado.");
		}else{
			this.cod = cod;
		}
	}

	public void setName(String name)throws NotNull {
		if(name.length()==0){
			throw new NotNull("El Nombre del Empleado no puede estar vacio.");
		}else{
		this.name = name;
		}
	}

	public void setApe(String ape)throws NotNull {
		if(ape.length()==0){
			throw new NotNull("El primer Apellido del Empleado no puede estar vacio.");
		}else{
		this.ape = ape;
		}
	}

	public void setApe2(String ape2)throws NotNull{
		if(ape2.length()==0){
			throw new NotNull("El segundo Apellido del Empleado no puede estar vacio.");
		}else{
		this.ape2 = ape2;
		}
	}

	public void setDirec(String direc) {
		this.direc = direc;
	}

	public void setTelef(String telef) {
		this.telef = telef;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public void setEstado_civil(String estado_civil) {
		this.estado_civil = estado_civil;
	}

	public void setSueldo(float sueldo) throws Sueldo {
		if(sueldo<0){
			throw new Sueldo("El sueldo del Empleado no puede ser negativo.");
		}else{
			this.sueldo = sueldo;
		}
	}

	
}
