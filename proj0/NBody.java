public class NBody {
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		in.readInt();
		double radius = in.readDouble();
		return radius;
	}
	public static Body[] readBodies(String fileName) {
		In in = new In(fileName);
		int bodies = in.readInt();
		in.readDouble();
		Body[] BodiesArray = new Body[bodies];
		for (int i = 0; i < bodies; i += 1) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			BodiesArray[i] = new Body(xP, yP, xV, yV, m, img);
		}
		return BodiesArray;
	}
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double Radius = readRadius(filename);
		Body[] Bodies = readBodies(filename);
		String Background = "images/starfield.jpg";
		StdDraw.clear();
		StdDraw.setScale(-1 * Radius, Radius);
		StdDraw.picture(0, 0, Background);
		for (int i = 0; i < Bodies.length; i += 1) {
			Bodies[i].draw();
		}
		StdDraw.enableDoubleBuffering();
		double time = 0;
		while (time < T) {
			double[] xForces = new double[Bodies.length];
			double[] yForces = new double[Bodies.length];
			for (int j = 0; j < Bodies.length; j += 1) {
				xForces[j] = Bodies[j].calcNetForceExertedByX(Bodies);
				yForces[j] = Bodies[j].calcNetForceExertedByY(Bodies);
			}
			for (int k = 0; k < Bodies.length; k += 1) {
				Bodies[k].update(dt, xForces[k], yForces[k]);
			}
			StdDraw.picture(0, 0, Background);
			for (int i = 0; i < Bodies.length; i += 1) {
				Bodies[i].draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}
		StdOut.printf("%d\n", Bodies.length);
		StdOut.printf("%.2e\n", Radius);
		for (int i = 0; i < Bodies.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", Bodies[i].xxPos, Bodies[i].yyPos, Bodies[i].xxVel,
				Bodies[i].yyVel, Bodies[i].mass, Bodies[i].imgFileName); 
		}
	}
}