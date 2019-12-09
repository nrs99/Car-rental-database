import java.sql.*;
public class Helpers{

    /* public static void chooseCarByVID(Connection conn, Statement s, int vehicle_id) 
    {
        try 
		{
            String query = "";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.executeUpdate();     
        }
		catch(SQLException e) 
		{
            System.out.println("Helpers.chooseCarByVID() method");
            e.printStackTrace();
		}


    } */

    public static void listRentableCars(Connection conn, Statement s) { //show cars where dropoff location exists
		try 
		{
            ResultSet prodq = s.executeQuery("select vehicle.make as make, vehicle.car_model as cmodel, rental.pickup_loc as location" + 
            " from vehicle join Rental on Vehicle.vehicle_id = Rental.vehicle_id where rental.dropoff_loc is not null");
            System.out.printf("%-15s %-20s %-20s\n", "Car Make", "Car Model", "Pickup location");
            System.out.printf("%-15s %-20s %-20s\n", "--------", "---- ----", "---------------");       
			while(prodq.next()) 
			{
				System.out.printf("%-15s %-20s %-20s\n", prodq.getString("make"), prodq.getString("cmodel"), prodq.getString("location"));
            }        
        }
		catch(Exception e) 
		{
            System.out.println("Helpers.listRentableCars() method");
            e.printStackTrace();
			}


    } 
	public static void listAllVehicles(Connection conn, Statement s) 
	{
		System.out.println("showing our inventory of vehicles");
		try 
		{
            ResultSet prodq = s.executeQuery("select * from Vehicle");
            System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s\n", "Vehicle_id", "Car Make", "Car Model", "Fuel", "Rental Rate", "Odomoter Reading");
            System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s\n", "----------", "--------", "---------", "----", "-----------", "----------------");       
			while(prodq.next()) 
			{
				
                System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s\n", prodq.getString("vehicle_id"), prodq.getString("make"), 
                prodq.getString("car_model"), prodq.getString("fuel"), prodq.getString("rate"), prodq.getString("miles_driven"));
            }        
        }
		catch(Exception e) 
		{
            System.out.println("Helpers.listOfAllVehicles() method");
		}
	}
	public static void checkBill(Connection conn, Statement s) 
	{
		System.out.println("showing customer bills");
		try 
		{
			ResultSet prodq = s.executeQuery("select first_name, last_name, ( select sum(Bill.v_cost + Bill.f_cost + Bill.insurance_cost + Bill.service_cost)" +
			"from Bill where Bill.renter_id = Customer.customer_id) AS total_cost from Customer");
			System.out.printf("%-20s %-20s %-20s\n", "Fist name","Last name", "Total");
			System.out.printf("%-20s %-20s %-20s\n", "--------","---------", "-----");       
			while(prodq.next()) 
			{
				System.out.printf("%-20s %-20s %-20s\n", prodq.getString("FIRST_NAME"),prodq.getString("LAST_NAME"), prodq.getString("total_cost")); //, prodq.getString("TOTAL_COST"));
			}        
		}
		catch(Exception e) 
		{
			System.out.println("errant");
			e.printStackTrace();
		}
    }
    public static void checkCustomerBill(Connection conn, Statement s, int cid) 
	{
		System.out.println("showing customer bills");
		try 
		{
            String query = "select first_name, last_name, ( select sum(Bill.v_cost + Bill.f_cost + Bill.insurance_cost + Bill.service_cost) from Bill where Bill.renter_id = Customer.customer_id) AS total_cost from Customer where customer_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, cid);
            ResultSet prodq = ps.executeQuery();
			System.out.printf("%-20s %-20s %-20s\n", "Fist name","Last name", "Total");
			System.out.printf("%-20s %-20s %-20s\n", "--------","---------", "-----");       
			while(prodq.next()) 
			{
				System.out.printf("%-20s %-20s %-20s\n", prodq.getString("FIRST_NAME"),prodq.getString("LAST_NAME"), prodq.getString("total_cost")); //, prodq.getString("TOTAL_COST"));
			}        
		}
		catch(Exception e) 
		{
			System.out.println("errant");
			e.printStackTrace();
		}
	}
	public static void addCustomerInfo(Connection conn, Statement s, String first_name, String last_name) 
	{
		try 
		{
            String query = "insert into Customer (customer_id, first_name, last_name) values (seq_simple.nextval, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, first_name);
            ps.setString(2, last_name);
            ps.executeUpdate();     
        }
		catch(SQLException e) 
		{
            System.out.println("Helpers.addCustomerInfo() method");
            e.printStackTrace();
		}

	}
	public static void showMainPage() 
	{
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t======              Rent a Lemon Car Service                          ======");
        System.out.println("\t\t======  1) Customers                                                  ======"); //customers should be able to see a list of cars to rent from and appropriate info for rental
        System.out.println("\t\t======                                                                ======");
        System.out.println("\t\t======  2) Staff or Manager                                           ======"); //renters should be able to
        System.out.println("\t\t======                                                                ======");
        System.out.println("\t\t========   Guarentee lowest price to put more $$$ in your pocket.   ========");
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t\t                   ____________________\n" +
                "\t\t                     //|           |        \\\n" +
                "\t\t                 //  |           |          \\\n" +
                "\t\t      ___________//____|___________|__________()\\__________________\n" +
                "\t\t    /__________________|_=_________|_=___________|_________________{}\n" +
                "\t\t    [           ______ |           | .           | ==  ______      { }\n" +
                "\t\t  __[__        /##  ##\\|           |             |    /##  ##\\    _{# }_\n" +
                "\t\t {_____)______|##    ##|___________|_____________|___|##    ##|__(______}\n" +
                "\t\t             /  ##__##                              /  ##__##        \\\n" +
                "\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\t==      Created By Nicholas Silva for CSE 241 Final Project               ==");
        System.out.println("\t\t----------------------------------------------------------------------------");
        System.out.println("\t\tWelcome to Rent a Lemon Car Services where you can save money by choosing\n" + 
        "\t\tfrom our selection of cheap cars.\n");
        System.out.println("---------------------------------------------------------------------------------------------\n\n");


        }
        //checkWhatSupplierShipsWhat
    
	public static void showStaffandManger() 
	{
        System.out.println("\t\t===================================================================");
        System.out.println("\t\t======						Staff & Manager					 ======");
        System.out.println("\t\t======  1) Loan car                                          ======"); //TODO: load cars rented/available 
        System.out.println("\t\t======                                                       ======");
        System.out.println("\t\t======  2) Accept returns                                    ======"); //TODO: return car
        System.out.println("\t\t======                                                       ======");
        System.out.println("\t\t======  3) Show reports                                      ======"); //DONE: show all customer bills
        System.out.println("\t\t======                                                       ======");
		System.out.println("\t\t======  4) Go back to Home                                   ======"); //DONE: go back home
        System.out.println("\t\t===================================================================");
		System.out.println("\t\t===================================================================");
     
	}
	public static void showCustomer() 
	{
        System.out.println("\t\t===================================================================");
        System.out.println("\t\t======						Customer					    ======"); //TODO: customers should be able to see a list of cars to rent from and appropriate info for rental
        System.out.println("\t\t======  1) Rent car                                          ======"); //TODO: process to loan: customer info(name)/see cars available/choose a car
        System.out.println("\t\t======                                                       ======");
        System.out.println("\t\t======  2) Return car                                        ======"); //TODO: process to return: customer info(name)/see car rented/choose dropoff location
        System.out.println("\t\t======                                                       ======");
        System.out.println("\t\t======  3) Show bill                                         ======"); //TODO: show customer's bill
        System.out.println("\t\t======                                                       ======");
        System.out.println("\t\t======  4) Go back to Home                                   ======"); //DONE: go back home
        System.out.println("\t\t===================================================================");
        System.out.println("\t\t===================================================================");
	}

    public static void carRental(Connection conn, Statement s, int vehicle_id) 
    {
        System.out.println("reserving : " + vehicle_id );
        try 
		{
            String query = "select make, car_model from vehicle where vehicle_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, vehicle_id);
            //ps.executeQuery();
            ResultSet prodq = ps.executeQuery();
            System.out.printf("%-10s %-10s\n", "Make","Model");
			System.out.printf("%-10s %-10s\n", "----","-----");     
			      
			while(prodq.next()) 
			{
            //return prodq.getString(columnIndex);
                System.out.printf("%-10s %-10s\n", prodq.getString("make"),prodq.getString("car_model")); 
                System.out.println();
			}        
		}
		catch(Exception e) 
		{
			System.out.println("errant");
			e.printStackTrace();
		}

    }

}