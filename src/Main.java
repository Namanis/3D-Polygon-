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
                {100, 25, 0},
                {75, 50, 0},
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
        List<Integer> extremePoints = findExtreme(points, azimuthDegrees);
        System.out.println("Extreme Points:");
        System.out.println(extremePoints.size());

        for (int index : extremePoints) {
            System.out.println(index);
            System.out.println("(" + points[index][0] + ", " + points[index][1] + ", " + points[index][2] + ")");
        }
    }


    //Todo for any Azimuth input
    //A lot of almost repetitive code!
    private static List<Integer> findExtreme(double[][] points, double azimuthDegrees) {
        List<Integer> extremePoints = new ArrayList<>();
        double minCoordinateX = points[0][0];
        double minCoordinateY = points[0][1];
        double maxCoordinateX = points[0][0];
        double maxCoordinateY = points[0][1];
        extremePoints.add(0);

        //Y must be highest
        if(azimuthDegrees == 0)
        {
            for (int i = 1; i < points.length; i++) {
                double currentY = points[i][1];
                if (currentY > maxCoordinateY) {
                    maxCoordinateY = currentY;
                    extremePoints.clear();
                    extremePoints.add(i);
                } else if (currentY == maxCoordinateY) {
                    extremePoints.add(i);
                }
            }
        }
        // X must be highest
        else if(azimuthDegrees == 90)
        {
            for (int i = 1; i < points.length; i++) {
                double currentX = points[i][0];
                if (currentX > maxCoordinateX) {
                    maxCoordinateX = currentX;
                    extremePoints.clear();
                    extremePoints.add(i);
                } else if (currentX == maxCoordinateX) {
                    extremePoints.add(i);
                }
            }
        }
        //Y must be lowest
        else if(azimuthDegrees == 180)
        {
            for (int i = 1; i < points.length; i++) {
                double currentY = points[i][1];
                if (currentY < minCoordinateY) {
                    minCoordinateY = currentY;
                    extremePoints.clear();
                    extremePoints.add(i);
                } else if (currentY == minCoordinateY) {
                    extremePoints.add(i);
                }
            }
        }
        //X must be lowest
        else if(azimuthDegrees == 270)
        {
            for (int i = 1; i < points.length; i++) {
                double currentX = points[i][0];
                if (currentX < minCoordinateX) {
                    minCoordinateX = currentX;
                    extremePoints.clear();
                    extremePoints.add(i);
                } else if (currentX == minCoordinateX) {
                    extremePoints.add(i);
                }
            }
        }
        else System.out.println("Not valid (for now)");

        return extremePoints;
    }

}