/**
 * A more advance organizer which takes a list of RankItem-participants and then
 *  orders them. It also has functionality to test the ordering over 
 *  multiple turns with memory of previous orders, in fact, it bases its ordering
 *  so that with a little variation the overall ordering will be as fair as possible.
 *  
 *  No real usage for this "application" at this point.
 */

import java.util.ArrayList;
import java.util.HashMap;

public class MultiRoundOrganizer {
	/**
	 * Creates an ordered RankItem-array ordered by the rank of
	 *  the items in the given arraylist
	 */
	public static RankItem[] makeOrder(ArrayList<RankItem> participants) {
		RankItem[] orderedRankArray = new RankItem[participants.size()];
		
		ArrayList<RankItem> copiedList = (ArrayList<RankItem>)participants.clone();
		
		/*
		 * Add the highest ranking item to the array
		 * Also add a new rank-value to the current rank-value
		 *  which is based on its new position in the array 
		 */
		int index = 0;
		while(!copiedList.isEmpty()) {
			int chosenIndex = getHighestRankIndex(copiedList);
			copiedList.get(chosenIndex).addRank(index);
			orderedRankArray[index++] = copiedList.remove(chosenIndex);
		}
		
		return orderedRankArray;
	}
	
	/**
	 * Returns the "highest rank" in the given list
	 * The comparison is done with a -1, 0 or +1 adding to 
	 *  add a bit of randomness
	 */
	private static int getHighestRankIndex(ArrayList<RankItem> list) {
		// Help-variables
		int highestRankIndex = 0;
		int highestRank = list.get(0).getRank() + plusMinusOne();

		// Search for "highest rank" in 'list'
		for (int i = 1 ; i < list.size(); i++) {
			int compareRank = list.get(i).getRank() + plusMinusOne();
			
			if (highestRank < compareRank) {
				highestRankIndex = i;
				highestRank = list.get(i).getRank() + plusMinusOne();
			}
		}

		return highestRankIndex;
	}
	
	/**
	 * Returns -1, 0 or 1
	 */
	private static int plusMinusOne() {
		int variation = (int)(Math.random() * 10) % 3 - 1;
		
		return variation;
	}
	
	/**
	 * Does some ranking-rounds giving the items from 'list' ranking values
	 *  and then returnes those as an (ordered) array.
	 */
	private static RankItem[] testRankOrdering(ArrayList<RankItem> list, int numberOfTestRounds) {
		// Check if the call made is valid
		if (numberOfTestRounds < 1) {
			System.err.println("Not allowed, number of test rounds must be >= 1");
			return null;
		}
		
		// Set up a hash-map to save rank items
		HashMap<String, Integer> rankMap = new HashMap<String, Integer>();
		
		// Fill the hashmap with the items
		for (int j = 0 ; j < list.size() ; j++) {
			RankItem rankItem = list.get(j);
			rankMap.put(rankItem.getItem(), rankItem.getRank());
		}

		// Do the test rounds
		for (int i = 0 ; i < numberOfTestRounds ; i++) {
			// Order the elements from 'list' randomly
			RankItem[] array = makeOrder(list);
			
			// Update the items rank in the hash map
			for (int k = 0 ; k < array.length ; k++) {
				RankItem rankItem = array[k];
				rankMap.put(rankItem.getItem(), rankItem.getRank());
			}			
		}
		
		// Create and fill a RankItem-array to be returned
		RankItem[] rankArray = new RankItem[list.size()];
		
		for (int n = 0 ; n < list.size(); n++) {
			rankArray[n] = list.get(n);
		}
		
		return rankArray;
	}
	
	/*
	 * Printers
	 */
	
	// Returns a string representing the RankItem-array (item + rank)
	public static String printArray(RankItem[] array) {
		StringBuilder sb = new StringBuilder();
		for (RankItem item : array) {
			sb.append(item + " ");
		}
		
		return sb.toString();
	}

	// Returns a string representing the HashMap of the strings with their ranks
	public static String printHashMap(HashMap<String, Integer> hashMap) {
		StringBuilder sb = new StringBuilder();
		for (String key : hashMap.keySet()) {
			sb.append(hashMap.get(key) + " ");
		}
		
		return sb.toString();
	}

	public static void main(String[] args) {
		ArrayList<RankItem> a = new ArrayList<RankItem>();
		a.add(new RankItem("A"));
		a.add(new RankItem("B"));
		a.add(new RankItem("C"));
		a.add(new RankItem("D"));
		a.add(new RankItem("E"));
		
		RankItem[] rankArray = testRankOrdering(a, 1000);
		System.out.println(printArray(rankArray));
	}
}