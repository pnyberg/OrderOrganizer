/**
 * A simple organizer which takes a list of String-participants and then
 *  orders them. It also has functionality to create the ordering over 
 *  multiple turns with memory of previous orders to get a more balanced
 *  ordering.
 *  
 *  No real usage for this "application" at this point.
 */
import java.util.ArrayList;
import java.util.HashMap;

public class SimpleOrganizer {
	/**
	 * Creates an ordered array for the given arraylist 
	 *  of strings. The ordering is totally random and 
	 *  since the returned array is of type string no
	 *  rank is given to the elements of the array
	 */
	public static String[] makeOrder(ArrayList<String> participants) {
		String[] orderedArray = new String[participants.size()];
		ArrayList<String> copiedList = (ArrayList<String>)participants.clone();
		
		/* 
		 * Going through the copied list and taking a random element
		 *  out of the list and placing it in the "next" position in
		 *  the array
		 */
		int index = 0;
		while(!copiedList.isEmpty()) {
			int chosenIndex = (int)(Math.random() * copiedList.size());
			String chosenString = copiedList.remove(chosenIndex);
			orderedArray[index++] = chosenString;
		}
		
		return orderedArray;
	}
	
	private static RankItem[] testRankOrdering(ArrayList<String> list, int numberOfTestRounds) {
		// Check if the given call is valid
		if (numberOfTestRounds < 1) {
			System.err.println("Not allowed, number of test rounds must be >= 1");
			return null;
		}
		
		// Setup a hashmap with rank at 0 for all elements
		HashMap<String, Integer> rankMap = new HashMap<String, Integer>();
		
		for (int j = 0 ; j < list.size() ; j++) {
			String item = list.get(j);
			rankMap.put(item, 0);
		}

		// Do the test-rounds
		for (int i = 0 ; i < numberOfTestRounds ; i++) {
			// Order the elements from 'list' randomly
			String[] array = makeOrder(list);
			
			// Give ranking-values based on the random ordering
			for (int k = 0 ; k < array.length ; k++) {
				String item = array[k];
				int rankNumber = rankMap.get(item) + k;
				rankMap.put(item, rankNumber);
			}
		}
		
		/*
		 *  Create a rank-array and input the ranked elements
		 *   with their ranking in it to be returned
		 */
		RankItem[] rankArray = new RankItem[list.size()];
		
		for (int n = 0 ; n < list.size(); n++) {
			String item = list.get(n);
			int rankNumber = rankMap.get(item);

			rankArray[n] = new RankItem(item);
			rankArray[n].addRank(rankNumber);
		}
		
		return rankArray;
	}
	
	/*
	 * Print-methods
	 */
	
	// Printing an array of strings
	public static String printArray(String[] array) {
		StringBuilder sb = new StringBuilder();
		for (String s : array) {
			sb.append(s + " ");
		}
		
		return sb.toString();
	}
	
	// Printing an array of rank items (item + rank)
	public static String printArray(RankItem[] array) {
		StringBuilder sb = new StringBuilder();
		for (RankItem item : array) {
			sb.append(item + " ");
		}
		
		return sb.toString();
	}

	public static void main(String[] args) {
		ArrayList<String> a = new ArrayList<String>();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		a.add("5");
		
		RankItem[] rankArray = testRankOrdering(a, 1000);
		System.out.println(printArray(rankArray));
	}
}