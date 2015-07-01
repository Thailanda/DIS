package apriori;
public class Main {
	public static void main(String[] args) {	
	    
	    String[] inputFiles = new String[] { "trans_test", "transactions.txt", "transactionslarge.txt"};	    		
		double minSup = 0.01d;
		
		Apriori algo = new Apriori(inputFiles[0], minSup);
		algo.execute();		
	}
}
