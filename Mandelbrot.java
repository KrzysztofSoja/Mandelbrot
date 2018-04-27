import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.lang.Math;

import mandelbrot.*;


public class Mandelbrot extends JPanel implements MouseListener, MouseMotionListener
{
	
	int x, y, x2, y2;
	boolean wsroduku;
	
	double r2 = 4; 
	
	int wartosciOdlotu[][];
	
	Complex Zakres1;
	Complex Zakres2;
	int imageX;
	int imageY;
	JLabel obrazek;
	
	
	
	Mandelbrot()
	{
		JFrame jfrm = new JFrame("Dodaj dwie liczby.");
		jfrm.setLayout( new FlowLayout() );
		jfrm.setSize( 1000, 700 );
		jfrm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		Zakres1 = new Complex( -2, 1);		// Wartości domyślne
		Zakres2 = new Complex( 1, -1);
		imageX = 900;
		imageY = 600;
		
		obrazek = new JLabel( generujObraz() );
		
		JLabel napisI = new JLabel("Wprowadź rozmiar obrazka(x i y): ");
		JLabel napisC = new JLabel("Wprowadź zakres zbioru: ");
		
		JTextField PoleX = new JTextField( 4 );
		JTextField PoleY = new JTextField( 4 );
		
		JTextField PoleZ1 = new JTextField( 4 );
		JTextField PoleZ2 = new JTextField( 4 );
		
		JButton Resetuj = new JButton("Resetuj");
		
		Resetuj.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent ae )
			{
				try
				{
					imageX = Integer.parseInt( PoleX.getText() );
					imageY = Integer.parseInt( PoleY.getText() );
					
					try
					{
						Zakres1 = Complex.valueOf( PoleZ1.getText() );
						Zakres2 = Complex.valueOf( PoleZ2.getText() );
					}
					catch ( NumberFormatException fe )
					{
						PoleZ1.setText("");
						PoleZ2.setText("");
					}
				}
				catch ( NumberFormatException nfe )	// Wykorzysta wtedy warości, które były wpisane poprzednio.
				{
					PoleX.setText("");
					PoleY.setText("");
				}
				finally
				{
					obrazek.setIcon( generujObraz() );
				}				
			}
		});
		
		
		
		obrazek.addMouseListener(this);
		obrazek.addMouseMotionListener(this);
		
		
		jfrm.add( obrazek );
		jfrm.add( Resetuj );
		jfrm.add( napisI );
		jfrm.add( PoleX );
		jfrm.add( PoleY );
		jfrm.add( napisC );
		jfrm.add( PoleZ1 );
		jfrm.add( PoleZ2 );	
		
		jfrm.setVisible( true );
	}
		
		
		
		
	public void mousePressed(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
    }
    
    public void mouseReleased(MouseEvent e) 
	{
		if ( wsroduku == true )
		{
			x2 = e.getX();
			y2 = e.getY();
			
			if ( x != x2 && y != y2 )
			{	
		
				//double maxRe = Math.max( Zakres1.re(), Zakres2.re() );
				double minRe = Math.min( Zakres1.re(), Zakres2.re() );
				double maxIm = Math.max( Zakres1.im(), Zakres2.im() );
				//double minIm = Math.min( Zakres1.im(), Zakres2.im() );
				
				
				double wartoscSkalowaniaX = (double)Math.abs( Zakres1.re() - Zakres2.re() ) / (double)imageX;
				double wartoscSkalowaniaY = (double)Math.abs( Zakres1.im() - Zakres2.im() ) / (double)imageY;
				
				Zakres1 = new Complex( minRe + ( x * wartoscSkalowaniaX), maxIm - ( y * wartoscSkalowaniaY ) );
				Zakres2 = new Complex( minRe + ( x2 * wartoscSkalowaniaX), maxIm - ( y2 * wartoscSkalowaniaY ) );
					
				obrazek.setIcon( generujObraz() );
			}
			
		}

    }
    
    public void mouseEntered(MouseEvent e) 
	{
		wsroduku = true;
    }
    
    public void mouseExited(MouseEvent e) 
	{
		wsroduku = false;
    }
    
    public void mouseClicked(MouseEvent e) {}
	public void mouseDragged( MouseEvent e ){}
	public void mouseMoved( MouseEvent e ){}
		
		
	ImageIcon generujObraz( )
	{
		
		wartosciOdlotu = new int[imageX][imageY];
		
		//double maxRe = Math.max( Zakres1.re(), Zakres2.re() );
		double minRe = Math.min( Zakres1.re(), Zakres2.re() );
		double maxIm = Math.max( Zakres1.im(), Zakres2.im() );
		//double minIm = Math.min( Zakres1.im(), Zakres2.im() );
		
		
		double wartoscSkalowaniaX = (double)Math.abs( Zakres1.re() - Zakres2.re() ) / (double)imageX;
		double wartoscSkalowaniaY = (double)Math.abs( Zakres1.im() - Zakres2.im() ) / (double)imageY;
		
		
		for ( int i = 0; i < imageX; i ++ )
		{
			for ( int j = 0; j < imageY; j++ )
			{
				Complex Z = new Complex(0,0);
				Complex temp = new Complex( minRe + ( i * wartoscSkalowaniaX), maxIm - ( j * wartoscSkalowaniaY ) );
				
				wartosciOdlotu[i][j] = -1;
				for ( int u = 0; u < 100; u++ )
				{
					Z = ( Z.mul(Z) ).add( temp );
					if (  Z.sqrAbs() >= r2 )	wartosciOdlotu[i][j] = u;
				}
			}
		}
	
		BufferedImage tlo = new BufferedImage( imageX, imageY, BufferedImage.TYPE_INT_RGB );
		
		for ( int i = 0; i < imageX; i++ )
			for ( int j = 0; j < imageY ; j++ )
			{
				if( wartosciOdlotu[i][j] == -1 )
					tlo.setRGB( i, j, Color.BLACK.getRGB() );
				else
					tlo.setRGB( i, j, koloruj( wartosciOdlotu[i][j] ).getRGB() );
			}
			
		return new ImageIcon( tlo );
	}
	
	Color koloruj( int i )		// Wyraz przy którym moduł osiąga wartość 2 lub więcej.
	{
		Color a = new Color(  20 *(int)Math.sqrt(i) , i*2 , (int)(140 - (1.3*i)) );
		return a;
	}
	
	
	public static void main( String args[] )
	{
		SwingUtilities.invokeLater( new Runnable ()
		{
			public void run()
			{
				new Mandelbrot();
			}
		});	
	} 
}