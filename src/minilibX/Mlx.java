package minilibX;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Mlx{
	private final Frame mainFrame;
	private final BufferedImage bi;
	private final KeyListener keyListener;
	private final MlxCanvas canvas = new MlxCanvas();
	private Hook listener;

	public Mlx(int width, int height, String title){
		mainFrame = new Frame(title);
		mainFrame.setSize(width+5, height+30);
		bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		mainFrame.setResizable(false);
		keyListener = new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(listener != null && e != null){
					listener.key(e.getExtendedKeyCode());
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		};
		mainFrame.add(canvas);
		mainFrame.setVisible(true);
	}

	public void clear(){
		Graphics2D g2d = bi.createGraphics();
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
	}

	public void pixelPut(int x, int y, int color){
		if(x > 0 && y > 0 && x < bi.getWidth() && y < bi.getHeight())
			bi.setRGB(x, y, color);
	}
	public void drawLine(int x, int y, int x2, int y2, int color){
		Graphics2D g2d = bi.createGraphics();
		g2d.setPaint(new Color(color));
		g2d.drawLine(x, y, x2, y2);
	}

	public void repaint(){
		canvas.repaint();
	}

	public void setHook(Hook listener){
		this.listener = listener;
	}

	public interface Hook{
		public void key(int keycode);
		public void expose();
	}

	private class MlxCanvas extends Canvas{
		private static final long serialVersionUID = 610172417278220095L;
		@Override
		public void paint(Graphics g) {
			mainFrame.removeKeyListener(keyListener);
			mainFrame.addKeyListener(keyListener);
			if(listener != null)
				listener.expose();
			g.drawImage(bi, 0, 0, MlxCanvas.this);
		}
	}
}
