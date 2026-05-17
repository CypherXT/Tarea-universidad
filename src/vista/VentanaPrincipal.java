package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import modelo2.Escuela;
import modelo1.Carrera;
import datos.ConexionBD;

public class VentanaPrincipal extends JFrame {
    private JComboBox<Escuela> comboEscuelas;
    private JButton btnConsultar;
    private JTable tablaCarreras;
    private DefaultTableModel modeloTabla;
    private ConexionBD conexionBD;
    private JLabel lblEstado;
    
    public VentanaPrincipal() {
        conexionBD = new ConexionBD();
        initComponents();
        cargarEscuelas();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Sistema de Consulta de Carreras - UASD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());
        
        // Panel superior (selección de escuela)
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelSuperior.setBackground(new Color(0, 102, 204));
        
        JLabel lblTitulo = new JLabel("Sistema de Consulta de Carreras UASD");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblEscuela = new JLabel("Seleccionar Escuela/Facultad:");
        lblEscuela.setFont(new Font("Arial", Font.BOLD, 14));
        lblEscuela.setForeground(Color.WHITE);
        
        comboEscuelas = new JComboBox<>();
        comboEscuelas.setPreferredSize(new Dimension(250, 30));
        comboEscuelas.setFont(new Font("Arial", Font.PLAIN, 12));
        
        btnConsultar = new JButton("Consultar Carreras");
        btnConsultar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConsultar.setBackground(new Color(255, 204, 0));
        btnConsultar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createHorizontalStrut(20));
        panelSuperior.add(lblEscuela);
        panelSuperior.add(comboEscuelas);
        panelSuperior.add(btnConsultar);
        
        // Panel central (tabla de resultados)
        String[] columnas = {"ID", "Nombre de la Carrera", "Duración (semestres)", "Créditos Totales", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaCarreras = new JTable(modeloTabla);
        tablaCarreras.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaCarreras.setRowHeight(25);
        tablaCarreras.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaCarreras.getTableHeader().setBackground(new Color(0, 102, 204));
        tablaCarreras.getTableHeader().setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(tablaCarreras);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Carreras Disponibles"));
        
        // Panel inferior (estado)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(new Color(240, 240, 240));
        lblEstado = new JLabel("Listo para consultar");
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 11));
        panelInferior.add(lblEstado);
        
        // Agregar paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        // Configurar acción del botón
        btnConsultar.addActionListener(e -> consultarCarreras());
    }
    
    private void cargarEscuelas() {
        try {
            List<Escuela> escuelas = conexionBD.obtenerEscuelas();
            comboEscuelas.removeAllItems();
            for (Escuela escuela : escuelas) {
                comboEscuelas.addItem(escuela);
            }
            if (escuelas.isEmpty()) {
                lblEstado.setText("No se encontraron escuelas en la base de datos");
            } else {
                lblEstado.setText("Escuelas cargadas correctamente. Seleccione una y consulte.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar escuelas: " + e.getMessage(),
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            lblEstado.setText("Error al conectar con la base de datos");
        }
    }
    
    private void consultarCarreras() {
        Escuela escuelaSeleccionada = (Escuela) comboEscuelas.getSelectedItem();
        
        if (escuelaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione una escuela/facultad",
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        // Mostrar mensaje de carga
        lblEstado.setText("Consultando carreras de " + escuelaSeleccionada.getNombre() + "...");
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        // Ejecutar consulta en un hilo separado
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private List<Carrera> carreras;
            private String error = null;
            
            @Override
            protected Void doInBackground() {
                try {
                    carreras = conexionBD.obtenerCarrerasPorEscuela(escuelaSeleccionada.getId());
                } catch (Exception e) {
                    error = e.getMessage();
                }
                return null;
            }
            
            @Override
            protected void done() {
                if (error != null) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, 
                        "Error al consultar carreras: " + error,
                        "Error de Consulta", 
                        JOptionPane.ERROR_MESSAGE);
                    lblEstado.setText("Error en la consulta");
                } else {
                    if (carreras.isEmpty()) {
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, 
                            "No hay carreras registradas para esta escuela",
                            "Sin Resultados", 
                            JOptionPane.INFORMATION_MESSAGE);
                        lblEstado.setText("No se encontraron carreras para " + escuelaSeleccionada.getNombre());
                    } else {
                        for (Carrera carrera : carreras) {
                            modeloTabla.addRow(new Object[]{
                                carrera.getId(),
                                carrera.getNombre(),
                                carrera.getDuracionSemestres(),
                                carrera.getCreditosTotales(),
                                carrera.getDescripcion()
                            });
                        }
                        lblEstado.setText("Se encontraron " + carreras.size() + 
                                        " carreras en " + escuelaSeleccionada.getNombre());
                    }
                }
                setCursor(Cursor.getDefaultCursor());
            }
        };
        worker.execute();
    }
    
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}