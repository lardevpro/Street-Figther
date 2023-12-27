package modelo;

public class Combate {

	private boolean turnoComputadora = false;
	private boolean terminado = false;
	
	public boolean isTerminado() {
		return terminado;
	}
	public boolean isTurnoComputadora() {
		return turnoComputadora;
	}


	public void setTurnoComputadora(boolean turnoComputadora) {
		this.turnoComputadora = turnoComputadora;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	
	public void defender(Luchador luchador) {
		luchador.setDefendiendo(true);
	}

	public void atacar(Luchador atacador, Luchador atacado) {

		if (!atacado.isDefendiendo()) {
			atacado.recibirGolpe(atacador.atacar());
		} else {
			atacado.defender(atacador.atacar());
		}
	}
	public void descansar(Luchador luchador) {
		luchador.descansa();
	}
	
}
