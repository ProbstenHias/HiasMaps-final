import static spark.Spark.*;

public class Server {
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Graph graph = new Graph("GraphFiles/germany.fmi");// path to map here
        // Dijkstra dijkstra = new Dijkstra(graph);
        AStar aStar = new AStar(graph);
        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        port(8080);
        staticFiles.location("/public");

        init();
        post("/api/latLon", (req, res) -> {
            try {
                var lat = Double.parseDouble(req.queryParams("lat"));
                var lon = Double.parseDouble(req.queryParams("lon"));
                return graph.getNextNode(lat, lon);
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });
        post("/api/route", (req, res) -> {
            try {
                var start = Integer.parseInt(req.queryParams("start"));
                var dest = Integer.parseInt(req.queryParams("dest"));
                long startTime = System.nanoTime();
                // dijkstra.dijkstra(start, dest);
                aStar.aStar(start, dest);
                long endTime = System.nanoTime();
                long finalTime = endTime - startTime;
                System.out.printf("Time to calc dijkstra in seconds: %.2f %n", finalTime / 1.0e9);
                //var iter = dijkstra.pathTo(dest);
                var iter = aStar.pathTo(dest);
                StringBuilder output = new StringBuilder();
                while (iter.hasNext()) {
                    int current = iter.next();
                    output.append(graph.getLatOf(current)).append(" ").append(graph.getLonOf(current)).append(" ");// add the lat and lon of every node to the string
                }
                output.append(aStar.distanceTo(dest));//add the distance to the destination
                return output.toString();
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });
    }
}
