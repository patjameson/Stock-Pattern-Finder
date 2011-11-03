package patternfinder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Tester {
	public static void main(String[] args) {
		try {
			BufferedReader file = new BufferedReader(new FileReader("/home/p/Documents/code/java/stocks/stockstuff/src/data.csv"));
			
			String line = null;
	        try {
	        	ArrayList<String> datesList = new ArrayList<String>();
	        	ArrayList<Double> closePricesList = new ArrayList<Double>();
	        	
	        	file.readLine();
				while ((line = file.readLine()) != null) {
					String[] csv = line.split(",");
					datesList.add(csv[0]);
					closePricesList.add(Double.parseDouble(csv[4]));
				}
				
				String[] dates = new String[datesList.size()];
				double[] closePrices = new double[closePricesList.size()];
				
				for(int i = 0;i < datesList.size();i++) {
					dates[i] = datesList.get(datesList.size() - i - 1);
					closePrices[i] = closePricesList.get(datesList.size() - i - 1);
				}
				
				new StockGrapher(dates, closePrices);
			} catch (IOException e) {
				System.out.println("IO Exception");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		
		
	}
}
