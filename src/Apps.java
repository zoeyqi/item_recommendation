import com.ECS.client.jax.Item;
import java.util.Scanner;
import com.ECS.client.jax.ItemSearchResponse;
import com.ECS.client.jax.Items;

public class Apps {
	private static Scanner scanner;

	/*
	 * The actual application that performs API search
	 * and displays a returned list of items
	 */
	
	public static void main (String[] args) {
		
		/*
		 * get input from terminal
		 */
		
		scanner = new Scanner(System.in);

		//  prompt asking for search index
		System.out.print("please chosse your search index from \n"+" Books, DVD, Music, Apparel, Video, Jewelry, Automotive, Watch, Electronics\n");

	    System.out.print("Enter your search index: ");

	    // get their input as a String
		String searchIndex = scanner.nextLine();//suggest: Books
		    
		// prompt asking for search key
		System.out.print("Enter your search Key: ");//suggest: software engineering 

	    // get the input as an string
		String searchKey = scanner.nextLine();

		System.out.println(String.format("your are seaching for %s, under %s", searchKey, searchIndex));
			
		System.out.println("==================");	
		
		/*
		 *  This demonstrates how to use AWSRequest class
		 */
		
		AWSRequest requestObj = new AWSRequest();
		requestObj.setRequest(searchIndex, searchKey, "ItemAttributes,Reviews");
		ItemSearchResponse result = requestObj.getResponse();
		// For all the items returned in the response, get all the title names
		for (Items itemList : result.getItems()) {
			for (Item item : itemList.getItem()){
				try {
					System.out.println("Book title name: " +
							item.getItemAttributes().getTitle());
					
					System.out.println("Customer Review URL: " +
							item.getCustomerReviews().getIFrameURL());
					
					System.out.println("==================");
				}
				catch (Exception e) {
					System.out.println(e);
				}	
			}	
		}		
	}		
}	