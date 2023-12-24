package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

import modelo.Luchador;
import modelo.Musica;
import vista.Vista;

public class Controlador implements ActionListener, MouseListener {

	private static int j1 = -1;
	private static int j2 = -1;
	private boolean j1Seleccionado = false;
	private boolean j2Seleccionado = false;

	private Vista vista;
	private Musica musica;
	private Luchador pJ1;
	private Luchador pJ2;
	private ArrayList<Luchador> luchadores;
	private DefaultComboBoxModel<String> jComboLuchadores;

	public Controlador(Vista vista) {
		this.vista = vista;
		jComboLuchadores = new DefaultComboBoxModel<String>();
		luchadores = new ArrayList<Luchador>();
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
			iniciarMusica("musica_historia");
			irAlSiguientePanel(vista.getPanelMenu(), vista.getPanelHistoriaPersonajes());

			// CONFIGURACIONES DE LUCHADORES
			cargarLuchadores(luchadores);
			cargarJComboboxCooNombresLuchadores(jComboLuchadores, luchadores);

			vista.getComboBoxNombresLuchadores().setModel(jComboLuchadores);

		} else if (e.getSource() == vista.getBtnVolverAtrasDesdeLeyendas()) {

			detenerMuscia();
			iniciarMusica("musica_inicio");
			irAlSiguientePanel(vista.getPanelHistoriaPersonajes(), vista.getPanelMenu());
			limparCamposHistoria();

		} else if (e.getSource() == vista.getBtnMostrarHistoriaPersonaje()) {

			int posicionPersonaje = vista.getComboBoxNombresLuchadores().getSelectedIndex();

			mostrarJugadorHistoria(posicionPersonaje - 1);

		} else if (e.getSource() == vista.getBtnEnfrentamiento()) {
			
			limpiarTextosSeleccionJugador();
			reiniciarJugadores();
			detenerMuscia();
			cargarLuchadores(luchadores);
			iniciarMusica("seleccion_menu");
			pausarJuego(3);
			detenerMuscia();
			// iniciarMusica("seleccion_personaje_tema");
			irAlSiguientePanel(vista.getPanelMenu(), vista.getPanelSeleccionPersonajes());
			vista.caragarPeronajeseSeleccion(15);
			vista.getLblTitulo1PjSeleccionarPersonaje().setText("Jugador 1");
			vista.getLblTitulo2PjSeleccionarPersonaje().setText("Jugador 2");

		} else if (e.getSource() == vista.getBtnVolverDesdeSeleccionarPersonaje()) {

			
			detenerMuscia();
			iniciarMusica("musica_inicio");
			irAlSiguientePanel(vista.getPanelSeleccionPersonajes(), vista.getPanelMenu());

		} else if (e.getSource() == vista.getBtnSeleccionarPersonaje()) {

			if (j1Seleccionado == false) {
				
				j1Seleccionado = mostrarJugador(j1,vista.getLblImgJ1Seleccionado(),vista.getLblTitulo1PjSeleccionarPersonaje());
				pJ1 = luchadores.get(j1);
			} else {

				j2Seleccionado = mostrarJugador(j2,vista.getLblImgJ2Seleccionado(),vista.getLblTitulo2PjSeleccionarPersonaje());
				pJ2 = luchadores.get(j2);
				
				prepararBotonJugar();
				
				ponerBordePersonajeSeleccionado(j1+"",j2+"");
				
				exclamaciones();
				
			}
			
		} else if( e.getSource() == vista.getBtnJugar()) {
			
			iniciarMusica("jugadores_seleccionados");
			pausarJuego(3);
			vista.getLblNombrePj1PanelJugar().setText(pJ1.getNombre());
			vista.getLblNombrePj2PanelJugar().setText(pJ2.getNombre());
			
			irAlSiguientePanel(vista.getPanelSeleccionPersonajes(), vista.getPanelJuegoEntrenamiento());	
		}

	}
	
	public void actualizarLabel(final JLabel label) {
	   
	}

	private boolean mostrarJugador(int posicion, JLabel lblImagen,JLabel lblTitulo) {
		
	            Luchador personajeSeleccionado = luchadores.get(posicion);
	            vozPersonajeSeleccionado(personajeSeleccionado.getVocesPersonaje());	
	            lblTitulo.setText(personajeSeleccionado.getNombre());
	            vista.ponerImagenAJlabel(lblImagen, personajeSeleccionado.getNombreImagen(), false);
	            
	    
		return true;
	}

	private void reiniciarJugadores() {

		vista.getBtnSeleccionarPersonaje().setEnabled(true);
		vista.getLblImgJ1Seleccionado().setIcon(null);
		vista.getLblImgJ2Seleccionado().setIcon(null);

		pJ1 = null;
		pJ2 = null;
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

	private void prepararBotonJugar() {

		vista.getBtnJugar().setEnabled(true);
		vista.getBtnJugar().setBackground(Color.YELLOW);
		vista.getBtnSeleccionarPersonaje().setBackground(Color.gray);
		vista.getBtnSeleccionarPersonaje().setEnabled(false);

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

	public void mostrarJugadorHistoria(int posicion) {

		if (posicion > -1) {
			vista.getLblAvisosHistoria().setText("");
			vista.getLblNombreHIstoria().setText(luchadores.get(posicion).getNombre());
			vista.getLblEstatura().setText(luchadores.get(posicion).getEstatura() + " m");
			vista.getTextAreaDescripcionHistoria().setText(luchadores.get(posicion).getDescripcion());
			vista.getLblPesoHistoria().setText(luchadores.get(posicion).getPeso() + " kg");
			vista.getLblEdadHIstoria().setText(luchadores.get(posicion).getEdad() + "");
			vista.ponerImagenAJlabel(vista.getLblPersonajeHistoriaImagen(), luchadores.get(posicion).getNombreImagen(),
					false);
			vista.getLblFisico().setText(luchadores.get(posicion).getFisico() + "");
			vista.getLblPotencia().setText(luchadores.get(posicion).getPotencia() + "");
			vista.getLblVelocidad().setText(luchadores.get(posicion).getVelocidad() + "");
		} else {
			limparCamposHistoria();
			vista.getLblAvisosHistoria().setText(" debe seleccionar un personaje");
		}

	}

	public void cargarLuchadores(ArrayList<Luchador> luchadores) {

		String vocesPersonajeRyu[] = { "ryu_pierde", "ryus-hadouken", "ryus-shoryuken", "ryus-tatsumaki" };
		Luchador ryu = new Luchador("Ryu", 26, 27, 1.75, 32, "Japón", 85, 31,
				"Ryu, hábil artista marcial, se embarca en torneos\n" + "para perfeccionar sus habilidades.\n"
						+ "Enfrenta dilemas al resistir el atractivo\n"
						+ "del Satsui no Hado, una fuerza destructiva.\n" + "Sus enfrentamientos tácticos contra\n"
						+ "adversarios como M. Bison y Seth destacan \n" + "su destreza y habilidades estratégicas.",
				"ryu_historia.png", vocesPersonajeRyu);

		String vocesPersonajeKen[] = { "kens-death", "kens-hadouken", "kens-shoryuken", "kens-tatsumaki" };
		Luchador ken = new Luchador("Ken", 25, 28, 1.75, 30, "EEUU", 86, 32,
				"Ken Masters, amigo y rival de Ryu,es un luchador\n" + "entrenado en las mismas artes marciales.\n"
						+ "A lo largo de su vida,\n" + "enfrenta desafíos personalesy profesionales,\n "
						+ "equilibrando su vida familiar con\n" + "su pasión por el combate.\n"
						+ "Su historia refleja la evolución\n" + "de un guerrero apasionado \n"
						+ "y la fuerza de una amistad perdurable.",
				"ken_historia.png", vocesPersonajeKen);

		String vocesPersonajeGuile[] = { "guile_dead", "guile_sarenKum", "guile_3", "guile_4" };
		Luchador guile = new Luchador("Guile", 42, 34, 1.85, 23, "EEUU", 100, 33,
				"Guile, teniente de la Fuerza Aérea de EE. UU.\n" + "Entra en el torneo en busca de venganza,\n"
						+ "por la desaparición de su amigo Charlie Nash\n"
						+ "investigador de la organización Shadaloo.\n",
				"guile_historia.png", vocesPersonajeGuile);

		String vocesPersonajeChunLi[] = { "", "", "", "" };
		Luchador chun_li = new Luchador("Chun-Li", 19, 18, 1.65, 21, "China", 58, 51,
				"Chun-Li, originaria de China, es una agente\n" + "de Interpol y una maestra en artes marciales.\n"
						+ "Su historia se centra en la búsqueda de\n" + "justicia por la desaparición de su padre\n"
						+ "a manos de la organización criminal Shadaloo.\n",
				"chun_li_historia.png", vocesPersonajeChunLi);

		String vocesPersonajeHonda[] = { "", "", "", "" };
		Luchador honda = new Luchador("Honda", 40, 46, 1.89, 26, "Japón", 170, 18,
				"E. Honda, orgulloso luchador de sumo,\n" + "busca demostrar la grandeza\n"
						+ "de su arte marcial al mundo.\n" + "A través de los torneos\n "
						+ "Street Fighter, lucha con honor y\n" + "determinación, inspirando\n"
						+ "a otros con su dedicación a preservar\n" + "las tradiciones del sumo.",
				"honda_historia.png", vocesPersonajeHonda);

		String vocesPersonajeDhalsim[] = { "", "", "", "" };
		Luchador dhalsim = new Luchador("Dhalsim", 58, 24, 1.76, 32, "India", 65, 34,
				"A pesar de sus poderes espirituales y su\n" + "habilidad para estirar sus extremidades,\n"
						+ "Dhalsim lucha no por la victoria personal,\n"
						+ "sino por proteger a su familia y comunidad.\n",
				"dalshim_historia.png", vocesPersonajeDhalsim);

		String vocesPersonajeBlanka[] = { "", "", "", "" };
		Luchador blanka = new Luchador("Blanka", 27, 22, 1.85, 36, "Brasil", 98, 32,
				"Inicialmente un piloto de las fuerzas aéreas\n" + "estadounidenses, Charlie Nash sufre una traición\n"
						+ "que lo lleva a una serie de experimentos por\n" + "parte de la organiczación Shadaloo.\n"
						+ "Convertido en Blanca, un ser con piel verde y\n"
						+ "poderes eléctricos, busca su humanidad perdida.\n",
				"blanca_historia.png", vocesPersonajeBlanka);

		String vocesPersonajeVega[] = { "", "", "", "" };
		Luchador vega = new Luchador("Vega", 24, 28, 1.86, 34, "España", 80, 28, "Vega, es un luchador español y\n"
				+ "miembro de la organización criminal Shadaloo.\n" + "Su historia presenta una combinación\n"
				+ "única de elegancia y ferocidad.\n" + "Vega es un maestro en el arte del ninjutsu y la lucha con\n"
				+ "garra,destacando su belleza y agilidad.\n" + "Su motivación principal es preservar la perfección y\n"
				+ "la estética, lo que lo lleva a participar en los\n" + "torneos para exhibir su destreza\n"
				+ "mortal y mantener su visión del arte del combate.\n", "vega_historia.png", vocesPersonajeVega);

		String vocesPersonajeZangief[] = { "", "", "", "" };
		Luchador zangief = new Luchador("Zangief", 44, 39, 2.13, 36, "Rusia", 160, 15,
				"Criado en una tierra fría, Zangief se\n" + "embarca en los torneos no solo\n"
						+ "para demostrar su destreza en la lucha,\n" + "sino también para representar a su país\n"
						+ "con honor.",
				"zangief_historia.png", vocesPersonajeZangief);

		String vocesPersonajeDeeJay[] = { "", "", "", "" };
		Luchador deeJay = new Luchador("Dee Jay", 31, 26, 1.89, 25, "Jamaica", 87, 39,
				"Con una actitud positiva y un espíritu\n" + "no solo por la gloria, sino\n"
						+ "también para difundir la diversión y el ritmo.\n"
						+ "Su historia resalta la importancia de la música\n"
						+ "y el baile como expresiones de felicidad.\n",
				"dee_jay_historia.png", vocesPersonajeDeeJay);

		String vocesPersonajeThawk[] = { "", "", "", "" };
		Luchador thawk = new Luchador("T.Hawk", 39, 37, 2.06, 31, "México", 112, 22,
				"Buscando la justicia para su pueblo y\n" + "su tierra natal, T. Hawk participa\n"
						+ "en los torneos para enfrentarse\n" + "a la amenaza de la organización Shadaloo\n"
						+ "y recuperar lo que fue tomado.\n",
				"thawk_historia.png", vocesPersonajeThawk);

		String vocesPersonajeCammy[] = { "", "", "", "" };
		Luchador cammy = new Luchador("Cammy", 26, 12, 1.68, 33, "Reino Unido", 65, 45,
				"Inicialmente creada como asesina por la\n" + "organización Shadaloo,\n"
						+ "Cammy se libera de su control y lucha para\n" + "descubrir su verdadera identidad.\n"
						+ "A pesar de enfrentar su oscuro pasado,\n" + "elige el camino de la redención y\n"
						+ "se une a Delta Red para enfrentar amenazas\n" + "globales.",
				"cammy_historia.png", vocesPersonajeCammy);

		String vocesPersonajeBalrog[] = { "", "", "", "" };
		Luchador balrog = new Luchador("Balrog", 35, 55, 1.98, 16, "EEUU", 118, 17,
				"La historia de Balrog es una reflexión sobre\n" + "la influencia del poder y la riqueza en la\n"
						+ "vida de una persona.\n" + "A pesar de sus decisiones cuestionables,\n"
						+ "Balrog inspira a través de su determinación\n" + "y capacidad para enfrentar desafíos,\n"
						+ "incluso cuando su camino se ve oscurecido\n" + "por la ambición y la avaricia.\n",
				"balrog_historia.png", vocesPersonajeBalrog);

		String vocesPersonajeSagat[] = { "", "", "", "" };
		Luchador sagat = new Luchador("Sagat", 49, 35, 2.25, 35, "Tailandia", 150, 20,
				"Inicialmente marcado por su sed de venganza\n" + "después de ser derrotado por Ryu, Sagat busca\n"
						+ "encontrar un propósito más elevado en los torneos.\n"
						+ "A lo largo de su viaje, descubre la importancia\n"
						+ "de la humildad y la superación personal.\n"
						+ "Su historia destaca la capacidad de transformar\n"
						+ "la adversidad en oportunidad y encontrar la\n"
						+ "verdadera fuerza en la aceptación y la redención.",
				"sagat_historia.png", vocesPersonajeSagat);

		String vocesPersonajeMbison[] = { "", "", "", "" };
		Luchador Mbison = new Luchador("M.Bison", 51, 37, 2.10, 38, "Desconocida", 115, 25,
				"M.Bison,creador de la organización criminal Shadaloo.\n"
						+ "A pesar de ser un antagonista,ha demostrado\n"
						+ "una voluntad inquebrantable para lograr sus\n"
						+ "objetivos.Su historia puede interpretarse como un\n"
						+ "recordatorio de cómo la determinación y la astucia\n"
						+ "pueden llevar a alguien a alcanzar altas posiciones\n"
						+ "aunque la moralidad de sus elecciones sea discutible.",
				"mbison_historia.png", vocesPersonajeMbison);

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

	public void cargarJComboboxCooNombresLuchadores(DefaultComboBoxModel<String> modelo,
			ArrayList<Luchador> luchadores) {
		modelo.addElement("Lista de Personajes");
		for (Luchador luchador : luchadores) {
			modelo.addElement(luchador.getNombre());
		}
	}

	public void detenerJuego(int tiempo) {
		try {
			Thread.sleep(tiempo * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METODO PARA INCIAR MUSCIA
	public void iniciarMusica(String nombreTema) {
		this.musica = new Musica("src/musica/" + nombreTema + ".wav");
		musica.reproducir();
	}

	// METODO PARA PARAR MUSCIA
	public void detenerMuscia() {
		if (this.musica != null) {
			this.musica.detener();
			this.musica = null;
		}
	}

	// METODO PARA MOSTRAR Y OCULTAR PANELES
	public void irAlSiguientePanel(JPanel aOcultar, JPanel aMostrar) {
		aOcultar.setVisible(false);
		aMostrar.setVisible(true);
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

	}

	private void vozPersonajeSeleccionado(String voces[]) {
		int aleatorioVoz = (int) (1 + Math.random() * (voces.length - 1));
		System.out.println(aleatorioVoz);
		iniciarMusica(voces[aleatorioVoz]);
		detenerMuscia();
	}
	public void pausarJuego(int segundos) {
        Timer timer = new Timer(segundos * 1000, null);  
        timer.setRepeats(false);  // El Timer se ejecutará solo una vez
        timer.start();
        
        while (timer.isRunning()) {
            // Espera hasta que el Timer haya terminado de contar 'segundos'
            // Esto mantendrá el programa en pausa.
        }
	}
}
