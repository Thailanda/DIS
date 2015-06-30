package apriori;
public class Main {
	public static void main(String[] args) {
		System.out.println("Starting...");
		
		AprioriProvider provider = new AprioriProvider();
		provider.readTransactions("transactions.txt");
		provider.run();
		
		System.out.println("Done!");
	}
}
