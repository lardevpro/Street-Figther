package modelo;

public class Luchador {
	private String nombre;
	private int edad;
	private int potencia;
	private double estatura;
	private int velocidad;
	private String nacionalidad;
	private int peso;
	private int fisico;
	private String descripcion;
	private String nombreImagen;
	private String [] vocesPersonaje;
	
	
	public Luchador(String nombre, int edad, int potencia, double estatura, int velocidad, String nacionalidad,
			int peso, int fisico,String descripcion,String nombreImagen,String [] vocesPersonaje) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.potencia = potencia;
		this.estatura = estatura;
		this.velocidad = velocidad;
		this.nacionalidad = nacionalidad;
		this.peso = peso;
		this.fisico = fisico;
		this.descripcion = descripcion;
		this.nombreImagen = nombreImagen;
		this.vocesPersonaje = vocesPersonaje;
	}
	
	
	
	
	public String[] getVocesPersonaje() {
		return vocesPersonaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public String getNombre() {
		return nombre;
	}
	public int getEdad() {
		return edad;
	}
	public int getPotencia() {
		return potencia;
	}
	public double getEstatura() {
		return estatura;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public int getPeso() {
		return peso;
	}
	public int getFisico() {
		return fisico;
	}
	
}
