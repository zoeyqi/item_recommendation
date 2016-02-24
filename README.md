please test the program by running Apps.java with command "java Apps"

The linguistic analysis result is a int number. When the result is negtive means item review is negative. When the result is positive means item review is positive. The absolute value represents the degree of positive or negative. The "0" means neutral.

For the database part, we used JDBC to connect to the database and send SQL requests to the database.
And we used PostgreSQL, so in order to test this part, you may need to download PostgreSQL, build a database,
and then change the information of DATABASE,USER,PASSWORD in JDBC.java, then uncomment the codes in JDBC.java 
and Apps.java, or the program may print error. For now, the program will just print the SQL sentences that will
be sent to the database to show how it will work.