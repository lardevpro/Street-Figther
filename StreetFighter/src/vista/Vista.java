package vista;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controlador.Controlador;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String rutasImagenesSeleccionPersonaje[][] = {
			{ "ryu_select.jpeg", "ken_select.jpeg", "guile_select.jpeg", "chun_li_select.jpeg", "honda_select.jpeg" },
			{ "dalshim_select.jpeg", "blanka_select.jpeg", "zangief_select.jpeg", "dee_jay_select.jpeg",
					"t_hawk_select.jpeg" },
			{ "cammy_select.jpeg", "balrog_select.jpeg", "vega_select.jpeg", "sagat_select.jpeg",
					"mbison_select.jpeg" } };
	private JButton btnLeyendaPersonajes, btnInfomracion, btnEnfrentamiento, btnModoHistoria,
			btnMostrarHistoriaPersonaje, btnSeleccionarPersonaje, btnVolverDesdeSeleccionarPersonaje,
			btnVolverAtrasDesdeLeyendas;

	private JComboBox<String> comboBoxNombresHistoria;
	private JPanel panelHistoriaPersonajes, panelMenu, panelSeleccionPersonajes;

	private JTextArea textAreaDescripcionHistoria;

	private JLabel lblEstatura, lblPesoHistoria, lblEdadHIstoria, lblNombreHIstoria, lblPersonajeHistoriaImagen,
			lblAvisosHistoria, lblNewLabel_potencia, lblNewLabel_velocidad, lblNewLabel_fisico, lblPotencia,
			lblVelocidad, lblFisico, FondoSeleccionPersonaje, lblFondoHistoriaPersonajes,
			lblVelocidadSeleccionarPersonaje, lblFisicoSeleccionarPersonaje, lblPotenciaSeleccionarPersonaje;

	private JLabel labelsSeleccionPersonajes[][];
	private JLabel lblNombreSeleccionPersonaje;
	private JPanel panelJuegoEntrenamiento;
	private JButton btnAtacar;
	private JButton btnDefender;
	private JButton btnDescansar;
	private JLabel lblNombrePj1PanelJugar;
	private JLabel lblNombrePj2PanelJugar;
	private JLabel lblImgKO;
	private JLabel lblFondoPartida;
	private JLabel lblImgJ1Seleccionado;
	private JLabel lblImgJ2Seleccionado;
	private JLabel lblTitulo1PjSeleccionarPersonaje;
	private JLabel lblTitulo2PjSeleccionarPersonaje;
	private JLabel lblTiempo;
	private JProgressBar progressBarPJ2;
	private JProgressBar progressBarPJ1;
	private JButton btnJugar;

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

		labelsSeleccionPersonajes = new JLabel[3][5];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelSeleccionPersonajes = new JPanel();
		panelSeleccionPersonajes.setBounds(2, 0, 835, 603);
		contentPane.add(panelSeleccionPersonajes);
		panelSeleccionPersonajes.setLayout(null);
		caragarPeronajeseSeleccion(15);
		panelSeleccionPersonajes.setVisible(false);

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

		btnSeleccionarPersonaje = new JButton("SELECCIONAR");
		btnSeleccionarPersonaje.setForeground(new Color(0, 0, 0));
		btnSeleccionarPersonaje.setBackground(new Color(255, 255, 0));
		btnSeleccionarPersonaje.setBounds(42, 22, 157, 33);
		panelSeleccionPersonajes.add(btnSeleccionarPersonaje);

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

		lblTitulo1PjSeleccionarPersonaje = new JLabel("New label");
		lblTitulo1PjSeleccionarPersonaje.setBackground(new Color(255, 0, 0));
		lblTitulo1PjSeleccionarPersonaje.setForeground(new Color(255, 255, 0));
		lblTitulo1PjSeleccionarPersonaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo1PjSeleccionarPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo1PjSeleccionarPersonaje.setBounds(188, 394, 167, 27);
		lblTitulo1PjSeleccionarPersonaje.setOpaque(true);
		panelSeleccionPersonajes.add(lblTitulo1PjSeleccionarPersonaje);

		lblTitulo2PjSeleccionarPersonaje = new JLabel("New label");
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

		FondoSeleccionPersonaje = new JLabel("");
		FondoSeleccionPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
		FondoSeleccionPersonaje.setBounds(0, 0, 845, 603);
		ponerImagenAJlabel(FondoSeleccionPersonaje, "fondo_seleccion_personaje.png", false);
		panelSeleccionPersonajes.add(FondoSeleccionPersonaje);

		panelHistoriaPersonajes = new JPanel();
		panelHistoriaPersonajes.setForeground(new Color(0, 0, 0));
		panelHistoriaPersonajes.setBackground(new Color(255, 255, 255));
		panelHistoriaPersonajes.setBounds(0, 0, 847, 603);
		contentPane.add(panelHistoriaPersonajes);
		panelHistoriaPersonajes.setLayout(null);
		panelHistoriaPersonajes.setVisible(false);

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
		lblPersonajeHistoriaImagen.setBounds(300, 283, 259, 261);
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
		comboBoxNombresHistoria.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBoxNombresHistoria.setBounds(600, 48, 195, 21);
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

		JLabel lblNewLabel_4 = new JLabel("Descripción:");
		lblNewLabel_4.setForeground(new Color(255, 255, 128));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_4.setBounds(18, 410, 109, 39);
		panelHistoriaPersonajes.add(lblNewLabel_4);

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
		lblPesoHistoria.setBounds(45, 252, 52, 26);
		panelHistoriaPersonajes.add(lblPesoHistoria);

		textAreaDescripcionHistoria = new JTextArea();
		textAreaDescripcionHistoria.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		textAreaDescripcionHistoria.setEnabled(false);
		textAreaDescripcionHistoria.setEditable(false);
		textAreaDescripcionHistoria.setBackground(new Color(0, 0, 0));
		textAreaDescripcionHistoria.setForeground(new Color(255, 255, 255));
		textAreaDescripcionHistoria.setBounds(15, 459, 370, 144);
		textAreaDescripcionHistoria.setOpaque(false);
		panelHistoriaPersonajes.add(textAreaDescripcionHistoria);

		lblAvisosHistoria = new JLabel();
		lblAvisosHistoria.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAvisosHistoria.setForeground(new Color(255, 0, 0));
		lblAvisosHistoria.setBounds(231, 188, 317, 28);
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
		lblFondoHistoriaPersonajes.setBounds(0, 0, 847, 603);
		ponerImagenAJlabel(lblFondoHistoriaPersonajes, "fondo_historias_personajes.jpg", true);
		panelHistoriaPersonajes.add(lblFondoHistoriaPersonajes);

		ponerImagenAJlabel(lblPersonajeHistoriaImagen, "interrogacion_historia.png", false);

		panelJuegoEntrenamiento = new JPanel();
		panelJuegoEntrenamiento.setBounds(0, 0, 847, 603);
		contentPane.add(panelJuegoEntrenamiento);
		panelJuegoEntrenamiento.setLayout(null);
		panelJuegoEntrenamiento.setVisible(false);

		progressBarPJ2 = new JProgressBar();
		progressBarPJ2.setForeground(new Color(255, 0, 0));
		progressBarPJ2.setBackground(new Color(255, 255, 0));
		progressBarPJ2.setBounds(39, 66, 323, 37);
		panelJuegoEntrenamiento.add(progressBarPJ2);

		progressBarPJ1 = new JProgressBar();
		progressBarPJ1.setBackground(new Color(255, 255, 0));
		progressBarPJ1.setForeground(new Color(255, 0, 0));
		progressBarPJ1.setBounds(470, 66, 323, 37);
		panelJuegoEntrenamiento.add(progressBarPJ1);

		lblTiempo = new JLabel("60");
		lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTiempo.setBounds(387, 132, 66, 63);
		panelJuegoEntrenamiento.add(lblTiempo);

		btnAtacar = new JButton("ATACAR");
		btnAtacar.setForeground(new Color(255, 255, 255));
		btnAtacar.setBackground(new Color(0, 0, 255));
		btnAtacar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAtacar.setBounds(356, 206, 132, 37);
		panelJuegoEntrenamiento.add(btnAtacar);

		btnDefender = new JButton("DEFENDER");
		btnDefender.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDefender.setForeground(new Color(255, 255, 255));
		btnDefender.setBackground(new Color(0, 0, 255));
		btnDefender.setBounds(356, 268, 132, 37);
		panelJuegoEntrenamiento.add(btnDefender);

		btnDescansar = new JButton("DESCANSAR");
		btnDescansar.setBackground(new Color(0, 0, 255));
		btnDescansar.setForeground(new Color(255, 255, 255));
		btnDescansar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDescansar.setBounds(356, 332, 132, 37);
		panelJuegoEntrenamiento.add(btnDescansar);

		lblNombrePj1PanelJugar = new JLabel("PJ1");
		lblNombrePj1PanelJugar.setForeground(new Color(255, 255, 0));
		lblNombrePj1PanelJugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrePj1PanelJugar.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNombrePj1PanelJugar.setBounds(39, 132, 187, 45);
		panelJuegoEntrenamiento.add(lblNombrePj1PanelJugar);

		lblNombrePj2PanelJugar = new JLabel("PJ2");
		lblNombrePj2PanelJugar.setForeground(new Color(255, 255, 0));
		lblNombrePj2PanelJugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrePj2PanelJugar.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNombrePj2PanelJugar.setBounds(606, 140, 187, 45);
		panelJuegoEntrenamiento.add(lblNombrePj2PanelJugar);

		lblImgKO = new JLabel("New label");
		lblImgKO.setBounds(372, 48, 88, 74);
		panelJuegoEntrenamiento.add(lblImgKO);

		lblFondoPartida = new JLabel("");
		lblFondoPartida.setBounds(0, 10, 847, 603);
		panelJuegoEntrenamiento.add(lblFondoPartida);

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

		ponerImagenAJlabel(tituloJlabel, "logo_inicio.png", false);
		ponerImagenAJlabel(fondoMenu, "fondo_inicio.png", true);
		ponerImagenAJlabel(lblFondoPartida, "fondo_partida.jpeg", true);

	}

	// METODO PARA INSERTAR IMAGENES EN LOS JLABEL

	
	public JButton getBtnLeyendaPersonajes() {
		return btnLeyendaPersonajes;
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

	public JPanel getPanelJuegoEntrenamiento() {
		return panelJuegoEntrenamiento;
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
		return lblImgKO;
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

	public JLabel[][] getLabelsSeleccionPersonajes() {
		return labelsSeleccionPersonajes;
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

	// PONER BORDE A JLABEL
	public void ponerBordeJlabel(JLabel label) {
		Border borde = new LineBorder(Color.DARK_GRAY, 5);
		label.setBorder(borde);
	}

	public JButton getBtnSeleccionarPersonaje() {
		return btnSeleccionarPersonaje;
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

	public void setLabelsSeleccionPersonajes(JLabel[][] labelsSeleccionPersonajes) {
		this.labelsSeleccionPersonajes = labelsSeleccionPersonajes;
	}

	// METODO PARA PONER IMAGENES A LOS JLABEL
	public void ponerImagenAJlabel(JLabel label, String nombreImagen, boolean opaque) {

		ImageIcon icono = new ImageIcon("src/imagenes/" + nombreImagen);
		java.awt.Image imagen = icono.getImage();
		java.awt.Image nuevaImagen = imagen.getScaledInstance(label.getWidth(), label.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		icono = new ImageIcon(nuevaImagen);
		label.setIcon(icono);
		label.setOpaque(opaque);
		label.revalidate();
		label.repaint();
	}

	// CARGA DE BOTONES
	public void caragarPeronajeseSeleccion(int personajesActivosLimite) {
		int activados = 0;
		int posY = 100; // px
		int posX = 260;
		int ancho = 70;
		int alto = 70;
		int x = 0;
		int y = 0;
		for (int i = 0; i < labelsSeleccionPersonajes.length; i++) {
			for (int j = 0; j < labelsSeleccionPersonajes[i].length; j++) {

				labelsSeleccionPersonajes[i][j] = new JLabel();
				if (activados < personajesActivosLimite) {
					labelsSeleccionPersonajes[i][j].setName(activados + "");
					labelsSeleccionPersonajes[i][j].setText(activados + "");

				} else {
					labelsSeleccionPersonajes[i][j].setName(null);
					labelsSeleccionPersonajes[i][j].setText(null);

				}

				// Establece las coordenadas de los botones

				x = posX;
				y = posY;

				for (int col = 0; col < j; col++) {
					x += ancho + 20;
				}

				for (int fila = 0; fila < i; fila++) {
					y += alto + 20;
				}
				if (activados < personajesActivosLimite) {
					labelsSeleccionPersonajes[i][j].setBounds(x, y, ancho, alto);
					panelSeleccionPersonajes.add(labelsSeleccionPersonajes[i][j]);
					ponerImagenAJlabel(labelsSeleccionPersonajes[i][j], rutasImagenesSeleccionPersonaje[i][j], true);

					activados++;
				} else {
					labelsSeleccionPersonajes[i][j].setBounds(x, y, ancho, alto);
					panelSeleccionPersonajes.add(labelsSeleccionPersonajes[i][j]);
					ponerImagenAJlabel(labelsSeleccionPersonajes[i][j], "personaje_secreto.jpg", true);
					activados++;
				}
			}
		}
	}
}