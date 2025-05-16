package Vista;

import Modelo.ServicioBancario;
import Controlador.CrearCuentaControlador;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CrearCuentaVista extends JPanel {
    public CrearCuentaVista(ServicioBancario servicio) {
        CrearCuentaControlador controlador = new CrearCuentaControlador(servicio);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Datos de la nueva cuenta",
                TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Ahorro", "Corriente"});
        JTextField dniField = new JTextField(10);
        JTextField nombreField = new JTextField(20);
        JTextField saldoField = new JTextField(10);
        JButton crearBtn = new JButton("Crear Cuenta");

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Tipo de cuenta:"), gbc);
        gbc.gridx = 1; formPanel.add(tipoCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1; formPanel.add(dniField, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; formPanel.add(nombreField, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Saldo inicial:"), gbc);
        gbc.gridx = 1; formPanel.add(saldoField, gbc);

        gbc.gridx = 1; gbc.gridy = ++y; formPanel.add(crearBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        crearBtn.addActionListener(e -> {
            try {
                String tipo = (String) tipoCombo.getSelectedItem();
                double saldo = Double.parseDouble(saldoField.getText());
                String dni = dniField.getText();
                String nombre = nombreField.getText();

                controlador.crearCuenta(tipo, saldo, dni, nombre);
                JOptionPane.showMessageDialog(this, "¡Cuenta creada exitosamente!");

                saldoField.setText(""); dniField.setText(""); nombreField.setText("");

                actualizarOtrasVistas(this);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un saldo válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void actualizarOtrasVistas(Component componenteActual) {
        Container parent = componenteActual.getParent();
        while (parent != null && !(parent instanceof JTabbedPane)) {
            parent = parent.getParent();
        }

        if (parent instanceof JTabbedPane tabbedPane) {
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                Component tab = tabbedPane.getComponentAt(i);
                if (tab instanceof TransferenciaVista vista) vista.actualizarCombos();
                if (tab instanceof MovimientoVista vista) vista.actualizarCombos();
            }
        }
    }
}
