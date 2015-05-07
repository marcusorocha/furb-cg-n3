package br.furb.cg.n3.core;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class BBox
{
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	
	/**
	 * Obtem a menor coordenada no eixo X
	 * 
	 * @return A menor coordenada no eixo X
	 */
	public double getxMin()
	{
		return xMin;
	}
	
	/**
	 * Atribui a menor coordenada no eixo X
	 * 
	 * @param xMin <code>double</code> contendo a menor coordenada no eixo X
	 */
	public void setxMin(double xMin)
	{
		this.xMin = xMin;
	}
	
	/**
	 * Obtem a maior coordenada no eixo X
	 * 
	 * @return A maior coordenada no eixo X
	 */
	public double getxMax()
	{
		return xMax;
	}
	
	/**
	 * Atribui a maior coordenada no eixo X
	 * 
	 * @param xMax <code>double</code> contendo a maior coordenada no eixo X
	 */
	public void setxMax(double xMax)
	{
		this.xMax = xMax;
	}
	
	/**
	 * Obtem a menor coordenada no eixo Y
	 * 
	 * @return A menor coordenada no eixo Y
	 */
	public double getyMin()
	{
		return yMin;
	}
	
	/**
	 * Atribui a menor coordenada no eixo Y
	 * 
	 * @param yMin <code>double</code> contendo a menor coordenada no eixo Y
	 */
	public void setyMin(double yMin)
	{
		this.yMin = yMin;
	}
	
	/**
	 * Obtem a maior coordenada no eixo Y
	 * 
	 * @return A maior coordenada no eixo Y
	 */
	public double getyMax()
	{
		return yMax;
	}
	
	/**
	 * Atribui a maior coordenada no eixo Y
	 * 
	 * @param yMin <code>double</code> contendo a maior coordenada no eixo Y
	 */
	public void setyMax(double yMax)
	{
		this.yMax = yMax;
	}
	
	/**
	 * Atribui todas as coordenadas máximas e mínimas dos eixos X e Y
	 * 
	 * @param xMin <code>double</code> contendo a menor coordenada no eixo X
	 * @param xMax <code>double</code> contendo a maior coordenada no eixo X
	 * @param yMin <code>double</code> contendo a menor coordenada no eixo Y
	 * @param yMax <code>double</code> contendo a maior coordenada no eixo Y
	 */
	public void setCoordenadas(double xMin, double xMax, double yMin, double yMax)
	{
		setxMax(xMax);
		setxMin(xMin);
		setyMax(yMax);
		setyMin(yMin);
	}
	
	/**
	 * Preenche as coordenadas máximas e mínimos dos eixos X e Y 
	 * com base em uma lista de <code>Ponto</code> 
	 * 
	 * @param vertices Lista de <code>Ponto</code>
	 */
	public void setCoordenadas(List<Ponto> vertices)
	{
		double minX = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double minY = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		
		for (Ponto p : vertices)
		{
			if (p.getX() > maxX)
				maxX = p.getX() + Ponto.RAIO_PONTO;
			
			if (p.getX() < minX)
				minX = p.getX() - Ponto.RAIO_PONTO;
			
			if (p.getY() > maxY)
				maxY = p.getY() + Ponto.RAIO_PONTO;
			
			if (p.getY() < minY)
				minY = p.getY() - Ponto.RAIO_PONTO;
		}
		
		setCoordenadas(minX, maxX, minY, maxY);
	}
	
	/**
	 * Obter a coordenada central no eixo X  
	 * 
	 * @return Coordenada central no eixo X
	 */
	public double getCentroX()
	{
		return ((xMax - xMin) / 2) + xMin;
	}
	
	/**
	 * Obter a coordenada central no eixo Y
	 * 
	 * @return Coordenada central no eixo Y
	 */
	public double getCentroY()
	{
		return ((yMax - yMin) / 2) + yMin;
	}
	
	/**
	 * Obter o <code>Ponto</code> contendo as coordenadas 
	 * centrais dos eixos X e Y.
	 * 	
	 * @return <code>Ponto</code> contendo as coordenadas centrais dos eixos X e Y.
	 */
	public Ponto getPontoMedio()
	{
		return new Ponto(getCentroX(), getCentroY());
	}
	
	
	public boolean isPontoDentro(Ponto p)
	{
		return (p.getX() > xMin) && (p.getX() < xMax) && (p.getY() > yMin) && (p.getY() < yMax);
	}
	
	public void desenha(GL gl, GLU glu)
	{
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glLineStipple(1, (short)0XAAAA);
		
		gl.glEnable(GL.GL_LINE_STIPPLE);
		gl.glBegin(GL.GL_LINE_LOOP);
		{	
			gl.glVertex2d(xMin, yMin);
			gl.glVertex2d(xMax, yMin);
			gl.glVertex2d(xMax, yMax);
			gl.glVertex2d(xMin, yMax);
		}
		gl.glEnd();
		gl.glDisable(GL.GL_LINE_STIPPLE);
		
		gl.glBegin(GL.GL_POINTS);
		{	
			gl.glVertex2d(xMin, yMin);
			gl.glVertex2d(xMax, yMin);
			gl.glVertex2d(xMax, yMax);
			gl.glVertex2d(xMin, yMax);
			
			gl.glVertex2d(xMin, getCentroY());
			gl.glVertex2d(xMax, getCentroY());
			gl.glVertex2d(getCentroX(), yMax);
			gl.glVertex2d(getCentroX(), yMin);
		}
		gl.glEnd();
	}
}