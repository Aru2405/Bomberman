package Modelo;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Bomberman...");

        //Crear una nueva partida
        Partida partida = Partida.getPartida();
        partida.iniciarJuego();

        //Obtener el tablero e imprimirlo
        Tablero tablero = Tablero.getTablero();
        tablero.imprimirTablero();

        //Crear Bomberman en la posición inicial (0,0)
        Bomberman jugador = new Bomberman(0, 0);
        System.out.println("Bomberman creado en posición: (" + jugador.getX() + ", " + jugador.getY() + ")");

        //Agregar interacción con el usuario
        Scanner scanner = new Scanner(System.in);
        boolean jugando = true;
        System.out.println("📌 El juego sigue activo. Esperando próxima acción...");

        while (jugando) {
            System.out.println("📌 El juego sigue activo. Esperando próxima acción...");

            System.out.println("\nAcciones:");
            System.out.println("1. Mover arriba (W)");
            System.out.println("2. Mover abajo (S)");
            System.out.println("3. Mover izquierda (A)");
            System.out.println("4. Mover derecha (D)");
            System.out.println("5. Colocar bomba (B)");
            System.out.println("6. Salir del juego (X)");

            System.out.print("Elige una acción: ");
            char accion = scanner.next().charAt(0);

            switch (Character.toUpperCase(accion)) {
                case 'W': jugador.moverse(-1, 0); break;
                case 'S': jugador.moverse(1, 0); break;
                case 'A': jugador.moverse(0, -1); break;
                case 'D': jugador.moverse(0, 1); break;
                case 'B': jugador.ponerBomba(); break;
                case 'X': jugando = false; break;
                default: System.out.println("Opción inválida.");
            }

            System.out.println("Bomberman está en: (" + jugador.getX() + ", " + jugador.getY() + ")");
            tablero.imprimirTablero(); 
        }

        System.out.println("Juego terminado.");
        scanner.close();
    }
}

