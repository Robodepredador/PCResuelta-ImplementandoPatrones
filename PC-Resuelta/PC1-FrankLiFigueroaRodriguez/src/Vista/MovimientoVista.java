package Vista;

import Modelo.*;
import Controlador.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MovimientoVista extends JPanel {
    private final ServicioBancario servicio;
    private final JComboBox<Cuenta> cuentaCombo;
    private final JTextArea movimientosArea;

    public MovimientoVista(ServicioBancario servicio) {
        this.servicio = servicio;
        MovimientosControlador controlador = new MovimientosControlador(servicio);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        cuentaCombo = new JComboBox<>();
        movimientosArea = new JTextArea(15, 40);
        movimientosArea.setEditable(false);

        JButton mostrarBtn = new JButton("Mostrar movimientos");
        JButton actualizarBtn = new JButton("Actualizar cuentas");

        cuentaCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cuenta cuenta) {
                    setText(String.format("%d - %s (%s)",
                            cuenta.getId(), cuenta.getNombreTitular(), cuenta.getDniTitular()));
                }
                return this;
            }
        });

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Consultar movimientos",
                TitledBorder.LEFT, TitledBorder.TOP));
        controlPanel.add(new JLabel("Cuenta:"));
        controlPanel.add(cuentaCombo);
        controlPanel.add(mostrarBtn);
        controlPanel.add(actualizarBtn);

        JScrollPane scroll = new JScrollPane(movimientosArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Movimientos"));

        add(controlPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        actualizarCombos();

        actualizarBtn.addActionListener(e -> actualizarCombos());

        mostrarBtn.addActionListener(e -> {
            movimientosArea.setText("");
            Cuenta cuenta = (Cuenta) cuentaCombo.getSelectedItem();
            if (cuenta != null) {
                controlador.obtenerMovimientos(cuenta.getId()).forEach(m ->
                        movimientosArea.append(String.format("%tF - %s: $%.2f%n",
                                m.getFecha(), m.getDescripcion(), m.getMonto()))
                );
            }
        });
    }

    public void actualizarCombos() {
        SwingUtilities.invokeLater(() -> {
            cuentaCombo.removeAllItems();
            servicio.getCuentas().forEach(cuentaCombo::addItem);
        });
    }
}
