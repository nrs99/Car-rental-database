import java.sql.*;
import java.util.*;
public class Database  
{
    public static void main(String [] args) throws SQLException 
    {
        boolean program  = false;
        System.out.println("In order to use this program, you must first validate yourself by entering you Lehigh ID and password.");
        while(!program) 
        {
            try 
            {
                Scanner scan = new Scanner(System.in);
                System.out.println("Oracle userid:\t");
                String [] id_and_password = new String[2];
                id_and_password[0] = scan.nextLine();
                System.out.println("Oracle password:\t");
                id_and_password[1] = scan.nextLine();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",
                id_and_password[0], id_and_password[1]);
                System.out.println("success");
                Statement statement = conn.createStatement();
                Helpers.showMainPage();
                boolean flag = false; 
                do
                {
                    System.out.print("Please enter C for Customer/S for Staff/Q for quit: ");
                    try
                    {
                        if(scan.hasNext())
                        {
                            String op = scan.next();
                            int choice;
                            boolean customer = false;
                            boolean staff = false;    
                            if(op.charAt(0) == 'C' | op.charAt(0) == 'c' && op.length() == 1)
                            {
                                System.out.println("customer page loading...");
                                Helpers.showCustomer();
                                while(!customer) 
                                {
                                    System.out.println("Please enter\n 1 to rent a car\n 2 to return your car\n 3 " +
                                    "to check you bill\n 4 for main menu ");
                                    choice = scan.nextInt();
                                    if(choice == 1)  //done
                                    {
                                        System.out.println("In order to rent a car, please fill out our form.");
                                        System.out.print("First name: ");
                                        String fn = scan.next();
                                        System.out.print("Last name: ");
                                        String ln = scan.next();
                                        Helpers.addCustomerInfo(conn, statement, fn, ln);
                                        System.out.println("list of available vehicles to rent loading...");
                                        Helpers.listRentableCars(conn, statement);
                                        //Helpers.listAllVehicles(conn, statement);
                                        System.out.println("Choose your vehicle by vehicle id");
                                        int vid = scan.nextInt();
                                        Helpers.carRental(conn, statement, vid);
                                    }
                                    else if(choice == 2) 
                                    {
                                        System.out.println("Car returned...dropoff location has been updated via our tracking system and will be picked up.");
                                    }
                                    else if(choice == 3) 
                                    {
                                        System.out.println("To see your bill, enter your customer id: ");
                                        int customer_id = scan.nextInt();
                                        Helpers.checkCustomerBill(conn, statement, customer_id);
                                    }
                                    else 
                                    {
                                        customer = true;
                                        //Helpers.show
                                        System.out.println("going to main menu");
                                    }
                                }   
                            }
                            else if(op.charAt(0) == 'S' | op.charAt(0) == 's' && op.length() == 1)
                            {
                                System.out.println("staff page loading...");
                                Helpers.showStaffandManger();
                                while(!staff) {
                                    System.out.println("Please enter\n 1 to see list of rentable vehicles\n 2 to accpet returns\n 3 to show reports (list of all bills) and car statuses\n 4 for main menu ");
                                    choice = scan.nextInt();
                                    if (choice == 1) 
                                    {
                                        System.out.println("cars that have been rented by customers...");
                                        Helpers.listRentableCars(conn, statement);
                                        System.out.println();
                                        
                                    }
                                    else if(choice == 2) 
                                    {
                                        System.out.println("Car returned...dropoff location has been updated via our tracking system.");
                                    }
                                    else if(choice == 3) 
                                    {
                                        Helpers.checkBill(conn, statement); //done
                                        Helpers.listAllVehicles(conn, statement);
                                    }
                                    else 
                                    {
                                        staff = true;
                                        System.out.println("going to main menu");
                                    }
                                }
                            }
                            else if(op.charAt(0) == 'Q' || op.charAt(0) == 'q')
                            {
                                System.out.print("Thanks for using my program. Bye.");
                                System.exit(1);
                                statement.close();
                                scan.close();
                            }
                            else
                            {
                            
                            }
                        }
                    }
                    catch(Exception e)
                    {      
                        System.out.println("don't");
                    }
                }
                while(flag = true); // breaks the do while loop only when flag = false 
                //which is good becuase after each interface is called, user should be prompted to see another interface without having to re run the proram. 
                program = true; // breaks the while loop only if credentials are approved
            }
            catch(SQLException sqle) 
            {
                System.out.println("Creditals don't match. You may try again.");
            }       
        }        
    }
}