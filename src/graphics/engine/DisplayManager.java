package graphics.engine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ATIDrawBuffers;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
   
	private static final int  WIDTH = 1280;
	private static final int  HEIGHT = 720;
	
	private static final int  FPS = 120;
	
	public static void createDisplay() {
		
		ContextAttribs attibs = new  ContextAttribs(3,2);
		attibs.withForwardCompatible(true);
		attibs.withProfileCore(true);
		
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(),attibs);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	};
	
	public static void updateDisplay() {
		Display.sync(FPS);
		Display.update();
		
	};
	public static void closeDisplay() {
		
		Display.destroy();
	};
	
}
