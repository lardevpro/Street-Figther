package modelo;

import java.awt.Color;
import javax.swing.JLabel;
import controlador.Controlador;

/**
 * Esta clase extiende la clase Thread y representa un hilo que controla el combate entre el jugador y la computadora en el juego.
 * Administra el flujo del combate, gestionando los turnos de los jugadores, las acciones automáticas de la computadora,
 * la interrupción del combate y la finalización del mismo. Además, puede interactuar con objetos de otras clases, como el controlador del juego y los luchadores.
 */
public class Combate extends Thread {

	private boolean turnoComputadora = false, combateInterrumpido = false,terminado = false;
	private Musica sonido;
	private int cuenta = 5,contadorRespuestaSiNoAtaque = 0;
	private JLabel vistaContador;
	private Luchador jugador,computadora;
	private Controlador controlador;	

	/**
	 * Crea una instancia de Combate.
	 */
	public Combate() {
		
	}
	/**
	 * recibe el controlador del programa para llamar a sus metodos y controlar diferentes cambios 
	 * @param controlador es la clase principal
	 */
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	/**
	 * comprueba de quien es el turno para decidir acción en el combate
	 * @param turnoComputadora true toca movimiento a computadora, en caso contrario el usuario
	 */
	public void setTurnoComputadora(boolean turnoComputadora) {
		this.turnoComputadora = turnoComputadora;
	}

	/**
	 * recibe el componente de la vista donde se reflejará la cuenta atrás del combate
	 * @param vistaContador label que recibe de la vista
	 */
	public void setVistaContador(JLabel vistaContador) {
		this.vistaContador = vistaContador;
	}

	/**
	 * recibe el luchadore que será el jugador en el combate actual
	 * @param jugador es el luchador que será el usuario
	 */
	public void setJugador(Luchador jugador) {
		this.jugador = jugador;
	}

	/**
	 * recibe el luchadore que será la computadora en el combate actual
	 * @param computadora es el luchador recibido como computadora acutal
	 */
	public void setComputadora(Luchador computadora) {
		this.computadora = computadora;
	}

	/**
	 * interrumpe el combate (solo puede hacerlo el usuario)
	 * @param combateInterrumpido es el valor que se cambia para que no siga la cuenta atrás del combate
	 */
	public void setCombateInterrumpido(boolean combateInterrumpido) {
		this.combateInterrumpido = combateInterrumpido;
	}

	/**
	 * hace que el jugador esté en defensa (restará en esa iteración un valor aleatorio al daño recibido)
	 * @param luchador será el luchador que se pondrá en estado defender
	 */
	public synchronized void defender(Luchador luchador) {
		luchador.setDefendiendo(true);
		comprobarVida(luchador);
	}

	/**
	 * hace que el luchador haga un ataque al rival
	 * @param atacante es el luchador que infringe el daño
	 * @param atacado es el luchadore que recibe el daño (en caso de no estar defendiendose será 100% daño infringido)
	 */
	public synchronized void atacar(Luchador atacante, Luchador atacado) {

		if (!atacado.isDefendiendo()) {
			atacado.recibirGolpe(atacante.atacar());
			comprobarVida(atacado);
		} else {
			atacado.defender(atacante.atacar());
			comprobarVida(atacado);
		}
	}

	/**
	 * hace que el luchadore esté en descanso (recupere vida) en esa iteración
	 * también llama el método comprobar vida para evitar posibles errores
	 * @param luchador ser el que se pondrá a descansar
	 */
	public synchronized void descansar(Luchador luchador) {
		luchador.descansar();
		comprobarVida(luchador);
	}

	/**
	 * Comprueba la vida actual del luchador y se encarga de que no sea inferior a cero
	 * Esto hace que no se muestren resultados negativos en los marcadores de la interfaz de combate
	 * @param luchador recibe el luchado al que comprobar su vida
	 */
	private void comprobarVida(Luchador luchador) {
		if (luchador.getVida() <= 0) {
			luchador.setVida(0);
		}

	}

	/**
	 * Ejecuta el ciclo principal del combate entre el jugador y la computadora.
	 * El combate continúa hasta que uno de los combatientes queda sin vida o hasta que el combate es interrumpido.
	 * Durante el combate, se realizan acciones automáticas para el personaje de la computadora, como descansar, defender o atacar.
	 * Además, se actualiza la vista del combate y se activan los botones de pelea según corresponda.
	 */
	@Override
	public void run() {

		while (!combateInterrumpido && !terminado && jugador.getVida() > 0 && computadora.getVida() > 0) {

			devolverCuenta();
			contadorRespuestaSiNoAtaque++;
			
			if(contadorRespuestaSiNoAtaque == 3) {
				contadorRespuestaSiNoAtaque = 0;
				atacar(computadora, jugador);
				controlador.actualizarVistaCombate();
				controlador.activarBotonesPelea();
			}

			if (turnoComputadora) {
				if (computadora.getCansancio() < 10) {
					contadorRespuestaSiNoAtaque = 0;
					computadora.descansar();
					turnoComputadora = false;
					controlador.actualizarVistaCombate();
					controlador.activarBotonesPelea();

				} else if (computadora.getVida() < jugador.getVida()) {

					int aleatorio = (int) (1 + Math.random() * 2);
					switch (aleatorio) {
					case 1:
						contadorRespuestaSiNoAtaque = 0;
						defender(computadora);
						turnoComputadora = false;
						controlador.actualizarVistaCombate();
						controlador.activarBotonesPelea();
						break;
					case 2:
						contadorRespuestaSiNoAtaque = 0;
						atacar(computadora, jugador);
						turnoComputadora = false;
						controlador.cambiarImagenAlAtacar(false,computadora.getImgPelea());
						controlador.actualizarVistaCombate();
						controlador.activarBotonesPelea();
						break;
					}

				} else {
					contadorRespuestaSiNoAtaque = 0;
					atacar(computadora, jugador);
					turnoComputadora = false;
					controlador.cambiarImagenAlAtacar(false,computadora.getImgPelea());
					controlador.actualizarVistaCombate();
					controlador.activarBotonesPelea();
				}
			}
		}

		pausar(1);
		if (terminado) {
			Controlador.iniciarSonido(sonido, "time_over");
		}
		pausar(1);
		if (!combateInterrumpido) {
			controlador.terminarCombateTacharPerdedor();
		}

	}

	/**
	 * Hace de cronometro para la partida 
	 * A partir del segundo 10 comienza una cuenta atrás con voz
	 * @return devuelve el segundo por el que va la partida
	 */
	public int devolverCuenta() {

		cuenta--;
		vistaContador.setText(cuenta + "");
		pausar(1);
		if (cuenta < 30) {
			vistaContador.setOpaque(true);
			vistaContador.setBackground(Color.RED);
			vistaContador.setForeground(Color.WHITE);
		}

		if (cuenta < 10) {

			switch (cuenta) {

			case 9:
				Controlador.iniciarSonido(sonido, "nueve");
				break;
			case 8:
				Controlador.iniciarSonido(sonido, "ocho");
				break;
			case 7:
				Controlador.iniciarSonido(sonido, "siete");
				break;
			case 6:
				Controlador.iniciarSonido(sonido, "seis");
				break;
			case 5:
				Controlador.iniciarSonido(sonido, "cinco");
				break;
			case 4:
				Controlador.iniciarSonido(sonido, "cuatro");
				break;
			case 3:
				Controlador.iniciarSonido(sonido, "tres");
				break;
			case 2:
				Controlador.iniciarSonido(sonido, "dos");
				break;
			case 1:
				Controlador.iniciarSonido(sonido, "uno");
				break;
			case 0:
				Controlador.iniciarSonido(sonido, "cero");
				terminado = true;
				break;
			}
		}

		return cuenta;
	}

	/**
	 * 
	 * @param tiempo
	 */
	private void pausar(int tiempo) {
		try {
			sleep(tiempo * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
