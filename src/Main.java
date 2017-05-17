import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	public static int silnia(int n) {
		int sil = 1;
		for (int i = 1; i <= n; i++) {
			sil *= i;
		}
		return sil;

	}

	public static double newton(int n, int k) {
		return silnia(n) / (silnia(k) * silnia(n - k));
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("teapot.txt");
		File fileOut = new File("Output.txt");
		FileReader fr = new FileReader(file);
		PrintWriter pw = new PrintWriter(fileOut);
		Scanner sc = new Scanner(fr);
		String line;
		String[] splited;
		pw.printf("x y z c%n");
		while(sc.hasNextLine())
		{	
		Point3D[][] data = new Point3D[4][4];
		// Loading points from file
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				line = sc.nextLine();
				splited = line.split(" ", 3);
				data[i][j] = new Point3D(Double.parseDouble(splited[0]), Double.parseDouble(splited[1]),
						Double.parseDouble(splited[2]));
			}
		}
		// calculating and sending points to file
		BezierPoints bp;
		for (double v = 0; v < 1; v += 0.01) {
			for (double w = 0; w < 1; w += 0.01) {
				bp = new BezierPoints(data, v, w);
				pw.printf("%.8f %.8f %.8f 2%n", bp.x, bp.y, bp.z);
			}
		}
		}
		sc.close();
		pw.close();
		System.out.println("done");
	}
	
}

class Point3D {
	public double x;
	public double y;
	public double z;

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + x + " " + y + " " + z;
	}
}

class BezierPoints {
	public double x;
	public double y;
	public double z;

	public BezierPoints(Point3D[][] points, double v, double w) {
		Point3D point = pointOnBezie(points, v, w);
		x = point.x;
		y = point.y;
		z = point.z;
	}

	private Point3D pointOnBezie(Point3D[][] points, double v, double w) {
		Point3D point = new Point3D(0, 0, 0);
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points.length; j++) {
				point.x += points[i][j].x * baseBesieFunction(i, v, points.length)
						* baseBesieFunction(j, w, points.length);
				point.y += points[i][j].y * baseBesieFunction(i, v, points.length)
						* baseBesieFunction(j, w, points.length);
				point.z += points[i][j].z * baseBesieFunction(i, v, points.length)
						* baseBesieFunction(j, w, points.length);
			}
		}
		return point;
	}

	private double baseBesieFunction(int i, double v, int len) {
		return Main.newton(len, i) * Math.pow(v, i) * Math.pow(1 - v, len - i);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + x + " " + y + " " + z + " 1";
	}
}
