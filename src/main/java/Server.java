import spark.QueryParamsMap;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        Graph graph = new Graph("GraphFiles/germany.fmi");// path to map here
        Dijkstra dijkstra = new Dijkstra(graph);
        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        port(8080);
        staticFiles.location("/public");

        init();
        post("/api/latLon", (req, res) -> {
            QueryParamsMap map = req.queryMap();
            try {
                var lat = Double.parseDouble(req.queryParams("lat"));
                var lon = Double.parseDouble(req.queryParams("lon"));
                var tmp = graph.getNextNode(lat, lon);
                return tmp;
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });

        post("/api/route", (req, res) -> {
            try {
                var start = Integer.parseInt(req.queryParams("start"));
                var dest = Integer.parseInt(req.queryParams("dest"));
                dijkstra.dijkstra(start, dest);
                var dist = dijkstra.distanceTo(dest);
                var iter = dijkstra.pathTo(dest);
                StringBuilder output = new StringBuilder();
                while (iter.hasNext()) {
                    int current = iter.next();
                    output.append(graph.getLatOf(current)).append(" ").append(graph.getLonOf(current)).append(" ");
                }
                output.append(dist);
                return output.toString();
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });
    }
}