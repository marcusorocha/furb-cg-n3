package br.furb.cg.n3.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import br.furb.cg.n3.utils.FuncoesGeometricas;

public class ObjetoGrafico extends ObjetoGraficoContainer
{
	private List<Ponto> vertices;
	
	private boolean selecionado;
	private boolean desenhando;
	private int primitiva;
	private Color cor;
	
	private BBox bbox;
	private Transformacao matrizObjeto;
	private Transformador transformador;
	
	private Ponto verticeSelecionado;
	
	public ObjetoGrafico(int primitiva)
	{
		this.primitiva = primitiva;
		this.vertices = new ArrayList<Ponto>();
		this.bbox = new BBox();
		this.matrizObjeto = new Transformacao();
		this.transformador = new Transformador(this);
		
		this.selecionado = false;
		this.cor = Color.BLACK;
		
		this.verticeSelecionado = null;
	}
	
	public void desenha(GL gl, GLU glu)
	{		
		gl.glPushMatrix();
		{
			gl.glMultMatrixd(matrizObjeto.getData(), 0);
			{
				gl.glColor3ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue());
				
				gl.glBegin(primitiva);
				{
					for (Ponto v : vertices)
						gl.glVertex2d(v.getX(), v.getY());
				}
				gl.glEnd();
				
				if (selecionado || desenhando)
				{
					gl.glColor3f(0.0f, 0.0f, 0.0f);
					
					if (selecionado)
						gl.glPointSize(4.0f);
					else
						gl.glPointSize(2.0f);
					
					gl.glBegin(GL.GL_POINTS);
					{
						for (Ponto v : vertices)
							gl.glVertex2d(v.getX(), v.getY());
					}
					gl.glEnd();											
				}
				
				if (verticeSelecionado != null)
				{					
					gl.glColor3f(1.0f, 0.0f, 0.0f);					
					gl.glPointSize(5.0f);
					gl.glBegin(GL.GL_POINTS);
					{
						gl.glVertex2d(verticeSelecionado.getX(), verticeSelecionado.getY());
					}
					gl.glEnd();
				}
				
				for (ObjetoGrafico f : getObjetos())
					f.desenha(gl, glu);
				
				if (selecionado) bbox.desenha(gl, glu);
			}
		}
		gl.glPopMatrix();
	}
	
	private void preencherBBox()
	{
		bbox.setCoordenadas(vertices);
	}

	/**
	 * Obtem uma referência da BBox do objeto gráfico
	 * 
	 * @return Referência da BBox do objeto gráfico
	 */
	public BBox getBbox()
	{	
		return bbox;
	}
	
	/**
	 * Atribui a primitiva gráfica que será utilizada pelo OpenGL 
	 * para desenhar o objeto gráfico
	 * 
	 * @param primitiva Índice da primitiva gráfica do OpenGL 
	 */
	public void setPrimitiva(int primitiva)
	{
		this.primitiva = primitiva;
	}
	
	/**
	 * Atribui a <code>Cor</code> que será utilizada juntamente com
	 * a primitiva no desenho e pintura do objeto gráfico
	 * 
	 * @param cor <code>Cor</code> para o objeto gráfico
	 * 
	 * @see #setPrimitiva(int)
	 */
	public void setCor(Color cor)
	{
		if (cor == null)
			return;
				
		this.cor = cor;
	}
	
	public void selecionar()
	{
		this.selecionado = true;
	}
	
	public void limparSelecao()
	{
		this.selecionado = false;
		this.verticeSelecionado = null;
	}
	
	public boolean isSelecionado()
	{
		return selecionado;
	}
	
	public Ponto addVertice(double x, double y)
	{
		preencherBBox();
		
		Ponto v = new Ponto(x, y, 0, 1);
		
		vertices.add(v);
		
		return v;
	}
	
	public void escalar(double proporcao)
	{		
		transformador.escalar(proporcao);
	}
	
	public void transladar(double x, double y)
	{
		transformador.transladar(x, y);
	}
	
	public void rotacionar(double graus)
	{		
		transformador.rotacionar(graus);
	}
	
	public Transformacao getMatrizObjeto()
	{
		return matrizObjeto;
	}
	
	public void exibeVertices()
	{
		for (int i = 0; i < vertices.size(); i++)
			System.out.println(String.format("P%d [ %s ]", i, vertices.get(i).toString()));
	}
	
	public void exibeMatrizTransformacao()
	{
		this.matrizObjeto.exibeMatriz();
	}
	
	public boolean contemPonto(Ponto p)
	{
		if (!bbox.isPontoDentro(p))
			return false;
		
		if (getVerticeDePonto(p) != null)
			return true;
		
		return FuncoesGeometricas.pontoEmPoligono(vertices, p);
	}
	
	public int getQuantidadeVertices()
	{
		return vertices.size();
	}
	
	public Ponto getPrimeiroVertice()
	{
		if (vertices.size() > 0)
			return vertices.get(0);
		
		return null;
	}
	
	public int getIndiceUltimoVertice()
	{
		return vertices.size() - 1;
	}
	
	public Ponto getUltimoVertice()
	{	
		int indiceUltimoVertice = getIndiceUltimoVertice();
		
		if (indiceUltimoVertice > -1)
			return vertices.get(indiceUltimoVertice);
		
		return null;
	}
	
	public void removeUltimoVertice()
	{
		int indiceUltimoVertice = getIndiceUltimoVertice();
		
		if (indiceUltimoVertice > -1)
			vertices.remove(indiceUltimoVertice);
	}
	
	public boolean isSelecionavel()
	{		
		return matrizObjeto.isIdentity();
	}
	
	public Ponto getVerticeDePonto(Ponto p)
	{	
		for (Ponto v : vertices)
			if (v.isPontoProximo(p))
				return v;
		
		return null;
	}

	public boolean isDesenhando()
	{
		return desenhando;
	}

	public void setDesenhando(boolean desenhando)
	{
		this.desenhando = desenhando;
	}
	
	public void pressionouMouse(Ponto p)
	{
		Ponto v = getVerticeDePonto(p);
		
		verticeSelecionado = v;
	}
	
	public void liberouMouse(Ponto p)
	{
		preencherBBox();
		selecionado = true;
	}
	
	public void arrastouMouse(Ponto p)
	{		
		if (verticeSelecionado != null)
		{	
			selecionado = false;
			verticeSelecionado.setX(p.getX());
			verticeSelecionado.setY(p.getY());
		}
	}
	
	public boolean removerVerticeSelecionado()
	{
		if (verticeSelecionado != null)
		{
			vertices.remove(verticeSelecionado);
			verticeSelecionado = null;
			preencherBBox();
			return true;
		}
		
		return false;
	}
}
