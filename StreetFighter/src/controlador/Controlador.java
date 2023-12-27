package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import modelo.Combate;
import modelo.CuentaAtras;
import modelo.Luchador;
import modelo.Musica;
import vista.Vista;

public class Controlador implements ActionListener, MouseListener {

	private static int j1 = -1, j2 = -1, personajesDesubiertos = 10;
	private boolean j1Seleccionado = false, j2Seleccionado = false;
	private boolean historia = false;
	private Combate combate;
	private Vista vista;
	private Musica musica, sonido;
	private Luchador jugador, computadora;
	private ArrayList<Luchador> luchadores;
	private DefaultComboBoxModel<String> jComboLuchadores;
	private CuentaAtras cuenta;

	public Controlador(Luchador pJ2) {
		this.computadora = pJ2;
	}

	public Controlador(Vista vista) {
		this.vista = vista;
		jComboLuchadores = new DefaultComboBoxModel<String>();
		luchadores = new ArrayList<Luchador>();
		this.sonido = null;
		this.musica = new Musica("src/musica/musica_inicio.wav");
		musica.reproducir();
		iniciarActionListeners();
		cargarEscuhcadoresSeleccionPersonajesMouseListener();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == vista.getBtnLeyendaPersonajes()) {

			// CONFIGURACIONES DE PANEL
			detenerMuscia();
			iniciarSonido(sonido, "seleccion");
			irAlSiguientePanel(vista.getPanelMenu(), vista.getPanelHistoriaPersonajes(), 1);
			iniciarMusica("musica_historia");

			// CONFIGURACIONES DE LUCHADORES
			cargarLuchadores(luchadores);
			cargarJComboboxCooNombresLuchadores(jComboLuchadores, luchadores);

			vista.getComboBoxNombresLuchadores().setModel(jComboLuchadores);

		} else if (e.getSource() == vista.getBtnVolverAtrasDesdeLeyendas()) {

			detenerMuscia();
			detenerSonido();
			iniciarSonido(sonido,"seleccion");
			irAlSiguientePanel(vista.getPanelHistoriaPersonajes(), vista.getPanelMenu(), 1);
			iniciarMusica("musica_inicio");
			limparCamposHistoria();

		} else if (e.getSource() == vista.getBtnMostrarHistoriaPersonaje()) {

			iniciarSonido(sonido,"seleccion");

			int posicionPersonaje = vista.getComboBoxNombresLuchadores().getSelectedIndex();

			mostrarJugadorHistoria(posicionPersonaje - 1);

		} else if (e.getSource() == vista.getBtnEnfrentamiento()) {

			iniciarSonido(sonido, "seleccion");
			limpiarTextosSeleccionJugador();
			reiniciarJugadores();
			detenerMuscia();
			cargarLuchadores(luchadores);
			iniciarSonido(sonido, "seleccion_menu");
			irAlSiguientePanel(vista.getPanelMenu(), vista.getPanelSeleccionPersonajes(), 3);
			vista.caragarPeronajeseSeleccion(15);
			vista.getLblTitulo1PjSeleccionarPersonaje().setText("Jugador 1");
			vista.getLblTitulo2PjSeleccionarPersonaje().setText("Jugador 2");

		} else if (e.getSource() == vista.getBtnVolverDesdeSeleccionarPersonaje()) {

			iniciarSonido(sonido,"seleccion");
			detenerMuscia();
			irAlSiguientePanel(vista.getPanelSeleccionPersonajes(), vista.getPanelMenu(), 1);
			iniciarMusica("musica_inicio");

		} else if (e.getSource() == vista.getBtnSeleccionarPersonaje()) {

			if (j1Seleccionado == false) {

				j1Seleccionado = mostrarJugador(j1, vista.getLblImgJ1Seleccionado(),
						vista.getLblTitulo1PjSeleccionarPersonaje());
				jugador = luchadores.get(j1);
				iniciarSonido(sonido,"jugador_seleccionado");
			} else {

				j2Seleccionado = mostrarJugador(j2, vista.getLblImgJ2Seleccionado(),
						vista.getLblTitulo2PjSeleccionarPersonaje());
				computadora = luchadores.get(j2);

				prepararBotonJugar();

				ponerBordePersonajeSeleccionado(j1 + "", j2 + "");

				exclamaciones();

				iniciarSonido(sonido,"jugador_seleccionado");

			}

		} else if (e.getSource() == vista.getBtnJugar()) {

			detenerMuscia();

			detenerSonido();

			iniciarSonido(sonido,"seleccion");

			iniciarSonido(sonido,"jugadores_seleccionados");

			irAlSiguientePanel(vista.getPanelSeleccionPersonajes(), vista.getPanelJuegoEntrenamiento(), 3);

			pantallaJugar();

			cuenta = new CuentaAtras(vista.getLblTiempo());

			cuenta.start();

			combate = new Combate();

			computadora.setRival(jugador);

			computadora.setCombate(combate);
			
			computadora.setVista(vista);

			jugador.setCombate(combate);

			computadora.start();

		} else if (e.getSource() == vista.getBtnVolverDesdeJugar()) {

			irAlSiguientePanel(vista.getPanelJuegoEntrenamiento(), vista.getPanelSeleccionPersonajes(), 1);

		} else if (e.getSource() == vista.getBtnAtacar()) {

			if (!combate.isTurnoComputadora()) {
				jugador.getCombate().atacar(jugador, computadora);
				iniciarSonido(sonido, procesarSonidosJugador(jugador.getVocesPersonaje()));
				vista.ponerImagenAJlabel(vista.getLblImagenJ1Juego(), cambiarImagenFondoAlAtacar(jugador.getImgPelea()), false);
				combate.setTurnoComputadora(true);
				desactivarAcciones();
				actualizarVista();
				jugador.getCombate().setTerminado(comprobarTiempo());
			} 

		} else if (e.getSource() == vista.getBtnDefender()) {

			if (!combate.isTurnoComputadora()) {
				jugador.setDefendiendo(true);
				jugador.getCombate().setTurnoComputadora(true);
				desactivarAcciones();
				actualizarVista();
				jugador.getCombate().setTerminado(comprobarTiempo());
			} 

		} else if (e.getSource() == vista.getBtnDescansar()) {

			if (!combate.isTurnoComputadora()) {
				jugador.getCombate().descansar(jugador);
				jugador.getCombate().setTurnoComputadora(true);
				desactivarAcciones();
				actualizarVista();
				jugador.getCombate().setTerminado(comprobarTiempo());

			}

		}

	}

	private void desactivarAcciones() {

		vista.getBtnAtacar().setEnabled(false);
		vista.getBtnDefender().setEnabled(false);
		vista.getBtnDescansar().setEnabled(false);

	}

	
	private String cambiarImagenFondoAlAtacar(String[] imagenes) {
		return imagenes[1 + (int) (Math.random() * (imagenes.length - 1))];

	}

	public String vozLosePersonaje(String[] voces) {
		return voces[0];
	}

	public static String procesarSonidosJugador(String[] sonidos) {
		return sonidos[(int) (1 + Math.random() * (sonidos.length - 1))];
	}

	public boolean comprobarTiempo() {
		return cuenta.isTerminado();
	}

	public void eliminarJugadorAnularBontonesActivarContinuar(JLabel jugadorImg, JLabel labelTachado,
			JLabel mensajeLucha, Luchador pj) {
		jugadorImg.setEnabled(false);
		vista.ponerImagenAJlabel(labelTachado, "eliminado.png", false);
		vista.getBtnAtacar().setEnabled(false);
		vista.getBtnDefender().setEnabled(false);
		vista.getBtnDescansar().setEnabled(false);
		cuenta.setTerminado(true);
		vista.getBtnVolverDesdeJugar().setEnabled(true);
		vista.getBtnVolverDesdeJugar().setOpaque(true);
		vista.getBtnVolverDesdeJugar().setBackground(Color.yellow);
		vista.getBtnVolverDesdeJugar().setForeground(Color.RED);
		vista.getBtnVolverDesdeJugar().setText("CONTINUAR");
		mensajeLucha.setOpaque(true);
		mensajeLucha.setForeground(Color.RED);
		mensajeLucha.setText("¡¡" + pj.getNombre() + " pierde el combate!!");

		iniciarSonido(sonido,vozLosePersonaje(pj.getVocesPersonaje()));

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

	

	private boolean mostrarJugador(int posicion, JLabel lblImagen, JLabel lblTitulo) {

		Luchador personajeSeleccionado = luchadores.get(posicion);
		String nombreImagenes[] = personajeSeleccionado.getImgPelea();
		vozPersonajeSeleccionado(personajeSeleccionado.getVocesPersonaje());
		lblTitulo.setText(personajeSeleccionado.getNombre());
		vista.ponerImagenAJlabel(lblImagen, nombreImagenes[0], false);

		return true;
	}

	private void reiniciarJugadores() {

		vista.getBtnSeleccionarPersonaje().setEnabled(true);
		vista.getLblImgJ1Seleccionado().setIcon(null);
		vista.getLblImgJ2Seleccionado().setIcon(null);

		jugador = null;
		computadora = null;
		j1Seleccionado = false;
		j2Seleccionado = false;

	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() instanceof JLabel) {
			JLabel label = (JLabel) e.getSource();

			if (!j1Seleccionado || !j2Seleccionado) {
				iniciarMusica("cambio_personaje_seleccion");
				ponerBordePersonajeSeleccionado(label.getName(), "");

				int posicion = Integer.parseInt(label.getName());

				vista.getLblNombreSeleccionPersonaje().setText(luchadores.get(posicion).getNombre());
				vista.getLblPotenciaSeleccionarPersonaje().setText(luchadores.get(posicion).getPotencia() + "");
				vista.getLblFisicoSeleccionarPersonaje().setText(luchadores.get(posicion).getFisico() + "");
				vista.getLblVelocidadSeleccionarPersonaje().setText(luchadores.get(posicion).getVelocidad() + "");

				if (!j1Seleccionado)
					j1 = posicion;
				else if (!j2Seleccionado)
					j2 = posicion;

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

	
	
	private void prepararBotonJugar() {

		vista.getBtnJugar().setEnabled(true);
		vista.getBtnJugar().setBackground(Color.YELLOW);
		vista.getBtnSeleccionarPersonaje().setBackground(Color.gray);
		vista.getBtnSeleccionarPersonaje().setEnabled(false);

	}

	public void cargarJComboboxCooNombresLuchadores(DefaultComboBoxModel<String> modelo,
			ArrayList<Luchador> luchadores) {
		modelo.addElement("Lista de Personajes");
		for (Luchador luchador : luchadores) {
			modelo.addElement(luchador.getNombre());
		}
	}

	// METODO PARA INCIAR MUSCIA
	public void iniciarMusica(String nombreTema) {

		this.musica = new Musica("src/musica/" + nombreTema + ".wav");
		musica.reproducir();
	}

	public static void iniciarSonido(Musica sonido,String nombreTema) {

		sonido = new Musica("src/musica/" + nombreTema + ".wav");
		sonido.reproducir();
	}

	public void detenerSonido() {
		if (this.sonido != null) {
			this.sonido.detener();
			this.sonido = null;
		}
	}

	// METODO PARA PARAR MUSCIA
	public void detenerMuscia() {
		if (this.musica != null) {
			this.musica.detener();
			this.musica = null;
		}
	}

	// METODO PARA MOSTRAR Y OCULTAR PANELES
	public void irAlSiguientePanel(JPanel aOcultar, JPanel aMostrar, int pausa) {
		try {
			Thread.sleep(pausa * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aOcultar.setVisible(false);
		aMostrar.setVisible(true);
	}
	
	private void vozPersonajeSeleccionado(String voces[]) {
		int aleatorioVoz = (int) (1 + Math.random() * (voces.length - 1));
		iniciarSonido(sonido,voces[aleatorioVoz]);
	}
	
	public void detenerJuego(int tiempo) {
		try {
			Thread.sleep(tiempo * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void cargarEscuhcadoresSeleccionPersonajesMouseListener() {

		JLabel labels[][] = vista.getLabelsSeleccionPersonajes();
		for (int i = 0; i < labels.length; i++) {
			for (int j = 0; j < labels[i].length; j++) {

				if (labels[i][j].getName() != null) {
					labels[i][j].addMouseListener(this);
				}
			}
		}

	}
	private void ponerBordePersonajeSeleccionado(String nombreLabel1, String nombreLabel2) {
		// TODO Auto-generated method stub
		JLabel labels[][] = vista.getLabelsSeleccionPersonajes();
		Border borde = null;

		for (int i = 0; i < labels.length; i++) {
			for (int j = 0; j < labels[i].length; j++) {
				if (labels[i][j].getName().equals(nombreLabel1) || labels[i][j].getName().equals(nombreLabel2)) {
					borde = new LineBorder(Color.yellow, 5);
					labels[i][j].setBorder(borde);
				} else {
					labels[i][j].setBorder(null);
				}
			}
		}

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
	private void limpiarTextosSeleccionJugador() {

		vista.getLblFisicoSeleccionarPersonaje().setText("?");
		vista.getLblNombreSeleccionPersonaje().setText("?");
		vista.getLblPotenciaSeleccionarPersonaje().setText("?");
		vista.getLblVelocidadSeleccionarPersonaje().setText("?");

	}

	private void exclamaciones() {

		vista.getLblFisicoSeleccionarPersonaje().setText("¡");
		vista.getLblNombreSeleccionPersonaje().setText("¡");
		vista.getLblPotenciaSeleccionarPersonaje().setText("¡");
		vista.getLblVelocidadSeleccionarPersonaje().setText("¡");

	}
	
	private void limparCamposHistoria() {
		vista.getLblAvisosHistoria().setText("");
		vista.getLblNombreHIstoria().setText("?");
		vista.getLblEstatura().setText("?");
		vista.getTextAreaDescripcionHistoria().setText("?");
		vista.getLblPesoHistoria().setText("?");
		vista.getLblEdadHIstoria().setText("?");
		vista.ponerImagenAJlabel(vista.getLblPersonajeHistoriaImagen(), "interrogacion_historia.png", false);
		vista.getLblFisico().setText("?");
		vista.getLblPotencia().setText("?");
		vista.getLblVelocidad().setText("?");

	}
	public void mostrarJugadorHistoria(int posicion) {

		if (posicion > -1) {
			String nombresImagenes[] = luchadores.get(posicion).getImgPelea();
			vista.getLblAvisosHistoria().setText("");
			vista.getLblNombreHIstoria().setText(luchadores.get(posicion).getNombre());
			vista.getLblEstatura().setText(luchadores.get(posicion).getEstatura() + " m");
			vista.getTextAreaDescripcionHistoria().setText(luchadores.get(posicion).getDescripcion());
			vista.getLblPesoHistoria().setText(luchadores.get(posicion).getPeso() + " kg");
			vista.getLblEdadHIstoria().setText(luchadores.get(posicion).getEdad() + "");
			vista.ponerImagenAJlabel(vista.getLblPersonajeHistoriaImagen(), nombresImagenes[0], false);
			vista.getLblFisico().setText(luchadores.get(posicion).getFisico() + "");
			vista.getLblPotencia().setText(luchadores.get(posicion).getPotencia() + "");
			vista.getLblVelocidad().setText(luchadores.get(posicion).getVelocidad() + "");
		} else {
			limparCamposHistoria();
			vista.getLblAvisosHistoria().setText("DEBE SELECCIONAR UN PERSONAJE");
		}

	}

	public void iniciarActionListeners() {
		vista.getBtnEnfrentamiento().addActionListener(this);
		vista.getBtnModoHistoria().addActionListener(this);
		vista.getBtnInfomracion().addActionListener(this);
		vista.getBtnLeyendaPersonajes().addActionListener(this);
		vista.getBtnVolverAtrasDesdeLeyendas().addActionListener(this);
		vista.getBtnMostrarHistoriaPersonaje().addActionListener(this);
		vista.getBtnVolverDesdeSeleccionarPersonaje().addActionListener(this);
		vista.getBtnSeleccionarPersonaje().addActionListener(this);
		vista.getBtnJugar().addActionListener(this);
		vista.getBtnAtacar().addActionListener(this);
		vista.getBtnDefender().addActionListener(this);
		vista.getBtnDescansar().addActionListener(this);
		vista.getBtnVolverDesdeJugar().addActionListener(this);

	}

	public void cargarLuchadores(ArrayList<Luchador> luchadores) {
		String imgPeleaRyu[] = { "ryu_historia.png", "ryu_ataque1.png", "ryu_ataque2.png", "ryu_ataque3.png" };
		String vocesPersonajeRyu[] = { "ryu_pierde", "ryus-hadouken", "ryus-shoryuken", "ryus-tatsumaki" };
		Luchador ryu = new Luchador("Ryu", 26, 27, 1.75, 32, "Japón", 85, 31,
				"Luchador entrenado por Gouken famoso por su potente Hadoken, golpe ganador con el que derrotó\n"
						+ "a Sagat en el primer torneo y lo hirió gravemente.",
				vocesPersonajeRyu, imgPeleaRyu);

		String imgPeleaKen[] = { "ken_historia.png", "ken_ataque1.png", "ken_ataque2.png", "ken_ataque3.png" };
		String vocesPersonajeKen[] = { "kens-death", "kens-hadouken", "kens-shoryuken", "kens-tatsumaki" };
		Luchador ken = new Luchador("Ken", 25, 28, 1.75, 30, "EEUU", 86, 32,
				"Descendiente de una rica familia estadounidense,su padre pagó para formarle en kárate.\n"
						+ "Discípulo de Gouken igual que Ryu pretende ser el mejor luchador del mundo, por encima de Ryu.",
				vocesPersonajeKen, imgPeleaKen);

		String imgPeleaGuile[] = { "guile_historia.png", "guile_ataque1.png", "guile_ataque2.png",
				"guile_ataque3.png" };
		String vocesPersonajeGuile[] = { "guile_dead", "guile_sarenKum", "guile_3", "guile_4" };
		Luchador guile = new Luchador("Guile", 42, 34, 1.85, 23, "EEUU", 100, 33,
				"Ingresa al torneo para poner a M.Bison bajo custodia por ser el asesino de su mejor amigo\n"
						+ "Charlie, sólo la victoria sobre el mismo le hará enfrentarse a él y poder derrotarlo.",
				vocesPersonajeGuile, imgPeleaGuile);

		String imgPeleaChunLi[] = { "chun_li_historia.png", "chun_li_ataque1.png", "chun_li_ataque2.png",
				"chun_li_ataque3.png" };
		String vocesPersonajeChunLi[] = { "chun_li_dead", "chun_li_ataque1", "chun_li_ataque2", "chun_li_ataque3" };
		Luchador chun_li = new Luchador("Chun-Li", 19, 18, 1.65, 21, "China", 58, 51,
				"Artista marcial experta y oficial de la Interpol. Sin descanso, ella busca venganza por la muerte de\n"
						+ "su padre a manos del líder de la organización criminal Shadaloo, M.Bison.",
				vocesPersonajeChunLi, imgPeleaChunLi);

		String imgPeleaHonda[] = { "honda_historia.png", "honda_ataque1.png", "honda_ataque2.png",
				"honda_ataque3.png" };
		String vocesPersonajeHonda[] = { "honda_dead", "honda_ataque1", "honda_ataque2", "honda_ataque3" };
		Luchador honda = new Luchador("Honda", 40, 46, 1.89, 26, "Japón", 170, 18,
				"Luchador de sumo profesional de Japón que entra al torneo para demostrar que el sumo es el\n"
						+ "mejor estilo de lucha del mundo por lo que retará a todo el que pretenda enfrentarse a él.",
				vocesPersonajeHonda, imgPeleaHonda);

		String imgPeleaDhalsim[] = { "dhalsim_historia.png", "dhalsim_ataque1.png", "dhalsim_ataque2.png",
				"dhalsim_ataque3.png" };
		String vocesPersonajeDhalsim[] = { "dhalsim_dead", "dhalsim_ataque1", "dhalsim_ataque2", "dhalsim_ataque3" };
		Luchador dhalsim = new Luchador("Dhalsim", 58, 24, 1.76, 32, "India", 65, 34,
				"Pacifista pero entró a un torneo de lucha para recaudar dinero para su aldea empobrecida.\n"
						+ "Ha dedicado su vida a la meditación lo que le permite expulsar fuego por la boca y estirar su cuerpo.",
				vocesPersonajeDhalsim, imgPeleaDhalsim);

		String imgPeleaBlanka[] = { "blanka_historia.png", "blanka_ataque1.png", "blanka_ataque2.png",
				"blanka_ataque3.png" };
		String vocesPersonajeBlanka[] = { "blanka_dead", "blanka_ataque1", "blanka_ataque2", "blanka_ataque3" };
		Luchador blanka = new Luchador("Blanka", 27, 22, 1.85, 36, "Brasil", 98, 32,
				"Hombre brasileño cuyo cuerpo ha sido infectado con demasiada clorofila proveniente de las selvas\n"
						+ "donde vive. Es famoso por su movimiento especial eléctrico y sus movimientos rodantes.",
				vocesPersonajeBlanka, imgPeleaBlanka);

		String imgPeleaVega[] = { "vega_historia.png", "vega_ataque1.png", "vega_ataque2.png", "vega_ataque3.png" };
		String vocesPersonajeVega[] = { "vega_dead", "vega_ataque1", "vega_ataque2", "vega_ataque" };
		Luchador vega = new Luchador("Vega", 24, 28, 1.86, 34, "España", 80, 28,
				"Luchador español contratado por Shadoloo que utiliza un estilo de lucha muy particular en el que\n"
						+ "mezcla una rápida habilidad de esquiva parecida a la esgrima con una potente garra.",
				vocesPersonajeVega, imgPeleaVega);

		String imgPeleaZangief[] = { "zangief_historia.png", "zangief_ataque1.png", "zangief_ataque2.png",
				"zangief_ataque3.png" };
		String vocesPersonajeZangief[] = { "zangief_dead", "zangief_ataque1", "zangief_ataque2", "zangief_ataque3" };
		Luchador zangief = new Luchador("Zangief", 44, 39, 2.13, 36, "Rusia", 160, 15,
				"Luchador ruso acostumbrado a entrenar con grandes osos. Es un luchador lento, pero si\n"
						+ "logra agarrarte, estás acabado. Ingresa al evento por motivos económicos.",
				vocesPersonajeZangief, imgPeleaZangief);

		String imgPeleaDeeJay[] = { "dee_jay_historia.png", "dee_jay_ataque1.png", "dee_jay_ataque2.png",
				"dee_jay_ataque3.png" };
		String vocesPersonajeDeeJay[] = { "dee_jay_dead", "dee_jay_ataque1", "dee_jay_ataque2", "dee_jay_ataque3" };
		Luchador deeJay = new Luchador("Dee Jay", 31, 26, 1.89, 25, "Jamaica", 87, 39,
				"Cantante que se interesó en un torneo de lucha como medio para ganar popularidad. Su fortaleza\n"
						+ "viene de un desastre en un concierto que le hizo ganar una onda expansiva en sus puños.",
				vocesPersonajeDeeJay, imgPeleaDeeJay);

		String imgPeleaTHawk[] = { "thawk_historia.png", "thahwk_ataque1.png", "thahwk_ataque2.png",
				"thahwk_ataque3.png" };
		String vocesPersonajeThawk[] = { "t_hawk_dead", "t_hawk_ataque1", "t_hawk_ataque2", "t_hawk_ataque3" };
		Luchador thawk = new Luchador("T.Hawk", 39, 37, 2.06, 31, "México", 112, 22,
				"Ingresa al torneo para vengar a sus compatriotas indios nativos, ya que Bison destruyó\n"
						+ "su asentamiento por el oro y la cantidad de riquezas que poseían.",
				vocesPersonajeThawk, imgPeleaTHawk);

		String imgPeleaCammy[] = { "cammy_historia.png", "cammy_ataque1.png", "cammy_ataque2.png",
				"cammy_ataque3.png" };
		String vocesPersonajeCammy[] = { "cammy_dead", "cammy_ataque1", "cammy_ataque2", "cammy_ataque3" };
		Luchador cammy = new Luchador("Cammy", 26, 12, 1.68, 33, "Reino Unido", 65, 45,
				"Tiene algunos lazos misteriosos con M.Bison, es una especialista de las fuerzas especiales del\n"
						+ "ejército británico conocido como Delta Red.",
				vocesPersonajeCammy, imgPeleaCammy);

		String imgPeleaBalrog[] = { "balrog_historia.png", "balrog_ataque1.png", "balrog_ataque2.png",
				"balrog_ataque2.png" };
		String vocesPersonajeBalrog[] = { "balrog_dead", "balrog_ataque1", "balrog_ataque2", "balrog_ataque3" };
		Luchador balrog = new Luchador("Balrog", 35, 55, 1.98, 16, "EEUU", 118, 17,
				"Ex boxeador profesional que trabaja bajo las órdenes de M.Bison en la organización criminal de\n"
						+ "Shadaloo. Lucha pura y exclusivamente con los puños.",
				vocesPersonajeBalrog, imgPeleaBalrog);

		String imgPeleaSagat[] = { "sagat_historia.png", "sagat_ataque1.png", "sagat_ataque2.png",
				"sagat_ataque3.png" };
		String vocesPersonajeSagat[] = { "sagat_dead", "sagat_ataque1", "sagat_ataque2", "sagat_ataque3" };
		Luchador sagat = new Luchador("Sagat", 49, 35, 2.25, 35, "Tailandia", 150, 20,
				"Integrante de Shadaloo, ingresa al torneo por venganza contra Ryu que años antes en una pelea le\n"
						+ "causó la cicatriz que tiene en el pecho.",
				vocesPersonajeSagat, imgPeleaSagat);

		String imgPeleaMbison[] = { "mbison_historia.png", "mbison_ataque1.png", "mbison_ataque2.png",
				"mbison_ataque3.png" };
		String vocesPersonajeMbison[] = { "m_bison_dead", "m_bison_ataque1", "m_bison_ataque2", "m_bison_ataque3" };
		Luchador Mbison = new Luchador("M.Bison", 51, 37, 2.10, 38, "Desconocida", 115, 25,
				"Líder de la organización criminal de Shadaloo. Es el organizador principal del torneo, aunque\r\n"
						+ "realmente es una tapadera, es un contrabandista de armas y drogas cegado por el poder.",
				vocesPersonajeMbison, imgPeleaMbison);

		luchadores.add(ryu);
		luchadores.add(ken);
		luchadores.add(guile);
		luchadores.add(chun_li);
		luchadores.add(honda);
		luchadores.add(dhalsim);
		luchadores.add(blanka);
		luchadores.add(zangief);
		luchadores.add(deeJay);
		luchadores.add(thawk);
		luchadores.add(cammy);
		luchadores.add(balrog);
		luchadores.add(vega);
		luchadores.add(sagat);
		luchadores.add(Mbison);

	}

}
