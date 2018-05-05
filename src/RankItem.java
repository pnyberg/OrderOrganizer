/**
 * This class is meant to hold String-items with their rank.
 *  The class is meant to be used by other classes ranking orderings/other.
 * 
 * @author pernyberg
 */
public class RankItem {
	private String item;
	private int rank;
	
	public RankItem(String item) {
		this.item = item;
		rank = 0;
	}
	
	/*
	 * Mutators
	 */
	public void addRank(int add) {
		rank += add;
	}
	
	/*
	 * Setters 
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	/*
	 * Getters
	 */
	public String getItem() {
		return item;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String toString() {
		return "[\"" + item + "\": " + rank + "]";
	}
}
