import java.lang.Math;
public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Body(Body b) {
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass;
		this.imgFileName = b.imgFileName;
	};
	public double calcDistance(Body b2) {
		double xxDist = this.xxPos - b2.xxPos;
		double yyDist = this.yyPos - b2.yyPos;
		return Math.sqrt(xxDist*xxDist + yyDist*yyDist);
	}
	public double calcForceExertedBy(Body b2) {
		if (this.calcDistance(b2) == 0) {
			return 0;
		}
		else {
			return (6.67e-11 * this.mass * b2.mass)/(this.calcDistance(b2)*this.calcDistance(b2));
		}
	}
	public double calcForceExertedByX(Body b2) {
		if (this.calcForceExertedBy(b2) == 0) {
			return 0;
		}
		else {
			return (this.calcForceExertedBy(b2) * (b2.xxPos - this.xxPos))/this.calcDistance(b2);
		}
	}
	public double calcForceExertedByY(Body b2) {
		if (this.calcForceExertedBy(b2) == 0) {
			return 0;
		}
		else {
			return (this.calcForceExertedBy(b2) * (b2.yyPos - this.yyPos))/this.calcDistance(b2);
		}
	}
	public double calcNetForceExertedByX(Body[] BodyArray) {
		double NetForceX = 0;
		for (Body b : BodyArray) {
			NetForceX += this.calcForceExertedByX(b);
		}
		return NetForceX;
	}
	public double calcNetForceExertedByY(Body[] BodyArray) {
		double NetForceY = 0;
		for (Body b : BodyArray) {
			NetForceY += this.calcForceExertedByY(b);
		}
		return NetForceY;
	}
	public void update(double dt, double fX, double fY) {
		double accelerationX = fX/this.mass;
		double accelerationY = fY/this.mass;
		this.xxVel += dt * accelerationX;
		this.yyVel += dt * accelerationY;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}
	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}