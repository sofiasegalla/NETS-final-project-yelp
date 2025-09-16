public class Edge implements Comparable<Edge>{
    YelpBusiness u;
    YelpBusiness v;
    double weight;

    Edge(YelpBusiness src, YelpBusiness tgt) {
        u = src;
        v = tgt;
        
        // calculate the miles between src and tgt 
        double uLat = Math.toRadians(u.getLatitude());
        double uLon = Math.toRadians(u.getLongitude());
        double vLat = Math.toRadians(v.getLatitude());
        double vLon = Math.toRadians(v.getLongitude());

        double lon = vLon - uLon;
        double lat = vLat - uLat;

        double x = Math.pow(Math.sin(lat / 2), 2) + Math.cos(uLat) * Math.cos(vLat)
                * Math.pow(Math.sin(lon / 2),2);
        double y = 2 * Math.asin(Math.sqrt(x));
        weight = y * 3956.0;
    }

    @Override
    public int compareTo(Edge e) {
        if (e.getWeight() > this.getWeight()) {
            return -1;
        } else if (e.getWeight() < this.getWeight()) {
            return 1;
        } else {
            return 0;
        }
    }


    public boolean equals(Edge e) {
        return this.getSrc() == e.getSrc() && this.getTgt() == e.getTgt()
                && this.getWeight() == e.getWeight();
    }

    double getWeight() {
        return weight;
    }

    public YelpBusiness getSrc() {
        return u;
    }

    public YelpBusiness getTgt() {
        return v;
    }

    public String toString() {
        return u.getName() + " connects to " + v.getName() + " in " + weight;
    }
}
