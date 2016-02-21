import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemSearchResponse;
import com.ECS.client.jax.Items;

public class Apps {
	/*
	 * The actual application that performs API search
	 * and displays a returned list of items
	 */
	
	public static void main (String[] args) {
		// This demonstrates how to use AWSRequest class
		AWSRequest requestObj = new AWSRequest();
		requestObj.setRequest("Books", "Software Engineering");
		ItemSearchResponse result = requestObj.getResponse();
	
		// For all the items returned in the response, get all the title names
		for (Items itemList : result.getItems()) {
			for (Item item : itemList.getItem()){
				System.out.println("Book title name: " +
					item.getItemAttributes().getTitle());
			}
		}
	}
}
