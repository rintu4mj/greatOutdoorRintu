package com.capgemini.go.presentationLayer;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.capgemini.go.bean.ProductBean;
import com.capgemini.go.dto.Address;
import com.capgemini.go.dto.Product;
import com.capgemini.go.dto.ProductFilter;
import com.capgemini.go.dto.User;
import com.capgemini.go.dto.WrongProductNotification;
import com.capgemini.go.exception.DatabaseException;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.ProductMasterException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.service.GoAdminService;
import com.capgemini.go.service.GoAdminServiceImpl;
import com.capgemini.go.service.ProductMasterService;
import com.capgemini.go.service.ProductMasterServiceImpl;
import com.capgemini.go.service.RetailerService;
import com.capgemini.go.service.RetailerServiceImpl;
import com.capgemini.go.service.SalesRepresentativeService;
import com.capgemini.go.service.SalesRepresentativeServiceImpl;
import com.capgemini.go.service.UserService;
import com.capgemini.go.service.UserServiceImpl;
import com.capgemini.go.utility.Authentication;
import com.capgemini.go.utility.DbConnection;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.PropertiesLoader;
import com.capgemini.go.utility.Validator;

public class GoUserInterface {
	static Connection connection = null;
	private static final String EXCEPTION_PROPERTIES_FILE = "exceptionStatement.properties";
	private static Properties exceptionProps = null;
	private static Properties goProps = null;
	private static final String GO_PROPERTIES_FILE = "goUtility.properties";

	public static void main(String[] args) {
		
		try {
			 connection = DbConnection.getInstance().getConnection();
		} catch (DatabaseException e2) {
			
			e2.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		List<ProductBean> activeProductView = new ArrayList<ProductBean>();
		UserService universalUser = new UserServiceImpl();
		try {
			activeProductView = universalUser.getAllProducts(connection);
		} catch (UserException e1) {
			
			e1.printStackTrace();
		}
	
		boolean entry = true;
		try {
		while (entry == true) {
			
			System.out.println("******************* GREAT OUTDOOR  Menu ********************");
			System.out.println("Press Your Choice according to the User");
			System.out.println(
					" 1 for GO ADMIN ... \n 2 for SALES REPRESENTATIVE ... \n 3 for RETAILER... \n 4 for PRODUCT MASTER...  \n 5 for ANY USER \n Press 0 to exit from the Application ...");
			int choice = scanner.nextInt();
			switch (choice) {
			case 0:
				entry = false;
				break;
			case 1:

				boolean goAdminEntry = true;
				GoAdminService goAdmin = new GoAdminServiceImpl();
				while(goAdminEntry)
				{
					System.out.println("************************** GO ADMIN MENU ************************");
					System.out.println("Press 1 to Add Product Master \n Press 2 to get Return Notification \n Press 3 to get frequently ordered product suggestion \n Press 0 to exit");
					int goAdminChoice = scanner.nextInt();
					scanner.nextLine();
					switch(goAdminChoice)
					{
					case 0:
						goAdminEntry = false;
						break;
					case 1:
						final String GO_PROPERTIES_FILE = "goUtility.properties";
						Properties goProps = null;
						System.out.println("************* PRODUCT MASTER REGISTRATION ***************");
						System.out.println("Enter the GO ID : ");
						String userId = scanner.nextLine();
						System.out.println("Enter the Product Master Name : ");
						String userName = scanner.nextLine();
						if(!(userName.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
						{
							System.out.println("user name can contain only character");
							break;
						}
						System.out.println("Enter the  Mail : ");
						String userMail = scanner.nextLine();
						System.out.println("Enter the Password : ");
						String userPassword = scanner.nextLine();
						if(Validator.validatePassword(userPassword) == false)
						{
							System.out.println("Be between 8 and 40 characters long\r\n" + 
									"Contain at least one digit.\r\n" + 
									"Contain at least one lower case character.\r\n" + 
									"Contain at least one upper case character.\r\n" + 
									"Contain at least on special character from [ @ # $ % ! . ].");
							break;
						}
						System.out.println("Enter the Contact Number : ");
						long userContact=0L;
						try {
						userContact = scanner.nextLong();
						scanner.nextLine();
						}
						catch(InputMismatchException e)
						{
							 System.out.println("User Contact Only contains Number");
							 break;
						}
												
						try
						{
							goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);
							userPassword = Authentication.encrypt(userPassword, goProps.getProperty("SECURITY_KEY"));
							User newUser = new User(userName, userId, userMail, userPassword, userContact, Integer.parseInt(goProps.getProperty("RETAILER")), false);
							boolean status = goAdmin.addProductMaster(newUser, connection);
							System.out.println("User Successfully Registered");
							System.out.println("Kindly Please Log-In");
						}
						catch(Exception e)
						{
							
							System.out.println(e.getMessage());
						}
						break;
					case 2:
						try
						{
							List<WrongProductNotification> notification = goAdmin.getNotification(connection);
							System.out.println("**************** Notification for Wrong Product Shipped *************");
							for(WrongProductNotification notify : notification)
							{
								System.out.println(notify);
							}
						}
						catch(GoAdminException e)
						{
							System.out.println(e.getMessage());
						}
						break;
					case 3:
						try
						{
							
			                 System.out.println("Enter the retailer ID");
			                 String retailerID = scanner.next();
			                 System.out.println("List of Notification for Wrong products Shipped");
			                 goAdmin.suggestFreqOrderProducts(retailerID,connection);
						}
						catch(GoAdminException e)
						{
							System.out.println(e.getMessage());
						}
						break;
					default:
						System.out.println("Invalid Input ! Please Enter Input from Menu");
					}
				}
				
				break;
			case 2:
				SalesRepPresentation.interfacesales();
				break;
			case 3:
				boolean RetailerEntry = true;
				RetailerService retailerservice = new RetailerServiceImpl();
				while (RetailerEntry == true) {
					System.out.println("::::::::::: RETAILER MENU :::::::::::");
					System.out
							.println("SELECT THE KEY ACCORDING TO YOUR REQUIREMENT");
					System.out
							.println("Press 1 to add address."
									+ "Press 2 to update the address"
									+ "Press 3 to delete the address"
									+ "Press 4 to change the order address"
									+ "Press 5 to add product to frequently ordered list"
									+ "Press 6 to change the product address in frequently ordered list");
					int retailerChoice = scanner.nextInt();
					scanner.nextLine();
					switch (retailerChoice) {
					case 0:
						RetailerEntry = false;
						break;
					case 1:
						System.out
								.println("This is the function to add a new address");

						Address address = null;
						System.out.println("Enter your retailerId");
						String retailerId = scanner.nextLine();
						System.out.println("Enter your buildingnum");
						String buildingnum = scanner.nextLine();
						System.out.println("Enter your city");
						String city = scanner.nextLine();
						System.out.println("Enter your state");
						String state = scanner.nextLine();
						System.out.println("Enter your country");
						String country = scanner.nextLine();
						System.out.println("Enter your zip");
						String zip = scanner.nextLine();
						String addressId = (address.getRetailerId() + 01);
						try {

							address = new Address(addressId, retailerId, city,
									state, zip, buildingnum, country, true);

							boolean status = retailerservice.addAddress(address, connection);
						} catch (RetailerException re) {
							System.out.println(re.getMessage());
						}

						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					default:
						System.out
								.println("Please enter any key from 1 or 2 or 3 or 4 or 5 or 6."
										+ " Don't press any two key simultaneously");
					}
				}

				break;
			case 4:
				boolean productMasterEntry = true;
				ProductMasterService productMaster = new ProductMasterServiceImpl();
				while(productMasterEntry == true)
				{
					System.out.println("************* PRODUCT MASTER MENU ***********");
					System.out.println("Press The key according to the operation you want to perform");
					System.out.println(" 1 to add a product... \n 2 to update a product ... \n 3 to delete a product ... \n 4 to increase product quantity \n Press 0 to go back to main menu");
					int productMasterChoice = scanner.nextInt();
					scanner.nextLine();
					switch(productMasterChoice)
					{
					case 0:
						productMasterEntry=false;
						break;
					case 1:
						System.out.println("Add a Product Function");
						System.out.println("Enter the Product Id : ");
						String productId = scanner.nextLine();
						System.out.println("Enter the Product Name : ");
						String productName = scanner.nextLine();
						if(!(productName.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
						{
							System.out.println("Product name can contain only character");
							break;
						}
						System.out.println("Enter the Product Price : ");
						double productPrice;
						try {
						 productPrice =scanner.nextDouble();
						scanner.nextLine();
						}
						catch(InputMismatchException e)
						{
							System.out.println("Price accepts Double value only");
							break;
						}
						if(productPrice <0)
						{
							System.out.println("Enter a value greater than 0");
							break;
						}
						System.out.println("Enter the Product Colour : ");
						String productColour = scanner.nextLine();
						System.out.println("Enter the Product Dimension : ");
						String productDimension =scanner.nextLine();
						System.out.println("Enter the Product Quantity : ");
						int productQuantity = scanner.nextInt();
						scanner.nextLine();
						if(productQuantity <1)
						{
							System.out.println("Minimum Quantity must be 1");
						}
						System.out.println("Enter the Product Specification : ");
						String productSpecification = scanner.nextLine();
						System.out.println("Enter \n 1 for Camping \n 2 for Golf \n 3 for Mountaineering \n 4 for Outdoor \n 5 for Personal");
						System.out.println("Enter the Product Category : ");
						int productCategory;
						try {
						productCategory =scanner.nextInt();
						scanner.nextLine();
						}
						catch(InputMismatchException e)
						{
							System.out.println("Category takes only integer");
							break;
						}
						if(productCategory < 1 || productCategory > 5)
						{
							System.out.println("Enter a valid product Category");
							break;
						}
						System.out.println("Enter the Product Manufacturer : ");
						String manufacturer = scanner.nextLine();
						try
						{
							Product product = new Product(productId, productPrice, productColour, productDimension, productSpecification, manufacturer, productQuantity, productCategory, productName);
							boolean status = productMaster.addProduct(product,connection);
						}
						catch(ProductMasterException e)
						{
							System.out.println(e.getMessage());
						}
						
						
						
						break;
					case 2:
						System.out.println("Update a Product Function");
						System.out.println("Press Enter to not update the feature and Press 0 for numeric value ");
						System.out.println("Enter the Product Id : ");
						String updateProductId = scanner.nextLine();
						System.out.println("Enter the Product Name : ");
						String updateProductName = scanner.nextLine();
						if(!(updateProductName.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
						{
							System.out.println("Product name can contain only character");
							break;
						}
						System.out.println("Enter the Product Price : ");
						double updateProductPrice;
						try {
						 updateProductPrice =scanner.nextDouble();
						scanner.nextLine();
						}
						catch(InputMismatchException e)
						{
							System.out.println("Price accepts Double value only");
							break;
						}
						if(updateProductPrice <0)
						{
							System.out.println("Enter a value greater than 0");
							break;
						}
						System.out.println("Enter the Product Colour : ");
						String updateProductColour = scanner.nextLine();
						System.out.println("Enter the Product Dimension : ");
						String updateProductDimension =scanner.nextLine();
						System.out.println("Enter the Product Specification : ");
						String updateProductSpecification = scanner.nextLine();
						System.out.println("Enter \n 1 for Camping \n 2 for Golf \n 3 for Mountaineering \n 4 for Outdoor \n 5 for Personal \n 0 to not update");
						System.out.println("Enter the Product Category : ");
						int updateProductCategory=0;
						try {
						updateProductCategory =scanner.nextInt();
						scanner.nextLine();
						}
						catch(InputMismatchException e)
						{
							System.out.println("Category takes only integer value");
							break;
						}
						if(updateProductCategory <0 || updateProductCategory > 5)
						{
							System.out.println("Enter Valid Product Category");
							break;
						}
							
						System.out.println("Enter the Product Manufacturer : ");
						String updateManufacturer = scanner.nextLine();
						try
						{
							Product product = new Product(updateProductId, updateProductPrice, updateProductColour, updateProductDimension, updateProductSpecification, updateManufacturer, 0, updateProductCategory, updateProductName );
							boolean status = productMaster.updateProduct(product,connection); 
						}
						catch(ProductMasterException e)
						{
							System.out.println(e.getMessage());
						}
						break;
					case 3:
						System.out.println(" ************** DELETE PRODUCT ***************");
						System.out.println("Enter the Product ID which is to be deleted :");
						String deleteProductId = scanner.nextLine();
						try
						{
							boolean status = productMaster.deleteProduct(deleteProductId,connection);
						}
						catch(ProductMasterException e)
						{
							System.out.println(e.getMessage());
						}
						break;
					case 4:
						System.out.println("INCREASE PRODUCT QUANTITY");
						System.out.println(" Enter the product id :");
						String prodId = scanner.nextLine();
						System.out.println(" Enter the quantity to be added ");
						int qty=1;
						try {
						qty = scanner.nextInt();
						scanner.nextLine();
						}
						catch(InputMismatchException e)
						{
							System.out.println("Quantity can be integer only");
							break;
						}
						if(qty<1)
						{
							System.out.println("Minimum quantity to be added must be 1");
							break;
						}
						try
						{
							Product product = new Product(prodId, 0.0, "", "", "", "", qty, 0, "");
							boolean status = productMaster.addExistingProduct(product,connection);
						}
						catch(ProductMasterException e)
						{
							System.out.println(e.getMessage());
						}
						break;
					default:
						System.out.println("Invalid Input . Enter a valid choice ");
					}
					
						
				}
				break;
			case 5:
				UserService user = new UserServiceImpl();
				
				String userPassword  = null;
				String userId = null;
				boolean userEntry = true;
				scanner.nextLine();
					while(userEntry == true)
					{
						System.out.println("************* USER MENU ***********");
						System.out.println("Press The key according to the operation you want to perform");
						System.out.println(" 1 for viewing all product ... \n 2 for searching a product ... \n 3 for filter product using several parameter ... \n 4 Sort product list accorsing to your choice ... \n 5 for login .. \n 6 for register \n Press 0 to go back to main menu");
						int userChoice = scanner.nextInt();
						scanner.nextLine();
						switch(userChoice)
						{
						case 0:
							userEntry = false;
							break;
						case 1:
							
							try {
								List<ProductBean> allProds = user.getAllProducts(connection);
								System.out.println(allProds.size() + " Record Found ...........");
								for (ProductBean product : allProds) {
									
									System.out.println(product);
								}
								activeProductView = allProds;
							} catch (UserException e) {
								
								GoLog.logger.error(e.getMessage());
								System.out.println(e.getMessage());
							}
							
							break;
						case 2:
							try {
								System.out.println("Enter the Product Name : ");
								String productName = scanner.nextLine();
								List<ProductBean> srchProds = user.searchProduct(productName,connection);
								activeProductView = srchProds;
								System.out.println(srchProds.size() + " Record Found ...........");
								for (ProductBean product : srchProds) {
									
									System.out.println(product);
								}
							} catch (UserException e) {
								
								GoLog.logger.error(e.getMessage());
								System.out.println(e.getMessage());
							}
							
							break;
						case 3:
							try {
								System.out.println("Enter the Product Name : ");
								String productName = scanner.nextLine();
								System.out.println("Enter the Product Colour : ");
								String productColour = scanner.nextLine();
								System.out.println("Enter the low range");
								double low = scanner.nextDouble();
								scanner.nextLine();
								System.out.println("Enter the high range");
								double high = scanner.nextDouble();
								scanner.nextLine();
								System.out.println("Enter the brand name :");
								String manufacturer = scanner.nextLine();
								System.out.println("Enter the product Category");
								System.out.println("Enter \n 1 for Camping \n 2 for Golf \n 3 for Mountaineering \n 4 for Outdoor \n 5 for Personal");
								int category = scanner.nextInt();
								scanner.nextLine();
								ProductFilter proFltr = new ProductFilter(productName, productColour, low, high, category, manufacturer);
								List<ProductBean> fltrProds = user.filterProduct(proFltr,connection);
								activeProductView = fltrProds;
								System.out.println(fltrProds.size() + " Record Found ...........");
								for (ProductBean product : fltrProds) {
									
									System.out.println(product);
								}
							} catch (UserException  e) {
								
								GoLog.logger.error(e.getMessage());
								System.out.println(e.getMessage());
							}
							break;
						case 4:
							System.out.println("Enter 1 to sort by name \n Enter 2 to Sort from low to High Price \n Enter 3 to Sort from High to Low Price \n");
							int input = scanner.nextInt();
							scanner.nextLine();
							if(input == 1)
							{
								activeProductView.sort((p1, p2) -> p1.getProductName().compareTo(p2.getProductName()));
								for(ProductBean product : activeProductView)
								{
									System.out.println(product);
								}
							}
							else if(input == 2)
							{
								activeProductView.sort((p1, p2) -> { if(p1.getPrice() > p2.getPrice())
									return 1;
								else if(p1.getPrice() < p2.getPrice())
									return -1;
								else
									return 0;
									}
									);
								for(ProductBean product : activeProductView)
								{
									System.out.println(product);
								}
								}
							else if(input == 3)
							{
								activeProductView.sort((p1, p2) -> { if(p1.getPrice() > p2.getPrice())
									return -1;
								else if(p1.getPrice() < p2.getPrice())
									return 1;
								else
									return 0;
									}
									);
								for(ProductBean product : activeProductView)
								{
									System.out.println(product);
								}
								}
							break;
						case 5:
							final String GO_PROPERTIES = "goUtility.properties";
							Properties goProp = null;
							System.out.println("************* USER LOG_IN ***************");
							System.out.println("Log-In to the Great Outdoors ");
							System.out.println("Enter the User ID : ");
							userId = scanner.nextLine();
							System.out.println("Enter the Password : ");
							userPassword = scanner.nextLine();
							
							
							try
							{
								goProp = PropertiesLoader.loadProperties(GO_PROPERTIES);
								//userPassword = Authentication.decrypt(userPassword, goProp.getProperty("SECURITY_KEY"));//
								User existingUser = new User(null, userId, null, userPassword, 0, 0,false);
								boolean status = user.userLogin(existingUser);
								System.out.println("User Successfully Logged-In");
								
							}
							catch(Exception e)
							{
								System.out.println(e.getMessage());
							}
						
							break;
						case 6:
							final String GO_PROPERTIES_FILE = "goUtility.properties";
							Properties goProps = null;
							System.out.println("************* USER REGISTRATION ***************");
							System.out.println("Welcome New User");
							System.out.println("Enter the User ID : ");
							userId = scanner.nextLine();
							System.out.println("Enter the User Name : ");
							String userName = scanner.nextLine();
							if(!(userName.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")))
							{
								System.out.println("user name can contain only character");
								break;
							}
							System.out.println("Enter the User Mail : ");
							String userMail = scanner.nextLine();
							System.out.println("Enter the Password : ");
							userPassword = scanner.nextLine();
							if(Validator.validatePassword(userPassword) == false)
							{
								System.out.println("Be between 8 and 40 characters long\r\n" + 
										"Contain at least one digit.\r\n" + 
										"Contain at least one lower case character.\r\n" + 
										"Contain at least one upper case character.\r\n" + 
										"Contain at least on special character from [ @ # $ % ! . ].");
								break;
							}
							System.out.println("Enter the Contact Number : ");
							long userContact=0L;
							try {
							userContact = scanner.nextLong();
							scanner.nextLine();
							}
							catch(InputMismatchException e)
							{
								 System.out.println("User Contact Only contains Number");
								 break;
							}
							System.out.println("Enter the Category [2-Sales Rep or 3-Retailer]");
							int userCategory= scanner.nextInt();
							scanner.nextLine();
							
							try
							{
								goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);
								userPassword = Authentication.encrypt(userPassword, goProps.getProperty("SECURITY_KEY"));//
								User newUser = new User(userName, userId, userMail, userPassword, userContact, userCategory, false);
								boolean status = user.userRegistration(newUser);
								System.out.println("User Successfully Registered");
								System.out.println("Kindly Please Log-In");
							}
							catch(Exception e)
							{
								System.out.println(e.getMessage());
							}
						
							break;
						default :
							System.out.println(" Invalid Input . Please Enter a Valid User Operation");
							
						}
						
					}
				break;
				
			default:
				System.out.println("************ Your choice is invalid .Enter a valid choice from 1 to 4 *********** ");

			}

			
		}
		scanner.close();
		}
		catch(InputMismatchException e)
		{
			System.out.println("ENTER THE CHOICE IN NUMBER ONLY");
		}
	}
}
