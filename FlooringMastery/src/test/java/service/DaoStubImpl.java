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
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author bobur
 */
public class DaoStubImpl implements Dao {
    
    private Order onlyOrder;
	
	public DaoStubImpl() {
		onlyOrder = new Order(23);
		onlyOrder.setArea(new BigDecimal(123));
		onlyOrder.setCostPerSquareFoot(new BigDecimal(2.55));
		onlyOrder.setCustomerName("Jacky Chan");
		onlyOrder.setLaborCostPerSquareFoot(new BigDecimal(1.45));
		onlyOrder.setOrderDate(LocalDate.now());
		onlyOrder.setProductType("Tile");
		onlyOrder.setState("Washington");
		onlyOrder.setTaxRate(new BigDecimal(3.25));
	}
	
	public DaoStubImpl(Order testOrder) {
		this.onlyOrder = testOrder;
	}

	@Override
	public List<Order> getAllOrders(LocalDate ld1) throws PersistenceException {
		
		List<Order> orderlist = new ArrayList<>();
		
		orderlist.add(onlyOrder);
		
		return orderlist;
	}

	public void addOrder(Integer OrderNumber, Order order) throws PersistenceException, IOException {
		
//		if(OrderNumber.equals(onlyOrder.getOrderNumber())) {
//			//return onlyOrder;
//		}

                onlyOrder.setOrderNumber(OrderNumber);
                onlyOrder = order;
                
		
	}

	@Override
	public Order getOrder(Integer OrderNumber, LocalDate date) throws PersistenceException {
		
		if(OrderNumber.equals(onlyOrder.getOrderNumber())) {
			return onlyOrder;
		}else {
			return null;
		}
		
	}

	@Override
	public void removeOrder(Integer OrderName, LocalDate date) throws PersistenceException, IOException {
		
		if(OrderName.equals(onlyOrder.getOrderNumber())) {
			//do something
		}
		
	}

	@Override
	public boolean validateStateName(String state) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public BigDecimal getOrderTaxRate(String state) {
            
            if(state.equals("Texas")){
                return new BigDecimal(4.45);
            }
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getProductTypesInArray() {
            
            String[] protypes = new String[10];
		// TODO Auto-generated method stub
		return protypes;
	}

	@Override
	public BigDecimal getCostPSF(String productType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getLaborCPSF(String productType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getRequestedOrderForEditing(LocalDate dateForEdit, Integer orderNumForEdit)
			throws PersistenceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exportAllAvailableData() throws PersistenceException, IOException {
		// TODO Auto-generated method stub
		
	}


    @Override
    public void editOrder(Integer orderNumber, Order editedOrder) throws PersistenceException, IOException {
        
        onlyOrder.setOrderNumber(orderNumber);
        onlyOrder = editedOrder;
    }
		
	}

   

    

    

    
	
	
        
