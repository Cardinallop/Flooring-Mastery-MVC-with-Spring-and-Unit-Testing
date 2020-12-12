/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.Dao;
import dao.PersistenceException;
import dto.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author bobur
 */
public class ServiceLayerImplTest {
    
  private ServiceLayer service;
	
	public ServiceLayerImplTest() {
		
		Dao dao = new DaoStubImpl();
		
		service = new ServiceLayerImpl(dao);
	}

	

	@Test
	void testAddOrder() {
		
		//Arrange
		Order order5 = new Order(32);
		order5.setArea(new BigDecimal(123));
		order5.setCostPerSquareFoot(new BigDecimal(2.55));
		order5.setCustomerName("Cory Chan");
		order5.setLaborCostPerSquareFoot(new BigDecimal(1.45));
		order5.setOrderDate(LocalDate.now());
		order5.setProductType("Tile");
		order5.setState("Washington");
		order5.setTaxRate(new BigDecimal(3.25));
                order5.getLaborCost();
                order5.getMaterialCost();
                order5.getTax();
                order5.getTotal();
                order5.getOrderNumber();
		
		//Act
		try {
			service.addOrder(order5.getOrderNumber(), order5);
			
		}catch( DuplicateOrderException
				| DataValidationException
				|PersistenceException
				| IOException e) {
			
			//Assert
			fail("Order was valid. No exception should have been thrown");
			
		}
		
		
		
	}

	@Test
	void testValidateStateName() {
            
            //Arrange
            String state = "Texas";
            
            //Act
            try{
                service.validateStateName(state);
                
            }catch( DuplicateOrderException
				| DataValidationException
				|PersistenceException
				| IOException e){
                //Assert
                fail("State name is valid. No exception is expected");
            }
           
	}

	@Test
	void testGetOrderTaxRate() throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
            
            String state = "Texas";
            
            BigDecimal taxrate = new BigDecimal(4.45);
            
            BigDecimal tax = null;
            
          
            tax = service.getOrderTaxRate(state);
          
            
            assertEquals(taxrate, tax, "Should be same taxrate");
		
	}

	@Test
	void testGetProductTypesInArray() {
            
            try{
                
                service.getProductTypesInArray();
            }catch(DuplicateOrderException
				| DataValidationException
				|PersistenceException
				| IOException e){
                //Assert
		fail("Should not throw an exception");
	}
            
        }

	@Test
	void testEditOrder() throws PersistenceException, IOException, DuplicateOrderException, DataValidationException{
            
            //Arrange
            
                Order newOrder1 = new Order(4);
		newOrder1.setArea(new BigDecimal(123));
		newOrder1.setCostPerSquareFoot(new BigDecimal(2.55));
		newOrder1.setCustomerName("Miss Jackson");
		newOrder1.setLaborCostPerSquareFoot(new BigDecimal(1.45));
		newOrder1.setOrderDate(LocalDate.now());
		newOrder1.setProductType("Tile");
		newOrder1.setState("Washington");
		newOrder1.setTaxRate(new BigDecimal(3.25));
                newOrder1.getLaborCost();
                newOrder1.getMaterialCost();
                newOrder1.getTax();
                newOrder1.getTotal();
                newOrder1.getOrderNumber();
                
                Order editedOne = new Order(4);
		editedOne.setArea(new BigDecimal(111));
		editedOne.setCostPerSquareFoot(new BigDecimal(2.55));
		editedOne.setCustomerName("Doctor Jackson");
		editedOne.setLaborCostPerSquareFoot(new BigDecimal(1.45));
		editedOne.setOrderDate(LocalDate.now());
		editedOne.setProductType("Tile");
		editedOne.setState("Washington");
		editedOne.setTaxRate(new BigDecimal(3.25));
                editedOne.getLaborCost();
                editedOne.getMaterialCost();
                editedOne.getTax();
                editedOne.getTotal();
                editedOne.getOrderNumber();
                
                
            
            //Act
            
            service.addOrder(newOrder1.getOrderNumber(), newOrder1);
            
            service.editOrder(editedOne.getOrderNumber(), editedOne);
            
            Order gotten = service.getOrder(newOrder1.getOrderNumber(), LocalDate.now());
            
            //Assert
            assertEquals(gotten.getCustomerName(), editedOne.getCustomerName(), "Both names should be equal");
            
	}

    
}
