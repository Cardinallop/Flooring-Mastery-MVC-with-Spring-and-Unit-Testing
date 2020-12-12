/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bobur
 */

@Component
public class View {
    
    private UserIO io;
    
    @Autowired
    public View(UserIO io){
        this.io = io;
    }

    public int printMenuAndGetselection() {
        io.print("  ****************************************");
        io.print("*************************");
        io.print("* << Flooring Program >>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("* ");
        io.print("**************************");
        io.print("  ********************************************");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public Order getNewOrderinfo(Order order) {

        String CustomerName = getCustomerName();
        
        BigDecimal Area = getOrderArea();
        
        order.setCustomerName(CustomerName);
        order.setArea(Area);

        return order;

    }

    private String getCustomerName() {

        boolean keepAsking = false;

        String name;

        do {
            name = io.readString("Please enter a valid customer name.");

            if (name.isBlank() || name.isEmpty()) {

                keepAsking = true;

            } else {

                keepAsking = false;

            }
        } while (keepAsking);

        return name;
    }

    public void displayAddOrderBanner() {
        io.print("=== Add Order ===");
    }

    public void displayAddOrderSuccessBanner() {
        io.readString("Order successfully added. Please hit enter to continue");
    }

    public String getStateforOrder() {

        String state = io.readString("Please enter State name for this order");

        return state;
    }

    public LocalDate getOrderDate() {

        LocalDate OrderDate = LocalDate.now();

        OrderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));

        io.print("The order date for this order is set for: " + OrderDate);

        return OrderDate;
    }

    public String showListandPick(String[] productTypes) {

        io.print("We have these products available: ");
        for (int i = 0; i < productTypes.length; i++) {
            io.print(i + 1 + ". " + productTypes[i]);
        }

        String picked = null;

        boolean keepAsking = true;

        while (keepAsking) {
            try {

                int pick = io.readInt("Please pick row number of your chosen product");

                picked = productTypes[pick - 1];

                keepAsking = false;

            } catch (Exception e) {
                io.print("Please choose from available products");
            }
        }

        return picked;
    }

    private BigDecimal getOrderArea() {
        
        boolean keepTrying = true;
        
        BigDecimal area = null;
        
        while(keepTrying){
            
            try{
                
                area = new BigDecimal(io.readDouble(""
                + "Please provide area measure (minimum: 100 sq ft)", 100, 9000));
                keepTrying = false;
                
            }catch(Exception e){
                io.print("Please enter a valid input");
            }
        }
        
        return area;
    }

    public boolean confirmOrder(Order newOrder) {
        
        io.print("==== Your Order Details ====");
        io.print("Your Order Number: "+ newOrder.getOrderNumber());
        io.print("Your Order Date: "+ newOrder.getOrderDate());
        io.print("Your Name: "+ newOrder.getCustomerName());
        io.print("The measure of area you've provided: "+ newOrder.getArea() + " square feet");
        io.print("The State: "+ newOrder.getState());
        io.print("The Tax Rate for the above state is: %"+ newOrder.getTaxRate());
        io.print("The product type you've selected: "+ newOrder.getProductType());
        io.print("The product cost per square feet: $"+ newOrder.getCostPerSquareFoot());
        io.print("Service cost per square feet: $"+ newOrder.getLaborCostPerSquareFoot());
        io.print("Total material cost for the area: $"+ newOrder.getMaterialCost());
        io.print("Total service cost for the area: $"+ newOrder.getLaborCost());
        io.print("Total taxes: $"+ newOrder.getTax());
        io.print("Total Order Price: $"+ newOrder.getTotal());
        
        String confirm = io.readString("Would you like to confirm this order? Yes/No");
        
        if(confirm.equals("yes")){
            
            return true;
        }else{ 
            return false;
        }
    }

    public void orderNotPlaced() {
        io.print("==== Your Order has not been placed ====");
    }

    public void displayDisplayAllOrders() {
        io.print("==== Displaying all available orders ====");
    }

    public void displayOrderList(List<Order> orderList, LocalDate ldl) {
        
        int count = 0;
        
        LocalDate ld = ldl;
        
        for(Order currentOrder : orderList){
            
            if(currentOrder.getOrderDate().equals(ld)){
                
                count++;
                
                displaySingleOrder(currentOrder);
            }
            
        }
        
        if(count==0){
            System.out.println("There are no orders for the given date");
        }
        
        io.readString("Please hit enter to continue");
    }

    private void displaySingleOrder(Order cO) {
        
        String orderInfo = String.format("#%s, %s, %s, %s, %s, "
                + "%s, %s, %s, %s, %s, %s, %s, %s", 
                cO.getOrderNumber(), 
                cO.getCustomerName(), cO.getState(), 
                cO.getTaxRate(), cO.getProductType(), 
                cO.getArea(), cO.getCostPerSquareFoot(), 
                cO.getLaborCostPerSquareFoot(),
                cO.getMaterialCost(), cO.getLaborCost(), 
                cO.getTax(), cO.getTotal(), cO.getOrderDate());
        
        io.print(orderInfo);
        
        
    }

    public void displayEditOrderBanner() {
        io.print("==== Editing Order ====");
    }

    public LocalDate getDateFor() {
        
        String date = io.readString("Please enter the date of the order. The format should be [yyyy-dd-mm]");
        
        LocalDate ld = LocalDate.parse(date);
        
        return ld;
        
    }

    public Integer getOrderNumFor() {
        
        int ordernum = io.readInt("Please enter the order number");
        
        Integer orderNum = ordernum;
        
        return orderNum;
    }

    public Order editThisOrder(Order editingOrder) {
        
        String newName = editOldName(editingOrder.getCustomerName());
        
        String state = editOldState(editingOrder.getState());
        
        String productType = editOldProductType(editingOrder.getProductType());
        
        BigDecimal area = editOldArea(editingOrder.getArea());
        
        editingOrder.setCustomerName(newName);
        editingOrder.setState(state);
        editingOrder.setProductType(productType);
        editingOrder.setArea(area);
        
        return editingOrder;
        
        
        
    }

    private String editOldName(String customerName) {
        
        String name = io.readString("Enter customer name ("+customerName+") ");
        
        if((name.isBlank())||(name.isEmpty())){
            
            return customerName;
        }
        return name;
    }

    private String editOldState(String state) {
        
        String name = io.readString("Enter state name ("+state+") ");
        
        if((name.isBlank())||(name.isEmpty())){
            
            return state;
        }
        return name;
        
    }

    private String editOldProductType(String productType) {
        
        String name = io.readString("Enter product type ("+productType+") ");
        
        if((name.isBlank())||(name.isEmpty())){
            
            return productType;
        }
        return name;
    }

    private BigDecimal editOldArea(BigDecimal area) {
        
        String area1 = io.readString("Enter new area measures ("+area+") ");
        
        if((area1.isBlank())||(area1.isEmpty())){
            
            return area;
        }else{
            
            BigDecimal area2 = new BigDecimal(area1);
            
            return area2;
        }
      
    }

    public void displayRemoveOrder() {
       io.print("==== Removing Order ====");
    }

    public void displayRemoval() {
        io.print("==== Order has been removed ====");
    }

    public void displayUnknownCommand() {
        io.print("Unknown Command!!!");
    }

    public void displayExitMessage() {
        io.print("Good Bye!");
    }

    public void dispayErrorMessage(String message) {
        io.print("==== ERROR ====");
        io.print(message);
    }

	public LocalDate getUserPickedDate() {
		
		LocalDate date = LocalDate.parse(io.readString("Please enter the date of the order. The format should be [yyyy-dd-mm]"));
		
		return date;
	}

	public void displayexportDataBanner() {
		io.print("==== Attention: Exporting all active orders into backup file... ====");
		
	}

	public void displayExportSuccessBanner() {
		io.print("==== Exporting active orders was successful ====");
		
	}

	public void displayEditSuccess() {
		io.print("==== Editing is done successfully ====");
		
	}
    
}
