package ru.geekbrains.ntr_ads07;

import static ru.geekbrains.ntr_ads07.City.*;

public class Test7 {

    public static void main(String[] args) {

        IGraph graph = new Graph(11);
        graph.addVertex(MOSCOW);
        graph.addVertex(TULA);
        graph.addVertex(LIPETSK);
        graph.addVertex(VORONEZH);
        graph.addVertex(RYAZAN);
        graph.addVertex(TAMBOV);
        graph.addVertex(SARATOV);
        graph.addVertex(KALUGA);
        graph.addVertex(OREL);
        graph.addVertex(KURSK);


        graph.addEdges(MOSCOW, TULA, RYAZAN, KALUGA);
        graph.addEdges(TULA, LIPETSK);
        graph.addEdges(TAMBOV, RYAZAN, SARATOV);
        graph.addEdges(OREL, KALUGA, KURSK);
        graph.addEdges(VORONEZH, LIPETSK, SARATOV, KURSK);

        graph.shortestRoute(MOSCOW, VORONEZH);
        /*
        Vertex{label='Москва'}
        Vertex{label='Тула'}
        Vertex{label='Липецк'}
        Vertex{label='Воронеж'}

         */
        graph.shortestRoute(MOSCOW, MOSCOW);
        //Start label is equals to end label.

        graph.shortestRoute(MOSCOW, OREL);
        /*
        Vertex{label='Москва'}
        Vertex{label='Калуга'}
        Vertex{label='Орел'}
         */
        graph.shortestRoute(MOSCOW, SARATOV);
        /*
        Vertex{label='Москва'}
        Vertex{label='Рязань'}
        Vertex{label='Тамбов'}
        Vertex{label='Саратов'}
         */

        graph.addVertex(PALERMO);
        graph.shortestRoute(MOSCOW, PALERMO);
        // The path between Vertex{label='Москва'} and Vertex{label='Палермо'} was not found.

        graph.addEdges(PALERMO, MOSCOW, VORONEZH);
        graph.shortestRoute(MOSCOW, VORONEZH);
        /*
        Vertex{label='Москва'}
        Vertex{label='Палермо'}
        Vertex{label='Воронеж'}
         */

        graph.shortestRoute(SARATOV, PALERMO);
        /*
        Vertex{label='Саратов'}
        Vertex{label='Воронеж'}
        Vertex{label='Палермо'}
         */
    }
}
