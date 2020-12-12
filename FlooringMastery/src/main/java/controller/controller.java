/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PersistenceException;
import dto.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.DataValidationException;
import service.DuplicateOrderException;
import service.ServiceLayer;
import view.View;

/**
 *
 * @author bobur
 */

@Component
public class controller {
    
     private View view;
    private ServiceLayer service;
    
    @Autowired
    public controller(ServiceLayer serv, View view){
        
        this.service = serv;
        this.view = view;
    }
    
    private static Integer newOrderNumber = 1;
    
    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
        
        try{
            while(keepGoing){
           
                menuSelection = getMenuSelection();
            
                switch(menuSelection){
                    case 1:
                        displayOrders();
                        break;
                    case 2: 
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4: 
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
            }
        }
        exitMessage();
        
        }catch(PersistenceException e){
            
            view.dispayErrorMessage(e.getMessage());
        }catch(IOException e){
            view.dispayErrorMessage(e.getMessage());
        }catch(DuplicateOrderException e){
            view.dispayErrorMessage(e.getMessage());
        }catch(DataValidationException e){
            view.dispayErrorMessage(e.getMessage());
        }
    }
    
    private void exportData() throws PersistenceException, IOException {
		
    	view.displayexportDataBanner();
    	service.exportAllAvailableData();
    	view.displayExportSuccessBanner();
		
	}

	private int getMenuSelection(){
        return view.printMenuAndGetselection();
    }
    
    private void createOrder() throws PersistenceException, IOException, DuplicateOrderException, DataValidationException{
        view.displayAddOrderBanner();
        boolean hasErrors = false;
       
        do {
        Integer OrderNumber = newOrderNumber++;
        
        Order newOrder = new Order(OrderNumber);
        
        newOrder.setOrderDate(view.getOrderDate());
        
        newOrder = view.getNewOrderinfo(newOrder);
        
        newOrder.setState(setOrderState());
        
        newOrder.setTaxRate(getOrderTaxRate(newOrder.getState()));
        
        newOrder.setProductType(getProductType());
        
        newOrder.setCostPerSquareFoot(getCPSF(newOrder.getProductType()));
        
        newOrder.setLaborCostPerSquareFoot(getLCPSF(newOrder.getProductType()));
        
        
        	
        	try {
        		
            if(view.confirmOrder(newOrder)){
            	
            service.addOrder(newOrder.getOrderNumber(), newOrder);
            
            view.displayAddOrderSuccessBanner();
            
        }else{
            view.orderNotPlaced();
        }
            hasErrors = false;
            
        
        }catch(DuplicateOrderException | DataValidationException e) {
        		
        		hasErrors = true;
        		view.dispayErrorMessage(e.getMessage());
        	}
        
    }while(hasErrors); 
        
        
    }

    private String setOrderState() throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
    	
        String state = null;
        
        boolean keepRequesting = true;
        
        while(keepRequesting) {
        	
        	state = view.getStateforOrder();
            
            if(service.validateStateName(state)){
            
                keepRequesting = false;
                
                return state;
                
           }else{
                
                System.out.println("Unfortunately we don't provide in service in "+state);
                
        }
        	
        }
        
       
    
    return state;
}

    private BigDecimal getOrderTaxRate(String state) throws PersistenceException, IOException, DuplicateOrderException, DataValidationException  {
        
        BigDecimal taxRate = service.getOrderTaxRate(state);
        
        return taxRate;
        
    }

    private String getProductType() throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
        String[] productTypes = service.getProductTypesInArray();
        
        String productType = view.showListandPick(productTypes);
        
        return productType;
    }

    private BigDecimal getCPSF(String productType) {
        
        return service.getCostPSF(productType);
        
    }

    private BigDecimal getLCPSF(String productType) {
        
        return service.getLaborCPSF(productType);
    }

    private void displayOrders() throws PersistenceException {
        
        view.displayDisplayAllOrders();
        LocalDate ld1 = view.getUserPickedDate();
        List<Order> orderList = service.getAllOrders(ld1);
        view.displayOrderList(orderList, ld1);
    }

    private void editOrder() throws PersistenceException, IOException {
        
        view.displayEditOrderBanner();
        
        LocalDate dateForEdit = view.getDateFor();
        
        Integer orderNumForEdit = view.getOrderNumFor();
        
        if(service.getRequestedOrderForEditing(dateForEdit, orderNumForEdit)){
            
            Order editingOrder = service.getOrder(orderNumForEdit, dateForEdit);
            
            Order editedOrder = view.editThisOrder(editingOrder);
            
            if(view.confirmOrder(editedOrder)){
            	service.removeOrder(editedOrder.getOrderNumber(), editedOrder.getOrderDate());
                try {
					service.editOrder(editedOrder.getOrderNumber(), editedOrder);
				} catch (PersistenceException | IOException | DuplicateOrderException | DataValidationException e) {
					view.dispayErrorMessage(e.getMessage());
				}
                view.displayEditSuccess();
            }else{
                view.orderNotPlaced();
            }
        }
        
        
        
    }

    private void removeOrder() throws PersistenceException, IOException {
        
        view.displayRemoveOrder();
        
        LocalDate dateForRemove = view.getDateFor();
        
        Integer orderNumForRemove = view.getOrderNumFor();
        
        if(service.getRequestedOrderForEditing(dateForRemove, orderNumForRemove)){
            
            service.removeOrder(orderNumForRemove, dateForRemove);
            
            view.displayRemoval();
        }
        
        
    }

    private void unknownCommand() {
       view.displayUnknownCommand();
    }

    private void exitMessage() {
        view.displayExitMessage();
    }
    
    
}
