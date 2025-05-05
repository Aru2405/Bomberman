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
	    switch (tipo.toUpperCase()) {
	        case "CLASSIC":
	            return new NivelClassic();
	        case "SOFT":
	            return new NivelSoft();
	        case "EMPTY":
	            return new NivelEmpty();
	        default:
	            throw new IllegalArgumentException("Tipo de nivel desconocido: " + tipo);
	    }
	}

}
