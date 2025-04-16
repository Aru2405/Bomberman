package Modelo;

public class NivelesFactory {
	private static NivelesFactory misNiveles;
	private NivelesFactory() {}
	public static NivelesFactory getmisNiveles() {
		if (misNiveles==null) {
			misNiveles=new NivelesFactory();
		}
		return misNiveles;
	}

    public static ConfiguradorNivel crearConfigurador(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "CLASSIC" -> new NivelClassic();
            case "SOFT" -> new NivelSoft();
            case "EMPTY" -> new NivelEmpty();
            default -> throw new IllegalArgumentException("Tipo de nivel desconocido: " + tipo);
        };
    }
}
