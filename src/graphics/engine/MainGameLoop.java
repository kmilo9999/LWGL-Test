package graphics.engine;

import Entity.Camera;
import Entity.Entity;
import graphics.engine.Renderer.ProjectionType;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import shaders.StaticShader;
import textures.ModelTexture;
import textures.TexturedModel;

public class MainGameLoop {
 
	
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader, ProjectionType.Ortho);
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,//v0
				-0.5f, -0.5f, 0f,//v1
				0.5f, -0.5f, 0f,//v2
				0.5f, 0.5f, 0f,//v3
		};
		
		int[] indices = {
				0,1,3,//top left triangle (v0, v1, v3)
				3,1,2//bottom right triangle (v3, v1, v2)
		};
		
		float[] textureCoords = {
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		
		RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("dragonballzsuper.png"));
		
		TexturedModel texturedModel = new TexturedModel(model,texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0,0,0.0f),new Vector3f(0,0,0),new Vector3f(1,1,1));
		Camera camera =  new Camera();
		
		while(!Display.isCloseRequested())
		{
			//entity.increasePosition(0, 0, -0.001f);
			camera.move();
			renderer.prepare();
			shader.start();	
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}

