package modelo;

import java.awt.Color;

import javax.swing.JLabel;

public class CuentaAtras extends Thread {

	private int cuenta = 60;
	private boolean terminado = false;
	private JLabel vistaContador ;	
	public CuentaAtras(JLabel vistaContador) {
		this.vistaContador = vistaContador;
	}
	
	@Override
	public void run() {
	
			while(!terminado) {
				devolverCuenta();
			}
	}
	public void devolverCuenta() {
		
		try {
			vistaContador.setText(cuenta+"");
			sleep(1000);
			cuenta--;
			if(cuenta < 30 ) {
				vistaContador.setOpaque(true);
				vistaContador.setBackground(Color.RED);
				vistaContador.setForeground(Color.WHITE);
			}
			
			if(cuenta < 0) {
				this.terminado = true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public boolean isTerminado() {
		return terminado;
	}
	
}
