package modelo;

import controlador.Controlador;
import vista.Vista;

public class Luchador extends Thread {
	
	private Musica sonido;
	private Vista vista;
	private Luchador rival;
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
	private String[] imgPelea;
	private int vida = 100;
	private int cansancio = 100;
	private boolean defendiendo = false;
	private String mensajePelea;

	public Luchador(String nombre, int edad, int potencia, double estatura, int velocidad, String nacionalidad,
			int peso, int fisico, String descripcion, String[] vocesPersonaje, String[] imgPelea) {
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
		this.imgPelea = imgPelea;

	}

	public void recibirGolpe(int golpe) {
		vida -= golpe;
		if (vida <= 0) {
			vida = 0;
		}
		mensajePelea = this.nombre + "ha recibido un golpe de " + golpe;
	}

	public int atacar() {

		int golpe = (int) (1 + Math.random() * potencia);
		if (cansancio - golpe >= 0) {
			cansancio -= golpe;
			mensajePelea = this.nombre + " ataqua," + golpe + " potencia";
			return golpe;
		} else {
			mensajePelea = this.nombre + " sin vitalidad suficinete";
			return 0;
		}
	}

	public void defender(int golpe) {

		defendiendo = false;
		int aleatorioVelocidad = (int) (1 + Math.random() * velocidad);
		cansancio -= aleatorioVelocidad;

		if (golpe > aleatorioVelocidad) {
			vida -= (golpe - aleatorioVelocidad);
			mensajePelea = this.nombre + " protege " + aleatorioVelocidad + " de vida";
		} else {
			mensajePelea = this.nombre + " se defiende del golpe";
		}

	}

	public void descansa() {

		int aleatorioFisico = (int) (1 + Math.random() * fisico);

		if (aleatorioFisico + cansancio <= 100) {
			cansancio += aleatorioFisico;
			mensajePelea = this.nombre + " recupera " + aleatorioFisico + " % vitalidad.";
		} else {
			mensajePelea = this.nombre + " está al 100 % de vitalidad";
			cansancio = 100;
		}
	}

	
	public void setVista(Vista vista) {
		this.vista = vista;
	}

	public Combate getCombate() {
		return combate;
	}

	public void setRival(Luchador rival) {
		this.rival = rival;
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
		return imgPelea;
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

	public void run() {

		while (!combate.isTerminado() && rival.getVida() > 0 && vida > 0) {

			if (combate.isTurnoComputadora()) {
				if (cansancio < 10) {
					combate.descansar(this);
					combate.setTurnoComputadora(false);
					actualizarVista();
					activarAcciones();
					
					
				} else if (vida < rival.vida) {
					
					int aleatorio = (int)(1+Math.random()*2);
					switch (aleatorio) {
					case 1:
						combate.defender(this);
						combate.setTurnoComputadora(false);
						actualizarVista();
						activarAcciones();
						break;
					case 2:
						combate.atacar(this, rival);
						combate.setTurnoComputadora(false);
						Controlador.iniciarSonido(sonido,Controlador.procesarSonidosJugador(vocesPersonaje));
						actualizarVista();
						activarAcciones();
						break;
					default:
						break;
					}

				} else {
					combate.atacar(this, rival);
					combate.setTurnoComputadora(false);
					actualizarVista();
					activarAcciones();
				}
			}

			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void activarAcciones() {
		vista.getBtnAtacar().setEnabled(true);
		vista.getBtnDefender().setEnabled(true);
		vista.getBtnDescansar().setEnabled(true);

	}
	public void actualizarVista() {
		
		vista.getLblVidaPj1().setText(rival.getVida() + "");
		vista.getLblCansancioPj1().setText(rival.getCansancio() + " %");
		vista.getProgressBarVidaPJ1().setValue(rival.getVida());
		vista.getProgressBarVitalidadPj1().setValue(rival.getCansancio());
		vista.getLblMensajePj1().setText(rival.getMensajePelea());
		
		vista.getLblVidaPj2().setText(vida + "");
		vista.getLblCansancioPj2().setText(cansancio + " %");
		vista.getProgressBarVidaPJ2().setValue(vida);
		vista.getProgressBarVitalidadPj2().setValue(cansancio);
		vista.getLblMensajePj2().setText(this.mensajePelea);

	}
}
