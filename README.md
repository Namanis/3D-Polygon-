# 3D Plane Projection

This Java program takes a set of 3D points representing a polygon on a plane and projects them onto a 3D plane at an inclined angle. It finds extreme points based on the azimuth angle, calculates distances from non-extreme points to the line formed by extreme points or a single extreme point in some scenarios, and then adjusts the height (z-coordinate) of each point accordingly.

## Detailed Code Explanation
The user inputs a valid polygon, meaning it should have more than 2 points. Also, the user must input slope and azimuth angles. The program is limited to accepting only (0, 90, 180, 270) as valid azimuth angles. The program automatically fills in the Z value, as the polygon should be on the XY plane in its initial state and has to be 0. Depending on the azimuth angle, extreme points are found and printed out on the screen. If the azimuth angle is 0, the program searches for points that have the highest Y value (are furthest north). For 90 angle furthest East, 180 South, 270 West. There can be multiple extreme points that are the same distance away, but there must be at least one such point. Distances from other points to extreme points are then calculated. If there is a single extreme point, the program calculates distance from point to point using formula

$$ Distance = {\sqrt{(x_{2}-x_{1})^2-(y_{2}-y_{1})^2}} $$

Given that there are more extreme points, distance is calculated from point to point on a line that is made of extreme points.
In other words, it is the shortest distance to a line.

$$ Distance = {\left\lvert(y_{2}-y_{1})*x- (x_{2}-x_{1})*y+x_{2}*y_{1}-y_{2}*x_{1}\right\rvert \over \sqrt {(y_{2}-y_{1})^2+(x_{2}-x_{1})^2}} $$

Each distance and point index is put into a map using a hashmap.
Lastly, the program finds all non-extreme points and calculates the Z coordinate. It finds the hypotenuse using the cos of the slope angle and distance.
$$ Hypothenuse = {Distance \over Cos(SlopeAngle)} $$
Then Z is calculated by
$$ Z = {\sqrt{Hypothenuse^2-Distance^2}} $$
To avoid precision problems, Z is rounded to 9 numbers after decimal




## Instructions

1. Run the program.
2. Enter Number of Points (minimum 3)
3. Enter X and Y coordinates (Z is automatically set to 0)
4. Enter the slope and azimuth angles in degrees.
5. View the input values, extreme points, and distances to the extreme points or lines.
6. Observe the modified points on the 3D plane after adjusting heights.

## Code Weaknesses

 1. It only accepts and calculates when the azimuth angles are 0, 90, 180, and 270.
 2. Given that there are more than 2 extreme points, it does not consider the line of extreme points to be from the furthest points. Meaning if there are 3 points, it should find 2 points that are furthest away from each other and make it its line
 3. Some code fragments are repetitive
 4. No third-party libraries were used

## Additional notes
It took about 30-50 minutes to get familiar with the task. 4 hours of pure programming and 2 hours of testing(I have very little experience with testing). Overall I've spent around 6-7 hours. 
I expected to finish it faster but working on it I kept running into more calculations I hadn't anticipated earlier that needed to be done to find the solution.
 
