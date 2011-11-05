package patternfinder;
/**
 * Finds patterns in stock market feeds.
 * 
 * @author Patrick Jameson
 */
public class StockPatternFinder {
	private static final int INTERVAL_LENGTH = 14;
	/**
	 * Given an array of the close prices, return the equations and the intervals which they are
	 * relevant.
	 * 
	 * Example return value: [[3, 4, 0, 5], [4, 7.25, 5, 7]]. This means that the first best fit
	 * equation would be y = 3x + 4 for the interval [0, 5] and the second equation would be 
	 * y = 4x + 7.25 for the interval [5, 7]
	 * 
	 * @param closePrices and array
	 * @return a two dimensional array in the format of [[m, b, start interval, end interval], etc.]
	 */
	public static double[][] findBestFitEquations(double[] closePrices) {
		double[][] bestFitEquations = new double[closePrices.length+1][4];
		
		double[] start = {-1, 0}, end = {-1, 0};
		
		//calculate the required sums for the slope and y-intercept equations
		for (int i = 1;i < closePrices.length - INTERVAL_LENGTH + 1;i++) {
			double sumX = 0, sumY = 0, sumXY = 0, sumXsquared = 0;
			for (int j = i;j < i + INTERVAL_LENGTH + 1;j++) {
				sumX += j;
				sumY += closePrices[j - 1];
				sumXY += j*closePrices[j - 1];
				sumXsquared += j*j;
			}
			double curM = (((INTERVAL_LENGTH+1) * sumXY) - (sumX * sumY)) / (((INTERVAL_LENGTH+1) * sumXsquared) - sumX*sumX);
			double curB = (sumY - (curM * sumX)) / (INTERVAL_LENGTH+1);
			if (start[0] == -1) {
				start = new double[]{i, curM};
			}
			
			boolean negative = (start[1] < 0);
			
			if (negative && curM > 0 || !negative && curM < 0) {
				if (Math.abs(curM) >= Math.abs(end[1])) {
					end = new double[]{i, curM};
				} else {
					sumX = 0;
					sumY = 0;
					sumXY = 0;
					sumXsquared = 0;
					for (int j = (int)start[0];j < end[0] + 1;j++) {
						sumX += j;
						sumY += closePrices[j - 1];
						sumXY += j*closePrices[j - 1];
						sumXsquared += j*j;
					}
					curM = (((end[0]-start[0]+1) * sumXY) - (sumX * sumY)) / (((end[0]-start[0]+1) * sumXsquared) - sumX*sumX);
					curB = (sumY - (curM * sumX)) / (end[0]-start[0]+1);
					
					double sumXYMean = 0, sumXYSquaredMeanX = 0, sumXYSquaredMeanY = 0;
					for (int j = (int)start[0];j < end[0] + 1;j++) {
						double XiX = j - sumX/(end[0]-start[0] + 1);
						double YiY = closePrices[j - 1] - sumY/(end[0]-start[0] + 1);
						sumXYMean += XiX*YiY;
						sumXYSquaredMeanX += XiX*XiX;
						sumXYSquaredMeanY += YiY*YiY; 
					}
					System.out.println("r = " + sumXYMean/(Math.sqrt(sumXYSquaredMeanX)*Math.sqrt(sumXYSquaredMeanY)));
					
					//find the slope ( m )
					bestFitEquations[i - 1][0] = curM;
				
					//find the y-intercept ( b )
					bestFitEquations[i - 1][1] = curB;
					
					bestFitEquations[i - 1][2] = start[0]-1;
					bestFitEquations[i - 1][3] = end[0]-1;
					
					start = end;
					end = new double[]{-1, 0};
				}
			}
		}
		double sumX = 0, sumY = 0, sumXY = 0, sumXsquared = 0;
		for (int j = (int)start[0];j < closePrices.length;j++) {
			sumX += j;
			sumY += closePrices[j - 1];
			sumXY += j*closePrices[j - 1];
			sumXsquared += j*j;
		}
		double curM = (((closePrices.length-start[0]+1) * sumXY) - (sumX * sumY)) / (((closePrices.length-start[0]+1) * sumXsquared) - sumX*sumX);
		double curB = (sumY - (curM * sumX)) / (closePrices.length-start[0]+1);
		
		//find the slope ( m )
		bestFitEquations[closePrices.length][0] = curM;
	
		//find the y-intercept ( b )
		bestFitEquations[closePrices.length][1] = curB;
		
		bestFitEquations[closePrices.length][2] = start[0]-1;
		bestFitEquations[closePrices.length][3] = closePrices.length-1;
		
		return bestFitEquations;
	}
}