import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        // Ensure at least 3 point
        int numOfPoints;
        do {
            System.out.print("Enter the number of vertices (minimum 3): ");
            numOfPoints = scanner.nextInt();

            if (numOfPoints < 3) {
                System.out.println("Error: The number of vertices must be at least 3.");
            }

        } while (numOfPoints < 3);

        double[][] points = new double[numOfPoints][3];

        for (int i = 0; i < numOfPoints; i++) {
            System.out.println("Enter coordinates for vertex " + (i + 1) + ":");
            System.out.print("X: ");
            points[i][0] = scanner.nextDouble();
            System.out.print("Y: ");
            points[i][1] = scanner.nextDouble();

            //It's on the plain so Z is always 0
            points[i][2] = 0.0;
        }


//        double[][] points = {
//                {50, 50, 0},
//                {100, -50, 0},
//                {50, -100, 0},
//
//        };

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




        // Find the extreme points
        List<Integer> extremePoints = findExtreme(points, azimuthDegrees);
        System.out.println("Extreme Points:");
        System.out.println(extremePoints.get(1));

        for (int index : extremePoints) {
            System.out.println("Point " + index +"(" + points[index][0] + ", " + points[index][1] + ", " + points[index][2] + ")");
        }



        // Calculate distances from non-extreme points to the line formed by the extreme points
        Map<Integer, Double> distancesMap = calculateDistancesToExtremePoints(points, extremePoints);

        // Print distances
        System.out.println("Distances to Extreme Points/Line:");

        for (Map.Entry<Integer, Double> entry : distancesMap.entrySet()) {
            System.out.println("Point " + (entry.getKey()+1) + ": " + entry.getValue());
        }

        // Calculate and print modified polygon on 3D plane
        System.out.println("Output points on 3D plane");
        calculateAndPrintHeights(points, slopeDegrees, distancesMap, extremePoints);
    }


    //Todo for any Azimuth input
    //A lot of almost repetitive code!
    static List<Integer> findExtreme(double[][] points, double azimuthDegrees) {
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


    //Calculate distance from points to extreme point(one)
    static Map<Integer, Double> calculateDistancesToExtremePoints(double[][] points, List<Integer> extremePoints) {
        //List<Double> distances = new ArrayList<>();
        Map<Integer, Double> distances = new HashMap<>();
        if (extremePoints.size() == 1) {
            int extremeIndex = extremePoints.get(0);
            double x1 = points[extremeIndex][0];
            double y1 = points[extremeIndex][1];

            for (int i = 0; i < points.length; i++) {
                if (!extremePoints.contains(i)) {
                    double dist = distanceToPoint(points[i][0], points[i][1], x1, y1);
                    distances.put(i, dist);
                }
            }
        } else if (extremePoints.size() >= 2) {
            int extremeIndex1 = extremePoints.get(0);
            int extremeIndex2 = extremePoints.get(1);
            double x1 = points[extremeIndex1][0];
            double y1 = points[extremeIndex1][1];
            double x2 = points[extremeIndex2][0];
            double y2 = points[extremeIndex2][1];

            for (int i = 0; i < points.length; i++) {
                if (!extremePoints.contains(i)) {
                    double dist = distanceToLine(points[i][0], points[i][1], x1, y1, x2, y2);
                    distances.put(i, dist);
                }
            }
        }

        return distances;

    }

    //Distance between two points
    static double distanceToPoint(double x1, double y1, double x2, double y2) {

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    //Distance between points and the line
    static double distanceToLine(double x, double y, double x1, double y1, double x2, double y2) {

        return Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1)/Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    protected static void calculateAndPrintHeights(double[][] points, double slopeDegrees, Map<Integer, Double> distancesMap, List<Integer> extremePoints) {
        //System.out.println("Points on 3d plane");

        for (int i = 0; i < points.length; i++) {
            // Check if the current point is not an extreme point
            if (!extremePoints.contains(i)) {
                double closestDistance = distancesMap.get(i);  // Get the distance from the map
                double hypo = closestDistance / Math.cos(Math.toRadians(slopeDegrees));

                //Calculate by how much points should be levitated
                double z = Math.sqrt(Math.pow(hypo, 2) - Math.pow(closestDistance, 2));
                //Round for nicer numbers
                z = Math.round(z * 1000000000.0) / 1000000000.0;

                points[i][2] = points[i][2] + z;
                System.out.println("Point " + (i + 1) + ": (" + points[i][0] + ", " + points[i][1] + ", " + points[i][2] + ")");
            }//if Extreme don't change
            else
                System.out.println("Point " + (i + 1) + ": (" + points[i][0] + ", " + points[i][1] + ", " + points[i][2] + ")");
        }
    }



}