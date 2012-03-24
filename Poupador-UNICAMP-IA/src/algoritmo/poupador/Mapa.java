package algoritmo;

import java.awt.Point;
import java.util.ArrayList;


import controle.Constantes;

public class Mapa {

	public class Casa {
		public int cheiro;
		public int conteudo;
	}

	
	private Casa _casas[][];
	private int _width, _height;
	private Point _banco = null;
	private ArrayList<ArrayList<Point>> _obstaculos = new ArrayList<ArrayList<Point>>();
	
	public Mapa(int w, int h)
	{
		_width = w;
		_height = h;
		_casas = new Casa[w][h];
		for(int i = 0; i < w; i++)
			for(int j = 0; j < h; j++)
			{
				_casas[i][j].cheiro=0;
				_casas[i][j].conteudo=-1;
			}
	}
	
	public void Setconteudo(int x, int y, int obj)
	{
		if (obj != Constantes.semVisao)
			_casas[x][y].conteudo = obj;
	}
	
	public int Getconteudo(int x, int y)
	{
		return _casas[x][y].conteudo;
	}
	
	public void SetBanco(Point banco)
	{
		_banco = banco;
	}
	
	public void Updatecheiro()
	{
		for(int w = 0; w < _width; w++)
			for(int h = 0; h < _height; h++)
				if(_casas[w][h].cheiro>0)
					_casas[w][h].cheiro--;
	}
	
	private static Mapa _instance = null;
	
	public static Mapa GetInstance()
	{
		if(_instance==null)_instance= new Mapa(30,30);
		return _instance;
	}
	
	public void Update(Poupador poupador) {
		Point p = poupador.sensor.getPosicao();
		
		for (int i = -2; i < 0; i++) {
			for (int j = -2; j <= 2; j++) {
				Setconteudo(p.y + i, p.x + j, poupador.sensor.getVisaoIdentificacao()[(2+i)*5 + (2 + j)]);
			}
		}
		Setconteudo(p.y, p.x - 2, poupador.sensor.getVisaoIdentificacao()[10]);
		Setconteudo(p.y, p.x - 1, poupador.sensor.getVisaoIdentificacao()[11]);
		Setconteudo(p.y, p.x, Constantes.posicaoLivre);
		Setconteudo(p.y, p.x + 1, poupador.sensor.getVisaoIdentificacao()[12]);
		Setconteudo(p.y, p.x + 2, poupador.sensor.getVisaoIdentificacao()[13]);
		for (int i = 1; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				Setconteudo(p.y + i, p.x + j, poupador.sensor.getVisaoIdentificacao()[(2+i)*5 + (1 + j)]);
			}
		}
		
	}
	
	public Point ObtemAlvoFuga(Poupador poupador)
	{
		if(_banco!=null)return _banco;
		return poupador.sensor.getPosicao();
	}
	
	public int ContornaObstaculo(Point source, ArrayList<Point> obstaculo, ArrayList<Point> ladroes)
	{
		int proxLadrao = -1;
		int nLadrao = 0;
		for(int i = 0; i < ladroes.size(); i++)
		{
			int novo = HeuristicaDist(source, ladroes.get(i));
			if(proxLadrao==-1 ||novo<proxLadrao) 
			{
				proxLadrao = novo;
				nLadrao = i;
			}
		}
		int dist = 0;
		Point destino = null;
		for(int i = 0; i < obstaculo.size(); i++)
		{
			int novo = HeuristicaDist(obstaculo.get(i), ladroes.get(nLadrao));
			if(novo>dist)
			{
				dist=novo;
				destino = obstaculo.get(i);
			}
		}
		return AEstrela(source, destino);
	}
	
	public int AEstrela(Point source, Point target)
	{
		return Console.readInteger();
	}
	
	public int HeuristicaDist(Point source, Point target)
	{
		return Math.abs(source.x-target.x)+Math.abs(source.y-target.y);
	}
}
