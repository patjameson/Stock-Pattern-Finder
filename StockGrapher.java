package patternfinder;
import graph.Graph;

import java.awt.Frame;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StockGrapher extends JFrame {
	public StockGrapher(String[] dates, double[] closePrices) {
		super("Stock Grapher");
		setSize(1000, 600);
		this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Graph graph = new Graph(1000, 600);
		
		double[][] points = new double[closePrices.length][2];
		for(int i = 0;i < closePrices.length;i++) {
			points[i] = new double[]{i, closePrices[i]};
		}
		
		graph.setPoints(points);
		graph.setCompression(10);
		
		graph.setEquations(StockPatternFinder.findBestFitEquations(closePrices));
		
		getContentPane().add(graph);
		
		setVisible(true);
	}
}
