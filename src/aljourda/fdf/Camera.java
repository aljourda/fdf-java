package aljourda.fdf;

public class Camera {
	double offsetX;
	double offsetY;
	double x;
	double y;
	double scale;
	
	public Camera(int width, int height){
		x = 0.5;
		y = 0.5;
		scale = 20;
		offsetX = width/2.0;
		offsetY = height/2.0;
	}
	
	public void scaleTo(int objWidth, int objHeight){
		double xSize = objWidth / (offsetX * 1.4);
		double ySize = objHeight / (offsetY * 1.4);
		scale = xSize;
		if(ySize > scale)
			scale = ySize;
		scale = 1/scale;
	}
	
	public void moveX(double value){
		x += value;
	}

	public void moveY(double value){
		y += value;
	}
	
	public void zoom(double value){
		scale += value;
		if(scale < 0.0)
			scale -= value;
	}

	public Point render(Vector3 vector){
		Point ret = null;
		if(vector !=null){
			ret = new Point(0, 0, vector.color);
			double z = -Math.sin(y) * vector.x + Math.cos(y) * vector.z;
			ret.x = (int) (((Math.cos(y) * vector.x + Math.sin(y) * vector.z) * scale) + offsetX);
			ret.y = (int) (((Math.cos(x) * vector.y - Math.sin(x) * z) * scale) + offsetY);
		}
		return ret;
	}
	
	@Override
	public String toString(){
		return "["+x+", "+y+", "+scale+"]";
	}


}
