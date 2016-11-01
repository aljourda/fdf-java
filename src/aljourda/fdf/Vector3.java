package aljourda.fdf;

public class Vector3 {
	public double x;
	public double y;
	public double z;
	public int color;

	public Vector3(double x, double y, double z, int color){
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
	}
	public Vector3(Vector3 toCopy){
		this.x = toCopy.x;
		this.y = toCopy.y;
		this.z = toCopy.z;
		this.color = toCopy.color;
	}
	
	@Override
	public String toString(){
		return "["+x+", "+y+", "+z+" : "+color+"]";
	}
}
