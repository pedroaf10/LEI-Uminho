import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    private final static Map<Character, Character> DIACRITICS_MAPPER = Map.of(
            'á', 'a',
            'ã', 'a',
            'ç', 'c',
            'é', 'e',
            'ê', 'e',
            'í', 'i',
            'ó', 'o'
    );

    // Parque Polivalente da Misericórdia
    private static final Coordinate FINAL_CORD = new Coordinate(-9.148458329988467, 38.71206572932743);

    // junta de freguesia
    private static final Coordinate GARAGE_CORD = new Coordinate(-9.149021499357278, 38.71137127199705);

    private static final double AVERAGE_RADIUS_OF_EARTH_M = 6371000;

    private static final Comparator<Node> NODE_COMPARATOR = (n1, n2) -> {
        int d1 = calculateDistance(n1.coord, GARAGE_CORD);
        int d2 = calculateDistance(n2.coord, GARAGE_CORD);
        return Integer.compare(d1, d2);
    };

    private static final int GARAGE_ID = 648463;
    private static final int DEPOSIT_ID = 999999;


    private static class Node {
        private int objectId;
        private Set<Integer> ids = new HashSet<>();
        private Coordinate coord;
        private String street;
        private Set<String> adjacentStreets;


        private int totalRubbish;
        private int totalOrganic;
        private int totalPlastic;
        private int totalPaper;
        private int totalGlass;

        private Node cloneMetadataOnly() {
            Node node = new Node();
            node.objectId = objectId;
            node.ids.add(objectId);
            node.coord = coord;
            node.street = street;
            node.adjacentStreets = adjacentStreets;
            return node;
        }

        private static Node add(Node n1, Node n2) {
            n1.ids.add(n1.objectId);
            n1.ids.add(n2.objectId);
            n1.totalRubbish += n2.totalRubbish;
            n1.totalOrganic += n2.totalOrganic;
            n1.totalPlastic += n2.totalPlastic;
            n1.totalPaper += n2.totalPaper;
            n1.totalGlass += n2.totalGlass;

            return n1;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "ids=" + ids.toString() +
                    ", objectId=" + objectId +
                    ", coord=" + coord +
                    ", street='" + street + '\'' +
                    ", adjacentStreets=" + adjacentStreets +
                    ", totalRubbish=" + totalRubbish +
                    ", totalOrganic=" + totalOrganic +
                    ", totalPlastic=" + totalPlastic +
                    ", totalPaper=" + totalPaper +
                    ", totalGlass=" + totalGlass +
                    '}';
        }
    }

    private static class Edge {
        private final Node n1;
        private final Node n2;
        private final int distance;

        private Edge(Node n1, Node n2) {
            this.n1 = n1;
            this.n2 = n2;
            distance = calculateDistance(n1, n2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            return (n1.equals(edge.n1) && n2.equals(edge.n2)) || (n2.equals(edge.n1) && n1.equals(edge.n2));
        }

        @Override
        public int hashCode() {
            return n1.hashCode() + n2.hashCode();
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "n1=" + n1.objectId +
                    ", n2=" + n2.objectId +
                    ", distance=" + distance +
                    '}';
        }
    }

    private static class Coordinate {
        private final double lat;
        private final double lon;

        private Coordinate(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate coord = (Coordinate) o;

            if (Double.compare(coord.lat, lat) != 0) return false;
            return Double.compare(coord.lon, lon) == 0;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(lat);
            result = (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(lon);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }


    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Please provide the path to the csv!");
            System.err.println("Usage: \n\tjava Parser.java path_to_csv");

            System.out.println("\nTo save the output to a file:");
            System.out.println("\tjava Parser.java path_to_csv > output_file.pl");
            return;
        }

        final Path path = Paths.get(args[0]);
        final Stream<String> lines = Files.lines(path);

        final Set<Node> nodesRaw = lines.skip(1).map(Parser::parseLine).collect(Collectors.toSet());
        final Set<Node> nodes = mergeNodes(nodesRaw).stream().filter(x -> !x.adjacentStreets.isEmpty()).collect(Collectors.toSet());

        final Set<Edge> edges = getEdges(nodes);

        final Node garage = new Node();
        garage.coord = GARAGE_CORD;
        garage.objectId = GARAGE_ID;

        final Node deposit = new Node();
        deposit.coord = FINAL_CORD;
        deposit.objectId = DEPOSIT_ID;

        // Edges from the garage/deposit to each node
        final Set<Edge> additionalEdges = getEdges(garage, deposit, nodes);

        System.out.println("%---------------------- STATS ----------------------");
        System.out.println("%\tNumber of nodes: " + nodes.size());
        System.out.println("%\tNumber of edges: " + edges.size());
        System.out.println("%\tNumber of edges (garbage deposit + garage): " + (edges.size() + additionalEdges.size()));
        System.out.println("%---------------------------------------------------\n\n");

//        System.out.println("%---------------- Garagem e Lixeira ----------------");
//        System.out.println("garagem(s).");
//        System.out.println("lixeira(t).\n");

        System.out.println("%--------------------- caixote ---------------------");
        printBins(nodes);

        System.out.println("%---------------------- aresta ---------------------");
        printEdges(edges);

        System.out.println("%---------------- arestas adicionais ----------------");
        printEdges(additionalEdges);

        System.out.println("%-------------------- estimativa -------------------");
        printEstimations(nodes);
        printEstimations(List.of(garage, deposit));
        System.out.println("%----------------- garagemDistancia ----------------");
        printGarageDistance(nodes);
        System.out.println("%--------------- garagemLixoDistancia --------------");
        printGarageDeposit();

    }

    private static Set<Edge> getEdges(Node garage, Node deposit, Set<Node> nodes) {
        final Set<Edge> edges = new HashSet<>();

        for (Node node : nodes) {
            edges.add(new Edge(garage, node));
            edges.add(new Edge(deposit, node));
        }

        return edges;
    }


    private static Set<Node> mergeNodes(Set<Node> nodes) {
        final Map<Coordinate, List<Node>> nodesByCoord = nodes.stream().collect(Collectors.toMap(n -> n.coord, n -> new ArrayList<>(List.of(n)), (x, y) -> {
            x.addAll(y);
            return x;
        }));

        final Set<Node> bigNodes = new HashSet<>();
        nodesByCoord.values().forEach(ns -> {
            final Node identity = ns.get(0).cloneMetadataOnly();
            bigNodes.add(ns.stream().reduce(identity, Node::add));
        });

        final Map<String, List<Node>> nodesByAddress = bigNodes.stream().collect(Collectors.toMap(n -> n.street, n -> new ArrayList<>(List.of(n)), (x, y) -> {
            x.addAll(y);
            return x;
        }));

        final Set<Node> bigNodes2 = new HashSet<>();
        nodesByAddress.values().forEach(ns -> {
            final Node identity = ns.get(0).cloneMetadataOnly();
            bigNodes2.add(ns.stream().reduce(identity, Node::add));
        });


        return bigNodes2;
    }


    private static Set<Edge> getEdges(Set<Node> nodes) {
        final Map<String, Set<Node>> streetNodes = new HashMap<>();

        for (Node node : nodes) {
            final Set<Node> set = streetNodes.getOrDefault(node.street, new HashSet<>());
            set.add(node);
            streetNodes.put(node.street, set);
        }

        final Set<Node> pivotNodesSet = new HashSet<>(); // pivot nodes will be connected between them
        final Set<Edge> edges = new HashSet<>();

        for (Set<Node> value : streetNodes.values()) {
            final List<Node> sameStreetNodes = new ArrayList<>(value);
            sameStreetNodes.sort(NODE_COMPARATOR); // sort bins
/*
            if (value.size() == 1) {
                System.out.println("->" + sameStreetNodes.get(0));

            }*/

            pivotNodesSet.add(sameStreetNodes.get(0));
            pivotNodesSet.add(sameStreetNodes.get(sameStreetNodes.size() - 1));

            // Add within street edges
            for (int i = 1; i < sameStreetNodes.size(); i++) {
                edges.add(new Edge(sameStreetNodes.get(i - 1), sameStreetNodes.get(i)));
            }
        }

        final List<Node> pivotNodes = new ArrayList<>(pivotNodesSet);

        // Add pivots edges
        for (int i = 0; i < pivotNodes.size(); i++) {
            for (int j = i + 1; j < pivotNodes.size(); j++) {
                final Node p1 = pivotNodes.get(i);
                final Node p2 = pivotNodes.get(j);

                if (!p1.street.equals(p2.street)) {
                    if (p1.adjacentStreets.contains(p2.street) || p2.adjacentStreets.contains(p1.street)) {
                        edges.add(new Edge(p1, p2));
                    }
                }
            }
        }

        return edges;
    }

    private static Node parseLine(String line) {
        final String[] columns = cleanUp(line).split(";");

        final Node node = new Node();
        node.coord = new Coordinate(Double.parseDouble(columns[0]), Double.parseDouble(columns[1]));
        node.objectId = Integer.parseInt(columns[2]);


        final String residueType = columns[5].trim();
        final int totalLiters = Integer.parseInt(columns[9]);

        fillNodeGarbage(node, residueType, totalLiters);

        final String streetInfo = columns[4];
        final String[] streetParts = streetInfo.split("\\(", 2);

        node.street = streetParts[0].split(":")[1].trim().split(",")[0].trim(); // discard the code

        if (streetParts.length > 1) {
            node.adjacentStreets = Arrays.stream(streetParts[1].split(":")[1].split("-")).map(String::trim).map(str -> str.replace(")", "")).collect(Collectors.toSet());
        } else {
            node.adjacentStreets = Collections.emptySet();
        }


        return node;

    }

    private static void fillNodeGarbage(Node node, String residueType, int totalLiters) {
        switch (residueType) {
            case "Lixos" -> node.totalRubbish = totalLiters;
            case "Embalagens" -> node.totalPlastic = totalLiters;
            case "Papel e Cartao" -> node.totalPaper = totalLiters;
            case "Vidro" -> node.totalGlass = totalLiters;
            case "Organicos" -> node.totalOrganic = totalLiters;
            default -> System.out.println(residueType);
        }
    }

    private static String cleanUp(String string) {
        final char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (DIACRITICS_MAPPER.containsKey(chars[i])) {
                chars[i] = DIACRITICS_MAPPER.get(chars[i]);
            }
        }

        return new String(chars);
    }

    private static int calculateDistance(Node n1, Node n2) {
        return calculateDistance(n1.coord, n2.coord);
    }

    private static int calculateDistance(Coordinate c1, Coordinate c2) {
        return calculateDistance(c1.lat, c1.lon, c2.lat, c2.lon);
    }

    private static int calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(lon1 - lon2);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) Math.round(AVERAGE_RADIUS_OF_EARTH_M * c);
    }

    private static void printBins(Collection<Node> nodes) {
        System.out.println("% caixote(id, lat, lon, rua, totalLixos, totalOrganico, totalPapel, totalEmbalagens, totalVidro");
        System.out.printf("caixote(s, %f, %f, 'rua', 0, 0, 0, 0, 0).%n", GARAGE_CORD.lat, GARAGE_CORD.lon);
        System.out.printf("caixote(t, %f, %f, 'rua', 0, 0, 0, 0, 0).%n", FINAL_CORD.lat, FINAL_CORD.lon);

        for (Node node : nodes) {
            if (!node.adjacentStreets.isEmpty()) {
                System.out.printf("caixote(%s, %f, %f, '%s', %d, %d, %d, %d, %d).%n",
                        getNodeKey(node), node.coord.lat, node.coord.lon, node.street, node.totalRubbish, node.totalOrganic, node.totalPaper, node.totalPlastic, node.totalGlass);
            }
        }
        System.out.println();
    }

    private static void printEdges(Collection<Edge> edges) {
        System.out.println("% aresta(id1, id2, distancia)");
        for (Edge edge : edges) {
            System.out.printf("aresta(%s, %s, %d).%n",
                    getNodeKey(edge.n1), getNodeKey(edge.n2), edge.distance);
        }
        System.out.println();
    }


    private static void printEstimations(Collection<Node> nodes) {
        System.out.println("% estimativa(id, distancia)");

        for (Node node : nodes) {
            System.out.printf("estimativa(%s, %d).%n",
                    getNodeKey(node), calculateDistance(FINAL_CORD, node.coord));
        }
        System.out.println();
    }

    private static void printGarageDistance(Collection<Node> nodes) {
        System.out.println("% garagemDistancia(id, distancia)");

        for (Node node : nodes) {
            System.out.printf("garagemDistancia(%s, %d).%n",
                    getNodeKey(node), calculateDistance(GARAGE_CORD, node.coord));
        }
        System.out.println();
    }

    private static void printGarageDeposit() {
        System.out.printf("garagemLixoDistancia(%d).%n",
                calculateDistance(GARAGE_CORD, FINAL_CORD));
        System.out.println();
    }

    private static String getNodeKey(Node node) {
        return switch (node.objectId) {
            case GARAGE_ID -> "s";
            case DEPOSIT_ID -> "t";
            default -> String.valueOf(node.objectId);
        };
    }
}