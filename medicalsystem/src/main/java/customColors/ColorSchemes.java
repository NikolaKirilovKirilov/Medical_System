package customColors;

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

public class ColorSchemes implements Paint, java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final Color BACKGROUND_BEIGE = new Color(242,237,210);
	
	public static final Color MENU_GREEN = new Color(82,194,34);
	
	@Override
	public int getTransparency() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds,
			AffineTransform xform, RenderingHints hints) {
		// TODO Auto-generated method stub
		return null;
	}
}
