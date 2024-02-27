import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MainTest {

    private final double[][] points;
    private final double azimuth;
    private final double slopeDegrees;
    private final List<Integer> expectedExtremePoints;
    private final int expectedMapSize;
    private final List<Double> expectedDistances;
    private String expectedOutput;

    public MainTest(double[][] points, double azimuth, double slopeDegrees, List<Integer> expectedExtremePoints, int expectedMapSize, List<Double> expectedDistances, String expectedOutput) {
        this.points = points;
        this.azimuth = azimuth;
        this.slopeDegrees = slopeDegrees;
        this.expectedExtremePoints = expectedExtremePoints;
        this.expectedMapSize = expectedMapSize;
        this.expectedDistances = expectedDistances;
        this.expectedOutput = expectedOutput;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new double[][] {{0, 0, 0}, {100, 0, 0}, {100, 50, 0}, {0, 50, 0}}, 90.0, 60, Arrays.asList(1, 2), 2, Arrays.asList(100.0, 100.0),
                        "Point 1: (0.0, 0.0, 173.205080757)\n" +
                                "Point 2: (100.0, 0.0, 0.0)\n" +
                                "Point 3: (100.0, 50.0, 0.0)\n" +
                                "Point 4: (0.0, 50.0, 173.205080757)"},
                {new double[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, 0.0, 45, Arrays.asList(0, 1, 2, 3), 0, Arrays.asList(0),
                        "Point 1: (0.0, 0.0, 0.0)\n" +
                                "Point 2: (0.0, 0.0, 0.0)\n" +
                                "Point 3: (0.0, 0.0, 0.0)\n" +
                                "Point 4: (0.0, 0.0, 0.0)"},

                {new double[][] {{50, 50, 0}, {100, -50, 0}, {50, -100, 0}}, 270, 45, Arrays.asList(0, 2), 1, Arrays.asList(50.0),
                        "Point 1: (50.0, 50.0, 0.0)\n" +
                                "Point 2: (100.0, -50.0, 50.0)\n" +
                                "Point 3: (50.0, -100.0, 0.0)"},
        });
    }

    @Test
    public void testFindExtreme() {
        List<Integer> extremePoints = Main.findExtreme(points, azimuth);
        assertEquals(expectedExtremePoints, extremePoints);
    }

    @Test
    public void testCalculateDistancesToExtremePoints() {
        Map<Integer, Double> distancesMap = Main.calculateDistancesToExtremePoints(points, expectedExtremePoints);

        assertEquals(expectedMapSize, distancesMap.size());
        int j=0;
        for (int i = 0; i < points.length; i++) {
            if (!expectedExtremePoints.contains(i)) {
                assertTrue("Distances map should contain key for point " + i, distancesMap.containsKey(i));

                // Assuming you have the expected distances, replace the values below with your expected distances
                double expectedDistance = expectedDistances.get(j);
                double actualDistance = distancesMap.get(i);
                j++;
                assertEquals(expectedDistance, actualDistance, 0.00001);
            }
        }
    }


    @Test
    public void testCalculateAndPrintHeights() {
        Map<Integer, Double> distancesMap = Main.calculateDistancesToExtremePoints(points, expectedExtremePoints);

        // Suppress standard output for the test
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Main.calculateAndPrintHeights(points, slopeDegrees, distancesMap, expectedExtremePoints);

        System.setOut(System.out);

        String printedOutput = outContent.toString().trim();
        String[] expectedLines = expectedOutput.split("\\r?\\n");
        String[] actualLines = printedOutput.split("\\r?\\n");

        assertEquals("Number of lines in the output is different", expectedLines.length, actualLines.length);


        // Compare each line individually
        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals("Line " + (i + 1) + " is different", expectedLines[i], actualLines[i]);
        }
    }


}
