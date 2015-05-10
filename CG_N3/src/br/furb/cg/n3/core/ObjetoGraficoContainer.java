package br.furb.cg.n3.core;

import java.util.ArrayList;
import java.util.List;

public class ObjetoGraficoContainer
{
	private List<ObjetoGrafico> objetos;
	
	public ObjetoGraficoContainer()
	{
		this.objetos = new ArrayList<ObjetoGrafico>();
	}
	
	public void adicionarObjeto(ObjetoGrafico o)
	{
		objetos.add(o);
	}

	public void removerObjeto(ObjetoGrafico o)
	{
		objetos.remove(o);
	}
	
	public List<ObjetoGrafico> getObjetos()
	{
		return objetos;
	}
	
	public boolean contem(ObjetoGrafico o)
	{
		return objetos.contains(o);
	}
	
	public boolean temObjetos()
	{
		return objetos.size() > 0;
	}
}