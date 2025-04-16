package Modelo;

public class NivelesFactory {

    public static ConfiguradorNivel crearConfigurador(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "CLASSIC" -> new NivelClassic();
            case "SOFT" -> new NivelSoft();
            case "EMPTY" -> new NivelEmpty();
            default -> throw new IllegalArgumentException("Tipo de nivel desconocido: " + tipo);
        };
    }
}