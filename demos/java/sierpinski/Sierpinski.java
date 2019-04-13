/**
*
*	@author Michael Leonffu
*	@version December 9, 2018
*
*	Sierpinski: Draws Sierpinski triangles using math formula (explained below)
*		Sierpinski's triangle is a trangle with triangles inside of it recursively!
*
*	constants
*		window width and height
*
*	instances
*		Point[] nodes, and Point current;
*			nodes are the 3 anchore points used in the math (randomly) the vetices of the triangle
*			current is the current position of the cursor which is placing dots to draw
*
*	constcutor
*		misc
*			name the window, set its size, and close opteration, set visable
*		nodes
*			generate 3 nodes with the dimentions of an equaliateral triangle using the window width and height
*				the height of the triangle should be its width/2 * 3^.5
*				therefore 2 nodes are placed at x = 0 y = 0 and the other at x = width y = 0;
*				the thrid node, to make it ealaterial; is placted at x = half of width and y = h
*			order doesnt matter
*			there is also a shift introducted as shidt+(value) where shrift can be anything
*		instance current by setting cuttent to be that of any of the 3 nodes
*		after all the misc is done, make a loop to repaint() which calls paint
*
*	paint (places at point at the current position)
*		randomize the position of current
*		then copy the psoition of cutent into a temp Point obkect
*		set color to black
*		draw a line that is 0 long at the point provided by the temp point object
*
*	randomize (moves the current position to a new positoin somwhat randomly but matmathmically)
*		randomyly select a node using mathrandom
*		then copy the psoition of cutent into a temp Point obkect
*		move the current poiion to be halfway between the temp point obkect and itself
*			do this using translate
*			find the X differnce and the Y differnce as temp.n - current.n
*			then translate by that delta
*
*	MATH (given in the book)
*		Supposeitly you can generate this triangle confirguation using simple maths
*			pick a point to start on of the nodes
*			then pick another point of the nodes
*			move the current position to be that halfway between the selected node
*			place a dot there and loop that with another ranomd node (keeping the same new position as the stiarting postion)
*
*	main
*		instancisates the object
*
*/

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Sierpinski extends JFrame{

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 800;

	private Point[] nodes;
	private Point current;

	public Sierpinski(){
		super("Sierpinski"); //setting name of window

		// this.nodes = new Point[]{
		// 	new Point(300+this.WINDOW_WIDTH/2	, 150+(int)(this.WINDOW_WIDTH/2.0 * Math.pow(3, 0.5))),
		// 	new Point(300+this.WINDOW_WIDTH		, 150+0),
		// 	new Point(300+0						, 150+0)
		// };

		//Other cool shapes
		// this.nodes = new Point[]{
		// 	new Point(this.WINDOW_WIDTH		, this.WINDOW_HEIGHT),
		// 	new Point(this.WINDOW_WIDTH		, 0),
		// 	new Point(0						, this.WINDOW_HEIGHT),
		// 	new Point(0						, 0)
		// };

		//1500 1500
		this.nodes = new Point[]{
			new Point(0,666),
			new Point(484,1332),
			new Point(1266,1077),
			new Point(1266,255),
			new Point(484,0)
		};

		//to 800 800
		this.nodes = new Point[]{
			new Point(0*800/1500,666*800/1500),
			new Point(484*800/1500,1332*800/1500),
			new Point(1266*800/1500,1077*800/1500),
			new Point(1266*800/1500,255*800/1500),
			new Point(484*800/1500,0*800/1500)
		};

		//currecnt starts on first node (no random)
		this.current = new Point(nodes[0]);

		//misc
		this.setSize(Sierpinski.WINDOW_WIDTH, Sierpinski.WINDOW_HEIGHT);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		setVisible(true);

		for(int i = 0; i < Integer.MAX_VALUE; i++)
			repaint();
	}

	private void randomlizeCurrent(){
		Point random = this.nodes[(int)(Math.random()*this.nodes.length)];
		Point at = new Point(this.current);
		this.current.translate((random.x - at.x)/2, (random.y - at.y)/2);
	}

	public void paint(Graphics g){
		this.randomlizeCurrent();
		Point at = new Point(this.current);
		g.setColor(Color.black);
		g.drawLine(at.x, this.WINDOW_HEIGHT-at.y, at.x, this.WINDOW_HEIGHT-at.y);
	}

	public static void main(String[] args){
		new Sierpinski();
	}
}