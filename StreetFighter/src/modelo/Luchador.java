package modelo;

import controlador.Controlador;

/**
 * Esta clase representa un luchador en el juego, con atributos que incluyen su
 * nombre, edad, potencia, estatura, velocidad, nacionalidad, peso, físico,
 * entre otros. Proporciona métodos para comparar luchadores, así como acciones
 * relacionadas con el combate, como golpear y reproducir sonidos. Además, puede
 * ser utilizado en el contexto de un combate específico a través de una
 * instancia de la clase Combate.
 */
public class Luchador implements Comparable<Luchador> {

	private int golpe = 0;
	private Musica sonido;
	private Combate combate;
	private String nombre;
	private int edad;
	private int potencia;
	private double estatura;
	private int velocidad;
	private String nacionalidad;
	private int peso;
	private int fisico;
	private String descripcion;
	private String[] vocesPersonaje;
	private String[] imgsPelea;
	private int vida = 100;
	private int cansancio = 100;
	private boolean defendiendo = false;
	private String mensajePelea;
	private boolean bloqueado;

	/**
	 * crea una instancia de luchador
	 */
	public Luchador() {

	}

	/**
	 * Constructor de la clase Luchador que inicializa sus atributos con los valores proporcionados.
	 *
	 * @param nombre           Nombre del luchador.
	 * @param edad             Edad del luchador.
	 * @param potencia         Nivel de potencia del luchador.
	 * @param estatura         Estatura del luchador.
	 * @param velocidad        Velocidad del luchador.
	 * @param nacionalidad     Nacionalidad del luchador.
	 * @param peso             Peso del luchador.
	 * @param fisico           Nivel físico del luchador.
	 * @param descripcion      Descripción del luchador.
	 * @param vocesPersonaje   Arreglo de voces del luchador.
	 * @param imgsPelea        Arreglo de imágenes de pelea del luchador.
	 * @param bloqueado        Indica si el luchador está bloqueado o desbloqueado.
	 */
	public Luchador(String nombre, int edad, int potencia, double estatura, int velocidad, String nacionalidad,
			int peso, int fisico, String descripcion, String[] vocesPersonaje, String[] imgsPelea, boolean bloqueado) {
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
		this.vocesPersonaje = vocesPersonaje;
		this.imgsPelea = imgsPelea;
		this.bloqueado = bloqueado;
	}

	/**
	 * recibe y resta el valor del golpe a la vida del luchador
	 * @param golpe valor que se restará a la vida del luchador
	 */
	public void recibirGolpe(int golpe) {
		vida -= golpe;
		if (vida <= 0) {
			vida = 0;
		}
		mensajePelea = this.nombre + " recibe " + golpe + " de daño";
	}

	/**
	 * Realiza un ataque, generando un valor aleatorio como el daño infligido.
	 * 
	 * @return El valor del daño infligido por el ataque.
	 */
	public int atacar() {

		Controlador.iniciarSonido(sonido, Controlador.procesarSonidosJugador(vocesPersonaje));
		golpe = (int) (1 + Math.random() * potencia);
		if (cansancio - golpe >= 0) {
			cansancio -= golpe;
			mensajePelea = this.nombre + " ataqua," + golpe + " de daño";
			return golpe;
		} else {
			mensajePelea = this.nombre + " sin vitalidad";
			return 0;
		}
	}

	/**
	 * crea un número aleatorio a partir de la velocidad del luchador el cual se restará al golpe recibido
	 * @param golpe el valor del golpe recibido por el adversario
	 */
	public void defender(int golpe) {

		defendiendo = false;
		int aleatorioVelocidad = (int) (1 + Math.random() * velocidad);
		cansancio -= (aleatorioVelocidad / 2);

		if (golpe > aleatorioVelocidad) {
			vida -= (golpe - aleatorioVelocidad);
			mensajePelea = this.nombre + " protege " + aleatorioVelocidad + " de daño";
		} else {
			mensajePelea = this.nombre + " esquiva el golpe";
		}
	}

	/**
	 * hace que el luchador descanse en esa iteración y recupere vida
	 */
	public void descansar() {

		int aleatorioFisico = (int) (1 + Math.random() * fisico);

		if (aleatorioFisico + cansancio <= 100) {
			cansancio += aleatorioFisico;
			mensajePelea = this.nombre + " recupera " + aleatorioFisico + " % vitalidad.";
		} else {
			mensajePelea = this.nombre + " está al 100 % de vitalidad";
			cansancio = 100;
		}
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public void setCansancio(int cansancio) {
		this.cansancio = cansancio;
	}

	public Combate getCombate() {
		return combate;
	}

	public void setCombate(Combate combate) {
		this.combate = combate;
	}

	public String getMensajePelea() {
		return mensajePelea;
	}

	public boolean isDefendiendo() {
		return defendiendo;
	}

	public void setDefendiendo(boolean defendiendo) {
		this.defendiendo = defendiendo;
	}

	public int getVida() {
		return vida;
	}

	public int getCansancio() {
		return cansancio;
	}

	public String[] getImgPelea() {
		return imgsPelea;
	}

	public String[] getVocesPersonaje() {
		return vocesPersonaje;
	}

	public String getDescripcion() {
		return descripcion;
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

	/**
	 * compara los valores de los luchadores para saber cual fue el ganador
	 */
	@Override
	public int compareTo(Luchador o) {

		if (vida > o.vida)
			return 1;
		else if (vida < o.vida)
			return -1;
		else {
			if (golpe > o.golpe)
				return 1;
			else if (golpe < o.golpe)
				return -1;
			else
				return 0;
		}
	}
}
