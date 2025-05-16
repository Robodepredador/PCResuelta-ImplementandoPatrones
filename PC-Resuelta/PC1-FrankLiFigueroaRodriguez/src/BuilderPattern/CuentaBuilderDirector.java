package BuilderPattern;

public class CuentaBuilderDirector {
    /**
     * Retorna un builder configurado según el tipo que elijas
     */
    public static CuentaBuilder getBuilder(String tipo) {
        return switch (tipo) {
            case "Ahorro"    -> new CuentaAhorroBuilder();
            case "Corriente" -> new CuentaCorrienteBuilder();
            default          -> throw new IllegalArgumentException("Tipo de cuenta inválido: " + tipo);
        };
    }
}
