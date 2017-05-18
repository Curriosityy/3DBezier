import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	public static double silnia(int n) {
		if(n<=1)
		{
			return 1;
			
		}else 
			return n*silnia(n-1);

	}

	public static double newton(int n, int k) {
		return silnia(n) / (silnia(k) * silnia(n-k));
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("teaspoon.txt");
		File fileOut = new File("Output.txt");
		FileReader fr = new FileReader(file);
		PrintWriter pw = new PrintWriter(fileOut);
		Scanner sc = new Scanner(fr);
		String line;
		String[] splited;
		pw.printf("x y z c%n");	
		Point3D[][] data = new Point3D[4][4];
		// Loading points from file
		while(sc.hasNextLine()){
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
				//pw.printf("%.8f %.8f %.8f 2%n", bp.x, bp.y, bp.z);
				pw.println(bp.x+" "+bp.y+" "+bp.z+" 1");
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
	public double x=0;
	public double y=0;
	public double z=0;

	public BezierPoints(Point3D[][] points, double v, double w) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				this.x += points[i][j].x * baseBesieFunction(i, v, points[1].length-1)
						* baseBesieFunction(j, w, points[1].length-1);
				this.y += points[i][j].y * baseBesieFunction(i, v, points[1].length-1)
						* baseBesieFunction(j, w, points[1].length-1);
				this.z += points[i][j].z * baseBesieFunction(i, v, points[1].length-1)
						* baseBesieFunction(j, w, points[1].length-1);
			}
		}
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
