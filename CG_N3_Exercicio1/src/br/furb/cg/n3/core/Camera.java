package br.furb.cg.n3.core;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Camera
{	
	private float ortho2D_minX = -400.0f;
	private float ortho2D_maxX = +400.0f;
	private float ortho2D_minY = -400.0f;
	private float ortho2D_maxY = +400.0f;
	
	public void posicionar(GL gl, GLU glu)
	{			
		glu.gluOrtho2D(ortho2D_minX, ortho2D_maxX, ortho2D_minY, ortho2D_maxY);
	}
	
	public void aproximar()
	{		
		if ((ortho2D_minX + 50) < -50 && 
			(ortho2D_maxX - 50) > +50 && 
			(ortho2D_minY + 50) < -50 && 
			(ortho2D_maxY - 50) > +50)
		{
			ortho2D_minX += 50.0f;
			ortho2D_maxX -= 50.0f;
			ortho2D_minY += 50.0f;
			ortho2D_maxY -= 50.0f;
		}
	}
	
	public void afastar()
	{
		if ((ortho2D_minX - 50) > -500 && 
			(ortho2D_maxX + 50) < +500 && 
			(ortho2D_minY - 50) > -500 && 
			(ortho2D_maxY + 50) < +500)
		{
			ortho2D_minX -= 50.0f;
			ortho2D_maxX += 50.0f;
			ortho2D_minY -= 50.0f;
			ortho2D_maxY += 50.0f;
		}
	}
	
	public void esquerda()
	{
		ortho2D_minX += 50.0f;
		ortho2D_maxX += 50.0f;
	}
	
	public void direita()
	{
		ortho2D_minX -= 50.0f;
		ortho2D_maxX -= 50.0f;
	}
	
	public void cima()
	{
		ortho2D_minY -= 50.0f;
		ortho2D_maxY -= 50.0f;
	}
	
	public void baixo()
	{
		ortho2D_minY += 50.0f;
		ortho2D_maxY += 50.0f;
	}
}
