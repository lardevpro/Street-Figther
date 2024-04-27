package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import modelo.Combate;
import modelo.Luchador;
import modelo.Musica;
import vista.Vista;

/**
 * 
 * Clase que gestiona las acciones entre la vista y el modelo Maneja los eventos
 * de botones, acciones del usuario y lógica de combate Se responsabiliza de la
 * carga de luchadores, combates, sonidos, música de la aplicación y la gestión
 * de modo historia y modo enfrentamiento
 * 
 * Este controlador sigue el patron de Modelo Vista Controlador (MVC)
 * 
 * @author LardevPro
 * @version 1.0
 * @since 26/04/2024
 * 
 */

public class Controlador implements ActionListener, MouseListener {

	// componetes para apuntar a las referencias de la vista
	private JLabel lblAvisosHistoria, lblPesoHistoria, lblEdadHIstoria, lblImagen, lblFisico, lblPotencia, lblVelocidad,
			lblEstatura, lblNombreHIstoria;
	private JTextArea textAreaDescripcionHistoria;
	private JComboBox<String> luchadoresLista;

	// componentes del controlador
	private int combatesGanadosEnHistoriaDeSeguido = 0, posicionSeleccionPersonaje = -1;
	public static int personajesDesbloqueados = 0;
	public static boolean modoHistoria = false;
	private boolean combateGanado = false, modoEnfrentamiento = false, volverDesdeInicio = true,
			jugadorSeleccionado = false, computadoraSeleccionada = false, escuchadoresPanelSeleccionPersonaje = false;
	private Combate combate;
	private Vista vista;
	private Musica musica, sonido;
	private Luchador jugador, computadora;
	private ArrayList<Luchador> jugadores, computadoras;
	private DefaultComboBoxModel<String> jComboLuchadores;
	private ArrayList<Integer> personajesEliminadosPosicion;

	public Controlador(Vista vista) {

		this.vista = vista;
		this.personajesEliminadosPosicion = new ArrayList<Integer>();
		jComboLuchadores = new DefaultComboBoxModel<String>();
		this.sonido = null;
		this.musica = new Musica("src/musica/musica_inicio.wav");
		musica.reproducir();
		iniciarActionListeners();
		jugadores = new ArrayList<Luchador>();
		computadoras = new ArrayList<Luchador>();
		cargarLuchadores(jugadores);
		cargarLuchadores(computadoras);

		// declaración de componentes apuntando a la los componentes de la vista
		lblAvisosHistoria = vista.getLblAvisosHistoria();
		lblNombreHIstoria = vista.getLblNombreHIstoria();
		textAreaDescripcionHistoria = vista.getTextAreaDescripcionHistoria();
		lblPesoHistoria = vista.getLblPesoHistoria();
		lblEdadHIstoria = vista.getLblEdadHIstoria();
		lblImagen = vista.getLblPersonajeHistoriaImagen();
		lblFisico = vista.getLblFisico();
		lblPotencia = vista.getLblPotencia();
		lblVelocidad = vista.getLblVelocidad();
		lblEstatura = vista.getLblEstatura();

		// en el panelHistori muestra la inforación del personaje seleccionado en el
		luchadoresLista = vista.getComboBoxNombresLuchadores();
		luchadoresLista.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarInformacionPersonaje();
				iniciarSonido(sonido,"seleccion");
			}
		});
	}

	/**
	 * Inicia la vista
	 */
	public void iniciarVista() {
		vista.setVisible(true);	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == vista.getBtnLeyendaPersonajes()) {

			irALaPantallaLeyenda();

		} else if (e.getSource() == vista.getBtnVolverAtrasDesdeLeyendas()) {

			volverDesdeLeyenda();

		} else if (e.getSource() == vista.getBtnEnfrentamiento()) {

			modoEnfrentamiento();

		} else if (e.getSource() == vista.getBtnVolverDeInfo()) {

			volverDesdePantallaInformacion();

		} else if (e.getSource() == vista.getBtnModoHistoria()) {

			modoHistoria();

		} else if (e.getSource() == vista.getBtnVolverDesdeSeleccionarPersonaje()) {

			iniciarSonido(sonido, "seleccion");
			volverAIncioDesdeSeleccionPersonaje();

		} else if (e.getSource() == vista.getBtnSeleccionarJugador()) {

			seleccionarJugadorModoEnfrentamiento();
			if (modoEnfrentamiento)
				posicionSeleccionPersonaje = -1;

		} else if (e.getSource() == vista.getBtnSeleccionarComputadora()) {

			if (modoEnfrentamiento)
				seleccionarComputadoraModoEnfrentamiento();
			else if (modoHistoria)
				seleccionarComputadoraModoHistoria();

		} else if (e.getSource() == vista.getBtnJugar()) {

			configuracionParaJugarPartida();

		} else if (e.getSource() == vista.getBtnVolverDesdeJugar()) {

			if (modoEnfrentamiento) {

				refrescarModoHistoriaSeleccionarPersonajes();

			} else if (!combateGanado && modoHistoria) {

				reiniciarModoHistoria(false);

			} else if (combatesGanadosEnHistoriaDeSeguido <= 4 && modoHistoria) {

				mostrarPanelQueCorresponda(false);
				refrescarBotonesSeleccionar();

			} else if (combatesGanadosEnHistoriaDeSeguido == 5 && modoHistoria) {

				mostrarPanelQueCorresponda(true);
				reiniciarModoHistoria(true);

			} else if (modoHistoria && !combateGanado) {

				modoHistoria();
			}

		} else if (e.getSource() == vista.getBtnContinuarDesbloquePersonaje()) {

			detenerMuscia();
			iniciarSonido(sonido, "seleccion");
			irAlSiguientePanel(vista.getPanelDesbloqueoPersonaje(), vista.getPanelSeleccionPersonajes(), 1);

		} else if (e.getSource() == vista.getBtnVovlerDesdeGanador()) {

			detenerMuscia();
			iniciarSonido(sonido, "seleccion");

			ArrayList<Integer> posicionesDesbloqueadas = vista.getPosicionesDesbloqueadas();
			if (!posicionesDesbloqueadas.contains(posicionSeleccionPersonaje)) {
				desbloquearPersonajeVencido();
				mostrarPersonajeADesbloquear();
				irAlSiguientePanel(vista.getPanelGanador(), vista.getPanelDesbloqueoPersonaje(), 1);
				posicionesDesbloqueadas.add(posicionSeleccionPersonaje);
			} else {
				irAlSiguientePanel(vista.getPanelGanador(), vista.getPanelSeleccionPersonajes(), 1);
			}

		} else if (e.getSource() == vista.getBtnAtacar()) {

			atacar();

		} else if (e.getSource() == vista.getBtnDefender()) {

			defender();

		} else if (e.getSource() == vista.getBtnDescansar()) {

			descansar();

		} else if (e.getSource() == vista.getBtnInfomracion()) {

			irAPantallaInformacion();

		}

	}

	private void mostrarPanelQueCorresponda(boolean ganador) {

		tacharPersonajeElimiando();

		ArrayList<Integer> posicionesDesbloqueadas = vista.getPosicionesDesbloqueadas();
		if (!posicionesDesbloqueadas.contains(posicionSeleccionPersonaje)) {
			if (ganador) {
				irAlSiguientePanel(vista.getPanelJuego(), vista.getPanelGanador(), 1);
				refrescarBotonesSeleccionar();
				vista.getBtnSeleccionarJugador().setEnabled(true);
				vista.getBtnSeleccionarJugador().setBackground(Color.YELLOW);
			} else {
				desbloquearPersonajeVencido();
				mostrarPersonajeADesbloquear();
				irAlSiguientePanel(vista.getPanelJuego(), vista.getPanelDesbloqueoPersonaje(), 1);
				posicionesDesbloqueadas.add(posicionSeleccionPersonaje);
			}
		} else {
			if (ganador) {
				irAlSiguientePanel(vista.getPanelJuego(), vista.getPanelGanador(), 1);
				mostrarDatosGanador();
				vista.getBtnSeleccionarJugador().setEnabled(true);
				vista.getBtnSeleccionarJugador().setBackground(Color.YELLOW);
			} else
				irAlSiguientePanel(vista.getPanelJuego(), vista.getPanelSeleccionPersonajes(), 1);
		}

	}

	private void mostrarDatosGanador() {

		String imgs[] = jugador.getImgPelea();
		vista.ponerImagenAJlabel(vista.getLblimgGanador(), imgs[0], false);

	}

	private void desbloquearPersonajeVencido() {

		ArrayList<JLabel> labels = vista.getSeleccionPersonajes();
		String imgs[] = vista.getRutasImagenesSeleccionPersonaje();

		vista.ponerImagenAJlabel(labels.get(posicionSeleccionPersonaje), imgs[posicionSeleccionPersonaje], true);
		vista.aniadirPosicionDesbloqueada(posicionSeleccionPersonaje);
		labels.get(posicionSeleccionPersonaje).addMouseListener(this);

	}

	private void refrescarModoHistoriaSeleccionarPersonajes() {

		combate.setCombateInterrumpido(true);
		refrescarBotonesSeleccionar();
		vista.getBtnSeleccionarJugador().setEnabled(true);
		vista.getBtnSeleccionarJugador().setBackground(Color.YELLOW);
		irAlSiguientePanel(vista.getPanelJuego(), vista.getPanelSeleccionPersonajes(), 1);
		jugadorSeleccionado = false;
		computadoraSeleccionada = false;
		if (!combateGanado)
			posicionSeleccionPersonaje = -1;
		limpiarLabelsDeBorde(true);
		limpiarLabelsDeBorde(false);

	}

	public void reiniciarModoHistoria(boolean finalizado) {

		combate.setCombateInterrumpido(true);
		jugador = null;
		computadora = null;
		jugadorSeleccionado = false;
		combate = null;
		computadoraSeleccionada = false;
		limpiarTextosSeleccionJugador();
		reiniciarVistaJugadores();
		limpiarLabelsDeBorde(true);
		limpiarLabelsDeBorde(false);
		combatesGanadosEnHistoriaDeSeguido = 0;
		if (!finalizado)
			irAlSiguientePanel(vista.getPanelJuego(), vista.getPanelSeleccionPersonajes(), 1);

	}

	public void seleccionarJugadorModoEnfrentamiento() {

		if (posicionSeleccionPersonaje >= 0 && posicionSeleccionPersonaje <= 14) {
			jugador = jugadores.get(posicionSeleccionPersonaje);
			iniciarSonido(sonido, "seleccion");
			mostrarJugadorSonidoVozSeleccionado(posicionSeleccionPersonaje, vista.getLblImgJ1Seleccionado(),
					vista.getLblTitulo1PjSeleccionarPersonaje());
			vista.getBtnSeleccionarJugador().setEnabled(false);
			jugadorSeleccionado = true;
			cargarEscuchadoresModoEnfrentamiento();
			if (computadoraSeleccionada) {
				prepararBotonJugar();
				exclamaciones();
			}
		} else if (posicionSeleccionPersonaje == -1) {
			vista.getLblAvisosSeleccionarJugador().setText("Debe seleccionar un personaje para el jugador");
		}
	}

	public void seleccionarComputadoraModoEnfrentamiento() {

		iniciarSonido(sonido, "seleccion");

		if (posicionSeleccionPersonaje != -1) {
			computadora = computadoras.get(posicionSeleccionPersonaje);
			mostrarJugadorSonidoVozSeleccionado(posicionSeleccionPersonaje, vista.getLblImgJ2Seleccionado(),
					vista.getLblTitulo2PjSeleccionarPersonaje());
			computadoraSeleccionada = true;
			if (jugadorSeleccionado) {
				prepararBotonJugar();
				exclamaciones();
			}

		} else {
			vista.getLblAvisosSeleccionarJugador().setText("Debe seleccionar personaje para computadora");
		}

	}

	private String imagenAleatoria(String[] imagenes) {
		return imagenes[(int) (0 + Math.random() * imagenes.length)];

	}

	private void tacharPersonajeElimiando() {

		ArrayList<JLabel> labelsDesactivar = vista.getSeleccionComputadora();
		ArrayList<JLabel> labels = vista.getSobrepuestosComputadora();

		System.out.println(posicionSeleccionPersonaje);

		labelsDesactivar.get(posicionSeleccionPersonaje).setEnabled(false);
		labelsDesactivar.get(posicionSeleccionPersonaje).setBorder(null);
		vista.ponerImagenAJlabel(labels.get(posicionSeleccionPersonaje), "eliminado.png", true);
		labels.get(posicionSeleccionPersonaje).repaint();
		labels.get(posicionSeleccionPersonaje).revalidate();
	}

	public void refrescarBotonesSeleccionar() {

		vista.getBtnSeleccionarComputadora().setEnabled(true);
		vista.getBtnJugar().setEnabled(false);
		vista.getBtnJugar().setBackground(Color.gray);
		vista.getBtnSeleccionarComputadora().setBackground(Color.yellow);
		vista.getBtnVolverDesdeJugar().setText("Volver");

	}

	private void modoHistoria() {

		detenerMuscia();
		iniciarSonido(sonido, "seleccion");
		modoHistoria = true;
		combateGanado = false;
		personajesEliminadosPosicion.clear();
		posicionSeleccionPersonaje = -1;

		combatesGanadosEnHistoriaDeSeguido = 3;
		vista.caragarPanelSeleccionDePersonajes();
		iniciarSeleccionJugadores();
		cargarEscuhcadoresSeleccionPersonajesMouseListenerModoHistoria();
		if (!escuchadoresPanelSeleccionPersonaje) {
			listenerPanelSeleccionerPersonajes();
			escuchadoresPanelSeleccionPersonaje = true;
		}
	}

	private void configuracionParaJugarPartida() {

		if (combateGanado && modoHistoria) {

			vista.getBtnSeleccionarJugador().setEnabled(false);
			vista.getBtnSeleccionarComputadora().setEnabled(true);
			vista.getBtnJugar().setEnabled(false);
		}

		resetearVidaLuchador(jugador);
		resetearVidaLuchador(computadora);
		reestablecerMarcadores();
		combate = new Combate();
		iniciarSonido(sonido, "seleccion");
		iniciarSonido(sonido, "jugadores_seleccionados");
		irAlSiguientePanel(vista.getPanelSeleccionPersonajes(), vista.getPanelJuego(), 3);
		pantallaJugar();

		jugador.setCombate(combate);
		combate.setJugador(jugador);
		combate.setComputadora(computadora);
		combate.setVistaContador(vista.getLblTiempo());
		combate.setControlador(this);
		combate.start();

	}

	/**
	 * Selección del personaje de la computadora en el modo historia. La computadora
	 * elige un personaje aleatorio para enfrentarse al jugador.
	 */
	private void seleccionarComputadoraModoHistoria() {

		if (posicionSeleccionPersonaje != -1) {
			int aleatorio = 0;
			computadora = new Luchador();

			ArrayList<JLabel> labels = vista.getSeleccionPersonajes();

			if (combatesGanadosEnHistoriaDeSeguido < 4) {
				aleatorio = (int) (Math.random() * (labels.size() - 1));
				while (aleatorio == posicionSeleccionPersonaje || personajesEliminadosPosicion.contains(aleatorio)) {
					aleatorio = (int) (Math.random() * (labels.size() - 1));
				}
			} else if (combatesGanadosEnHistoriaDeSeguido == 4) {
				aleatorio = 14;
			}

			ponerBordeSeleccionPersonaje(aleatorio, false);

			System.out.println(posicionSeleccionPersonaje);
			posicionSeleccionPersonaje = aleatorio;

			mostrarJugadorSonidoVozSeleccionado(posicionSeleccionPersonaje, vista.getLblImgJ2Seleccionado(),
					vista.getLblTitulo2PjSeleccionarPersonaje());

			personajesEliminadosPosicion.add(posicionSeleccionPersonaje);
			computadora = computadoras.get(posicionSeleccionPersonaje);

			exclamaciones();

			prepararBotonJugar();
			iniciarSonido(sonido, "jugador_seleccionado");
			vista.getLblAvisosSeleccionarJugador().setText("");

		} else {
			vista.getLblAvisosSeleccionarJugador().setText("¡¡Debe seleccionar un personaje!!");
		}
	}

	private void resetearVidaLuchador(Luchador luchador) {
		luchador.setCansancio(100);
		luchador.setVida(100);

	}

	private void iniciarSeleccionJugadores() {

		if (volverDesdeInicio) {
			iniciarSonido(sonido, "seleccion_menu");
			volverDesdeInicio = false;

		}
		if (vista.getPanelMenu().isEnabled()) {
			vista.caragarPanelSeleccionDePersonajes();
			irAlSiguientePanel(vista.getPanelMenu(), vista.getPanelSeleccionPersonajes(), 3);
			reiniciarVistaJugadores();
		} else {
			vista.caragarPanelSeleccionDePersonajes();
			irAlSiguientePanel(vista.getPanelJuego(), vista.getPanelSeleccionPersonajes(), 0);
			reiniciarVistaJugadores();

		}

	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {

		if (e.getSource() instanceof JLabel) {

			JLabel label = (JLabel) e.getSource();

			if (posicionSeleccionPersonaje == -1)
				posicionSeleccionPersonaje = Integer.parseInt(label.getName());

			if (!jugadorSeleccionado) {
				ponerBordeSeleccionPersonaje(posicionSeleccionPersonaje, true);
				recogerPosicionYMostrarDatosPersonaje(label);
			} else if (!modoHistoria) {
				ponerBordeSeleccionPersonaje(posicionSeleccionPersonaje, false);
				recogerPosicionYMostrarDatosPersonaje(label);
			}
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void limpiarLabelsDeBorde(boolean jugador) {// o computadora

		ArrayList<JLabel> labels = null;

		if (jugador) {

			labels = vista.getSeleccionPersonajes();
		} else {
			labels = vista.getSeleccionComputadora();
		}

		for (JLabel jLabel : labels) {
			jLabel.setBorder(null);
		}

	}

	private void ponerBordeSeleccionPersonaje(int posicionPersonaje, boolean jugador) {

		Border borde = null;

		ArrayList<JLabel> labels = null;

		if (jugador)
			labels = vista.getSeleccionPersonajes();
		else
			labels = vista.getSeleccionComputadora();

		for (JLabel jLabel : labels) {
			if (jLabel.getName().equals(posicionPersonaje + "")) {
				if (posicionPersonaje != 0)
					iniciarSonido(sonido, "cambio_personaje_seleccion");

				borde = new LineBorder(Color.yellow, 5);
				jLabel.setBorder(borde);
			} else {
				jLabel.setBorder(null);
			}
		}

	}

	private void recogerPosicionYMostrarDatosPersonaje(JLabel label) {

		if (posicionSeleccionPersonaje == -1) {
			vista.getLblAvisosHistoria().setText("!Debe seleccionar un personaje!");
		} else {
			ponerDatosJugadorSeleccionadoEnJLabels(posicionSeleccionPersonaje);
		}

	}

	private void descansar() {

		jugador.getCombate().descansar(jugador);
		jugador.getCombate().setTurnoComputadora(true);
		desactivarAcciones();
		actualizarVistaCombate();

	}

	private void defender() {

		jugador.setDefendiendo(true);
		jugador.getCombate().setTurnoComputadora(true);
		desactivarAcciones();
		actualizarVistaCombate();

	}

	private void atacar() {

		jugador.getCombate().atacar(jugador, computadora);
		iniciarSonido(sonido, procesarSonidosJugador(jugador.getVocesPersonaje()));
		combate.setTurnoComputadora(true);
		cambiarImagenAlAtacar(true, jugador.getImgPelea());
		desactivarAcciones();
		actualizarVistaCombate();

	}

	private void mostrarInformacionPersonaje() {

		int posicionPersonaje = vista.getComboBoxNombresLuchadores().getSelectedIndex();
		mostrarJugadorHistoria(posicionPersonaje - 1);

	}

	// booleano en true cambia la imagen del jugador y en false la del computer
	public void cambiarImagenAlAtacar(boolean jugador, String[] imagenes) {
		if (jugador)
			vista.ponerImagenAJlabel(vista.getLblImagenJ1Juego(), cambiarImagenFondoAlAtacar(imagenes), false);
		else
			vista.ponerImagenAJlabel(vista.getLblImagenJ2Juego(), cambiarImagenFondoAlAtacar(imagenes), false);
	}

	private void modoEnfrentamiento() {

		detenerMuscia();
		iniciarSonido(sonido, "seleccion");
		modoEnfrentamiento = true;
		iniciarSeleccionJugadores();
		cargarEscuchadoresModoEnfrentamiento();

		if (!escuchadoresPanelSeleccionPersonaje) {
			listenerPanelSeleccionerPersonajes();
			escuchadoresPanelSeleccionPersonaje = true;
		}

	}

	public void terminarCombateTacharPerdedor() {

		if (jugador.compareTo(computadora) == 1) {
			iniciarSonido(sonido, "you_win");
			detenerJuego(1);
			eliminarJugadorAnularBontonesActivarContinuar(vista.getLblImagenJ2Juego(), vista.getLblEliminado2(),
					vista.getLblMensajePj2(), computadora);
			combateGanado = true;
			combatesGanadosEnHistoriaDeSeguido++;
		} else if (jugador.compareTo(computadora) == -1) {
			iniciarSonido(sonido, "you_lose");
			detenerJuego(1);
			eliminarJugadorAnularBontonesActivarContinuar(vista.getLblImagenJ1Juego(), vista.getLblEliminado1(),
					vista.getLblMensajePj1(), jugador);
			combateGanado = false;
			combatesGanadosEnHistoriaDeSeguido = 0;
			ponerBordeSeleccionPersonaje(0, true);
		}
	}

	private void desactivarAcciones() {

		vista.getBtnAtacar().setEnabled(false);
		vista.getBtnDefender().setEnabled(false);
		vista.getBtnDescansar().setEnabled(false);

	}

	public static String cambiarImagenFondoAlAtacar(String[] imagenes) {
		return imagenes[1 + (int) (Math.random() * (imagenes.length - 1))];

	}

	public String vozLosePersonaje(String[] voces) {
		return voces[0];
	}

	public static String procesarSonidosJugador(String[] sonidos) {
		return sonidos[(int) (1 + Math.random() * (sonidos.length - 1))];
	}

	public void actualizarVista() {

		vista.getLblVidaPj1().setText(jugador.getVida() + "");
		vista.getLblVidaPj2().setText(computadora.getVida() + "");

		vista.getLblCansancioPj1().setText(jugador.getCansancio() + " %");
		vista.getLblCansancioPj2().setText(computadora.getCansancio() + " %");

		vista.getProgressBarVidaPJ1().setValue(jugador.getVida());
		vista.getProgressBarVidaPJ2().setValue(computadora.getVida());

		vista.getProgressBarVitalidadPj1().setValue(jugador.getCansancio());
		vista.getProgressBarVitalidadPj2().setValue(computadora.getCansancio());

		vista.getLblMensajePj1().setText(jugador.getMensajePelea());
		vista.getLblMensajePj2().setText(computadora.getMensajePelea());

	}

	private void mostrarJugadorSonidoVozSeleccionado(int posicion, JLabel lblImagen, JLabel lblTitulo) {

		if (posicion > 15)
			posicion -= 15;

		Luchador personajeSeleccionado = jugadores.get(posicion);
		String nombreImagenes[] = personajeSeleccionado.getImgPelea();
		vozPersonajeSeleccionado(personajeSeleccionado.getVocesPersonaje());
		lblTitulo.setText(personajeSeleccionado.getNombre());
		vista.ponerImagenAJlabel(lblImagen, nombreImagenes[0], false);

	}

	private void prepararBotonJugar() {

		vista.getBtnJugar().setEnabled(true);
		vista.getBtnJugar().setBackground(Color.YELLOW);
		vista.getBtnSeleccionarJugador().setBackground(Color.gray);
		vista.getBtnSeleccionarJugador().setEnabled(false);
		vista.getBtnSeleccionarComputadora().setBackground(Color.gray);
		vista.getBtnSeleccionarComputadora().setEnabled(false);

	}

	
	private void irALaPantallaLeyenda() {

		// CONFIGURACIONES DE PANEL
		detenerMuscia();
		iniciarSonido(sonido, "seleccion");
		irAlSiguientePanel(vista.getPanelMenu(), vista.getPanelHistoriaPersonajes(), 1);
		iniciarMusica("musica_historia");

		// CONFIGURACIONES DE LUCHADORES
		cargarJComboboxCooNombresLuchadores(jComboLuchadores, jugadores);
		vista.getComboBoxNombresLuchadores().setModel(jComboLuchadores);

	}

	private void volverAIncioDesdeSeleccionPersonaje() {

		volverDesdeInicio = true;
		detenerMuscia();
		irAlSiguientePanel(vista.getPanelSeleccionPersonajes(), vista.getPanelMenu(), 1);
		iniciarMusica("musica_inicio");
		posicionSeleccionPersonaje = -1;

		if (modoHistoria)
			modoHistoria = false;

		if (modoEnfrentamiento)
			modoEnfrentamiento = false;

		if (jugadorSeleccionado)
			jugadorSeleccionado = false;

		if (computadoraSeleccionada)
			computadoraSeleccionada = false;

		if (escuchadoresPanelSeleccionPersonaje)
			escuchadoresPanelSeleccionPersonaje = false;

		ponerBordeSeleccionPersonaje(0, true);

	}

	/**
	 * Método que se encarga de gestionar y preparar la pantalla principal si se
	 * desea volver desde el modo leyenda
	 */
	private void volverDesdeLeyenda() {
		detenerMuscia();
		detenerSonido(sonido);
		iniciarSonido(sonido, "seleccion");
		irAlSiguientePanel(vista.getPanelHistoriaPersonajes(), vista.getPanelMenu(), 1);
		iniciarMusica("musica_inicio");
		limparCamposHistoria();
	}

	/**
	 * Método que se encarga de recoger los nombres de los luchadores en el panel
	 * leyenda y cargarlos en un JComboBox,este componente interactua con el usuario
	 * dándole la opción de seleccionar el que quiere que se muestre
	 * 
	 * @param modelo     tipo JComboBox para cargar todos los nombres de los
	 *                   luchadores
	 * @param luchadores recibe la lista de luchadores que se cargan en el modelo
	 */
	public void cargarJComboboxCooNombresLuchadores(DefaultComboBoxModel<String> modelo,
			ArrayList<Luchador> luchadores) {

		modelo.addElement("Lista de Personajes");
		for (Luchador luchador : luchadores) {
			modelo.addElement(luchador.getNombre());
		}
	}

	/**
	 * Método que inicia todos los temas musicales referentes al juego
	 * 
	 * @param nombreTema que se reproducirá en esa sección
	 */
	public void iniciarMusica(String nombreTema) {
		this.musica = new Musica("src/musica/" + nombreTema + ".wav");
		musica.reproducir();
	}

	/**
	 * Método que se encargará de recrear los sonidos correspondientes a un momento
	 * breve de tiempo en la aplicación
	 * 
	 * @param sonido Objeto de la clase Música que recibre para iniciar o
	 *               reiniciarlo
	 * @nombre String que se refiere al titulo del sonido
	 * 
	 */
	public static void iniciarSonido(Musica sonido, String nombre) {
		
		if(sonido != null)
			sonido.detener();
		
		sonido = new Musica("src/musica/" + nombre + ".wav");
		sonido.reproducir();
	}

	/**
	 * Método que se encarga de detener un sonido para que no haya conflictos entre
	 * hilos
	 * 
	 * @param sonido objeto de la clase Musica
	 */
	public static void detenerSonido(Musica sonido) {
		if (sonido != null) {
			sonido.detener();
			sonido = null;
		}
	}

	/**
	 * Método que se encarga de detener la música antes del transito entre secciones
	 * impidiendo conflictos y liberando recursos
	 */
	public void detenerMuscia() {
		if (this.musica != null) {
			this.musica.detener();
			this.musica = null;
		}
	}

	/**
	 * Método que se encarga de preparar el panel de infromación, sus componentes y
	 * multimedia
	 */
	private void irAPantallaInformacion() {
		detenerMuscia();
		iniciarSonido(sonido, "cambio_personaje_seleccion");
		detenerJuego(1);
		iniciarMusica("musica_info");
		irAlSiguientePanel(vista.getPanelMenu(), vista.getPanelInformacion(), 1);

	}

	/**
	 * Método que se encarga de preparar el panel principal, sus componentes y
	 * multimedia
	 */
	private void volverDesdePantallaInformacion() {
		detenerMuscia();
		iniciarSonido(sonido, "cambio_personaje_seleccion");
		detenerJuego(1);
		irAlSiguientePanel(vista.getPanelInformacion(), vista.getPanelMenu(), 1);
		iniciarMusica("musica_inicio");

	}

	/**
	 * Método que gestiona los paneles
	 * 
	 * @param aOcultar objeto tipo JPanle que será ocultado
	 * @param aMostrar objeto tipo JPanel que se mostrará
	 * @param pausa    tiempo en segundos que habrá de pausa entre cambios de panel
	 */
	public void irAlSiguientePanel(JPanel aOcultar, JPanel aMostrar, int pausa) {
		try {
			Thread.sleep(pausa * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		aOcultar.setVisible(false);
		aMostrar.setVisible(true);
	}

	/**
	 * Método que recibe un array de String siendo estos titulos de las voces
	 * correspondientes a luchadores, una vez dentro del método se escogerá un
	 * sonido aleatorio cada vez
	 * 
	 * @param voces array de String que recoge las voces de los personajes
	 */
	private void vozPersonajeSeleccionado(String voces[]) {
		int aleatorioVoz = (int) (1 + Math.random() * (voces.length - 1));
		iniciarSonido(sonido, voces[aleatorioVoz]);
	}

	/**
	 * Método que se encarga de detener el juego por el tiempo deseado cuando se es
	 * llamado
	 * 
	 * @param tiempo será los segundos que se detendrá
	 */
	public void detenerJuego(int tiempo) {
		try {
			Thread.sleep(tiempo * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Método que activa los botones del panelCombate (no se puede iniciar con los
	 * demás porque el JPanel es creado después)
	 */
	public void activarBotonesPelea() {
		vista.getBtnAtacar().setEnabled(true);
		vista.getBtnDefender().setEnabled(true);
		vista.getBtnDescansar().setEnabled(true);

	}

	/**
	 * Método que muestra el jugador desbloqueado en panelPersonajeDesbloqueado
	 * (solo en modo historia)
	 */
	private void mostrarPersonajeADesbloquear() {

		Luchador desbloqueado = jugadores.get(posicionSeleccionPersonaje);

		iniciarSonido(sonido, "nuevo_personaje_desbloqueado");

		vista.getLblNombreDesbloqueo().setText(desbloqueado.getNombre());
		vista.getLblEdadDesbloqueoPersonaje().setText(desbloqueado.getEdad() + "");
		vista.getLblEstaturaDesbloqueoPersonaje().setText(desbloqueado.getEstatura() + "");
		vista.getLblPesoDesbloqueoPersonaje().setText(desbloqueado.getPeso() + "");
		vista.getLblFisicoDesbloqueoPersonaje().setText(desbloqueado.getFisico() + "");
		vista.getLabelVelocidadDesbloqueoPersonaje().setText(desbloqueado.getVelocidad() + "");
		vista.getLblPtenciaDesbloquearPersonaje().setText(desbloqueado.getPotencia() + "");
		vista.getTextPaneDescripcionDesbloqueo().setText(desbloqueado.getDescripcion());
		vista.ponerImagenAJlabel(vista.getLblImgJugadorDesbloqueado(), imagenAleatoria(desbloqueado.getImgPelea()),
				false);

	}

	/**
	 * Método que actualiza los marcadores en el panelCombate cada vez que es
	 * llamado
	 */
	public void actualizarVistaCombate() {

		if (jugador != null) {
			vista.getLblVidaPj1().setText(jugador.getVida() + "");
			vista.getLblCansancioPj1().setText(jugador.getCansancio() + " %");
			vista.getProgressBarVidaPJ1().setValue(jugador.getVida());
			vista.getProgressBarVitalidadPj1().setValue(jugador.getCansancio());
			vista.getLblMensajePj1().setText(jugador.getMensajePelea());

			vista.getLblVidaPj2().setText(computadora.getVida() + "");
			vista.getLblCansancioPj2().setText(computadora.getCansancio() + " %");
			vista.getProgressBarVidaPJ2().setValue(computadora.getVida());
			vista.getProgressBarVitalidadPj2().setValue(computadora.getCansancio());
			vista.getLblMensajePj2().setText(computadora.getMensajePelea() + "");
		}
	}

	/**
	 * Método que carga los escuchadores en el panelSeleccionPersonajes (no se carga
	 * al principio porque el panel no es estático)
	 */
	private void cargarEscuhcadoresSeleccionPersonajesMouseListenerModoHistoria() {

		ArrayList<JLabel> labels = vista.getSeleccionPersonajes();
		ArrayList<Integer> posicionesDesbloqueadas = vista.getPosicionesDesbloqueadas();

		if (modoHistoria) {
			for (int i = 0; i < labels.size(); i++) {
				if (posicionesDesbloqueadas.contains(i))
					labels.get(i).addMouseListener(this);
			}
		} else {

			for (int i = 0; i < labels.size(); i++) {
				labels.get(i).addMouseListener(this);
			}
		}
	}

	/*
	 * Carga los escuchadores correspondientes para que no se puedan seleccionar
	 * personajes que no han sido seleccionados (solo en modo historia)
	 */
	private void cargarEscuchadoresModoEnfrentamiento() {

		ArrayList<JLabel> labels = null;

		if (!jugadorSeleccionado)
			labels = vista.getSeleccionPersonajes();
		else
			labels = vista.getSeleccionComputadora();

		for (JLabel label : labels) {
			label.addMouseListener(this);
		}
	}

	/**
	 * Método que hace un reset a los componentes del panelCombate cuando se llama
	 */
	private void reestablecerMarcadores() {

		vista.getLblVidaPj1().setText(jugador.getVida() + "");

		vista.getLblVidaPj2().setText(computadora.getVida() + "");

		vista.getLblCansancioPj1().setText(jugador.getCansancio() + "");
		vista.getLblCansancioPj2().setText(computadora.getCansancio() + "");

		vista.getProgressBarVidaPJ1().setValue(jugador.getVida());
		vista.getProgressBarVidaPJ2().setValue(computadora.getVida());
		vista.getProgressBarVitalidadPj1().setValue(jugador.getCansancio());
		vista.getProgressBarVitalidadPj2().setValue(computadora.getCansancio());

		vista.getLblEliminado1().setIcon(null);
		vista.getLblEliminado2().setIcon(null);

		vista.getLblImagenJ1Juego().setEnabled(true);
		vista.getLblImagenJ2Juego().setEnabled(true);

		vista.getLblMensajePj1().setText("");
		vista.getLblMensajePj2().setText("");

		vista.getLblMensajePj1().setOpaque(false);

		vista.getLblMensajePj2().setOpaque(false);

		vista.getBtnAtacar().setEnabled(true);
		vista.getBtnDefender().setEnabled(true);
		vista.getBtnDescansar().setEnabled(true);

	}

	/**
	 * 
	 * @param jugadorImg
	 * @param labelTachado
	 * @param mensajeLucha
	 * @param pj
	 */
	public void eliminarJugadorAnularBontonesActivarContinuar(JLabel jugadorImg, JLabel labelTachado,
			JLabel mensajeLucha, Luchador pj) {
		jugadorImg.setEnabled(false);
		vista.ponerImagenAJlabel(labelTachado, "eliminado.png", false);
		vista.getBtnAtacar().setEnabled(false);
		vista.getBtnDefender().setEnabled(false);
		vista.getBtnDescansar().setEnabled(false);
		vista.getBtnVolverDesdeJugar().setBackground(Color.yellow);
		vista.getBtnVolverDesdeJugar().setText("CONTINUAR");
		mensajeLucha.setOpaque(true);
		mensajeLucha.setForeground(Color.RED);
		mensajeLucha.setText("¡¡" + pj.getNombre() + " pierde el combate!!");
		iniciarSonido(sonido, vozLosePersonaje(pj.getVocesPersonaje()));

	}

	private void reiniciarVistaJugadores() {

		if (!combateGanado) {
			vista.getLblImgJ1Seleccionado().setIcon(null);
			vista.getBtnJugar().setEnabled(false);
			vista.getBtnJugar().setBackground(Color.gray);
			vista.getBtnSeleccionarJugador().setEnabled(true);
			vista.getBtnSeleccionarJugador().setBackground(Color.yellow);
			vista.getBtnSeleccionarJugador().setEnabled(true);
		}
		vista.getLblImgJ2Seleccionado().setIcon(null);
		vista.getBtnSeleccionarComputadora().setEnabled(true);
		vista.getBtnSeleccionarComputadora().setBackground(Color.yellow);
		vista.getBtnSeleccionarComputadora().setEnabled(true);

		limpiarTextosSeleccionJugador();

		combate = new Combate();

	}

	private void ponerDatosJugadorSeleccionadoEnJLabels(int posicion) {

		vista.getLblNombreSeleccionPersonaje().setText(jugadores.get(posicion).getNombre());
		vista.getLblPotenciaSeleccionarPersonaje().setText(jugadores.get(posicion).getPotencia() + "");
		vista.getLblFisicoSeleccionarPersonaje().setText(jugadores.get(posicion).getFisico() + "");
		vista.getLblVelocidadSeleccionarPersonaje().setText(jugadores.get(posicion).getVelocidad() + "");

	}

	private void pantallaJugar() {

		int aleatorioPj1 = (int) (1 + Math.random() * 3);
		int aleatorioPj2 = (int) (1 + Math.random() * 3);
		String imagenesPj1[] = jugador.getImgPelea();
		String imagenesPj2[] = computadora.getImgPelea();

		vista.getLblNombrePj1PanelJugar().setText(jugador.getNombre());
		vista.getLblNombrePj2PanelJugar().setText(computadora.getNombre());
		vista.ponerImagenAJlabel(vista.getLblImagenJ1Juego(), imagenesPj1[aleatorioPj1], false);
		vista.ponerImagenAJlabel(vista.getLblImagenJ2Juego(), imagenesPj2[aleatorioPj2], false);
		vista.ponerImagenAJlabel(vista.getLabelKO(), "ko.png", false);
		vista.ponerImagenAJlabel(vista.getLblImgVS(), "vs.png", false);

		vista.getLblVidaPj1().setText(jugador.getVida() + "");
		vista.getLblVidaPj2().setText(computadora.getVida() + "");

		vista.getLblCansancioPj1().setText(jugador.getCansancio() + " %");
		vista.getLblCansancioPj2().setText(computadora.getCansancio() + " %");

	}

	/**
	 * Método que resetea los valores de los componentes en el
	 * panelSeleccionarJugador
	 */
	private void limpiarTextosSeleccionJugador() {

		vista.getLblTitulo1PjSeleccionarPersonaje().setText("Jugador");
		vista.getLblTitulo2PjSeleccionarPersonaje().setText("Computadora");
		vista.getLblFisicoSeleccionarPersonaje().setText("?");
		vista.getLblNombreSeleccionPersonaje().setText("?");
		vista.getLblPotenciaSeleccionarPersonaje().setText("?");
		vista.getLblVelocidadSeleccionarPersonaje().setText("?");

	}

	/**
	 * Metodo que se encarga de poner exclamaciones en los marcadores de la pantalla
	 * panelSeleccionarJugador de que pulsa 'ir a combate'
	 */
	private void exclamaciones() {
		vista.getLblFisicoSeleccionarPersonaje().setText("¡");
		vista.getLblNombreSeleccionPersonaje().setText("¡");
		vista.getLblPotenciaSeleccionarPersonaje().setText("¡");
		vista.getLblVelocidadSeleccionarPersonaje().setText("¡");

	}

	/**
	 * Limpia todos los campos de la interfaz de usuario relacionados con la
	 * historia del jugador estableciendo valores predeterminados o vacíos en cada
	 * uno de ellos.
	 */
	private void limparCamposHistoria() {

		lblAvisosHistoria.setText("");
		lblNombreHIstoria.setText("?");
		lblEstatura.setText("?");
		textAreaDescripcionHistoria.setText("?");
		lblPesoHistoria.setText("?");
		lblEdadHIstoria.setText("?");
		lblFisico.setText("?");
		lblPotencia.setText("?");
		lblVelocidad.setText("?");

		vista.ponerImagenAJlabel(vista.getLblPersonajeHistoriaImagen(), "interrogacion_historia.png", false);// cambiar
																												// imagen
																												// principal
																												// por
																												// una
																												// interrogacion
																												// '?'

	}

	/**
	 * Muestra la información de un jugador en la interfaz de usuario basado en la
	 * posición especificada. Si la posición es válida, se muestra la información
	 * del jugador correspondiente. Si la posición es inválida, se muestra un
	 * mensaje de advertencia en la interfaz de usuario.
	 *
	 * @param posicion La posición del jugador en la lista de jugadores.
	 */
	public void mostrarJugadorHistoria(int posicion) {

		if (posicion > -1) {
			jugador = jugadores.get(posicion);
			lblAvisosHistoria.setText("");
			lblEstatura.setText(String.valueOf(jugador.getEstatura()));
			lblNombreHIstoria.setText(jugador.getNombre());
			textAreaDescripcionHistoria.setText(jugador.getDescripcion());
			lblPesoHistoria.setText(jugador.getPeso() + " kg");
			lblEdadHIstoria.setText(String.valueOf(jugador.getEdad()));
			vista.ponerImagenAJlabel(lblImagen, jugador.getImgPelea()[0], false);
			lblFisico.setText(String.valueOf(jugador.getFisico()));
			lblPotencia.setText(String.valueOf(jugador.getPotencia()));
			lblVelocidad.setText(String.valueOf(jugador.getVelocidad()));
		} else {
			limparCamposHistoria();
			lblAvisosHistoria.setText("DEBE SELECCIONAR UN PERSONAJE");
		}

	}

	public void iniciarActionListeners() {

		vista.getBtnEnfrentamiento().addActionListener(this);
		vista.getBtnModoHistoria().addActionListener(this);
		vista.getBtnInfomracion().addActionListener(this);
		vista.getBtnLeyendaPersonajes().addActionListener(this);
		vista.getBtnVolverAtrasDesdeLeyendas().addActionListener(this);
		vista.getBtnAtacar().addActionListener(this);
		vista.getBtnDefender().addActionListener(this);
		vista.getBtnDescansar().addActionListener(this);
		vista.getBtnVolverDesdeJugar().addActionListener(this);
		vista.getBtnContinuarDesbloquePersonaje().addActionListener(this);
		vista.getBtnVolverDeInfo().addActionListener(this);
		vista.getBtnVovlerDesdeGanador().addActionListener(this);

	}

	/**
	 * Método que
	 */
	private void listenerPanelSeleccionerPersonajes() {
		vista.getBtnJugar().addActionListener(this);
		vista.getBtnVolverDesdeSeleccionarPersonaje().addActionListener(this);
		vista.getBtnSeleccionarJugador().addActionListener(this);
		vista.getBtnSeleccionarComputadora().addActionListener(this);
	}

	/**
	 * Carga una lista de luchadores con sus respectivas características y detalles
	 * de la historia. Agrega los luchadores a la lista proporcionada como
	 * parámetro.
	 *
	 * @param luchadores La lista de luchadores a la que se agregarán los luchadores
	 *                   cargados.
	 */
	public void cargarLuchadores(ArrayList<Luchador> luchadores) {

		Luchador luchadorAux;
		// Información de los luchadores
		String[][] infoLuchadores = {
				// Nombre, País, Edad, Estatura, Peso, Potencia, Velocidad, Físico, Descripción,imagenes, voces
				{ "Ryu", "Japón", "26", "1.75", "85", "27", "32", "31",
						"Luchador entrenado por Gouken famoso por su potente Hadoken, golpe ganador con el que derrotó\r\na Sagat en el primer torneo y lo hirió gravemente.",
						"ryu_historia.png", "ryu_ataque1.png", "ryu_ataque2.png", "ryu_ataque3.png", "ryu_pierde",
						"ryus-hadouken", "ryus-shoryuken", "ryus-tatsumaki" },
				{ "Ken", "EEUU", "25", "1.75", "86", "28", "30", "32",
						"Descendiente de una rica familia estadounidense,su padre pagó para formarle en kárate.\r\nDiscípulo de Gouken igual que Ryu pretende ser el mejor luchador del mundo, por encima de Ryu.",
						"ken_historia.png", "ken_ataque1.png", "ken_ataque2.png", "ken_ataque3.png", "kens-death",
						"kens-hadouken", "kens-shoryuken", "kens-tatsumaki" },
				{ "Guile", "EEUU", "42", "1.85", "100", "34", "23", "33",
						"Ingresa al torneo para poner a M.Bison bajo custodia por ser el asesino de su mejor amigo\r\nCharlie, sólo la victoria sobre el mismo le hará enfrentarse a él y poder derrotarlo.",
						"guile_historia.png", "guile_ataque1.png", "guile_ataque2.png", "guile_ataque3.png",
						"guile_dead", "guile_sarenKum", "guile_3", "guile_4" },
				{ "Chun-Li", "China", "19", "1.65", "58", "18", "21", "51",
						"Artista marcial experta y oficial de la Interpol. Sin descanso, ella busca venganza por la muerte de\r\nsu padre a manos del líder de la organización criminal Shadaloo, M.Bison.",
						"chun_li_historia.png", "chun_li_ataque1.png", "chun_li_ataque2.png", "chun_li_ataque3.png",
						"chun_li_dead", "chun_li_ataque1", "chun_li_ataque2", "chun_li_ataque3" },
				{ "Honda", "Japón", "40", "1.89", "170", "46", "26", "18",
						"Luchador de sumo profesional de Japón que entra al torneo para demostrar que el sumo es el\r\nmejor estilo de lucha del mundo por lo que retará a todo el que pretenda enfrentarse a él.",
						"honda_historia.png", "honda_ataque1.png", "honda_ataque2.png", "honda_ataque3.png",
						"honda_dead", "honda_ataque1", "honda_ataque2", "honda_ataque3" },
				{ "Dhalsim", "India", "58", "1.76", "65", "24", "32", "34",
						"Pacifista pero entró a un torneo de lucha para recaudar dinero para su aldea empobrecida.\r\nHa dedicado su vida a la meditación lo que le permite expulsar fuego por la boca y estirar su cuerpo.",
						"dhalsim_historia.png", "dhalsim_ataque1.png", "dhalsim_ataque2.png", "dhalsim_ataque3.png",
						"dhalsim_dead", "dhalsim_ataque1", "dhalsim_ataque2", "dhalsim_ataque3" },
				{ "Blanka", "Brasil", "22", "1.85", "98", "22", "36", "32",
						"Hombre brasileño cuyo cuerpo ha sido infectado con demasiada clorofila proveniente de las selvas\r\ndonde vive. Es famoso por su movimiento especial eléctrico y sus movimientos rodantes.",
						"blanka_historia.png", "blanka_ataque1.png", "blanka_ataque2.png", "blanka_ataque3.png",
						"blanka_dead", "blanka_ataque1", "blanka_ataque2", "blanka_ataque3" },
				{ "Zangief", "Rusia", "44", "2.13", "160", "39", "36", "15",
						"Luchador ruso acostumbrado a entrenar con grandes osos. Es un luchador lento, pero si\r\nlogra agarrarte, estás acabado. Ingresa al evento por motivos económicos.",
						"zangief_historia.png", "zangief_ataque1.png", "zangief_ataque2.png", "zangief_ataque3.png",
						"zangief_dead", "zangief_ataque1", "zangief_ataque2", "zangief_ataque3" },
				{ "Dee Jay", "Jamaica", "31", "1.89", "87", "26", "25", "39",
						"Cantante que se interesó en un torneo de lucha como medio para ganar popularidad. Su fortaleza\r\nviene de un desastre en un concierto que le hizo ganar una onda expansiva en sus puños.",
						"dee_jay_historia.png", "dee_jay_ataque1.png", "dee_jay_ataque2.png", "dee_jay_ataque3.png",
						"dee_jay_dead", "dee_jay_ataque1", "dee_jay_ataque2", "dee_jay_ataque3" },
				{ "T.Hawk", "Mexico", "39", "2.06", "112", "37", "31", "22",
						"Ingresa al torneo para vengar a sus compatriotas indios nativos, ya que Bison destruyó\r\nsu asentamiento por el oro y la cantidad de riquezas que poseían.",
						"thawk_historia.png", "thahwk_ataque1.png", "thahwk_ataque2.png", "thahwk_ataque3.png",
						"t_hawk_dead", "t_hawk_ataque1", "t_hawk_ataque2", "t_hawk_ataque3" },
				{ "Cammy", "Reino Unido", "26", "1.68", "65", "12", "33", "45",
						"Tiene algunos lazos misteriosos con M.Bison, es una especialista de las fuerzas especiales del\r\nejército británico conocido como Delta Red.",
						"cammy_historia.png", "cammy_ataque1.png", "cammy_ataque2.png", "cammy_ataque3.png",
						"cammy_dead", "cammy_ataque1", "cammy_ataque2", "cammy_ataque3" },
				{ "Balrog", "EEUU", "35", "1.98", "118", "55", "16", "17",
						"Ex boxeador profesional que trabaja bajo las órdenes de M.Bison en la organización criminal de\r\nShadaloo. Lucha pura y exclusivamente con los puños.",
						"balrog_historia.png", "balrog_ataque1.png", "balrog_ataque2.png", "balrog_ataque3.png",
						"balrog_dead", "balrog_ataque1", "balrog_ataque2", "balrog_ataque3" },
				{ "Sagat", "Tailandia", "49", "2.25", "150", "35", "35", "20",
						"Integrante de Shadaloo, ingresa al torneo por venganza contra Ryu que años antes en una pelea le\r\nncausó la cicatriz que tiene en el pecho.",
						"sagat_historia.png", "sagat_ataque1.png", "sagat_ataque2.png", "sagat_ataque3.png",
						"sagat_dead", "sagat_ataque1", "sagat_ataque2", "sagat_ataque3" },
				{ "M.Bison", "Desconocida", "51", "2.10", "115", "37", "38", "25",
						"Líder de la organización criminal de Shadaloo. Es el organizador principal del torneo, aunque\r\n realmente es una tapadera, es un contrabandista de armas y drogas cegado por el poder.",
						"mbison_historia.png", "mbison_ataque1.png", "mbison_ataque2.png", "mbison_ataque3.png",
						"m_bison_dead", "m_bison_ataque1", "m_bison_ataque2", "m_bison_ataque3" } };

		//bucle que introduce la información en los objetos luchador
		for (String[] info : infoLuchadores) {
			
			//recogida de datos
			String nombre = info[0];
			String pais = info[1];
			int edad = Integer.parseInt(info[2]);
			double estatura = Double.parseDouble(info[3]);
			int peso = Integer.parseInt(info[4]);
			int potencia = Integer.parseInt(info[5]);
			int velocidad = Integer.parseInt(info[6]);
			int fisico = Integer.parseInt(info[7]);
			String descripcion = info[8];
			String[] imgPelea = { info[9], info[10], info[11], info[12] };
			String[] voces = { info[13], info[14], info[15], info[16] };

			//creación del luchador
			luchadorAux = new Luchador(nombre, edad, potencia, estatura, velocidad, pais, peso, fisico, descripcion,voces, imgPelea, true);

			//almacenamiento de los luchadores en una lista
			luchadores.add(luchadorAux);
		}
	}
}
