package br.furb.cg.n3.core;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Camera
{	
	private float ortho2D_minX = -400.0f;
	private float ortho2D_maxX = +400.0f;
	private float ortho2D_minY = -400.0f;
	private float ortho2D_maxY = +400.0f;
	
	/**
	 * Atribui os valores máximos e minimos dos eixos X e Y 
	 * ao "ortho". 
	 * 
	 * @param gl
	 * @param glu
	 * 
	 * @see javax.media.opengl.glu.GLU#gluOrtho2D(double, double, double, double)
	 */
	public void posicionar(GL gl, GLU glu)
	{			
		glu.gluOrtho2D(ortho2D_minX, ortho2D_maxX, ortho2D_minY, ortho2D_maxY);
	}
	
	/**
	 * Aumenta em 50 pixels os valores mínimos dos eixos X e Y e diminui 
	 * em 50 pixels os valores máximos dos eixos X e Y de referência do 
	 * ortho2D para aproximar a câmera. Os valores mínimos não serão 
	 * maiores que -50 pixels e os valores máximos não serão menores 
	 * que +50 pixels, ou seja, a maior aproximação possível será uma
	 * visão de 100 x 100 pixels.
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void aproximar()
	{		
		if ((ortho2D_minX) < -100 && 
			(ortho2D_maxX) > +100 && 
			(ortho2D_minY) < -100 && 
			(ortho2D_maxY) > +100)
		{
			ortho2D_minX += 50.0f;
			ortho2D_maxX -= 50.0f;
			ortho2D_minY += 50.0f;
			ortho2D_maxY -= 50.0f;
		}
	}
	
	/**
	 * Diminui em 50 pixels os valores mínimos dos eixos X e Y e aumenta 
	 * em 50 pixels os valores máximos dos eixos X e Y de referência do 
	 * ortho2D para afastas a câmera. Os valores mínimos não serão 
	 * menores que -500 pixels e os valores máximos não serão maiores 
	 * que +500 pixels, ou seja, o maior afastamento possível será uma
	 * visão de 700 x 700 pixels.
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void afastar()
	{
		if ((ortho2D_minX) > -700 && 
			(ortho2D_maxX) < +700 && 
			(ortho2D_minY) > -700 && 
			(ortho2D_maxY) < +700)
		{
			ortho2D_minX -= 50.0f;
			ortho2D_maxX += 50.0f;
			ortho2D_minY -= 50.0f;
			ortho2D_maxY += 50.0f;
		}
	}
	
	/**
	 * Aumenta em 50 pixels o valor mínimo e máximo do eixo X
	 * para fazer com que a câmera seja movida para a esquerda
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void esquerda()
	{
		ortho2D_minX += 50.0f;
		ortho2D_maxX += 50.0f;
	}
	
	/**
	 * Diminui em 50 pixels o valor mínimo e máximo do eixo X
	 * para fazer com que a câmera seja movida para a direita
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void direita()
	{
		ortho2D_minX -= 50.0f;
		ortho2D_maxX -= 50.0f;
	}
	
	/**
	 * Diminui em 50 pixels o valor mínimo e máximo do eixo Y
	 * para fazer com que a câmera seja movida para a acima
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void acima()
	{
		ortho2D_minY -= 50.0f;
		ortho2D_maxY -= 50.0f;
	}
	
	/**
	 * Aumenta em 50 pixels o valor mínimo e máximo do eixo Y
	 * para fazer com que a câmera seja movida para a abaixo
	 * do centro do ambiente gráfico (SRU).
	 * 
	 * @see #posicionar(GL, GLU)
	 */
	public void abaixo()
	{
		ortho2D_minY += 50.0f;
		ortho2D_maxY += 50.0f;
	}
	
	/**
	 * Converte as coordenadas de x e y do clique para as coordenadas do ambiente
	 * onde se encontra a câmera.
	 * 
	 * @param xPonto Coordenada X do clique
	 * @param yPonto Coordenada Y do clique
	 * @param xTela Largura total do componente de apresentação do ambiente
	 * @param yTela Altura total do componente de apresentação do ambiente
	 * 
	 * @return Um <code>Ponto</code> com as coordenadas convertidas.
	 */
	public Ponto convertePontoTela(double xPonto, double yPonto, double xTela, double yTela)
	{
		double xTotal = ortho2D_maxX - ortho2D_minX;
		double yTotal = ortho2D_maxY - ortho2D_minY;
		
		double escalaX = xTotal / xTela;
		double escalaY = yTotal / yTela;

		double x = ((xPonto * escalaX) + ortho2D_minX);
		double y = ((yPonto * escalaY) + ortho2D_minY) * -1;
		
		return new Ponto(x, y);
	}
}
