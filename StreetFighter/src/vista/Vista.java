package vista;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controlador.Controlador;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Vista extends JFrame {

	private ArrayList<Integer> posicionesDesbloqueadas;
	private static final long serialVersionUID = 1L;
	private String rutasImagenesSeleccionPersonaje[] = { "ryu_select.jpeg", "ken_select.jpeg", "guile_select.jpeg"
			, "chun_li_select.jpeg", "honda_select.jpeg","dalshim_select.jpeg", "blanka_select.jpeg", 
			"zangief_select.jpeg", "dee_jay_select.jpeg","t_hawk_select.jpeg" ,"cammy_select.jpeg", 
			"balrog_select.jpeg", "vega_select.jpeg", "sagat_select.jpeg","mbison_select.jpeg"  };

	private JButton btnLeyendaPersonajes, btnInfomracion, btnEnfrentamiento, btnModoHistoria,
			btnMostrarHistoriaPersonaje, btnSeleccionarJugador, btnVolverDesdeSeleccionarPersonaje,
			btnVolverAtrasDesdeLeyendas, btnAtacar, btnDefender, btnDescansar, btnVolverDesdeJugar, btnJugar;

	private JComboBox<String> comboBoxNombresHistoria;
	private JPanel contentPane, panelHistoriaPersonajes, panelMenu, panelSeleccionPersonajes, panelCombate,
			panelInformacion, panelPersonajeDesbloqueado;

	private JLabel lblEstatura, lblPesoHistoria, lblEdadHIstoria, lblNombreHIstoria, lblPersonajeHistoriaImagen,
			lblAvisosHistoria, lblNewLabel_potencia, lblNewLabel_velocidad, lblNewLabel_fisico, lblPotencia,
			lblVelocidad, lblFisico, FondoSeleccionPersonaje, lblFondoHistoriaPersonajes,
			lblVelocidadSeleccionarPersonaje, lblFisicoSeleccionarPersonaje, lblPotenciaSeleccionarPersonaje,
			lblNombreSeleccionPersonaje, lblNombrePj1PanelJugar, lblNombrePj2PanelJugar, lblImgVS, lblFondoPartida,
			lblImgJ1Seleccionado, lblImgJ2Seleccionado, lblTitulo1PjSeleccionarPersonaje,
			lblTitulo2PjSeleccionarPersonaje, lblTiempo, labelKO, lblImagenJ1Juego, lblImagenJ2Juego, lblVidaPj2,
			lblVidaPj1, lblCansancioPj2, lblCansancioPj1, lblEliminado2, lblEliminado1, lblNewLabel, lblMensajePj1,
			lblMensajePj2, lblAvisosSeleccionarJugador, lblStreetMinis, lblP,
			lblFondoInfo, lblNombreDesbloqueo, lblLogoStreetFighterInfo, lblEdadDesbloqueoPersonaje,
			lblEstaturaDesbloqueoPersonaje, lblPesoDesbloqueoPersonaje, lblFisicoDesbloqueoPersonaje,
			labelVelocidadDesbloqueoPersonaje, lblTituloDesbloqueoPersonaje, lblImgJugadorDesbloqueado,
			lblPtenciaDesbloquearPersonaje, lblNewLabel_11, lblCandado, lblMensajeDesbloqueado;

	private ArrayList<JLabel> seleccionPersonajes,seleccionComputadora,sobrepuestosComputadora;
	private JProgressBar progressBarVidaPJ2, progressBarVidaPJ1;
	private JProgressBar progressBarVitalidadPj1, progressBarVitalidadPj2;
	private JTextArea textAreaDescripcionHistoria, textAreaScrollPanel;
	private JScrollPane scrollPaneInfo;
	private JButton btnVolverDeInfo;
	private JButton btnContinuarDesbloquePersonaje;
	private JTextPane textPaneDescripcionDesbloqueo;
	private JButton btnSeleccionarComputadora;
	private JLabel lblFondoGanador;
	private JButton btnVovlerDesdeGanador;
	private JLabel lblLogoStreetFighter;
	private JLabel lblimgGanador;
	private JPanel panelGanador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					Controlador controlador = new Controlador(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Vista() {

		posicionesDesbloqueadas= new ArrayList<Integer>();
		posicionesDesbloqueadas.add(0);
		posicionesDesbloqueadas.add(1);
		posicionesDesbloqueadas.add(2);
		posicionesDesbloqueadas.add(3);
		posicionesDesbloqueadas.add(4);
		
		
		seleccionPersonajes = new ArrayList<JLabel>();
		seleccionComputadora = new ArrayList<JLabel>();
		sobrepuestosComputadora = new ArrayList<JLabel>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelGanador = new JPanel();
		panelGanador.setBounds(-2, 0, 849, 613);
		contentPane.add(panelGanador);
		panelGanador.setLayout(null);
		panelGanador.setVisible(false);
		
		lblimgGanador = new JLabel("");
		lblimgGanador.setBounds(220, 159, 432, 395);
		panelGanador.add(lblimgGanador);
		
		lblLogoStreetFighter = new JLabel("");
		lblLogoStreetFighter.setBounds(21, 10, 234, 108);
		panelGanador.add(lblLogoStreetFighter);
		
		btnVovlerDesdeGanador = new JButton("Continuar");
		btnVovlerDesdeGanador.setBackground(new Color(255, 255, 0));
		btnVovlerDesdeGanador.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVovlerDesdeGanador.setBounds(21, 565, 128, 21);
		panelGanador.add(btnVovlerDesdeGanador);
		
		JLabel lblImgWIN = new JLabel("");
		lblImgWIN.setBounds(514, 21, 299, 231);
		panelGanador.add(lblImgWIN);
		
		lblFondoGanador = new JLabel("");
		lblFondoGanador.setBounds(0, 0, 849, 603);
		panelGanador.add(lblFondoGanador);

		

		panelPersonajeDesbloqueado = new JPanel();
		panelPersonajeDesbloqueado.setBounds(0, 0, 847, 613);
		contentPane.add(panelPersonajeDesbloqueado);
		panelPersonajeDesbloqueado.setLayout(null);
		panelPersonajeDesbloqueado.setVisible(false);

		lblImgJugadorDesbloqueado = new JLabel("");
		lblImgJugadorDesbloqueado.setBounds(432, 166, 333, 269);
		panelPersonajeDesbloqueado.add(lblImgJugadorDesbloqueado);

		btnContinuarDesbloquePersonaje = new JButton("Continuar");
		btnContinuarDesbloquePersonaje.setBackground(new Color(255, 255, 128));
		btnContinuarDesbloquePersonaje.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		btnContinuarDesbloquePersonaje.setBounds(10, 539, 127, 42);
		panelPersonajeDesbloqueado.add(btnContinuarDesbloquePersonaje);

		JLabel lblNewLabel_4 = new JLabel("Nombre:");
		lblNewLabel_4.setForeground(new Color(255, 255, 0));
		lblNewLabel_4.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_4.setBounds(29, 205, 70, 26);
		panelPersonajeDesbloqueado.add(lblNewLabel_4);

		JLabel lblNewLabel_6 = new JLabel("Edad:");
		lblNewLabel_6.setForeground(new Color(255, 255, 0));
		lblNewLabel_6.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_6.setBounds(29, 241, 70, 21);
		panelPersonajeDesbloqueado.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Estatura:");
		lblNewLabel_7.setForeground(new Color(255, 255, 0));
		lblNewLabel_7.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_7.setBounds(29, 272, 82, 21);
		panelPersonajeDesbloqueado.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Peso:");
		lblNewLabel_8.setForeground(new Color(255, 255, 0));
		lblNewLabel_8.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_8.setBounds(29, 303, 53, 21);
		panelPersonajeDesbloqueado.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Físico:");
		lblNewLabel_9.setForeground(new Color(255, 255, 0));
		lblNewLabel_9.setFont(new Font("Baskerville Old Face", Font.BOLD, 15));
		lblNewLabel_9.setBounds(29, 334, 70, 21);
		panelPersonajeDesbloqueado.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Velocidad:");
		lblNewLabel_10.setForeground(new Color(255, 255, 0));
		lblNewLabel_10.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_10.setBounds(29, 365, 82, 21);
		panelPersonajeDesbloqueado.add(lblNewLabel_10);

		lblP = new JLabel("Potencia:");
		lblP.setForeground(new Color(255, 255, 0));
		lblP.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblP.setBounds(29, 396, 82, 26);
		panelPersonajeDesbloqueado.add(lblP);

		lblNombreDesbloqueo = new JLabel("");
		lblNombreDesbloqueo.setForeground(new Color(255, 255, 255));
		lblNombreDesbloqueo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreDesbloqueo.setFont(new Font("Bookman Old Style", Font.BOLD, 17));
		lblNombreDesbloqueo.setBounds(131, 205, 101, 33);
		panelPersonajeDesbloqueado.add(lblNombreDesbloqueo);

		lblEdadDesbloqueoPersonaje = new JLabel("");
		lblEdadDesbloqueoPersonaje.setForeground(new Color(255, 255, 255));
		lblEdadDesbloqueoPersonaje.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEdadDesbloqueoPersonaje.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		lblEdadDesbloqueoPersonaje.setBounds(131, 229, 101, 33);
		panelPersonajeDesbloqueado.add(lblEdadDesbloqueoPersonaje);

		lblEstaturaDesbloqueoPersonaje = new JLabel("");
		lblEstaturaDesbloqueoPersonaje.setForeground(new Color(255, 255, 255));
		lblEstaturaDesbloqueoPersonaje.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstaturaDesbloqueoPersonaje.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		lblEstaturaDesbloqueoPersonaje.setBounds(131, 260, 101, 33);
		panelPersonajeDesbloqueado.add(lblEstaturaDesbloqueoPersonaje);

		lblPesoDesbloqueoPersonaje = new JLabel("");
		lblPesoDesbloqueoPersonaje.setForeground(new Color(255, 255, 255));
		lblPesoDesbloqueoPersonaje.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPesoDesbloqueoPersonaje.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblPesoDesbloqueoPersonaje.setBounds(131, 291, 101, 33);
		panelPersonajeDesbloqueado.add(lblPesoDesbloqueoPersonaje);

		lblFisicoDesbloqueoPersonaje = new JLabel("");
		lblFisicoDesbloqueoPersonaje.setForeground(new Color(255, 255, 255));
		lblFisicoDesbloqueoPersonaje.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFisicoDesbloqueoPersonaje.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblFisicoDesbloqueoPersonaje.setBounds(131, 322, 101, 33);
		panelPersonajeDesbloqueado.add(lblFisicoDesbloqueoPersonaje);

		labelVelocidadDesbloqueoPersonaje = new JLabel("");
		labelVelocidadDesbloqueoPersonaje.setForeground(new Color(255, 255, 255));
		labelVelocidadDesbloqueoPersonaje.setHorizontalAlignment(SwingConstants.RIGHT);
		labelVelocidadDesbloqueoPersonaje.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		labelVelocidadDesbloqueoPersonaje.setBounds(131, 353, 101, 33);
		panelPersonajeDesbloqueado.add(labelVelocidadDesbloqueoPersonaje);

		lblTituloDesbloqueoPersonaje = new JLabel("");
		lblTituloDesbloqueoPersonaje.setBounds(324, 20, 419, 121);
		panelPersonajeDesbloqueado.add(lblTituloDesbloqueoPersonaje);

		lblPtenciaDesbloquearPersonaje = new JLabel("");
		lblPtenciaDesbloquearPersonaje.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPtenciaDesbloquearPersonaje.setForeground(new Color(255, 255, 255));
		lblPtenciaDesbloquearPersonaje.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		lblPtenciaDesbloquearPersonaje.setBounds(131, 389, 101, 33);
		panelPersonajeDesbloqueado.add(lblPtenciaDesbloquearPersonaje);

		lblNewLabel_11 = new JLabel("Descripción:");
		lblNewLabel_11.setForeground(new Color(255, 255, 0));
		lblNewLabel_11.setBackground(new Color(255, 255, 255));
		lblNewLabel_11.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_11.setBounds(233, 455, 101, 26);
		panelPersonajeDesbloqueado.add(lblNewLabel_11);

		textPaneDescripcionDesbloqueo = new JTextPane();
		textPaneDescripcionDesbloqueo.setForeground(new Color(255, 255, 255));
		textPaneDescripcionDesbloqueo.setBackground(new Color(64, 0, 64));
		textPaneDescripcionDesbloqueo.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		textPaneDescripcionDesbloqueo.setBounds(186, 480, 621, 105);
		panelPersonajeDesbloqueado.add(textPaneDescripcionDesbloqueo);
		ponerImagenAJlabel(lblTituloDesbloqueoPersonaje, "logo_street_fighter.png", false);

		lblCandado = new JLabel("");
		lblCandado.setBounds(242, 136, 115, 95);
		panelPersonajeDesbloqueado.add(lblCandado);

		lblMensajeDesbloqueado = new JLabel("");
		lblMensajeDesbloqueado.setBounds(-2, 0, 139, 105);
		panelPersonajeDesbloqueado.add(lblMensajeDesbloqueado);

		JLabel lblFondoDesbloqueoPersonaje = new JLabel("");
		lblFondoDesbloqueoPersonaje.setBounds(-2, 0, 837, 596);
		panelPersonajeDesbloqueado.add(lblFondoDesbloqueoPersonaje);

		panelHistoriaPersonajes = new JPanel();
		panelHistoriaPersonajes.setForeground(new Color(0, 0, 0));
		panelHistoriaPersonajes.setBackground(new Color(255, 255, 255));
		panelHistoriaPersonajes.setBounds(0, 0, 847, 663);
		contentPane.add(panelHistoriaPersonajes);
		panelHistoriaPersonajes.setLayout(null);
		panelHistoriaPersonajes.setVisible(false);

		textAreaDescripcionHistoria = new JTextArea();
		textAreaDescripcionHistoria.setOpaque(false);
		textAreaDescripcionHistoria.setForeground(Color.WHITE);
		textAreaDescripcionHistoria.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		textAreaDescripcionHistoria.setEnabled(false);
		textAreaDescripcionHistoria.setEditable(false);
		textAreaDescripcionHistoria.setBackground(Color.BLACK);
		textAreaDescripcionHistoria.setBounds(22, 551, 686, 39);
		panelHistoriaPersonajes.add(textAreaDescripcionHistoria);

		lblNewLabel = new JLabel("Descripción:");
		lblNewLabel.setForeground(new Color(255, 255, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(22, 514, 109, 39);
		panelHistoriaPersonajes.add(lblNewLabel);

		lblFisico = new JLabel("?");
		lblFisico.setForeground(new Color(255, 255, 255));
		lblFisico.setHorizontalAlignment(SwingConstants.CENTER);
		lblFisico.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFisico.setBounds(254, 80, 75, 34);
		panelHistoriaPersonajes.add(lblFisico);

		lblVelocidad = new JLabel("?");
		lblVelocidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblVelocidad.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVelocidad.setForeground(new Color(255, 255, 255));
		lblVelocidad.setBounds(156, 84, 58, 26);
		panelHistoriaPersonajes.add(lblVelocidad);

		lblPotencia = new JLabel("?");
		lblPotencia.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPotencia.setHorizontalAlignment(SwingConstants.CENTER);
		lblPotencia.setForeground(new Color(255, 255, 255));
		lblPotencia.setBounds(32, 84, 65, 26);
		panelHistoriaPersonajes.add(lblPotencia);

		lblPersonajeHistoriaImagen = new JLabel("");
		lblPersonajeHistoriaImagen.setBounds(301, 278, 259, 261);
		panelHistoriaPersonajes.add(lblPersonajeHistoriaImagen);

		lblNewLabel_potencia = new JLabel("POTENCIA");
		lblNewLabel_potencia.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_potencia.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_potencia.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_potencia.setForeground(new Color(255, 255, 128));
		lblNewLabel_potencia.setBackground(new Color(0, 0, 255));
		lblNewLabel_potencia.setBounds(11, 61, 116, 67);
		panelHistoriaPersonajes.add(lblNewLabel_potencia);

		comboBoxNombresHistoria = new JComboBox();
		comboBoxNombresHistoria.setForeground(new Color(255, 255, 255));
		comboBoxNombresHistoria.setBackground(new Color(0, 128, 255));
		comboBoxNombresHistoria.setFont(new Font("Dialog", Font.BOLD, 20));
		comboBoxNombresHistoria.setBounds(547, 48, 248, 39);
		panelHistoriaPersonajes.add(comboBoxNombresHistoria);

		btnMostrarHistoriaPersonaje = new JButton("Mostrar Historia");
		btnMostrarHistoriaPersonaje.setForeground(new Color(255, 255, 255));
		btnMostrarHistoriaPersonaje.setBackground(new Color(0, 128, 255));
		btnMostrarHistoriaPersonaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMostrarHistoriaPersonaje.setBounds(634, 116, 161, 34);
		panelHistoriaPersonajes.add(btnMostrarHistoriaPersonaje);

		JLabel lblNewLabel_1 = new JLabel("Nombre: ");
		lblNewLabel_1.setForeground(new Color(255, 255, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(15, 169, 65, 21);
		panelHistoriaPersonajes.add(lblNewLabel_1);

		lblNombreHIstoria = new JLabel("?");
		lblNombreHIstoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreHIstoria.setForeground(new Color(255, 255, 255));
		lblNombreHIstoria.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreHIstoria.setBounds(69, 168, 58, 21);
		panelHistoriaPersonajes.add(lblNombreHIstoria);

		JLabel lblNewLabel_2 = new JLabel("Edad: ");
		lblNewLabel_2.setForeground(new Color(255, 255, 128));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(15, 199, 36, 26);
		panelHistoriaPersonajes.add(lblNewLabel_2);

		lblEdadHIstoria = new JLabel("?");
		lblEdadHIstoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblEdadHIstoria.setForeground(new Color(255, 255, 255));
		lblEdadHIstoria.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEdadHIstoria.setBounds(44, 200, 36, 22);
		panelHistoriaPersonajes.add(lblEdadHIstoria);

		JLabel lblNewLabel_3 = new JLabel("Estatura: ");
		lblNewLabel_3.setForeground(new Color(255, 255, 128));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(15, 231, 65, 16);
		panelHistoriaPersonajes.add(lblNewLabel_3);

		lblEstatura = new JLabel("?");
		lblEstatura.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstatura.setForeground(new Color(255, 255, 255));
		lblEstatura.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEstatura.setBounds(69, 227, 58, 22);
		panelHistoriaPersonajes.add(lblEstatura);

		btnVolverAtrasDesdeLeyendas = new JButton("Atrás");
		btnVolverAtrasDesdeLeyendas.setBackground(new Color(255, 255, 0));
		btnVolverAtrasDesdeLeyendas.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVolverAtrasDesdeLeyendas.setBounds(707, 555, 99, 21);
		panelHistoriaPersonajes.add(btnVolverAtrasDesdeLeyendas);

		JLabel lblNewLabel_5 = new JLabel("Peso:");
		lblNewLabel_5.setForeground(new Color(255, 255, 128));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(15, 257, 52, 19);
		panelHistoriaPersonajes.add(lblNewLabel_5);

		lblPesoHistoria = new JLabel("?");
		lblPesoHistoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblPesoHistoria.setForeground(new Color(255, 255, 255));
		lblPesoHistoria.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPesoHistoria.setBounds(55, 252, 52, 26);
		panelHistoriaPersonajes.add(lblPesoHistoria);

		lblAvisosHistoria = new JLabel();
		lblAvisosHistoria.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAvisosHistoria.setForeground(new Color(255, 0, 0));
		lblAvisosHistoria.setBounds(264, 219, 397, 28);
		panelHistoriaPersonajes.add(lblAvisosHistoria);
		panelHistoriaPersonajes.setVisible(false);

		lblNewLabel_velocidad = new JLabel("VELOCIDAD");
		lblNewLabel_velocidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_velocidad.setForeground(new Color(255, 255, 128));
		lblNewLabel_velocidad.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_velocidad.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_velocidad.setBounds(125, 61, 116, 67);
		panelHistoriaPersonajes.add(lblNewLabel_velocidad);

		lblNewLabel_fisico = new JLabel("FÍSICO");
		lblNewLabel_fisico.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_fisico.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_fisico.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_fisico.setForeground(new Color(255, 255, 128));
		lblNewLabel_fisico.setBounds(236, 61, 116, 67);
		panelHistoriaPersonajes.add(lblNewLabel_fisico);

		ponerBordeJlabel(lblNewLabel_potencia);
		ponerBordeJlabel(lblNewLabel_velocidad);
		ponerBordeJlabel(lblNewLabel_fisico);

		lblFondoHistoriaPersonajes = new JLabel();
		lblFondoHistoriaPersonajes.setBounds(0, 0, 847, 608);
		ponerImagenAJlabel(lblFondoHistoriaPersonajes, "fondo_historias_personajes.jpg", true);
		panelHistoriaPersonajes.add(lblFondoHistoriaPersonajes);

		ponerImagenAJlabel(lblPersonajeHistoriaImagen, "interrogacion_historia.png", false);

		panelInformacion = new JPanel();
		panelInformacion.setBounds(0, 0, 829, 613);
		contentPane.add(panelInformacion);
		panelInformacion.setVisible(false);
		panelInformacion.setLayout(null);

		lblStreetMinis = new JLabel("");
		lblStreetMinis.setBounds(396, 10, 210, 142);
		panelInformacion.add(lblStreetMinis);

		textAreaScrollPanel = new JTextArea();
		textAreaScrollPanel.setBackground(new Color(0, 128, 255));
		textAreaScrollPanel.setForeground(new Color(255, 255, 255));
		textAreaScrollPanel.setEditable(false);
		textAreaScrollPanel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));

		textAreaScrollPanel.append(cargarScrollPanel());

		lblLogoStreetFighterInfo = new JLabel("");
		lblLogoStreetFighterInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLogoStreetFighterInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogoStreetFighterInfo.setBounds(52, 10, 255, 123);
		ponerImagenAJlabel(lblLogoStreetFighterInfo, "logo_inicio.png", false);
		panelInformacion.add(lblLogoStreetFighterInfo);

		scrollPaneInfo = new JScrollPane(textAreaScrollPanel);
		scrollPaneInfo.setToolTipText("");
		scrollPaneInfo.setBounds(126, 162, 648, 376);
		panelInformacion.add(scrollPaneInfo);

		btnVolverDeInfo = new JButton("Volver");
		btnVolverDeInfo.setBackground(new Color(255, 255, 0));
		btnVolverDeInfo.setForeground(new Color(0, 0, 0));
		btnVolverDeInfo.setBounds(22, 550, 79, 34);
		panelInformacion.add(btnVolverDeInfo);

		lblFondoInfo = new JLabel("");
		lblFondoInfo.setBounds(-10, -10, 861, 613);
		panelInformacion.add(lblFondoInfo);
		ponerImagenAJlabel(lblFondoInfo, "fondo_info.jpg", true);
		ponerImagenAJlabel(lblLogoStreetFighterInfo, "logo_street_fighter.png", false);

		panelCombate = new JPanel();
		panelCombate.setBounds(0, 0, 847, 603);
		contentPane.add(panelCombate);
		panelCombate.setLayout(null);
		panelCombate.setVisible(false);

		lblEliminado2 = new JLabel("");
		lblEliminado2.setBounds(531, 191, 281, 313);
		panelCombate.add(lblEliminado2);

		lblEliminado1 = new JLabel("");
		lblEliminado1.setBounds(24, 209, 283, 295);
		panelCombate.add(lblEliminado1);

		progressBarVidaPJ2 = new JProgressBar(0, 100);
		progressBarVidaPJ2.setForeground(new Color(255, 255, 0));
		progressBarVidaPJ2.setBackground(new Color(255, 0, 0));
		progressBarVidaPJ2.setBounds(471, 20, 281, 37);
		progressBarVidaPJ2.setValue(100);
		panelCombate.add(progressBarVidaPJ2);

		progressBarVidaPJ1 = new JProgressBar(0, 100);
		progressBarVidaPJ1.setBackground(new Color(255, 0, 0));
		progressBarVidaPJ1.setForeground(new Color(255, 255, 0));
		progressBarVidaPJ1.setBounds(85, 20, 281, 37);
		progressBarVidaPJ1.setValue(100);
		panelCombate.add(progressBarVidaPJ1);

		lblTiempo = new JLabel("60");
		lblTiempo.setForeground(new Color(255, 255, 255));
		lblTiempo.setBackground(new Color(255, 255, 255));
		lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempo.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTiempo.setBounds(385, 67, 73, 53);
		panelCombate.add(lblTiempo);

		btnAtacar = new JButton("ATACAR");
		btnAtacar.setForeground(new Color(255, 255, 255));
		btnAtacar.setBackground(new Color(0, 0, 255));
		btnAtacar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAtacar.setBounds(348, 229, 145, 37);
		panelCombate.add(btnAtacar);

		btnDefender = new JButton("DEFENDER");
		btnDefender.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDefender.setForeground(new Color(255, 255, 255));
		btnDefender.setBackground(new Color(0, 0, 255));
		btnDefender.setBounds(348, 303, 145, 37);
		panelCombate.add(btnDefender);

		btnDescansar = new JButton("DESCANSAR");
		btnDescansar.setBackground(new Color(0, 0, 255));
		btnDescansar.setForeground(new Color(255, 255, 255));
		btnDescansar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDescansar.setBounds(348, 370, 145, 37);
		panelCombate.add(btnDescansar);

		lblNombrePj1PanelJugar = new JLabel("PJ1");
		lblNombrePj1PanelJugar.setForeground(new Color(255, 255, 0));
		lblNombrePj1PanelJugar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombrePj1PanelJugar.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNombrePj1PanelJugar.setBounds(260, 136, 116, 45);
		panelCombate.add(lblNombrePj1PanelJugar);

		lblNombrePj2PanelJugar = new JLabel("PJ2");
		lblNombrePj2PanelJugar.setForeground(new Color(255, 255, 0));
		lblNombrePj2PanelJugar.setHorizontalAlignment(SwingConstants.LEFT);
		lblNombrePj2PanelJugar.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNombrePj2PanelJugar.setBounds(460, 136, 156, 45);
		panelCombate.add(lblNombrePj2PanelJugar);

		lblImgVS = new JLabel("");
		lblImgVS.setBounds(385, 130, 73, 65);
		panelCombate.add(lblImgVS);

		lblImagenJ1Juego = new JLabel("");
		lblImagenJ1Juego.setBounds(24, 209, 281, 313);
		panelCombate.add(lblImagenJ1Juego);

		lblImagenJ2Juego = new JLabel("");
		lblImagenJ2Juego.setBounds(531, 196, 281, 313);
		panelCombate.add(lblImagenJ2Juego);

		progressBarVitalidadPj1 = new JProgressBar(0, 100);
		progressBarVitalidadPj1.setBackground(new Color(128, 128, 128));
		progressBarVitalidadPj1.setForeground(new Color(255, 255, 255));
		progressBarVitalidadPj1.setBounds(24, 532, 281, 11);
		progressBarVitalidadPj1.setValue(100);
		panelCombate.add(progressBarVitalidadPj1);

		progressBarVitalidadPj2 = new JProgressBar(0, 100);
		progressBarVitalidadPj2.setBackground(new Color(128, 128, 128));
		progressBarVitalidadPj2.setForeground(new Color(255, 255, 255));
		progressBarVitalidadPj2.setBounds(531, 532, 281, 11);
		progressBarVitalidadPj2.setValue(100);
		panelCombate.add(progressBarVitalidadPj2);

		btnVolverDesdeJugar = new JButton("Volver");
		btnVolverDesdeJugar.setForeground(new Color(255, 0, 0));
		btnVolverDesdeJugar.setBackground(new Color(255, 255, 0));
		btnVolverDesdeJugar.setBounds(348, 427, 145, 21);
		btnVolverDesdeJugar.setOpaque(false);
		panelCombate.add(btnVolverDesdeJugar);

		labelKO = new JLabel("");
		labelKO.setBounds(386, 20, 62, 37);
		panelCombate.add(labelKO);

		lblCansancioPj1 = new JLabel("100");
		lblCansancioPj1.setForeground(new Color(255, 255, 255));
		lblCansancioPj1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCansancioPj1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCansancioPj1.setBounds(313, 517, 63, 37);
		panelCombate.add(lblCansancioPj1);

		lblCansancioPj2 = new JLabel("100");
		lblCansancioPj2.setForeground(new Color(255, 255, 255));
		lblCansancioPj2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCansancioPj2.setBounds(460, 517, 65, 37);
		panelCombate.add(lblCansancioPj2);

		lblVidaPj1 = new JLabel("100");
		lblVidaPj1.setForeground(new Color(255, 0, 0));
		lblVidaPj1.setHorizontalAlignment(SwingConstants.CENTER);
		lblVidaPj1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblVidaPj1.setBounds(24, 20, 51, 37);
		panelCombate.add(lblVidaPj1);

		lblVidaPj2 = new JLabel("100");
		lblVidaPj2.setForeground(new Color(0, 0, 255));
		lblVidaPj2.setHorizontalAlignment(SwingConstants.CENTER);
		lblVidaPj2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblVidaPj2.setBounds(761, 20, 51, 37);
		panelCombate.add(lblVidaPj2);

		lblMensajePj1 = new JLabel("");
		lblMensajePj1.setForeground(new Color(255, 255, 255));
		lblMensajePj1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMensajePj1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajePj1.setBounds(34, 67, 332, 27);
		panelCombate.add(lblMensajePj1);

		lblMensajePj2 = new JLabel("");
		lblMensajePj2.setForeground(new Color(255, 255, 255));
		lblMensajePj2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMensajePj2.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajePj2.setBounds(481, 71, 328, 27);
		panelCombate.add(lblMensajePj2);

		lblFondoPartida = new JLabel("");
		lblFondoPartida.setBounds(0, 0, 847, 603);
		panelCombate.add(lblFondoPartida);
		ponerImagenAJlabel(lblFondoPartida, "fondo_partida.jpeg", true);

		panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 851, 613);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);

		btnLeyendaPersonajes = new JButton("Leyenda personajes");
		btnLeyendaPersonajes.setFont(new Font("Segoe UI Light", Font.BOLD, 12));
		btnLeyendaPersonajes.setForeground(new Color(255, 255, 255));
		btnLeyendaPersonajes.setBackground(new Color(0, 128, 255));
		btnLeyendaPersonajes.setBounds(575, 480, 156, 21);
		panelMenu.add(btnLeyendaPersonajes);

		btnInfomracion = new JButton("Información");
		btnInfomracion.setFont(new Font("Segoe UI Light", Font.BOLD, 12));
		btnInfomracion.setForeground(new Color(255, 255, 255));
		btnInfomracion.setBackground(new Color(0, 128, 255));
		btnInfomracion.setBounds(575, 530, 156, 21);
		panelMenu.add(btnInfomracion);

		btnEnfrentamiento = new JButton("Enfrentamiento");
		btnEnfrentamiento.setFont(new Font("Segoe UI Light", Font.BOLD, 20));
		btnEnfrentamiento.setBackground(new Color(255, 255, 0));
		btnEnfrentamiento.setBounds(558, 396, 173, 40);
		panelMenu.add(btnEnfrentamiento);

		btnModoHistoria = new JButton("Modo Historia");
		btnModoHistoria.setFont(new Font("Segoe UI Light", Font.BOLD, 20));
		btnModoHistoria.setBackground(new Color(255, 255, 0));
		btnModoHistoria.setBounds(558, 316, 173, 40);
		panelMenu.add(btnModoHistoria);

		JLabel tituloJlabel = new JLabel();
		tituloJlabel.setBounds(432, 21, 366, 105);
		panelMenu.add(tituloJlabel);
		tituloJlabel.setOpaque(false);

		JLabel fondoMenu = new JLabel();
		fondoMenu.setBounds(0, 0, 847, 612);
		panelMenu.add(fondoMenu);

		ponerImagenAJlabel(tituloJlabel, "logo_street_fighter.png", false);
		ponerImagenAJlabel(fondoMenu, "fondo_inicio.png", true);
		ponerImagenAJlabel(lblStreetMinis, "informacion_etiqueta_minis.png", false);
		ponerImagenAJlabel(lblFondoDesbloqueoPersonaje, "fondo_desbloqueo_personaje.jpg", true);
		ponerImagenAJlabel(lblCandado, "candado.png", false);
		ponerImagenAJlabel(lblMensajeDesbloqueado, "new.png", false);
		ponerImagenAJlabel(lblFondoGanador, "fondo_ganador.jpg", false);
		ponerImagenAJlabel(lblLogoStreetFighter, "logo_street_fighter.png", false);
		ponerImagenAJlabel(lblImgWIN, "winner.png", false);
	}

	// METODO PARA INSERTAR IMAGENES EN LOS JLABEL

	
	
	public JButton getBtnLeyendaPersonajes() {
		return btnLeyendaPersonajes;
	}
	public JPanel getPanelGanador() {
		return panelGanador;
	}

	public JButton getBtnVovlerDesdeGanador() {
		return btnVovlerDesdeGanador;
	}

	public JLabel getLblimgGanador() {
		return lblimgGanador;
	}

	public ArrayList<Integer> getPosicionesDesbloqueadas() {
		return posicionesDesbloqueadas;
	}

	public void aniadirPosicionDesbloqueada (int posicion) {
		posicionesDesbloqueadas.add(posicion);
	}

	public String[] getRutasImagenesSeleccionPersonaje() {
		return rutasImagenesSeleccionPersonaje;
	}

	public ArrayList<JLabel> getSeleccionPersonajes() {
		return seleccionPersonajes;
	}

	public JButton getBtnSeleccionarComputadora() {
		return btnSeleccionarComputadora;
	}

	public JButton getBtnSeleccionarJugador() {
		return btnSeleccionarJugador;
	}

	
	public JLabel getLblNombreDesbloqueo() {
		return lblNombreDesbloqueo;
	}

	public JTextPane getTextPaneDescripcionDesbloqueo() {
		return textPaneDescripcionDesbloqueo;
	}

	public JLabel getLblPtenciaDesbloquearPersonaje() {
		return lblPtenciaDesbloquearPersonaje;
	}

	public JLabel getLblEdadDesbloqueoPersonaje() {
		return lblEdadDesbloqueoPersonaje;
	}

	public JLabel getLblEstaturaDesbloqueoPersonaje() {
		return lblEstaturaDesbloqueoPersonaje;
	}

	public JLabel getLblPesoDesbloqueoPersonaje() {
		return lblPesoDesbloqueoPersonaje;
	}

	public JLabel getLblFisicoDesbloqueoPersonaje() {
		return lblFisicoDesbloqueoPersonaje;
	}

	public JLabel getLblTituloDesbloqueoPersonaje() {
		return lblTituloDesbloqueoPersonaje;
	}

	public JLabel getLblImgJugadorDesbloqueado() {
		return lblImgJugadorDesbloqueado;
	}

	public JButton getBtnContinuarDesbloquePersonaje() {
		return btnContinuarDesbloquePersonaje;
	}

	public JPanel getPanelDesbloqueoPersonaje() {
		return panelPersonajeDesbloqueado;
	}

	public JPanel getPanelInformacion() {
		return panelInformacion;
	}

	public JScrollPane getScrollPaneInfo() {
		return scrollPaneInfo;
	}

	public JButton getBtnVolverDeInfo() {
		return btnVolverDeInfo;
	}

	public JLabel getLblAvisosSeleccionarJugador() {
		return lblAvisosSeleccionarJugador;
	}

	public JLabel getLblMensajePj1() {
		return lblMensajePj1;
	}

	public JLabel getLblMensajePj2() {
		return lblMensajePj2;
	}

	public JLabel getLblEliminado1() {
		return lblEliminado1;
	}

	public JLabel getLblEliminado2() {
		return lblEliminado2;
	}

	public JLabel getLblVidaPj2() {
		return lblVidaPj2;
	}

	public JLabel getLblVidaPj1() {
		return lblVidaPj1;
	}

	public JLabel getLblCansancioPj2() {
		return lblCansancioPj2;
	}

	public JLabel getLblCansancioPj1() {
		return lblCansancioPj1;
	}

	public JComboBox<String> getComboBoxNombresHistoria() {
		return comboBoxNombresHistoria;
	}

	public JLabel getLblImgVS() {
		return lblImgVS;
	}

	public JLabel getLblTiempo() {
		return lblTiempo;
	}

	public JProgressBar getProgressBarVidaPJ2() {
		return progressBarVidaPJ2;
	}

	public JProgressBar getProgressBarVidaPJ1() {
		return progressBarVidaPJ1;
	}

	public JProgressBar getProgressBarVitalidadPj1() {
		return progressBarVitalidadPj1;
	}

	public JProgressBar getProgressBarVitalidadPj2() {
		return progressBarVitalidadPj2;
	}

	public JLabel getLabelKO() {
		return labelKO;
	}

	public JLabel getLblImagenJ1Juego() {
		return lblImagenJ1Juego;
	}

	public JLabel getLblImagenJ2Juego() {
		return lblImagenJ2Juego;
	}

	public JButton getBtnVolverDesdeJugar() {
		return btnVolverDesdeJugar;
	}

	public JButton getBtnJugar() {
		return btnJugar;
	}

	public JLabel getLblNombrePj1PanelJugar() {
		return lblNombrePj1PanelJugar;
	}

	public JLabel getLblNombrePj2PanelJugar() {
		return lblNombrePj2PanelJugar;
	}

	public JPanel getPanelJuego() {
		return panelCombate;
	}

	public JButton getBtnAtacar() {
		return btnAtacar;
	}

	public JButton getBtnDefender() {
		return btnDefender;
	}

	public JButton getBtnDescansar() {
		return btnDescansar;
	}

	public JLabel getLblImgKO() {
		return lblImgVS;
	}

	public JLabel getLblVelocidadSeleccionarPersonaje() {
		return lblVelocidadSeleccionarPersonaje;
	}

	public JLabel getLblFisicoSeleccionarPersonaje() {
		return lblFisicoSeleccionarPersonaje;
	}

	public JLabel getLblPotenciaSeleccionarPersonaje() {
		return lblPotenciaSeleccionarPersonaje;
	}

	public JLabel getLblNombreSeleccionPersonaje() {
		return lblNombreSeleccionPersonaje;
	}

	public JLabel getLblAvisosHistoria() {
		return lblAvisosHistoria;
	}

	public JLabel getLblPesoHistoria() {
		return lblPesoHistoria;
	}

	public JButton getBtnInfomracion() {
		return btnInfomracion;
	}

	public JButton getBtnEnfrentamiento() {
		return btnEnfrentamiento;
	}

	public JButton getBtnModoHistoria() {
		return btnModoHistoria;
	}

	public JPanel getPanelHistoriaPersonajes() {
		return panelHistoriaPersonajes;
	}

	public JPanel getPanelMenu() {
		return panelMenu;
	}

	public JButton getBtnMostrarHistoriaPersonaje() {
		return btnMostrarHistoriaPersonaje;
	}

	public JComboBox getComboBoxNombresLuchadores() {
		return comboBoxNombresHistoria;
	}

	public JButton getBtnVolverAtrasDesdeLeyendas() {
		return btnVolverAtrasDesdeLeyendas;
	}

	public JLabel getLblEstatura() {
		return lblEstatura;
	}

	public JLabel getLblEdadHIstoria() {
		return lblEdadHIstoria;
	}

	public JLabel getLblNombreHIstoria() {
		return lblNombreHIstoria;
	}

	public JLabel getLblPersonajeHistoriaImagen() {
		return lblPersonajeHistoriaImagen;
	}

	public JTextArea getTextAreaDescripcionHistoria() {
		return textAreaDescripcionHistoria;
	}

	public JLabel getLblPotencia() {
		return lblPotencia;
	}

	public JLabel getLblVelocidad() {
		return lblVelocidad;
	}

	public JLabel getLblFisico() {
		return lblFisico;
	}

	public JPanel getPanelSeleccionPersonajes() {
		return panelSeleccionPersonajes;
	}
	
	
	public JButton getBtnVolverDesdeSeleccionarPersonaje() {
		return btnVolverDesdeSeleccionarPersonaje;
	}

	public JLabel getLblImgJ1Seleccionado() {
		return lblImgJ1Seleccionado;
	}

	public JLabel getLblImgJ2Seleccionado() {
		return lblImgJ2Seleccionado;
	}

	public JLabel getLblTitulo1PjSeleccionarPersonaje() {
		return lblTitulo1PjSeleccionarPersonaje;
	}

	public JLabel getLblTitulo2PjSeleccionarPersonaje() {
		return lblTitulo2PjSeleccionarPersonaje;
	}


	// PONER BORDE A JLABEL
	public void ponerBordeJlabel(JLabel label) {
		Border borde = new LineBorder(Color.DARK_GRAY, 5);
		label.setBorder(borde);
	}
	
	
	public ArrayList<JLabel> getSeleccionComputadora() {
		return seleccionComputadora;
	}

	public ArrayList<JLabel> getSobrepuestosComputadora() {
		return sobrepuestosComputadora;
	}
	

	public JLabel getLabelVelocidadDesbloqueoPersonaje() {
		return labelVelocidadDesbloqueoPersonaje;
	}

	// METODO PARA PONER IMAGENES A LOS JLABEL
	public void ponerImagenAJlabel(JLabel label, String nombreImagen, boolean opaque) {

		

		ImageIcon icono = new ImageIcon("src/imagenes/" + nombreImagen);
		Image imagen = icono.getImage();
		Image nuevaImagen = imagen.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
		icono = new ImageIcon(nuevaImagen);
		label.setIcon(icono);
		label.setOpaque(opaque);
	}

	// CARGA DE BOTONES
	public void caragarPanelSeleccionDePersonajes() {

		
		
		
		panelSeleccionPersonajes = new JPanel();
		panelSeleccionPersonajes.setBounds(2, 0, 835, 603);
		contentPane.add(panelSeleccionPersonajes);
		panelSeleccionPersonajes.setLayout(null);
		panelSeleccionPersonajes.setVisible(false);
		
		panelSeleccionPersonajes.removeAll();
		
		ponerJlabelsPesonajes();
		
		lblPotenciaSeleccionarPersonaje = new JLabel("?");
		lblPotenciaSeleccionarPersonaje.setForeground(new Color(255, 255, 255));
		lblPotenciaSeleccionarPersonaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPotenciaSeleccionarPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblPotenciaSeleccionarPersonaje.setBounds(65, 191, 38, 27);
		panelSeleccionPersonajes.add(lblPotenciaSeleccionarPersonaje);

		lblVelocidadSeleccionarPersonaje = new JLabel("?");
		lblVelocidadSeleccionarPersonaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVelocidadSeleccionarPersonaje.setForeground(new Color(255, 255, 255));
		lblVelocidadSeleccionarPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblVelocidadSeleccionarPersonaje.setBounds(65, 244, 38, 26);
		panelSeleccionPersonajes.add(lblVelocidadSeleccionarPersonaje);

		lblFisicoSeleccionarPersonaje = new JLabel("?");
		lblFisicoSeleccionarPersonaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFisicoSeleccionarPersonaje.setForeground(new Color(255, 255, 255));
		lblFisicoSeleccionarPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblFisicoSeleccionarPersonaje.setBounds(65, 298, 38, 26);
		panelSeleccionPersonajes.add(lblFisicoSeleccionarPersonaje);

		JLabel lblNewLabelValido = new JLabel("POTENCIA");
		lblNewLabelValido.setBackground(new Color(0, 0, 0));
		lblNewLabelValido.setForeground(new Color(255, 255, 128));
		lblNewLabelValido.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabelValido.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabelValido.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabelValido.setBounds(42, 165, 85, 58);
		lblNewLabelValido.setOpaque(true);
		panelSeleccionPersonajes.add(lblNewLabelValido);

		JLabel lblNewLabelValido_1 = new JLabel("VELOCIDAD");
		lblNewLabelValido_1.setBackground(new Color(0, 0, 0));
		lblNewLabelValido_1.setForeground(new Color(255, 255, 128));
		lblNewLabelValido_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabelValido_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabelValido_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabelValido_1.setBounds(42, 220, 85, 58);
		lblNewLabelValido_1.setOpaque(true);
		panelSeleccionPersonajes.add(lblNewLabelValido_1);

		JLabel lblNewLabelValido3 = new JLabel("FÍSICO");
		lblNewLabelValido3.setBackground(new Color(0, 0, 0));
		lblNewLabelValido3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabelValido3.setForeground(new Color(255, 255, 128));
		lblNewLabelValido3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabelValido3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabelValido3.setBounds(42, 276, 85, 58);
		lblNewLabelValido3.setOpaque(true);
		panelSeleccionPersonajes.add(lblNewLabelValido3);

		btnSeleccionarJugador = new JButton(" Seleccionar Jugador");
		btnSeleccionarJugador.setForeground(new Color(0, 0, 0));
		btnSeleccionarJugador.setBackground(new Color(255, 255, 0));
		btnSeleccionarJugador.setBounds(152, 104, 288, 21);
		panelSeleccionPersonajes.add(btnSeleccionarJugador);

		btnVolverDesdeSeleccionarPersonaje = new JButton("VOLVER");
		btnVolverDesdeSeleccionarPersonaje.setBackground(new Color(255, 255, 128));
		btnVolverDesdeSeleccionarPersonaje.setForeground(new Color(0, 0, 0));
		btnVolverDesdeSeleccionarPersonaje.setBounds(42, 555, 85, 21);
		panelSeleccionPersonajes.add(btnVolverDesdeSeleccionarPersonaje);
		ponerBordeJlabel(lblNewLabelValido);
		ponerBordeJlabel(lblNewLabelValido_1);
		ponerBordeJlabel(lblNewLabelValido3);

		lblNombreSeleccionPersonaje = new JLabel("?");
		lblNombreSeleccionPersonaje.setBackground(new Color(255, 255, 128));
		lblNombreSeleccionPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreSeleccionPersonaje.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNombreSeleccionPersonaje.setBounds(42, 104, 85, 41);
		lblNombreSeleccionPersonaje.setOpaque(true);
		panelSeleccionPersonajes.add(lblNombreSeleccionPersonaje);

		ponerBordeJlabel(lblNombreSeleccionPersonaje);

		lblImgJ1Seleccionado = new JLabel("");
		lblImgJ1Seleccionado.setBounds(188, 431, 167, 162);
		ponerBordeJlabel(lblImgJ1Seleccionado);
		panelSeleccionPersonajes.add(lblImgJ1Seleccionado);

		lblImgJ2Seleccionado = new JLabel("");
		lblImgJ2Seleccionado.setBounds(594, 431, 167, 162);
		ponerBordeJlabel(lblImgJ2Seleccionado);
		panelSeleccionPersonajes.add(lblImgJ2Seleccionado);

		lblTitulo1PjSeleccionarPersonaje = new JLabel("");
		lblTitulo1PjSeleccionarPersonaje.setBackground(new Color(255, 0, 0));
		lblTitulo1PjSeleccionarPersonaje.setForeground(new Color(255, 255, 0));
		lblTitulo1PjSeleccionarPersonaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo1PjSeleccionarPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo1PjSeleccionarPersonaje.setBounds(188, 394, 167, 27);
		lblTitulo1PjSeleccionarPersonaje.setOpaque(true);
		panelSeleccionPersonajes.add(lblTitulo1PjSeleccionarPersonaje);

		lblTitulo2PjSeleccionarPersonaje = new JLabel("");
		lblTitulo2PjSeleccionarPersonaje.setBackground(new Color(255, 0, 0));
		lblTitulo2PjSeleccionarPersonaje.setForeground(new Color(255, 255, 0));
		lblTitulo2PjSeleccionarPersonaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo2PjSeleccionarPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo2PjSeleccionarPersonaje.setBounds(594, 394, 167, 26);
		lblTitulo2PjSeleccionarPersonaje.setOpaque(true);
		panelSeleccionPersonajes.add(lblTitulo2PjSeleccionarPersonaje);

		JLabel lblVSSeleccionPersonajes = new JLabel("");
		lblVSSeleccionPersonajes.setBounds(402, 451, 167, 127);
		ponerImagenAJlabel(lblVSSeleccionPersonajes, "vs.png", false);
		panelSeleccionPersonajes.add(lblVSSeleccionPersonajes);

		btnJugar = new JButton("JUGAR");
		btnJugar.setForeground(new Color(255, 0, 0));
		btnJugar.setBackground(new Color(192, 192, 192));
		btnJugar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnJugar.setEnabled(false);
		btnJugar.setBounds(629, 22, 132, 46);
		panelSeleccionPersonajes.add(btnJugar);

		lblAvisosSeleccionarJugador = new JLabel("Seleccione personajes");
		lblAvisosSeleccionarJugador.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAvisosSeleccionarJugador.setForeground(new Color(255, 0, 0));
		lblAvisosSeleccionarJugador.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvisosSeleccionarJugador.setBounds(224, 22, 370, 33);

		panelSeleccionPersonajes.add(lblAvisosSeleccionarJugador);

		btnSeleccionarComputadora = new JButton("Seleccionar computadora");
		btnSeleccionarComputadora.setBackground(new Color(255, 255, 0));
		btnSeleccionarComputadora.setBounds(495, 104, 297, 21);
		panelSeleccionPersonajes.add(btnSeleccionarComputadora);
		
		FondoSeleccionPersonaje = new JLabel("");
		FondoSeleccionPersonaje.setBackground(new Color(255, 255, 0));
		FondoSeleccionPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		FondoSeleccionPersonaje.setBounds(0, 0, 845, 603);
		ponerImagenAJlabel(FondoSeleccionPersonaje, "fondo_seleccion_personaje.png", false);
		panelSeleccionPersonajes.add(FondoSeleccionPersonaje); 
		
		
	}

	public void ponerJlabelsPesonajes() {
		
		seleccionComputadora.clear();
		seleccionPersonajes.clear();
		
			

		int fila = 0;
		int columna = 0;
		int contador = 0;
		int ancho = 50;
		int alto = 50;
		int nombreLabel = 0;

		int posXInicial = 150; // Posición X inicial
		int posY = 130; // Posición Y inicial
		int separacionX = 10; // Separación horizontal entre los JLabels
		for (fila = 0; fila < 3; fila++) {
			for (columna = 0; columna < 5; columna++) {
				
				JLabel label = new JLabel();

				if (posicionesDesbloqueadas.contains(contador) || !Controlador.modoHistoria) {
				
					int posX = posXInicial + columna * (ancho + separacionX);

					label.setBounds(posX, posY + fila * (alto + 20), ancho, alto);
					ponerImagenAJlabel(label, rutasImagenesSeleccionPersonaje[nombreLabel], true);

					label.setName(nombreLabel + "");

					panelSeleccionPersonajes.add(label);
					seleccionPersonajes.add(label);
					
					nombreLabel++;
					contador++;

				} else {
					int posX = posXInicial + columna * (ancho + separacionX);

					label.setBounds(posX, posY + fila * (alto + 20), ancho, alto);
					ponerImagenAJlabel(label, "interrogacion_historia.png", true);

					label.setName(nombreLabel + "");

					panelSeleccionPersonajes.add(label);
					seleccionPersonajes.add(label);
					
					nombreLabel++;
					contador++;
					
				}
			}
		}

		
		nombreLabel = 0;
		int posXInicialB = 500; // Posición X inicial
		int posYB = 130; // Posición Y inicial
		int separacionXB = 10; // Separación horizontal entre los JLabels

		for (fila = 0; fila < 3; fila++) {
			for (columna = 0; columna < 5; columna++) {
				int posX = posXInicialB + columna * (ancho + separacionXB);
				
				JLabel label = new JLabel();
				JLabel sobrePuesto = new JLabel();

				sobrePuesto.setBounds(posX, posYB + fila * (alto + 20), ancho, alto);
				sobrePuesto.setOpaque(false);

				label.setBounds(posX, posYB + fila * (alto + 20), ancho, alto);
				ponerImagenAJlabel(label, rutasImagenesSeleccionPersonaje[nombreLabel], true);

				label.setName(nombreLabel + "");
				sobrePuesto.setName(nombreLabel + "");

				
				panelSeleccionPersonajes.add(sobrePuesto);
				panelSeleccionPersonajes.add(label);

				sobrepuestosComputadora.add(sobrePuesto);
				seleccionComputadora.add(label);
				nombreLabel++;

			}
		}
	}
	
	public String cargarScrollPanel() {

		String instrucciones = " *** Instrucciones del Juego ***\n" + "\n" + " *** 1. Objetivo General ***\n"
				+ " - Vencer al adversario:\n    reducir su vida a cero o mantener mayor nivel de vida al acabado el  tiempo.\n"
				+ "\n" + " *** 2. Elementos del Juego ***\n" + " *** 2.1 Personajes ***\n\n" + " - Atributos:\n"
				+ "    - Potencia\n" + "    - Velocidad\n" + "    - Físico\n" + "    - Vida\n"
				+ "    - Cansancio(Vitalidad)\n" + "\n\n" + " *** 2.2 Acciones Disponibles ***\n" + "\n"
				+ " | Acción               | Descripción                                                                   | Procedimiento                                    | Requisitos                        | Beneficio                                |\n"
				+ " |-------------------|-------------------------------------------------------------- |--------------------------------------------|------------------------------|-----------------------------------|\n"
				+ " | **Atacar**         | Lanza un ataque contra el oponente.                          | Valor aleatorio (1 - potencia)             | Vitalidad >= Daño           | Inflige daño al oponente.    |\n"
				+ " | **Defender**    | Prepara una defensa contra el ataque enemigo.        | Valor aleatorio (1 - velocidad)            | N/A                                   | Reduce el daño recibido.     |\n"
				+ " | **Descansar**   | Recupérate y fortalécete durante tu turno.                | Valor aleatorio (1 - físico)                   | N/A                                   | Aumenta la vitalidad.           |\n"
				+ "\n" + " *** 3. Condiciones de Victoria ***\n" + "\n"
				+ " | Condición                    | Criterio                                                      |\n"
				+ " |-------------------------- |-------------------------------------------------|\n"
				+ " | **Derrota por Vida**  | Reduce la vida del oponente a cero.        |\n" + "\n"
				+ " *** 4. Consideraciones Adicionales***\n"
				+ "    - La táctica es esencial; selecciona acciones según el estado y situación.\n"
				+ "    - Administrar la vitalidad adecuadamente prolonga tu duración en el combate.\n\n\n";

		return instrucciones;

	}
}
