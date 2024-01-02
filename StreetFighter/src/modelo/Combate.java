package modelo;

import java.awt.Color;

import javax.swing.JLabel;

import controlador.Controlador;

public class Combate extends Thread {

	private boolean turnoComputadora = false;
	private boolean terminado = false;
	private boolean combateInterrumpido = false;
	private Musica sonido;
	private int cuenta = 10;
	private JLabel vistaContador ;
	private Luchador jugador;
	private Luchador computadora;
	private Controlador controlador;
	
	
	public Combate() {
	
	}
	
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
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


	public boolean isCombateInterrumpido() {
		return combateInterrumpido;
	}
	public void setCombateInterrumpido(boolean combateInterrumpido) {
		this.combateInterrumpido = combateInterrumpido;
	}
	public synchronized boolean isTerminado() {
		return terminado;
	}
	public synchronized boolean isTurnoComputadora() {
		return turnoComputadora;
	}

	public synchronized void setTurnoComputadora(boolean turnoComputadora) {
		this.turnoComputadora = turnoComputadora;
	}

	public synchronized void setTerminado(boolean terminado) {
		this.terminado = terminado;
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
		luchador.descansa();
		comprobarVida(luchador);
	}
	private void comprobarVida(Luchador luchador) {
		if(luchador.getVida() <= 0) {
			this.terminado = true;
		}
		
	}
	
	@Override
	public void run() {
	
			while(!combateInterrumpido && !terminado && jugador.getVida() > 0 && computadora.getVida() > 0) {
				devolverCuenta();
			}
			pausar(1);					
			if(terminado && jugador.getVida() > 0 && computadora.getVida() > 0) {
				Controlador.iniciarSonido(sonido, "time_over");
			}
			pausar(1);
			if(!combateInterrumpido) {
				controlador.terminarCombateTacharPerdedor();
			}
			
	}
	public int devolverCuenta() {
		
			vistaContador.setText(cuenta+"");
			cuenta--;
			pausar(1);
			if(cuenta < 30 ) {
				vistaContador.setOpaque(true);
				vistaContador.setBackground(Color.RED);
				vistaContador.setForeground(Color.WHITE);
			}
			
			if(cuenta < 10) {
				
				switch(cuenta) {
				
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
					break;
				case -1:
					terminado = true;
					break;
				}
			}
			
			return cuenta;
	}
	
	private void pausar(int tiempo) {
		try {
			sleep(tiempo*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void comprobarGanadorCombate() {
		
	// TODO Auto-generated method stub

	}
}
