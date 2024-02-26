import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        //Todo for any sized polygon

        // Input for Rectangle
        double[][] points = {
                {0, 0, 0},
                {100, 0, 0},
                {100, 50, 0},
                {0, 50, 0},

        };

        // Input for Slope and Azimuth
        System.out.print("Enter the slope in degrees: ");
        double slopeDegrees = scanner.nextDouble();
        System.out.print("Enter the azimuth in degrees: ");
        double azimuthDegrees = scanner.nextDouble();

        // Output the input values
        System.out.println("\nInput Values:");
        for (int i = 0; i < points.length; i++) {
            System.out.println("Point " + (i + 1) + ": (" + points[i][0] + ", " + points[i][1] + ", " + points[i][2] + ")");
        }
        System.out.println("Slope: " + slopeDegrees + " degrees");
        System.out.println("Azimuth: " + azimuthDegrees + " degrees");

        //Todo 100 must be changed to extreme point distance to points we are calculating
        // If there are more than 1 extreme point it should be shortest distance to line between extreme points?
        double hypo = 100 / Math.cos(Math.toRadians(slopeDegrees));

        //Calculate by how much points should be levitated
        double h = Math.sqrt(Math.pow(hypo, 2) - Math.pow(100, 2));
        //Precision match
        h = Math.round(h * 1000000000.0) / 1000000000.0;

        System.out.println("Hypo: " + hypo );
        System.out.println("z is: " + h);

        //Todo find extreme points determined by Azimuth (can't be more than two or less than one).
        // Given a better thought maybe there can be more than 2 points

        // Find the westernmost points
        List<Integer> extremePoints = findWest(points);
        System.out.println("Westernmost Points:");
        System.out.println(extremePoints.size());

        for (int index : extremePoints) {
            System.out.println(index);
            System.out.println("(" + points[index][0] + ", " + points[index][1] + ", " + points[index][2] + ")");
        }
    }

    //Todo find North, East, and South as well given that user inputs (0, 90, 180)
    //Todo for any Azimuth input
    private static List<Integer> findWest(double[][] points) {
        List<Integer> westIndices = new ArrayList<>();
        double minCoordinateX = points[0][0];
        westIndices.add(0);
        for (int i = 1; i < points.length; i++) {
            double currentX = points[i][0];
            if (currentX < minCoordinateX) {
                minCoordinateX = currentX;
                westIndices.clear();
                westIndices.add(i);
            } else if (currentX == minCoordinateX) {
                westIndices.add(i);
            }
        }

        return westIndices;
    }

}