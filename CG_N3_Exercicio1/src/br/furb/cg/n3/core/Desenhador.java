package br.furb.cg.n3.core;

import javax.media.opengl.GL;

public class Desenhador
{
	private ObjetoGrafico og;
	private Ponto pv;
	private Ponto uv;
	
	private RecipienteObjetosGraficos recipente;
	
	public Desenhador()
	{
		inicilizar();
	}
	
	private void inicilizar()
	{
		this.og = null;
		this.pv = null;
		this.uv = null;
	}
	
	public boolean isDesenhando()
	{
		return og != null;
	}
	
	public RecipienteObjetosGraficos getRecipente()
	{
		return recipente;
	}

	public void setRecipente(RecipienteObjetosGraficos recipente)
	{
		this.recipente = recipente;
	}
	
	private void iniciarDesenho(Ponto p)
	{		
		og = new ObjetoGrafico(GL.GL_LINE_STRIP);
		og.setDesenhando(true);
		pv = og.addVertice(p.getX(), p.getY());
		uv = og.addVertice(p.getX(), p.getY());
		
		recipente.adicionarObjeto(og);
	}
	
	public void adicionarVertice(Ponto p)
	{
		uv = og.addVertice(p.getX(), p.getY());
	}
	
	public void atualizarUltimoVertice(Ponto p)
	{
		if (uv != null)
		{
			uv.setX(p.getX());
			uv.setY(p.getY());
		}
	}
	
	public boolean isFecharDesenho(Ponto p)
	{
		if (pv != null)
			return pv.isPontoProximo(p);
		
		return false;
	}
	
	public void finalizarDesenho(boolean fechado)
	{
		if (isDesenhando())
		{
			og.removeUltimoVertice();			
			og.setDesenhando(false);
			
			if (fechado) og.setPrimitiva(GL.GL_LINE_LOOP);
			
			inicilizar();
		}
	}
	
	public void removerUltimoVertice()
	{
		if (isDesenhando())
		{			
			if (og.getQuantidadeVertices() > 2)
			{
				og.removeUltimoVertice();
				og.removeUltimoVertice();
				og.addVertice(uv.getX(), uv.getY());
			}
			else
			{
				recipente.removerObjeto(og);				
				
				inicilizar();
			}
		}
	}
	
	public void tratarNovoPonto(Ponto p)
	{
		if (!isDesenhando())
			iniciarDesenho(p);
		else
			if (isFecharDesenho(p))
				finalizarDesenho(true);
			else
				adicionarVertice(p);
	}
}