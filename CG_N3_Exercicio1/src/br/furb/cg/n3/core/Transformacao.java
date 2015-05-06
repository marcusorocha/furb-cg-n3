package br.furb.cg.n3.core;

public final class Transformacao 
{
	static final double RAS_DEG_TO_RAD = 0.017453292519943295769236907684886;

	private double[] matrix = 
		{	
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1
		};

	public Transformacao() 
	{
		// Construtor vazio
	}

	public void MakeIdentity() 
	{
		for (int i = 0; i < 16; ++i)
			matrix[i] = 0.0;
	
		matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
	}

	public void MakeTranslation(Ponto translationVector)
	{
	    MakeIdentity();
	    matrix[12] = translationVector.getX();
	    matrix[13] = translationVector.getY();
	    matrix[14] = translationVector.getZ();
	}

	public void MakeXRotation(double radians)
	{
	    MakeIdentity();
	    matrix[5] =   Math.cos(radians);
	    matrix[9] =  -Math.sin(radians);
	    matrix[6] =   Math.sin(radians);
	    matrix[10] =  Math.cos(radians);
	}

	public void MakeYRotation(double radians)
	{
	    MakeIdentity();
	    matrix[0] =   Math.cos(radians);
	    matrix[8] =   Math.sin(radians);
	    matrix[2] =  -Math.sin(radians);
	    matrix[10] =  Math.cos(radians);
	}

	public void MakeZRotation(double radians)
	{
	    MakeIdentity();
	    matrix[0] =  Math.cos(radians);
	    matrix[4] = -Math.sin(radians);
	    matrix[1] =  Math.sin(radians);
	    matrix[5] =  Math.cos(radians);
	}

	public void MakeScale(double sX, double sY, double sZ)
	{
	    MakeIdentity();
	    matrix[0] =  sX;
	    matrix[5] =  sY;
	    matrix[10] = sZ;
	}
	
	public Ponto transformPoint(Ponto p) 
	{
		double x = (matrix[0] * p.getX()) + (matrix[4] * p.getY()) + (matrix[8] * p.getZ()) + (matrix[12] * p.getW());
		double y = (matrix[1] * p.getX()) + (matrix[5] * p.getY()) + (matrix[9] * p.getZ()) + (matrix[13] * p.getW()); 
		double z = (matrix[2] * p.getX()) + (matrix[6] * p.getY()) + (matrix[10] * p.getZ()) + (matrix[14] * p.getW()); 
		double w = (matrix[3] * p.getX()) + (matrix[7] * p.getY()) + (matrix[11] * p.getZ()) + (matrix[15] * p.getW()); 
		
		return new Ponto(x, y, z, w);
	}

	public Transformacao transformMatrix(Transformacao t)
	{
		Transformacao result = new Transformacao();
		
	    for (int i = 0; i < 16; ++i)
	        result.matrix[i] = matrix[i%4] * t.matrix[i/4*4] + 
	        				   matrix[(i%4)+4] * t.matrix[i/4*4+1] + 
	        				   matrix[(i%4)+8] * t.matrix[i/4*4+2] + 
	        				   matrix[(i%4)+12] * t.matrix[i/4*4+3];
		
	    return result;
	}
	
	public double getElement(int index)
	{
		return matrix[index];
	}
	
	public void setElement(int index, double value) 
	{
		matrix[index] = value;
	}

	public double[] getData() 
	{
		return matrix;
	}
	
	public void setData(double[] data)
	{
	    for (int i = 0; i < 16; i++)
	        matrix[i] = (data[i]);
	}
	
	public boolean isIdentity()
	{
		return matrix[0] == 1.0 && matrix[4] == 0.0 && matrix[8] == 0.0 && matrix[12] == 0.0 && 
			   matrix[1] == 0.0 && matrix[5] == 1.0 && matrix[9] == 0.0 && matrix[13] == 0.0 &&
			   matrix[2] == 0.0 && matrix[6] == 0.0 && matrix[10] == 1.0 && matrix[14] == 0.0 &&
			   matrix[3] == 0.0 && matrix[7] == 0.0 && matrix[11] == 0.0 && matrix[15] == 1.0;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (!(obj instanceof Transformacao))
			return false;
		
		Transformacao t = (Transformacao)obj;
		
		return t.getData().equals(getData());
	}
	
	public void exibeMatriz()
	{
		System.out.println("______________________");
		System.out.println("|" + getElement(0) + " | "+ getElement(4) + " | " + getElement(8) + " | "+ getElement(12));
		System.out.println("|" + getElement(1) + " | "+ getElement(5) + " | " + getElement(9) + " | "+ getElement(13));
		System.out.println("|" + getElement(2) + " | "+ getElement(6) + " | " + getElement(10) + " | "+ getElement(14));
		System.out.println("|" + getElement(3) + " | "+ getElement(7) + " | " + getElement(11) + " | "+ getElement(15));
		System.out.println("______________________");
	}

}
