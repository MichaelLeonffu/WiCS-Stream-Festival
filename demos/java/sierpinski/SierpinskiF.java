/**
*
*
*
*
*
*/


import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;


public class Sierpinski extends JFrame{

	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 1200;

	private int time = 0;
	private boolean clear = true;

	private int m = 0;

	private Point[] nodes;
	private Point current;

	public Sierpinski(){
		super("Sierpinski"); //setting name of window

		this.nodes = new Point[]{
			new Point(this.WINDOW_WIDTH/2	, (int)(this.WINDOW_WIDTH/2.0 * Math.pow(3, 0.5))),
			new Point(this.WINDOW_WIDTH		, 0),
			new Point(0						, 0)
		};

		// this.nodes = new Point[]{
		// 	new Point(this.WINDOW_WIDTH		, this.WINDOW_HEIGHT),
		// 	new Point(this.WINDOW_WIDTH		, 0),
		// 	new Point(0						, this.WINDOW_HEIGHT),
		// 	new Point(0						, 0)
		// };

		// this.nodes = new Point[]{
		// 	new Point(0,666),
		// 	new Point(484,1332),
		// 	new Point(1266,1077),
		// 	new Point(1266,255),
		// 	new Point(484,0)
		// };

		//currecnt starts on first node (no random)
		this.current = new Point(nodes[0]);

		//misc
		this.setSize(Sierpinski.WINDOW_WIDTH, Sierpinski.WINDOW_HEIGHT);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		setVisible(true);

		m = 0;
		while(true){
			m+= 1 + m/100;
			for(int i = 0; i < 1000000; i++){
				repaint();
			}
			System.out.println(m + " done");
			this.nodes = new Point[]{
			new Point(this.WINDOW_WIDTH/2	, (int)(this.WINDOW_WIDTH/2.0 * Math.pow(3, 0.5))-m),
			new Point(this.WINDOW_WIDTH		, 0),
			new Point(0						, 0)
		};
			clear = true;
		}
	}

	private void randomlizeCurrent(){
		Point random = this.nodes[(int)(Math.random()*this.nodes.length)];
		Point at = new Point(this.current);
		this.current.translate((random.x - at.x)/2, (random.y - at.y)/2);
	}

	public void paint(Graphics g){
		// if(clear){
		// 	g.setColor(Color.black);
		// 	g.fillRect(0, 0, this.WINDOW_WIDTH, this.WINDOW_HEIGHT); //eh... not elegant but clears.
		// 	clear = false;
		// }
		this.randomlizeCurrent();
		// int x = this.current.x;
		// int y = this.current.y;
		Point at = new Point(this.current);
		// g.drawLine(this.current.x, this.current.y, this.current.x, this.current.y);
		// g.drawLine(x, this.WINDOW_HEIGHT-y, x, this.WINDOW_HEIGHT-y);
		// g.setColor(Color.black);
		g.setColor(new Color(m/5, m/10, m/5));
		g.drawLine(at.x, this.WINDOW_HEIGHT-at.y, at.x, this.WINDOW_HEIGHT-at.y);
	}

	public static void main(String[] args){
		new Sierpinski();
	}
}