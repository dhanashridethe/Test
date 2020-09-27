
// Given a polygon and a point 'p', find if 'p' lies inside the polygon or not. The points lying on the border are considered inside
class Point {
	double x;
	double y;

	Point() {
	}

	Point(double p, double q) {
		x = p;
		y = q;
	}

}

//Main Class
public class PointCheckPolygon {

	// find max of 2 numbers
	public static double max(double a, double b) {
		return (a >= b) ? a : b;
	}

	// find min of 2 numbers
	public static double min(double a, double b) {
		return (a >= b) ? b : a;
	}

	// check if the point is on the side of polygon
	public static boolean onSide(Point p, Point q, Point r) {
		return q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) && q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y);

	}

	// check if the 3 points are colinear
	public static int orientation(Point p, Point q, Point r) {
		double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		if (val == 0)
			return 0;
		return (val > 0) ? 1 : 2;
	}

	// check if the line p1q1 intersects p2q2
	public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {

		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		if (o1 != o2 && o3 != o4)
			return true;

		if (o1 == 0 && onSide(p1, p2, q1))
			return true;

		if (o2 == 0 && onSide(p1, q2, q1))
			return true;

		if (o3 == 0 && onSide(p2, p1, q2))
			return true;

		return (o4 == 0 && onSide(p2, q1, q2));

	}

	// check if the point is inside or not
	public static boolean isInside(Point[] polygon, int numberOfPoints, Point pointToBeCheck) {
		int infinity = 100000;
		int count = 0;
		int iterator = 0;
		
		//check if the polygon is valid
		if (numberOfPoints <= 2)
			return false;

		//create point at the infinite distance
		Point infinite = new Point(infinity, pointToBeCheck.y);

		do {
			int next = (iterator + 1) % numberOfPoints;
			if (doIntersect(polygon[iterator], polygon[next], pointToBeCheck, infinite)) {
				if (orientation(polygon[iterator], pointToBeCheck, polygon[next]) == 0)
					return onSide(polygon[iterator], pointToBeCheck, polygon[next]);

				count++;
			}
			iterator = next;
		} while (iterator != 0);

		return (count & 1) == 1;
	}

	public static void main(String args[]) {
		Point[] polygon1 = { new Point(1, 0), new Point(8, 3), new Point(8, 8), new Point(1, 5) };

		Point p = new Point(3, 5);
		System.out.println(isInside(polygon1, polygon1.length, p));

		Point[] polygon2 = { new Point(-3, 2), new Point(-2, -0.8), new Point(0, 1.2), new Point(2.2, 0),
				new Point(2, 4.5) };
		Point p2 = new Point(0, 0);

		System.out.println(isInside(polygon2, polygon2.length, p2));
	}
}