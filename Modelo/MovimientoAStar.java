package Modelo;

import java.util.*;

public class MovimientoAStar implements EstrategiaMovimiento {

    private Enemigo enemigo;
    private Bomberman bomberman;
    private Tablero tablero;

    public MovimientoAStar(Enemigo enemigo) {
        this.enemigo = enemigo;
        this.tablero = Tablero.getTablero();
        this.bomberman = tablero.getBomberman();
    }

    @Override
    public int[] calcularDireccion() {
        int startX = enemigo.getX();
        int startY = enemigo.getY();
        int goalX = bomberman.getX();
        int goalY = bomberman.getY();

        PriorityQueue<Nodo> abierta = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<String, Nodo> visitados = new HashMap<>();

        Nodo inicio = new Nodo(startX, startY, 0, heuristica(startX, startY, goalX, goalY), null);
        abierta.add(inicio);

        while (!abierta.isEmpty()) {
            Nodo actual = abierta.poll();
            String key = actual.x + "," + actual.y;

            if (visitados.containsKey(key)) continue;
            visitados.put(key, actual);

            if (actual.x == goalX && actual.y == goalY) {
                while (actual.padre != null && actual.padre.padre != null) {
                    actual = actual.padre;
                }
                return new int[]{actual.x - startX, actual.y - startY};
            }

            for (int[] dir : new int[][]{{1,0},{-1,0},{0,1},{0,-1}}) {
                int nx = actual.x + dir[0];
                int ny = actual.y + dir[1];

                if (!tablero.esValida(nx, ny)) continue;
                Casilla c = tablero.getCasilla(nx, ny);

                if (c.tieneBloqueDuro() || c.tieneBloqueBlando() || c.tieneBomba()) {}

                Nodo vecino = new Nodo(nx, ny, actual.g + 1, heuristica(nx, ny, goalX, goalY), actual);
                abierta.add(vecino);
            }
        }

        return new int[]{0, 0};
    }

    private int heuristica(int x1, int y1, int x2, int y2) {
    	int x = x1 - x2;
        int y = y1 - y2;
        
        if(x<0){x=x*-1;}
        if(y<0){y=y*-1;}

        
         return x+y;
    }

    private static class Nodo {
        int x, y, g, f;
        Nodo padre;
        Nodo(int x, int y, int g, int h, Nodo padre) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.f = g + h;
            this.padre = padre;
        }
    }
}
