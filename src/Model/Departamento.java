package Model;

import Exceptions.NotNull;


public class Departamento {

	private String cod;
	private String name;
	
	
	public Departamento(String cod, String name) throws NotNull{
		
		setCod(cod);
		setName(name);
	}


	public String getCod() {return cod;}
	public String getName() {return name;}
	
	public void setCod(String cod) throws NotNull{
		if(cod.length()==0){
			throw new NotNull("El Codigo ni puede estar vacio.");
		}else{
		this.cod = cod;
		}
		}
	public void setName(String name) throws NotNull {
		if(name.length()==0){
			throw new NotNull("El nombre del Departamento no puede estar vacio.");
		}else{
		this.name = name;
		}
	}
	
}
