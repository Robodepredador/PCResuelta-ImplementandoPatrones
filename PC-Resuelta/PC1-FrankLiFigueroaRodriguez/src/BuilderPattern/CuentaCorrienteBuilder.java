package BuilderPattern;

import Modelo.Cuenta;
import Modelo.CuentaCorriente;

public class CuentaCorrienteBuilder implements CuentaBuilder {
    private int id;
    private double saldo;
    private String dniTitular;
    private String nombreTitular;
    private double limite = 1000;  // valor por defecto

    @Override
    public CuentaCorrienteBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public CuentaCorrienteBuilder setSaldo(double saldoInicial) {
        this.saldo = saldoInicial;
        return this;
    }

    @Override
    public CuentaCorrienteBuilder setDniTitular(String dni) {
        this.dniTitular = dni;
        return this;
    }

    @Override
    public CuentaCorrienteBuilder setNombreTitular(String nombre) {
        this.nombreTitular = nombre;
        return this;
    }

    /** Método propio para configurar límite distinto del defecto */
    public CuentaCorrienteBuilder setLimite(double limite) {
        this.limite = limite;
        return this;
    }

    @Override
    public Cuenta build() {
        return new CuentaCorriente(id, saldo, limite, dniTitular, nombreTitular);
    }
}
