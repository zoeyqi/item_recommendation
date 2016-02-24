import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ECS.client.jax.Item;

public class JDBC {
	public static String DATABASE = "dblp"; //change me!
    public static String USER = "dblpuser"; //change me!
    public static String PASSWORD = "95533"; //change me!
    
    public static Connection getconn(){
    	try { 
            Class.forName("org.postgresql.Driver"); 
            String url = "jdbc:postgresql://localhost/" + DATABASE + "?user=" + USER + "&password=" + PASSWORD; 
            return DriverManager.getConnection(url);
            } 
            catch (ClassNotFoundException e) { 
              e.printStackTrace(); 
              return null;
            }
            catch (SQLException e) { 
                e.printStackTrace(); 
                return null;
            }
    }
    
    public static void createdb(Connection conn){
    	String table = "CREATE TABLE Products(title varchar(500), rank varchar(500), url varchar(2000), review varchar(2000));";
    	System.out.println("creating the table with statement: ");
    	System.out.println(table);
    	System.out.println("==================");
    	
    	//uncomment us!(after changing the JDBC)
    /*	try {
    		PreparedStatement statement = conn.prepareStatement(table);
    		statement.executeUpdate();
    	}
    	 catch (SQLException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}*/
    }
    
    public static void writedb(Connection conn, Item item){
    	String insert = "INSERT INTO Products(title,rank,url,review) VALUES(?,?,?,?);";
    	
    	/*
		 * This part shows how the insert sentences work. 
		 * 
		 */
    	
    	System.out.println("Example insert sentence: ");
    	System.out.print("INSERT INTO Products(");
    	System.out.print(item.getItemAttributes().getTitle() + ", ");
    	System.out.print(item.getSalesRank()+ ", ");
    	System.out.print(item.getDetailPageURL()+ ", ");
    	System.out.println(item.getCustomerReviews().getIFrameURL()+");");
    	System.out.println("==================");
    	
    	//uncomment us!(after changing the JDBC)
    	/*try{
    		PreparedStatement s = conn.prepareStatement(insert);
    		s.setString(1,item.getItemAttributes().getTitle());
    		s.setString(2,item.getSalesRank());
    		s.setString(3,item.getDetailPageURL());
    		s.setString(4,item.getCustomerReviews().getIFrameURL());
    		//s.execute(); //uncomment me!(after changing the JDBC)
    		
        	System.out.println("inserting into database with statement: ");
        	System.out.println(s);
        	System.out.println("==================");
    		
    	}
    	catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
    	
    }
}
