package br.furb.cg.n3.core;

public class Transformador
{
	private ObjetoGrafico objeto;
	
	public Transformador(ObjetoGrafico objeto)
	{
		this.objeto = objeto;
	}
	
	/**
	 * Aplicar a transformação de "Escala" no objeto gráfico
	 * conforme a "Proporção" desejada. Essa proporção será
	 * aplicada nos eixos x e y em relação ao centro do 
	 * objeto gráfico.
	 * 
	 * @param proporcao A proporção da escala
	 */
	public void escalar(double proporcao)
	{
		double Sx = proporcao;
		double Sy = proporcao;
		
		Transformacao escala = new Transformacao();
		
		escala.MakeScale(Sx, Sy, 1.0);
		
		aplicarTransformacao(escala, true);
	}
	
	/**
	 * Aplicar a tranformação de "Translação" no objeto gráfico
	 * adicionando os valores de x e y à posição atual do objeto.
	 * 
	 * @param x Valor para translação no eixo X
	 * @param y Valor para translação no eixo Y
	 */
	public void transladar(double x, double y)
	{	
		Transformacao translacao = new Transformacao();
		
		translacao.MakeTranslation(new Ponto(x, y));
		
		aplicarTransformacao(translacao, false);
	}
	
	/**
	 * Aplicar a transformação de "Rotação" no objeto gráfico
	 * conforme a quantidade de "graus" desejada. A rotação
	 * do objeto será aplicado em relação ao centro do 
	 * objeto gráfico.
	 * 
	 * @param graus 
	 * 		Valor em graus para rotação. Se esse valor for 
	 * 		negativo a rotação será feita para o lado esquerdo
	 * 		do objeto gráfico, caso contrário, para o lado
	 * 		direito do objeto gráfico.
	 */
	public void rotacionar(double graus)
	{		
		Transformacao rotacao = new Transformacao();
		
		rotacao.MakeZRotation(Transformacao.RAS_DEG_TO_RAD * graus);
		
		aplicarTransformacao(rotacao, true);
	}
	
	/**
	 * Aplica a transformação no objeto gráfico.
	 * 
	 * @param transformacao 
	 * 		Transformação a ser aplicada no objeto gráfico
	 * 
	 * @param usaPontoFixo 
	 * 		Define se a transformação será aplicada 
	 * 		com relação ao centro do objeto ou com
	 * 		relação ao centro do ambiente gráfico.
	 */
	private void aplicarTransformacao(Transformacao transformacao, boolean usaPontoFixo)
	{
		Transformacao matrizGlobal = new Transformacao();

		if (usaPontoFixo)
		{
			Ponto pMedio = objeto.getBbox().getPontoMedio();
			Ponto pFixo = new Ponto(-pMedio.getX(), -pMedio.getY());
			
			Transformacao matrizPontoFixo = new Transformacao();
			Transformacao matrizInversa = new Transformacao();
			
			matrizPontoFixo.MakeTranslation(pFixo);
			matrizInversa.MakeTranslation(pMedio);
			
			matrizGlobal = matrizPontoFixo.transformMatrix(matrizGlobal);			
			matrizGlobal = transformacao.transformMatrix(matrizGlobal);					
			matrizGlobal = matrizInversa.transformMatrix(matrizGlobal);			
		}
		else
			matrizGlobal = transformacao.transformMatrix(matrizGlobal);
		
		Transformacao matrizObjeto = objeto.getMatrizObjeto();		
		Transformacao matrizAuxiliar = matrizObjeto.transformMatrix(matrizGlobal);
		
		matrizObjeto.setData(matrizAuxiliar.getData());
	}
}
