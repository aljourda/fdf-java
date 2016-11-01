package aljourda.fdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Map {
	private int width;
	private int height;
	private int relief;
	private Vector3[][] vectors;
	
	public Map(){
		width = 0;
		height = 0;
		relief = 0;
		vectors = null;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Vector3 getVectorAt(int x, int y){
		if(x >= 0 && y >= 0 && x < width && y < height){
			return vectors[x][y];
		}
		return null;
	}

	public boolean read(String filename, int defaultColor){
		Vector<Vector3> points = new Vector<Vector3>();
		FileReader fileReader = null;
		BufferedReader br = null;
		int lineNumber = 0;
		int lineSize = -1;
		int maxZ = 0;
		try {
			fileReader = new FileReader(new File(filename));
			br = new BufferedReader(fileReader);
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.replaceAll("  ", " ");
				line = line.replaceAll("  ", " ");
				String[] coords = line.split(" ");
				if(coords != null){
					if(lineSize == -1)
						lineSize = coords.length;
					if(lineSize != coords.length){
						points.clear();
						break ;
					}
					for(int i=0;i<coords.length;i++){
						String[] data = coords[i].split(",");
						if(data != null && data.length>0){
							int z = Integer.decode(data[0]);
							if(z > maxZ){
								maxZ = z;
							}
							int color = defaultColor;
							if(data.length>1){
								color = Integer.decode(data[1]);
							}
							Vector3 v = new Vector3(i, lineNumber, z, color);
							points.add(v);
						}
					}
				}
				lineNumber++;
			}
		} catch (Exception e) {
			points.clear();
			e.printStackTrace();
		} finally {
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//Normalize in double array
		if(lineSize > 0 && lineNumber > 0 && points.size() > 0){
			width = lineSize;
			height = lineNumber;
			relief = maxZ;
			vectors = new Vector3[width][height];
			for(Vector3 e:points){
				vectors[(int) e.x][(int) e.y] = e;
				//Normalize vector
				e.x -= width / 2.0;
				e.y -= height / 2.0;
				if(relief > 0)
					e.z /= (relief / 2.0);
			}
			return true;
		}
		return false;
	}

}
