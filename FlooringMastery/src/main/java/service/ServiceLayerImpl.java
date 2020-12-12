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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bobur
 */

@Component
public class ServiceLayerImpl implements ServiceLayer {
    
    Dao dao;
	
        @Autowired
	public ServiceLayerImpl(Dao dao) {
		
		this.dao = dao;
	}

	@Override
	public List<Order> getAllOrders(LocalDate ld1) throws PersistenceException {
		// TODO Auto-generated method stub
		return dao.getAllOrders(ld1);
	}
	
	private void validateOrderData(Order order) throws DataValidationException {
		
		if(order.getCustomerName().trim().length()==0
			||order.getOrderDate()==null
			||order.getOrderNumber()==null
			||order.getArea()==null
			||order.getCostPerSquareFoot()==null
			||order.getProductType().trim().length()==0
			||order.getState().trim().length()==0
			||order.getTotal()==null
			||order.getTaxRate()==null
			||order.getMaterialCost()==null
			||order.getTax()==null
			||order.getLaborCost()==null){
				
				throw new DataValidationException("ERROR: All fields are requiered");
			}
			
			
	}

	@Override
	public void addOrder(Integer OrderNumber, Order order)
			throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
		
		
		if((dao.getOrder(OrderNumber, order.getOrderDate())!=null)
				&&(dao.getOrder(OrderNumber, order.getOrderDate()).getCustomerName().equals(order.getCustomerName()))){
			
			throw new DuplicateOrderException("ERROR: Could not add order. Customer "
			+order.getCustomerName()+ " with the same order number already exists");
		}
		
		validateOrderData(order);
		
		dao.addOrder(OrderNumber, order);
		
		
	}

	@Override
	public Order getOrder(Integer OrderNumber, LocalDate date) throws PersistenceException {
		
		return dao.getOrder(OrderNumber, date);
	}

	@Override
	public void removeOrder(Integer OrderName, LocalDate date) throws PersistenceException, IOException {
		
		dao.removeOrder(OrderName, date);
		
	}

	
	@Override
	public boolean validateStateName(String state) throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
		
		return dao.validateStateName(state);
	}

	@Override
	public BigDecimal getOrderTaxRate(String state) throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
		
		return dao.getOrderTaxRate(state);
	}

	@Override
	public String[] getProductTypesInArray() throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
		
		return dao.getProductTypesInArray();
	}

	@Override
	public BigDecimal getCostPSF(String productType) {
		
		return dao.getCostPSF(productType);
	}

	@Override
	public BigDecimal getLaborCPSF(String productType) {
		
		return dao.getLaborCPSF(productType);
	}

	@Override
	public boolean getRequestedOrderForEditing(LocalDate dateForEdit, Integer orderNumForEdit)
			throws PersistenceException {
		
		return dao.getRequestedOrderForEditing(dateForEdit, orderNumForEdit);
	}

	@Override
	public void exportAllAvailableData() throws PersistenceException, IOException {
		dao.exportAllAvailableData();
		
	}

	@Override
	public void editOrder(Integer orderNumber, Order editedOrder)
			throws PersistenceException, IOException, DuplicateOrderException, DataValidationException {
		dao.editOrder(orderNumber, editedOrder);
		
	}

    
}
