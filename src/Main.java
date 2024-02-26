import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input for Rectangle
        //Todo for any sized polygon
//        double[][] points = new double[4][3];
//        for (int i = 0; i < 4; i++) {
//            System.out.print("Enter coordinates for point " + (i + 1) + " (x y z): ");
//            for (int j = 0; j < 3; j++) {
//                points[i][j] = scanner.nextDouble();
//            }
//        }
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
        double hypo = 100 / Math.cos(Math.toRadians(slopeDegrees));

        //Calculate by how much points should be levitated
        double h = Math.sqrt(Math.pow(hypo, 2) - Math.pow(100, 2));
        //Precision match
        h = Math.round(h * 1000000000.0) / 1000000000.0;

        System.out.println("Hypo: " + hypo );
        System.out.println("z is: " + h);

        //Todo find extreme points determined by Azimuth (can't be more than two or less than one)
    }
}