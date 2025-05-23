package Controlador;

import BuilderPattern.CuentaBuilder;
import BuilderPattern.CuentaBuilderDirector;
import Modelo.Cuenta;
import Modelo.ServicioBancario;

public class CrearCuentaControlador {
    private final ServicioBancario servicio;

    public CrearCuentaControlador(ServicioBancario servicio) {
        this.servicio = servicio;
    }

    public void crearCuenta(String tipo, double saldoInicial, String dniTitular, String nombreTitular) {
        // Validaciones...
        if (dniTitular == null || dniTitular.isBlank() ||
                nombreTitular == null || nombreTitular.isBlank()) {
            throw new IllegalArgumentException("DNI y Nombre son obligatorios");
        }
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("Saldo inicial no puede ser negativo");
        }

        // 1) Obtenemos el builder
        CuentaBuilder builder = CuentaBuilderDirector.getBuilder(tipo)
                .setId(servicio.getCuentas().size() + 1)
                .setSaldo(saldoInicial)
                .setDniTitular(dniTitular)
                .setNombreTitular(nombreTitular);

        if (builder instanceof BuilderPattern.CuentaCorrienteBuilder ccBuilder) {
            ccBuilder.setLimite(1500);
        }

        // 3) Construir y persistir
        Cuenta cuenta = builder.build();
        servicio.agregarCuenta(cuenta);
    }
}
