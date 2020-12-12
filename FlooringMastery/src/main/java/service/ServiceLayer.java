/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PersistenceException;
import dto.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bobur
 */
public interface ServiceLayer {
    
    List<Order> getAllOrders(LocalDate ld1) throws PersistenceException;
    
    /**
     * Adds the given Order to the company and associate it with the given OrderNumber.
     * 
     */
    
    void addOrder(Integer OrderNumber, Order order) throws PersistenceException, IOException,DuplicateOrderException, DataValidationException;
    
    /**
     * Edits an order
     */
    
    Order getOrder(Integer OrderNumber, LocalDate date) throws PersistenceException;
    
    /**
     * Removes an Order from the system
     */
    
    void removeOrder(Integer OrderName, LocalDate date) throws PersistenceException, IOException;
    

    public boolean validateStateName(String state)throws PersistenceException, IOException, DuplicateOrderException, DataValidationException;

    public BigDecimal getOrderTaxRate(String state) throws PersistenceException, IOException, DuplicateOrderException, DataValidationException ;

    public String[] getProductTypesInArray() throws PersistenceException, IOException, DuplicateOrderException, DataValidationException;

    public BigDecimal getCostPSF(String productType);

    public BigDecimal getLaborCPSF(String productType);

    public boolean getRequestedOrderForEditing(LocalDate dateForEdit, Integer orderNumForEdit) throws PersistenceException;

	void exportAllAvailableData() throws PersistenceException, IOException;
    
    
	void editOrder(Integer orderNumber, Order editedOrder) throws PersistenceException, IOException, DuplicateOrderException, DataValidationException;
    
}
