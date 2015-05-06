package br.furb.cg.n3.core;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Mundo extends RecipienteObjetosGraficos
{
	private float sruX = 200;
	private float sruY = 200;
	
	private Camera camera;
	
	public Mundo()
	{
		camera = new Camera();		
	}

	/**
	 * Retorna uma referência a instância da câmera
	 * @return Uma câmera
	 */
	public Camera getCamera()
	{
		return camera;
	}
	
	/**
	 * Faz a chamada à função de posicionamento da câmera 
	 * @param gl
	 * @param glu
	 */
	public void posicionaCamera(GL gl, GLU glu)
	{
		camera.posicionar(gl, glu);
	}
	
	/**
	 * Desenha os eixos de referências (SRU)
	 * @param gl
	 * @param glu
	 */
	public void desenhaSRU(GL gl, GLU glu)
	{
		gl.glLineWidth(1.0f);

		// Eixo X
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		{
			gl.glVertex2d(-sruX, 0.0);
			gl.glVertex2d(+sruX, 0.0);
		}
		gl.glEnd();

		// Eixo Y
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		{
			gl.glVertex2d(0.0, -sruY);
			gl.glVertex2d(0.0, +sruY);
		}
		gl.glEnd();
	}
	
	/**
	 * Desenha os objetos relacionados ao mundo
	 * @param gl
	 * @param glu
	 */
	public void desenhaObjetos(GL gl, GLU glu)
	{
		for (ObjetoGrafico obj : getObjetos())
			obj.desenha(gl, glu);
	}
	
	/**
	 * Localizar algum objeto gráfico que se encontra no ponto de coordenadas (x, y, z)
	 * @param p
	 * @return Um objeto gráfico
	 */
	public ObjetoGrafico localizarObjeto(Ponto p)
	{
		return localizarObjetoR(getObjetos(), p);
	}
	
	/**
	 * Função recursiva para varrer a estrutura de objetos gráficos em busca 
	 * de um objeto localizado no ponto de coordenadas (x, y, z) 
	 * @param listaObjetos
	 * @param p
	 * @return Um objeto gráfico
	 */
	private ObjetoGrafico localizarObjetoR(List<ObjetoGrafico> listaObjetos, Ponto p)
	{
		for (ObjetoGrafico o : listaObjetos)
		{			
			if (o.isSelecionavel())
			{
				if (o.contemPonto(p))
					return o;
				
				ObjetoGrafico s = localizarObjetoR(o.getObjetos(), p);
				
				if (s != null) return s;
			}
		}
		
		return null;
	}
	
	/**
	 * Função para buscar o recipiente de um objeto gráfico
	 * @param o
	 * @return O reciente de um objeto gráfico
	 */
	public RecipienteObjetosGraficos getRecipiente(ObjetoGrafico o)
	{
		return getRecipienteR(getObjetos(), o);
	}
	
	/**
	 * Função recursiva para varrer a estrutura de objetos gráficos em busca 
	 * do recipiente de um objeto gráfico 
	 * @param listaObjetos
	 * @param o
	 * @return O reciente de um objeto gráfico
	 */
	private RecipienteObjetosGraficos getRecipienteR(List<ObjetoGrafico> listaObjetos, ObjetoGrafico o)
	{		
		for (ObjetoGrafico og : listaObjetos)
		{
			if (og.equals(o))
				return this;
			
			RecipienteObjetosGraficos roc = getRecipienteR(og.getObjetos(), o);
			
			if (roc != null) return roc;
		}
		
		return null;
	}
}
