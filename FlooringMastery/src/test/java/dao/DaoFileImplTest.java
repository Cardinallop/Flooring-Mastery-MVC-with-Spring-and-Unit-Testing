/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Order;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author bobur
 */
public class DaoFileImplTest {
    
    Dao testDao;
	
	DaoFileImplTest(){
		
	}

	

	@BeforeEach
	void setUp() throws Exception {
		
		String testFile = "testFile.txt";
		
		new FileWriter(testFile);
		testDao = new DaoFileImpl(testFile);
	}

	

	@Test
	void testGetAllOrders() throws Exception {
		
		//Arrange
		Order order1 = new Order(1) {};
		order1.setArea(new BigDecimal(230).setScale(2, RoundingMode.HALF_UP));
		order1.setCostPerSquareFoot(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_UP));
		order1.setCustomerName("Jacky Maguire");
		order1.setLaborCostPerSquareFoot(new BigDecimal(3.90).setScale(2, RoundingMode.HALF_UP));
		order1.setOrderDate(LocalDate.now());
		order1.setProductType("Tile");
		order1.setState("Kentucky");
		order1.setTaxRate(new BigDecimal(1.75).setScale(2, RoundingMode.HALF_UP));
		
		Order order2 = new Order(2);
		order2.setArea(new BigDecimal(209));
		order2.setCostPerSquareFoot(new BigDecimal(2.45).setScale(2, RoundingMode.HALF_UP));
		order2.setCustomerName("Tommy Ford");
		order2.setLaborCostPerSquareFoot(new BigDecimal(5.45).setScale(2, RoundingMode.HALF_UP));
		order2.setOrderDate(LocalDate.now());
		order2.setProductType("Carpet");
		order2.setState("Washington");
		order2.setTaxRate(new BigDecimal(2.25));
		
		
		//Act
		testDao.addOrder(order1.getOrderNumber(), order1);
		testDao.addOrder(order2.getOrderNumber(), order2);
		
		List<Order> orderlist = testDao.getAllOrders(LocalDate.now());
                
                Order or1 = orderlist.get(0);
                Order or2 = orderlist.get(1);
		
		//Assert
		assertNotNull(orderlist, "The list of orders must not be null");
		assertEquals(2, orderlist.size(), "List of orders should have 2 orders");
		assertEquals(or1.getCustomerName(), order1.getCustomerName(), "The list should include Jacky");
		assertEquals(or2.getCustomerName(), order2.getCustomerName(), "The list should include Tommy");
		
	}

	@Test
	void testAddOrder() throws Exception {
		
		//Arrange - Create Order
		Order order = new Order(17);
		order.setArea(new BigDecimal(200));
		order.setCostPerSquareFoot(new BigDecimal(2.25));
		order.setCustomerName("Jerry Maguire");
		order.setLaborCostPerSquareFoot(new BigDecimal(3.45));
		order.setOrderDate(LocalDate.now());
		order.setProductType("Wood");
		order.setState("Texas");
		order.setTaxRate(new BigDecimal(1.25));
		
		
		//Act
		testDao.addOrder(order.getOrderNumber(), order);
		Order retOrder = testDao.getOrder(order.getOrderNumber(), order.getOrderDate());
		
		//Assert
		assertEquals(order.getCustomerName(), retOrder.getCustomerName(), 
				"Checking Customes name");
		assertEquals(order.getArea(), retOrder.getArea(), "Checking area size");
		assertEquals(order.getOrderDate(), retOrder.getOrderDate(), "Checking Order date");
		assertEquals(order.getProductType(), retOrder.getProductType(), "Checking Product Type");
		assertEquals(order.getState(), retOrder.getState(), "Checking state");
		assertEquals(order.getTaxRate(), retOrder.getTaxRate(), "Checking tax rate");
		
		
	}

	@Test
	void testGetOrder() throws Exception {
		
		//Arrange
                Order order4 = new Order(26);
		order4.setArea(new BigDecimal(215));
		order4.setCostPerSquareFoot(new BigDecimal(2.35));
		order4.setCustomerName("John Grimes");
		order4.setLaborCostPerSquareFoot(new BigDecimal(3.45));
		order4.setOrderDate(LocalDate.now());
		order4.setProductType("Tile");
		order4.setState("Washington");
		order4.setTaxRate(new BigDecimal(2.25));
                
		//Act
                testDao.addOrder(order4.getOrderNumber(), order4);
		Order retOrder5 = testDao.getOrder(order4.getOrderNumber(), order4.getOrderDate());
                
		//Assert
                assertEquals(order4.getCustomerName(), retOrder5.getCustomerName(), 
				"Checking Customes name");
		assertEquals(order4.getArea(), retOrder5.getArea(), "Checking area size");
		assertEquals(order4.getOrderDate(), retOrder5.getOrderDate(), "Checking Order date");
		assertEquals(order4.getProductType(), retOrder5.getProductType(), "Checking Product Type");
		assertEquals(order4.getState(), retOrder5.getState(), "Checking state");
		assertEquals(order4.getTaxRate(), retOrder5.getTaxRate(), "Checking tax rate");
                
                
	}

	@Test
	void testRemoveOrder() throws Exception {
		
		//Arrange
		Order order12 = new Order(44) {};
		order12.setArea(new BigDecimal(230).setScale(2, RoundingMode.HALF_UP));
		order12.setCostPerSquareFoot(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_UP));
		order12.setCustomerName("Manuel Jerome");
		order12.setLaborCostPerSquareFoot(new BigDecimal(3.90).setScale(2, RoundingMode.HALF_UP));
		order12.setOrderDate(LocalDate.now());
		order12.setProductType("Wood");
		order12.setState("Texas");
		order12.setTaxRate(new BigDecimal(1.75).setScale(2, RoundingMode.HALF_UP));
		
		Order order21 = new Order(71);
		order21.setArea(new BigDecimal(209));
		order21.setCostPerSquareFoot(new BigDecimal(2.45).setScale(2, RoundingMode.HALF_UP));
		order21.setCustomerName("Rene Taylor");
		order21.setLaborCostPerSquareFoot(new BigDecimal(5.45).setScale(2, RoundingMode.HALF_UP));
		order21.setOrderDate(LocalDate.now());
		order21.setProductType("Tile");
		order21.setState("Kentucky");
		order21.setTaxRate(new BigDecimal(2.25));
                
		//Act
                testDao.addOrder(order12.getOrderNumber(), order12);
		testDao.addOrder(order21.getOrderNumber(), order21);
		testDao.removeOrder(order21.getOrderNumber(), order21.getOrderDate());
		List<Order> orderlist222 = testDao.getAllOrders(LocalDate.now());
                
		//Assert
		assertNotNull(orderlist222, "The list of orders must not be null");
		assertEquals(1, orderlist222.size(), "List of orders should have 1 order");                
                
		
	}

	@Test
	void testEditOrder() throws Exception {
		
		//Arrange
                Order beforeEdit = new Order(2) {};
		beforeEdit.setArea(new BigDecimal(230).setScale(2, RoundingMode.HALF_UP));
		beforeEdit.setCostPerSquareFoot(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_UP));
		beforeEdit.setCustomerName("Cassie Abel");
		beforeEdit.setLaborCostPerSquareFoot(new BigDecimal(3.90).setScale(2, RoundingMode.HALF_UP));
		beforeEdit.setOrderDate(LocalDate.now());
		beforeEdit.setProductType("Wood");
		beforeEdit.setState("Texas");
		beforeEdit.setTaxRate(new BigDecimal(1.75).setScale(2, RoundingMode.HALF_UP));
                
                Order afterEdit = new Order(2) {};
		afterEdit.setArea(new BigDecimal(230).setScale(2, RoundingMode.HALF_UP));
		afterEdit.setCostPerSquareFoot(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_UP));
		afterEdit.setCustomerName("Manny Jerome");
		afterEdit.setLaborCostPerSquareFoot(new BigDecimal(3.90).setScale(2, RoundingMode.HALF_UP));
		afterEdit.setOrderDate(LocalDate.now());
		afterEdit.setProductType("Wood");
		afterEdit.setState("Texas");
		afterEdit.setTaxRate(new BigDecimal(1.75).setScale(2, RoundingMode.HALF_UP));
                
                
		//Act
                testDao.addOrder(beforeEdit.getOrderNumber(), beforeEdit);
                testDao.editOrder(afterEdit.getOrderNumber(), afterEdit);
                
                Order Ordr1 = testDao.getOrder(beforeEdit.getOrderNumber(), beforeEdit.getOrderDate());
                
                
		//Assert
                assertEquals(afterEdit.getCustomerName(), Ordr1.getCustomerName(), "Names should be Manny Jerome");
		
	}
    
}
