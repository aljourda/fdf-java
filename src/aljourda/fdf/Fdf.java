package aljourda.fdf;

import java.awt.Color;

import minilibX.Mlx;

public class Fdf {
	public static int defaultColor = Color.WHITE.getRGB();

	public static void main(String[] args) {
		String filename = "testmaps/42.fdf";
		int width = 800;
		int height = 600;
		if(args != null && args.length > 0){
			filename = args[0];
			if(args.length > 2){
				width = Integer.decode(args[1]);
				height = Integer.decode(args[2]);
			}
		}else{
			System.out.println("Usage : fdf filename windowWidth windowHeight");
			System.out.println("Key rotate with arrow and zoom with -+");
			System.out.println("Quit with escape");
		}

		final Map map = new Map();
		if(map.read(filename, defaultColor)){
			final Mlx window = new Mlx(width, height, "Fdf "+filename);
			final Camera cam = new Camera(width, height);
			cam.scaleTo(map.getWidth(), map.getHeight());
			
			window.setHook(new Mlx.Hook(){
				@Override
				public void key(int keycode) {
//					System.out.println("Key pressed "+keycode);
					if(keycode == 27){
						System.exit(0);
					}
					if(keycode == 37)
						cam.moveY(1/100.0);
					if(keycode == 38)
						cam.moveX(1/100.0);
					if(keycode == 39)
						cam.moveY(-1/100.0);
					if(keycode == 40)
						cam.moveX(-1/100.0);
					if(keycode == 109)
						cam.zoom(-0.5);
					if(keycode == 107)
						cam.zoom(0.5);
			        expose();
			        window.repaint();
				}

				@Override
				public void expose() {
//					System.out.println("Expose");
					window.clear();
					for(int x=0;x<map.getWidth();x++){
						for(int y=0;y<map.getHeight();y++){
							Point start = cam.render(map.getVectorAt(x, y));
							if (x + 1 < map.getWidth()){
								MlxLine.linePut(window, start, cam.render(map.getVectorAt(x + 1, y)));
							}
							if (y + 1 < map.getHeight()){
								MlxLine.linePut(window, start, cam.render(map.getVectorAt(x, y + 1)));
							}
						}
					}
				}
			});
		}else{
			System.err.println("error");
		}
	}
}
