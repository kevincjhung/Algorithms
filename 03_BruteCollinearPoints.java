import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = null;

    /**
     * finds all line segments containing 4 points
     * exactly once
     * Do not include subsegments
     * @param points
     */
    public BruteCollinearPoints(Point[] points){
        if(points == null)
            throw new IllegalArgumentException("parameter to BruteCollinearPoints() cannot be null");

        for(int i = 0; i < points.length-4; i++){
            for(int j = i+1; j < points.length-3; j++){
                for(int k = j+1; k < points.length-2; k++){
                    for(int l = k + 1; l < points.length-1; l++){
                        checkExceptions(points[i], points[j], points[k], points[l]);

                        if(isCollinear(points[i], points[j], points[k], points[l])){
                            Point[] orderedPoints = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(orderedPoints);
                            lineSegments.add(new LineSegment(orderedPoints[0], orderedPoints[3]));
                        }
                    }
                }
            }
        }
    }

    /**
    returns the number of line segments
     */
    public int numberOfSegments(){
        return lineSegments.size();
    }

    public LineSegment[] segments(){
        return (LineSegment[]) lineSegments.toArray();
    }

    public boolean isCollinear(Point i, Point j, Point k, Point l){
        return (i.slopeTo(j) == i.slopeTo(k) && i.slopeTo(k) == i.slopeTo(l));
    }


    /**
     * first checks for null exceptions
     * then checks for duplicate points
     * @param i
     * @param j
     * @param k
     * @param l
     */
    public void checkExceptions(Point i, Point j, Point k, Point l){
        if(i == null || j == null || k == null || l == null){
            throw new IllegalArgumentException("points cannot be null");
        }
        if (i.compareTo(j) == 0) throw new IllegalArgumentException("cannot have duplicate points");
        if (i.compareTo(k) == 0) throw new IllegalArgumentException("cannot have duplicate points");
        if (i.compareTo(l) == 0) throw new IllegalArgumentException("cannot have duplicate points");
        if (j.compareTo(k) == 0) throw new IllegalArgumentException("cannot have duplicate points");
        if (j.compareTo(l) == 0) throw new IllegalArgumentException("cannot have duplicate points");
        if (k.compareTo(l) == 0) throw new IllegalArgumentException("cannot have duplicate points");
    }
}
