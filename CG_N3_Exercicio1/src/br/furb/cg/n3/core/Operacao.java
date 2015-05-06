package br.furb.cg.n3.core;

import java.awt.event.KeyEvent;

public enum Operacao
{
	// Transformações de objeto
	MOVER_DIREITA,
	MOVER_ESQUERDA,
	MOVER_ACIMA,
	MOVER_ABAIXO,	
	AMPLIAR,
	REDUZIR,	
	GIRAR_DIREITA,
	GIRAR_ESQUERDA,
	
	// Transformações de camera
	APROXIMAR_CAMERA,
	AFASTAR_CAMERA,
	MOVER_CAMERA_ESQUERDA,
	MOVER_CAMERA_DIRETA,
	MOVER_CAMERA_ABAIXO,
	MOVER_CAMERA_ACIMA,
	
	// Genérico
	DESENHAR,
	CANCELAR,
	REMOVER,
	COLORIR,
	
	IMPRIMIR_VERTICES,
	IMPRIMIR_MATRIZ,
	
	NENHUM;
	
	
	public static Operacao getOperacaoDeTeclado(KeyEvent e)
	{
		if (e.isShiftDown())
		{
			switch(e.getKeyCode())
			{				
				case KeyEvent.VK_LEFT  : return GIRAR_ESQUERDA;
				case KeyEvent.VK_RIGHT : return GIRAR_DIREITA;
				case KeyEvent.VK_D     : return DESENHAR;
				case KeyEvent.VK_C     : return COLORIR;
			}
		}
		else
		{		
			switch(e.getKeyCode())
			{				
				case KeyEvent.VK_M: return IMPRIMIR_MATRIZ;
				case KeyEvent.VK_V: return IMPRIMIR_VERTICES;					
				case KeyEvent.VK_PAGE_DOWN : return REDUZIR;
				case KeyEvent.VK_PAGE_UP : return AMPLIAR;					
				case KeyEvent.VK_LEFT : return MOVER_ESQUERDA;							
				case KeyEvent.VK_RIGHT : return MOVER_DIREITA;					
				case KeyEvent.VK_UP : return MOVER_ACIMA;
				case KeyEvent.VK_DOWN : return MOVER_ABAIXO;					
				case KeyEvent.VK_ESCAPE : return CANCELAR;
				case KeyEvent.VK_BACK_SPACE :
				case KeyEvent.VK_DELETE : return REMOVER;			
			}
		}
		
		return NENHUM;
	}
}