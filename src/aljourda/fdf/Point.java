package aljourda.fdf;

public class Point {
	public int x;
	public int y;
	public int color;

	public Point(int x, int y, int color){
		this.x=x;
		this.y=y;
		this.color=color;
	}
	
	public Point(Point toCopy){
		this.x=toCopy.x;
		this.y=toCopy.y;
		this.color=toCopy.color;
	}


}
