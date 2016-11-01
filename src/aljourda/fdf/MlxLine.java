package aljourda.fdf;

import minilibX.Mlx;

public class MlxLine {

	public static int gradient(int start, int end, double steps){
		double	r;
		double	g;
		double	b;
		int		ret;

		if (steps > 0)
		{
			r = (end >> 16) & 0xff;
			r -= (start >> 16) & 0xff;
			r = Math.round(r / steps);
			g = (end >> 8) & 0xff;
			g -= (start >> 8) & 0xff;
			g = Math.round(g / steps);
			b = (end >> 0) & 0xff;
			b -= (start >> 0) & 0xff;
			b = Math.round(b / steps);
			ret = (int)r;
			ret <<= 8;
			ret += (int)g;
			ret <<= 8;
			ret += (int)b;
			return (ret + start);
		}
		return (end);
	}
	public static void linePut(Mlx mlx, Point start, Point end){
		Point s = new Point(start);
		Point e = new Point(end);
		boolean	dir;
		int		dx;
		int		dy;
		int		err;
		int		ystep;

		dir = lineCalcDir(s, e);
		dx = e.x - s.x;
		dy = Math.abs(e.y - s.y);
		err = dx / 2;
		ystep = (s.y < e.y) ? 1 : -1;
		while (s.x <= e.x)
		{
			if (dir)
				mlx.pixelPut(s.y, s.x, s.color);
			else
				mlx.pixelPut(s.x, s.y, s.color);
			
			if ((err -= dy) < 0)
			{
				s.y += ystep;
				err += dx;
			}
			s.color = gradient(s.color, e.color, (e.x - s.x));
			s.x++;
		}
	}
	private static boolean lineCalcDir(Point start, Point end){
		boolean dir;
		int tmp;

		dir = Math.abs(end.y - start.y) > Math.abs(end.x - start.x);
		if (dir)
		{
			tmp = start.x;
			start.x = start.y;
			start.y = tmp;
			tmp = end.x;
			end.x = end.y;
			end.y = tmp;
		}
		if (start.x > end.x)
		{
			tmp = start.x;
			start.x = end.x;
			end.x = tmp;
			tmp = start.y;
			start.y = end.y;
			end.y = tmp;
			tmp = start.color;
			start.color = end.color;
			end.color = tmp;
		}
		return (dir);
	}
}
