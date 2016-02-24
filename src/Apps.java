import com.ECS.client.jax.Item;

import java.sql.Connection;
import java.util.Scanner;
import com.ECS.client.jax.ItemSearchResponse;
import com.ECS.client.jax.Items;

public class Apps {
	/*
	 * The actual application that performs API search
	 * and displays a returned list of items
	 */
	
	public static void main (String[] args) {
		
		// get input from terminal
		LinguisticAnalyzer analyzer = new LinguisticAnalyzer();	
		Scanner scanner = new Scanner(System.in);		

		// prompt asking for search index
		System.out.print("Please choose your search index from the list below:\n"
				+ "Books, DVD, Music, Video, "
				+ "Apparel, Jewelry, Kitchen, "
				+ "Automotive, Watch, Electronics, "
				+ "Miscellaneous\n");
	    
		// suggest: Books
		System.out.print("Enter your search index: ");

	    // get user input as a String
		String searchIndex = scanner.nextLine();
		    
		// prompt asking for search key 
		// suggest: software engineering 
		System.out.print("Enter your search Key: ");

		String searchKey = scanner.nextLine();

		System.out.println(String.format("You are seaching for %s, under %s", searchKey, searchIndex));
		
		System.out.print("Do you want to save the information to database? (y/n) ");
		String optionfordb = scanner.nextLine();
		int flag = 0; //to show if the table has created or not
			
		System.out.println("\nStart searching...");
		System.out.println("\n==================\n");	
		
		// initialize AWSRequest object
		AWSRequest requestObj = new AWSRequest();
		// specify what Amazon API should return
		requestObj.setRequest(searchIndex, searchKey, "ItemAttributes,Reviews");
		// make a request and store the retrieved response
		ItemSearchResponse result = requestObj.getResponse();
		// for all the items returned in the response, get all the relevant info
		for (Items itemList : result.getItems()) {
			for (Item item : itemList.getItem()) {
				/*
				 * For the database part, please first change the information in JDBC.java, 
				 * then uncomment the statement.
				 */
				if (optionfordb.equals("y")) {
					Connection conn = null; 
					//conn = JDBC.getconn();  //UNCOMMENT ME! (after changing the JDBC)
					if (flag == 0) {
					   JDBC.createdb(conn);
					   flag = 1;
					}
					JDBC.writedb(conn,item);
				}
				
				try {
					System.out.println("Product title name: " +
							item.getItemAttributes().getTitle());
				} catch (Exception e) {}
				
				try {
					System.out.println("List price: " +
							item.getItemAttributes().getListPrice().getFormattedPrice());
				} catch (Exception e) {}
				
				try {
					System.out.println("Sales Rank: " +
							item.getSalesRank());
				} catch (Exception e) {}
				
				try {
					System.out.println("URL to the product: " +
							item.getDetailPageURL());
				} catch (Exception e) {}
				
				
				try {
					String URL = item.getCustomerReviews().getIFrameURL();
					System.out.println("Customer Review URL: " +
							URL);
					analyzer.ReviewDownload(URL); //analyze product reviews
					System.out.println("The linguistic analysis rating to this item is: " + 
						analyzer.SentimentAnalyzer());
				} catch (Exception e) {}
			
				System.out.println("\n==================\n");
			}	
		}
	System.out.println("The search finished...");
	}		
}	
