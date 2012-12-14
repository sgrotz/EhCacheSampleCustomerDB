EhCacheSampleCustomerDB
=======================

Version: 0.1
Author: Stephan Grotz
URL: https://github.com/sgrotz/EhCacheSampleCustomerDB

Sample Application showcasing a sample Customer DB integration with EhCache. 

Requirements: 
In order to run the customer sample, you need to have: 
* MySQL 5++ installed (JDBC jar file 5.5 is included in the lib folder - please update if using a newer version)
* Customer database created (use the ehcache.sql file in the config folder!)
* Terracotta license key (Place in the config folder)
* Terracotta 3.7.0 pre-installed. If you are using a newer version, please make sure to replace the libraries in the lib folder!
 
To run the application, simply open it in your favorite IDE. Make sure to provide the path to the terracotta license through the java -Dcom.tc.productkey.path argument.

Have fun... 


