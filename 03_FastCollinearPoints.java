import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException("points[] cannot be null");

        double[] slopesToOrigin = new double [points.length];
        Arrays.sort(points);

        for(int i = 1; i < points.length; i++){
            checkExceptions(points[i-1], points[i]);
            slopesToOrigin[i] = points[0].slopeTo(points[i]);
        }

        int sStart = 0; // start of sequence w/ same slope
        int sEnd = 0; // end of sequence w/ same slope
        int counter = 1; // variable used to iterate through the entire slopeToOrigin[]

        //iterate through slopeToOrigin[], find all line segments > 4
        while(counter < slopesToOrigin.length-1){
            if(sEnd > slopesToOrigin.length-3) break;
            counter++;

            // do adjacent elements in slopesToOrigin[] have the same value
            if(slopesToOrigin[counter] == slopesToOrigin[counter-1]){
                sStart = counter-1;
                sEnd = counter;

                /* loop will stop once sEnd reaches end of slope sequence
                sEnd increments while sStart stays at the beginning of the sequence
                */
                while(slopesToOrigin[sEnd] == slopesToOrigin[sEnd-1]){
                    sEnd++;
                    if(sEnd > slopesToOrigin.length-1) break;
                }

                /* if sequence w/ same slope is bigger than or equal to 4
                add to ArrayList lineSegments
                 */
                if( (sEnd-sStart) >= 4 ){
                    lineSegments.add(new LineSegment(points[sStart], points[sEnd-1]));
                }
                counter = sEnd;
            }
        }
    }

    // check for null points and repeat points
    public void checkExceptions(Point i, Point j){
        if(i == null || j == null ){
            throw new IllegalArgumentException("points cannot be null");
        }
        if (i.compareTo(j) == 0) {
            throw new IllegalArgumentException("cannot have duplicate points");
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }
}