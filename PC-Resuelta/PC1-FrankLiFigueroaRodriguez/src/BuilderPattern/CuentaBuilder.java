package BuilderPattern;
import Modelo.*;
public interface CuentaBuilder {
    CuentaBuilder setId(int id);
    CuentaBuilder setSaldo(double saldoInicial);
    CuentaBuilder setDniTitular(String dni);
    CuentaBuilder setNombreTitular(String nombre);
    Cuenta build();
}
