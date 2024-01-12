package modelo;

import java.awt.Color;
import javax.swing.JLabel;
import controlador.Controlador;

public class Combate extends Thread {

	
	private boolean turnoComputadora = false, combateInterrumpido = false,terminado = false;
	private Musica sonido;
	private int cuenta = 5,contadorRespuestaSiNoAtaque = 0;
	private JLabel vistaContador;
	private Luchador jugador,computadora;
	private Controlador controlador;	

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	

	public void setTurnoComputadora(boolean turnoComputadora) {
		this.turnoComputadora = turnoComputadora;
	}


	public void setVistaContador(JLabel vistaContador) {
		this.vistaContador = vistaContador;
	}

	public void setJugador(Luchador jugador) {
		this.jugador = jugador;
	}

	public void setComputadora(Luchador computadora) {
		this.computadora = computadora;
	}

	public void setCombateInterrumpido(boolean combateInterrumpido) {
		this.combateInterrumpido = combateInterrumpido;
	}

	public synchronized void defender(Luchador luchador) {
		luchador.setDefendiendo(true);
		comprobarVida(luchador);
	}

	public synchronized void atacar(Luchador atacante, Luchador atacado) {

		if (!atacado.isDefendiendo()) {
			atacado.recibirGolpe(atacante.atacar());
			comprobarVida(atacado);
		} else {
			atacado.defender(atacante.atacar());
			comprobarVida(atacado);
		}
	}

	public synchronized void descansar(Luchador luchador) {
		luchador.descansar();
		comprobarVida(luchador);
	}

	private void comprobarVida(Luchador luchador) {
		if (luchador.getVida() <= 0) {
			luchador.setVida(0);
		}

	}

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

	private void pausar(int tiempo) {
		try {
			sleep(tiempo * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
