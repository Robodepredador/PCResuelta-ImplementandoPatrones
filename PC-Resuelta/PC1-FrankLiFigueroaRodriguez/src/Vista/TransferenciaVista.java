package Vista;

import Modelo.*;
import Controlador.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TransferenciaVista extends JPanel {
    private final JComboBox<Cuenta> origenCombo;
    private final JComboBox<Cuenta> destinoCombo;
    private final ServicioBancario servicio;

    public TransferenciaVista(ServicioBancario servicio) {
        this.servicio = servicio;
        TransferenciaControlador controlador = new TransferenciaControlador(servicio);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Realizar transferencia",
                TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        origenCombo = new JComboBox<>();
        destinoCombo = new JComboBox<>();
        JTextField montoField = new JTextField(10);
        JButton transferirBtn = new JButton("Transferir");
        JButton actualizarBtn = new JButton("Actualizar cuentas");

        DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cuenta cuenta) {
                    setText(String.format("%d - %s (%s) - $%.2f",
                            cuenta.getId(), cuenta.getNombreTitular(),
                            cuenta.getDniTitular(), cuenta.getSaldo()));
                }
                return this;
            }
        };
        origenCombo.setRenderer(renderer);
        destinoCombo.setRenderer(renderer);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Cuenta Origen:"), gbc);
        gbc.gridx = 1; formPanel.add(origenCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Cuenta Destino:"), gbc);
        gbc.gridx = 1; formPanel.add(destinoCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Monto:"), gbc);
        gbc.gridx = 1; formPanel.add(montoField, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(transferirBtn, gbc);
        gbc.gridx = 1; formPanel.add(actualizarBtn, gbc);

        add(formPanel, BorderLayout.CENTER);
        actualizarCombos();

        actualizarBtn.addActionListener(e -> actualizarCombos());

        transferirBtn.addActionListener(e -> {
            try {
                Cuenta origen = (Cuenta) origenCombo.getSelectedItem();
                Cuenta destino = (Cuenta) destinoCombo.getSelectedItem();
                if (origen == null || destino == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione ambas cuentas.");
                    return;
                }

                double monto = Double.parseDouble(montoField.getText());
                boolean exito = controlador.transferir(origen.getId(), destino.getId(), monto);
                JOptionPane.showMessageDialog(this, exito ? "Transferencia exitosa!" : "Error en la transferencia");
                actualizarCombos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
            montoField.setText("");
        });
    }

    public void actualizarCombos() {
        origenCombo.removeAllItems();
        destinoCombo.removeAllItems();
        for (Cuenta cuenta : servicio.getCuentas()) {
            origenCombo.addItem(cuenta);
            destinoCombo.addItem(cuenta);
        }
    }
}
