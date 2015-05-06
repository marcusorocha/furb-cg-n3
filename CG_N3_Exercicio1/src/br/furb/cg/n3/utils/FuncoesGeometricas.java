package br.furb.cg.n3.utils;

import java.util.List;

import br.furb.cg.n3.core.Ponto;

public class FuncoesGeometricas
{	
	/**
	 * Função para verificar se o Ponto "p" se encontra no interior do 
	 * polígono formado pela lista de vértices "vertices"
	 * 
	 * @param vertices
	 * @param p
	 * 
	 * @return 
	 * 		Verdadeiro caso do ponto "p" esteja dentro do polígono e 
	 * 		Falso no caso contrário 
	 */
	public static boolean pontoEmPoligono(List<Ponto> vertices, Ponto p)
	{
		int nInt = 0;
		int nVertices = vertices.size();
		
		for (int iA = 0; iA < nVertices; iA++)
		{
			int iB = (iA + 1) % nVertices;
			
			Ponto pA = vertices.get(iA);
			Ponto pB = vertices.get(iB);
			
			if (pA.getY() != pB.getY())
			{
				Ponto pInt = pontoDeInterseccao(pA, pB, p.getY()); 
				if (pInt != null)
				{
					if (pInt.getX() >= p.getX() && 
						pInt.getY() >= Math.min(pA.getY(), pB.getY()) && 
						pInt.getY() <= Math.max(pA.getY(), pB.getY()))
					{
						nInt += 1;
					}
				}
			}
		}
		
		return !((nInt % 2) == 0);
	}
	
	/**
	 * Calcular o ponto de intersecção entre a coordenada y e 
	 * a reta formada pelos pontos p1 (x, y) e p2 (x, y).
	 * 
	 * @param p1
	 * @param p2
	 * @param y
	 * 
	 * @return
	 * 		O ponto (x, y) de intersecção caso essa intersecção exista
	 */
	public static Ponto pontoDeInterseccao(Ponto p1, Ponto p2, double y)
	{		
		double ti = (y - p1.getY()) / (p2.getY() - p1.getY());
		
		if (ti >= 0.0 && ti <= 1.0)
		{
			double x = p1.getX() + ((p2.getX() - p1.getX()) * ti);
			
			return new Ponto(x, y);
		}
				
		return null;
	}

}
