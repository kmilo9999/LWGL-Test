package graphics.engine;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Entity.Entity;
import shaders.StaticShader;
import textures.TexturedModel;
import utils.MathHelper;

public class Renderer {

	public enum ProjectionType
	{
		Ortho,
		Perspective
	};
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000.0f;
	
	private Matrix4f projectionMatrix;
	
	public Renderer(StaticShader shader, ProjectionType type)
	{
		if(type == ProjectionType.Perspective)
		{
			createPerspectiveProjectionMatrix();	
		}
		else
		{
			createOthoProjectionMatrix();	
		}
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void prepare()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1, 0, 0,1);
		
	}
	
	public void render(Entity entity, StaticShader shader )
	{
		TexturedModel model = entity.getModel();
		GL30.glBindVertexArray(model.getRawModel().getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		Matrix4f transformationMatrix = MathHelper.createTransformationMatrix(
				entity.getPosition(),entity.getRotation(),entity.getScale());
		
 		shader.loadTransformationMatrix(transformationMatrix);
 		shader.loadProjectionMatrix(projectionMatrix);
 		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
	}
	
	private void createPerspectiveProjectionMatrix()
	{
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
	}
	
	private void createOthoProjectionMatrix()
	{
		float near = 0.1f; 
	    float far = 100f; 
	    float left = -10.0f;
	    float rigth = 10.0f;
	    float bottom = -10.0f;
	    float top =	10.0f;
	    projectionMatrix = new Matrix4f();
	    projectionMatrix.m00 = 2.0f/ (rigth - left);
        projectionMatrix.m11 = 2.0f/ (top - bottom);
        projectionMatrix.m22 = 2.0f/ (far - near);

        projectionMatrix.m30 = -(rigth + left) / (rigth - left); 
        projectionMatrix.m31 =  -(top + bottom) / (top - bottom);
        projectionMatrix.m32 = -(far + near) / (far - near); 
        projectionMatrix.m33 = 1;
	    
	    
	    
	    
	}
}
