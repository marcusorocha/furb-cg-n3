package br.furb.cg.n3.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.swing.JColorChooser;

import br.furb.cg.n3.core.Desenhador;
import br.furb.cg.n3.core.Mundo;
import br.furb.cg.n3.core.ObjetoGrafico;
import br.furb.cg.n3.core.Ponto;
import br.furb.cg.n3.core.RecipienteObjetosGraficos;

public class AmbienteGrafico extends EventosAdapter
{
	private GL gl;
	private GLU glu;

	private GLAutoDrawable glDrawable;

	private Component owner;
	private Mundo mundo;
	private Estado estado;
	private Desenhador desenhador;
	private ObjetoGrafico selecionado;
	
	public AmbienteGrafico(Component owner)
	{
		this.owner = owner;
		this.estado = Estado.SELECAO;
	}

	public void init(GLAutoDrawable drawable)
	{
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		mundo = new Mundo();
		desenhador = new Desenhador();
		selecionado = null;
	}

	public void display(GLAutoDrawable arg0)
	{
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		
		mundo.posicionaCamera(gl, glu);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		mundo.desenhaSRU(gl, glu);
		mundo.desenhaObjetos(gl, glu);

		gl.glFlush();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{		
		executarOperacao(Operacao.getOperacaoDeTeclado(e));
		
		redesenhar();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{	
		Ponto p = getPontoDeEventoMouse(e);
		
		p.exibirCoordenadas();
		
		switch (estado)
		{
			case DESENHO :
			{	
				desenhador.setRecipente(getRecipienteEmFoco());
				desenhador.tratarNovoPonto(p);
				break;
			}
			
			case SELECAO :
			{				
				ObjetoGrafico selecao = mundo.localizarObjeto(p);
				
				if (selecionado != null)
					if (!selecionado.equals(selecao))					
						selecionado.limparSelecao();				
				
				selecionado = selecao;
				
				if (selecionado != null)
				{
					selecionado.selecionar();		
					selecionado.pressionouMouse(p);					
				}
				
				break;
			}
		}
		
		redesenhar();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (selecionado != null)
		{
			selecionado.liberouMouse(getPontoDeEventoMouse(e));
			redesenhar();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (selecionado != null)
		{
			selecionado.arrastouMouse(getPontoDeEventoMouse(e));
			redesenhar();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (desenhador != null)
		{
			if (desenhador.isDesenhando())
			{
				Ponto p = getPontoDeEventoMouse(e);
				
				desenhador.atualizarUltimoVertice(p);
				
				redesenhar();
			}
		}
	}
	
	private RecipienteObjetosGraficos getRecipienteEmFoco()
	{
		if (selecionado != null)
			return selecionado;
		
		return mundo;
	}
	
	private Ponto getPontoDeEventoMouse(MouseEvent e)
	{
		int x = (e.getX() - 200) * +2;
		int y = (e.getY() - 200) * -2;
		
		return new Ponto(x, y);
	}
	
	private void atualizarCursor()
	{
		Cursor c = Cursor.getDefaultCursor();
		
		switch (estado)
		{
			case DESENHO : c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR); break;
			case SELECAO : c = Cursor.getDefaultCursor();
		}
		
		owner.setCursor(c);
	}
	
	private void redesenhar()
	{
		glDrawable.display();
	}
	
	private void executarOperacao(Operacao operacao)
	{
		switch (operacao)
		{	
			case NENHUM :
				return;
			
			case DESENHAR :
				estado = Estado.DESENHO;
				atualizarCursor();
				break;
			
			case APROXIMAR_CAMERA :
				mundo.getCamera().aproximar();
				break;
				
			case AFASTAR_CAMERA :
				mundo.getCamera().afastar();
				break;
				
			case MOVER_CAMERA_ESQUERDA :
				mundo.getCamera().esquerda();
				break;
				
			case MOVER_CAMERA_DIRETA :
				mundo.getCamera().direita();
				break;
				
			case MOVER_CAMERA_ACIMA :
				mundo.getCamera().acima();
				break;
				
			case MOVER_CAMERA_ABAIXO :
				mundo.getCamera().abaixo();
				break;
			
			default : break;
		}
		
		switch (estado)
		{
			case DESENHO :
			{
				switch (operacao)
				{
					case REMOVER :
						desenhador.removerUltimoVertice();
						break;
					
					case CANCELAR :
						desenhador.finalizarDesenho(false);
						estado = Estado.SELECAO;
						atualizarCursor();
						break;
					
					default : break;
				}
				break;
			}
			case SELECAO :
			{
				if (selecionado != null)
				{
					switch (operacao)
					{
						case REMOVER :
							if (!selecionado.removerVerticeSelecionado())
							{
								RecipienteObjetosGraficos roc = mundo.getRecipiente(selecionado);
								roc.removerObjeto(selecionado);							
								selecionado = null;
							}
							break;
						
						case CANCELAR :							
							selecionado.limparSelecao();
							selecionado = null;
							break;
							
						case COLORIR :
							Color novaCor = JColorChooser.showDialog(owner, "Escolha uma cor para o polígono", Color.BLACK);							
							selecionado.setCor(novaCor);
							break;

						case AMPLIAR :
							selecionado.escalar(2.0);
							break;
						
						case REDUZIR :
							selecionado.escalar(0.5);
							break;
						
						case MOVER_DIREITA :
							selecionado.transladar(+5, 0);
							break;
						
						case MOVER_ESQUERDA :
							selecionado.transladar(-5, 0);
							break;
						
						case MOVER_ACIMA :
							selecionado.transladar(0, +5);
							break;
						
						case MOVER_ABAIXO :
							selecionado.transladar(0, -5);
							break;
						
						case GIRAR_DIREITA :
							selecionado.rotacionar(-5);
							break;
						
						case GIRAR_ESQUERDA :						
							selecionado.rotacionar(+5);
							break;
						
						case IMPRIMIR_MATRIZ :						
							selecionado.exibeMatrizTransformacao();
							break;
						
						case IMPRIMIR_VERTICES :
							selecionado.exibeVertices();
							break;
						
						default : break;
					}
				}
				break;
			}
		}
	}
}
