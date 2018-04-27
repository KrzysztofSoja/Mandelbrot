package mandelbrot;

/**
*
* @author Krzysztof Soja
*/


import java.lang.Math.*;
import java.lang.String.*;

public class Complex // clasa ma być publiczna
{
	private double r, i;



    public Complex()
	{
		r = 0;
		i = 0;
	}
	
    public Complex(double r )
	{
		this.r = r; 
		this.i = 0;
	}
    public Complex(double r , double i)
	{
		this.r = r;
		this.i = i;
	}
    public Complex(Complex c)
	{
		this.r = c.r;
		this.i = c.i;
	}
    public Complex( String s ) throws NumberFormatException , ArrayIndexOutOfBoundsException
	{
		
		try
		{
			s = s.substring(0, s.length()-1);
			String r = new String();;
			if (s.charAt(0) == '-'){
				r = "-";
				s = s.substring(1);
			}
			else{
				r = "";
			}

			String parts[] = new String[2];
			if(s.split("\\+")[0] == s){
				parts = s.split("\\-");
				parts[1] = "-" + parts[1];
			}
			else{   
				parts = s.split("\\+");
			}
			parts[0] = r + parts[0];
			Complex n = new Complex();
			this.r = Double.parseDouble(parts[0]);
			this.i = Double.parseDouble(parts[1]);
			
		}
		catch ( NumberFormatException | ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Podana liczba zespolona w złym formacie.");
			throw e;
		}
	}
	
	
	
	
	
    public static Complex add(Complex a , Complex b )
	/* Zwraca sume dwóch liczb zespolonych */
	{
		Complex c = new Complex ( a.r + b.r, a.i + b.i );
		return c;
	}
	
	
    public static Complex sub(Complex a , Complex b) 
    /* Zwraca różnice dwóch liczb zespolonych */
	{
		Complex c = new Complex ( a.r - b.r, a.i - b.i );
		return c;
	}
	
	
    public static Complex mul(Complex a , Complex b )
    /* Zwraca iloczyn dwóch liczb zespolonych */
	{
		double r = (( a.r * b.r ) - ( a.i * b.i ));
		double i  = (( a.r * b.i ) + ( b.r * a.i ));
		
		Complex c = new Complex( r, i );
		
		return c;
	}
	
	
    public static Complex div(Complex a, Complex b)
    /* Zwraca iloraz dwóch liczb zespolonych */
	{
		double dzielnik = b.r * b.r + b.i * b.i;
		double r = ( ( a.r * b.r ) + ( a.i * b.i ) )/dzielnik;
		double i = ( ( a.i * b.r ) - ( b.i * a.r ) )/dzielnik;
		
		Complex c = new Complex( r, i );
		
		return c;
	}
	
	
    public static double abs(Complex z )    
    /* Zwraca moduł liczby zespolonej */ 
	{
		return Math.sqrt( z.r * z.r + z.i * z.i );
	}
    public static double phase(Complex z )        
    /* Zwraca faze liczby zespolonej */
	{
		if ( z.r == 0 && z.i == 0 )
		{
			System.out.println("Faza nieokreslona.");
			return -1;
		}
		else if ( z.r == 0 && z.i != 0 ) 
		{
			if ( z.i > 0 ) return 0.5 * Math.PI;
			else return -0.5 * Math.PI;	
		}
		else if ( z.r != 0 && z.i == 0 )
		{
			if ( z.r > 0 ) return 0;
			else return Math.PI;
		}
		else // z.r != 0 && z.i != 0
		{
			if ( z.r > 0 ) return Math.atan( z.i/z.r);
			else return Math.atan( z.i/z.r ) + Math.PI;
		}
	}
	
	
    public static double sqrAbs( Complex z )
    /* Zwraca kwadrat modułu liczby zespolonej */
	{
		return Math.pow( z.abs(), 2 );
	}	
	
	
    public static double re(Complex z ){ return z.r; }
    /* Zwraca część rzeczywistą liczby zespolonej */    
    public static double im(Complex z ) { return z.i; }
    /* Zwraca część urojoną liczby zespolonej */    

	
	
    /* Poniższe metody modyfikuja aktualny obiekt i zwracają 'this' */

    public Complex add(Complex z )   // Dodaje liczbę zespoloną
	{
		this.i += z.i;
		this.r += z.r;
		return this;
	}
    public Complex sub(Complex z)   // Odejmuje liczbę zespoloną
	{
		this.i -= z.i;
		this.r -= z.r;
		return this;
	}
    public Complex mul(Complex z)    // Mnoży przez liczbę zespoloną
	{
		double tempr = (( this.r * z.r ) - ( this.i * z.i ));
		
		this.i = (( this.r * z.i ) + ( z.r * this.i ));
		this.r = tempr;
		
		return this;
	}
    public Complex div(Complex z)   // Dzieli przez liczbę zespoloną
	{
		double dzielnik = z.r * z.r + z.i * z.i;
		double tempr = ( ( this.r * z.r ) + ( this.i * z.i ) )/dzielnik;
		
		this.i = ( ( this.i * z.r ) - ( z.i * this.r ) )/dzielnik;
		this.r = tempr;
		
		return this;
	}
	
	
	
	
    public double abs()
	{
		return Math.sqrt( r*r + i*i );
	}
	
	
    public double sqrAbs()
	{
		return Math.pow( this.abs(), 2 );
	}
	
	
    public double re() { return r; }
    public double im()	{ return i; }
	
    
    public String toString()							
    /* Zwraca String z zapisaną 
        liczbą zespoloną formacie "-1.23+4.56i" */
	{
		return ("" + r + " + " + i + "i");
	}

    public static Complex valueOf(String strZ) throws NumberFormatException , ArrayIndexOutOfBoundsException
    /* Zwraca liczbę zespolona o wartości podanej 
        w argumencie w formacie "-1.23+4.56i" */
	{
		return new Complex( strZ );
	}

    public void setRe(double r )
    /* Przypisuje podaną wartość części rzeczywistej */
	{
		this.r = r;
	}

    public void setIm(double i )
    /* Przypisuje podaną wartość części urojonej */
	{
		this.i = i;
	}

    public void setVal(Complex z)
	{
		this.r = z.r;
		this.i = z.i;
	}
    public void setVal(double r, double i)
    /* Przypisuje podaną wartość */
	{
		this.r = r;
		this.i = i;
	}
}