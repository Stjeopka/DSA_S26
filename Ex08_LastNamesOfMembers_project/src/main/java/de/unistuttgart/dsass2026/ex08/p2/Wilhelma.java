package de.unistuttgart.dsass2026.ex08.p2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class Wilhelma {

    /**
     * This class contains the main method, which starts the program.
     * I.e. it reads the whilhelma.osm into an JSONObject from which it generates the WeightedGraph.
     *
     * Then it calculates the MST of this graph.
     *
     * Finally the MST is exported into geojson and printed to the console.
     * You can view this geojson file e.g. on "geojson.io".

     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        WeightedGraph graph = new WeightedGraph();

        String filepath = "src/main/resources/wilhelma.osm";
        String jsonTxt = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(jsonTxt);

        // iterates over all "node"-types and adds the ID, lat and long to the graph
        for(Object s: json.getJSONArray("elements")) {
            JSONObject entry = (JSONObject) s;
            if(((String) entry.get("type")).compareTo("node") == 0) {
                long id = Long.valueOf(entry.get("id").toString());
                double lat = (double) entry.get("lat");
                double lon = (double) entry.get("lon");
                graph.addNode(id, new Node(id,lat,lon));
            }

        }

        // iterates over all "way"-types and adds them to the graph
        for(Object s: json.getJSONArray("elements")) {
            JSONObject entry = (JSONObject) s;

            if (((String) entry.get("type")).compareTo("way") == 0) {
                JSONArray wayPoints = entry.getJSONArray("nodes");
                for(int i = 0; i < wayPoints.length()-1; i++) {
                    long src = Long.valueOf(wayPoints.get(i).toString());
                    long dst = Long.valueOf(wayPoints.get(i+1).toString());

                    Node srcMeta = graph.getNode(src);
                    Node dstMeta = graph.getNode(dst);
                    // approximate the weight of the edge by the pythagoran theorem. For a better approx. have a look at geodesics
                    double weight = Math.sqrt(Math.pow(srcMeta.getLatitude()-dstMeta.getLatitude(),2)
                            +Math.pow(srcMeta.getLongitude()-dstMeta.getLongitude(),2));

                    // OSM-data is undirected
                    graph.addEdge(new Edge(src, dst, weight));
                    graph.addEdge(new Edge(dst, src, weight));
                }
            }
        }

        Set<IEdge> mst = MinimalSpanningTree.kruskal(graph);

        // generating the geojson. i.e. iterating over the path and getting the coordinates of the nodes on it
        String path = "";

        Iterator<IEdge> edgeIter = mst.iterator();

        while(edgeIter.hasNext()) {
            IEdge e = edgeIter.next();
            if (edgeIter.hasNext()) {
                path += "[["+graph.getNode(e.getSource()).getLongitude()+","+graph.getNode(e.getSource()).getLatitude()+"],\n"
                    +"["+graph.getNode(e.getDestination()).getLongitude()+","+graph.getNode(e.getDestination()).getLatitude()+"]],\n";
            } else {
                path += "[["+graph.getNode(e.getSource()).getLongitude()+","+graph.getNode(e.getSource()).getLatitude()+"],\n"
                        +"["+graph.getNode(e.getDestination()).getLongitude()+","+graph.getNode(e.getDestination()).getLatitude()+"]]\n";
            }
        }

        /*Iterator<IEdge> iter = sp.pathTo(destination).iterator();
        while(iter.hasNext()){
            IEdge edge = iter.next();
            long src = edge.getSource();
            path += "["+ graph.getNode(src).getLongitude()+","+graph.getNode(src).getLatitude()+"],\n";

            if(!iter.hasNext()) {
                long dst = edge.getDestination();
                path += "    ["+ graph.getNode(dst).getLongitude()+","+graph.getNode(dst).getLatitude()+"]\n";
            }
        }*/

        path = "{\n"
              + " \"type\": \"FeatureCollection\",\n"
              + "  \"features\": [\n"
              + "  {\"type\": \"Feature\",\n"
              + "   \"geometry\": {\n"
              + "    \"type\": \"MultiLineString\",\n"
              + "     \"coordinates\": [\n"
              + path
              + "    ]},\n"
              + "    \"properties\": {\n"
              + "    }\n  "
              + "   }"
              + "  ]\n"
            + "}";

        System.out.println(path);

    }
}