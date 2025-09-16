import javafx.util.Pair;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Itinerary {

    ArrayList<YelpBusiness> biz;
    ArrayList<YelpBusiness> mapping;
    LinkedList<Edge> edges;
    LinkedList<LinkedList<Edge>> graph;
    YelpBusiness best;
    YelpBusiness worst;
    LinkedList<YelpBusiness> path;
    double[] d;


    public Itinerary(ArrayList<YelpBusiness> businesses) {
        biz = businesses;
        mapping = new ArrayList<YelpBusiness>();
        int count = 0;
        for (YelpBusiness yb : businesses) {
            mapping.add(count, yb);
            count++;
        }
        edges = new LinkedList<Edge>();
        best = this.calculateBest();
        System.out.println("Best: " + best.getName() + " Rank: " + best.getRating() + " RC: " + best.getRc());
        worst = this.calculateWorst();
        System.out.println("Worst: " + worst.getName() + " Rank: " + worst.getRating() + " RC" + worst.getRc());

        graph = new LinkedList<LinkedList<Edge>>();
        for (int i = 0; i < mapping.size(); i++) {
            graph.add(i, new LinkedList<Edge>());
        }
    }

    /**
     * Calculates the best rated attraction, breaks tie with most review count
     * @return YelpBusiness
     */
    public YelpBusiness calculateBest() {
        YelpBusiness b = new YelpBusiness("", "", 0, 0, -1, -1);
        for (YelpBusiness yb : biz) {
            if (yb.getRating() > b.getRating()) {
                b = yb;
            } else if (yb.getRating() == b.getRating() && yb.getRc() > b.getRc()) {
                b = yb;
            }
        }
        return b;
    }
    /**
     * Calculates the worst rated attraction, break tie with most review count
     * @return
     */
    public YelpBusiness calculateWorst() {
        YelpBusiness w = new YelpBusiness("", "", 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (YelpBusiness yb : biz) {
            if (yb.getRating() < w.getRating()) {
                w = yb;
            } else if (yb.getRating() == w.getRating() && yb.getRc() < w.getRc()) {
                w = yb;
            }
        }
        return w;
    }

    public YelpBusiness pickBetter(YelpBusiness yb, YelpBusiness b) {
        if (yb.getRating() > b.getRating()) {
            return yb;
        } else if (yb.getRating() < b.getRating()) {
            return b;
        } else {
            if (yb.getRc() > b.getRc()) {
                return yb;
            } else {
                return b;
            }
        }
    }

    public YelpBusiness pickWorst(YelpBusiness yb, YelpBusiness b) {
        if (yb.getRating() < b.getRating()) {
            return yb;
        } else if (yb.getRating() > b.getRating()) {
            return b;
        } else {
            if (yb.getRc() < b.getRc()) {
                return yb;
            } else {
                return b;
            }
        }
    }
    /**
     * Iterates YelpBusinesses and will add an edge from worst rated attraction
     * to the better rated attraction
     */
    public void makeGraph() {
        //make edges
        for (YelpBusiness src : biz) {
            for (YelpBusiness tgt : biz) {
                if (!src.equals(tgt)) {
                    Edge temp = new Edge(pickWorst(src, tgt), pickBetter(src, tgt));
//                    System.out.println("***********");
//                    System.out.println("Op1: " + src.getName() + " Rate: " + src.getRating() + " RC: " + src.getRc());
//                    System.out.println("Op2: " + tgt.getName() + " Rate: " + tgt.getRating() + " RC: " + tgt.getRc());
//                    System.out.println(temp.getSrc().getName() + " to " + temp.getTgt().getName());
//                    System.out.println("***********");
                    edges.add(temp);
                }


            }
        }
        Edge temp = new Edge (worst, best);

        //populate graph
        for (Edge e : edges) {
            if (!e.equals(temp)) {
//                System.out.println(mapping.indexOf(e.getSrc())+ " " + e.getSrc().getName() + " to " + mapping.indexOf(e.getTgt()) + " " + e.getTgt().getName());
                for (int i = 0; i < graph.size(); i++) {
                    YelpBusiness src = e.getSrc();
                    YelpBusiness tgt = e.getTgt();
                    if (i == mapping.indexOf(src)) {
                        graph.get(i).addFirst(e);
                    }
                }
            }

        }

    }
    /**
     * Find the shortest path from the src attraction to the target attraction
     * @param src
     * @param tgt
     */
    public void dijkstra(YelpBusiness src, YelpBusiness tgt) {
        boolean[] visited = new boolean[mapping.size()];
        double[] dist = new double[mapping.size()];
        YelpBusiness[] parent = new YelpBusiness[mapping.size()];

        for (int i = 0; i < dist.length; i++) {
            dist[i] = Double.MAX_VALUE;
        }

        PriorityQueue<Pair<Double, YelpBusiness>> p = new PriorityQueue<>(mapping.size(), new Comparator<Pair<Double, YelpBusiness>>() {
            @Override
            public int compare(Pair<Double, YelpBusiness> x, Pair<Double, YelpBusiness> y) {
                if (y.getKey() > x.getKey()) {
                    return -1;
                } else if (y.getKey() < x.getKey()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        dist[mapping.indexOf(src)] = 0;
        Pair<Double, YelpBusiness> pr = new Pair<>(dist[mapping.indexOf(src)], src);
        p.offer(pr);

        while (!p.isEmpty()) {
            Pair<Double, YelpBusiness> extracted = p.poll();
            YelpBusiness curr = extracted.getValue();
            int index = mapping.indexOf(curr);

            if (visited[index] == false) {
                visited[index] = true;

                LinkedList<Edge> neighbors = graph.get(index);
                for (int i = 0; i < neighbors.size(); i++) {
                    Edge e = neighbors.get(i);
                    YelpBusiness v = e.getTgt();
                    if (visited[mapping.indexOf(v)] == false) {
                        double updateDist = dist[index] + e.getWeight();
                        if (dist[mapping.indexOf(v)] > updateDist) {
                            Pair<Double, YelpBusiness> temp = new Pair<>(updateDist, v);
                            p.offer(temp);
                            dist[mapping.indexOf(v)] = updateDist;
                            parent[mapping.indexOf(v)] = curr;
                        }
                    }
                }
            }
        }
        printPath(dist, parent, src, tgt);
    }
    /**
     * Print shortest path 
     * @param distance
     * @param parent
     * @param src
     * @param tgt
     */
    public void printPath(double[] distance, YelpBusiness[] parent, YelpBusiness src, YelpBusiness tgt) {
        d = distance;
        YelpBusiness[] p = parent;

        System.out.println("Shortest path: ");
        int c = mapping.size();
        YelpBusiness curr = tgt;
        path = new LinkedList<>();
        while (!curr.equals(src)) {
            if (p[mapping.indexOf(curr)] == null) {
                System.out.println("No path from worst location to best");
            }
            path.addFirst(curr);
            curr = p[mapping.indexOf(curr)];
        }
        path.addFirst(src);
        for (int i = 0; i < path.size(); i++) {
            System.out.println(i + ". Vist: " + path.get(i).getName() + " Dist: " + d[mapping.indexOf(path.get(i))]);
        }

    }

    public YelpBusiness getBest() {
        return best;
    }

    public YelpBusiness getWorst() {
        return worst;
    }

    public LinkedList<YelpBusiness> getPath() {
        return path;
    }
}
