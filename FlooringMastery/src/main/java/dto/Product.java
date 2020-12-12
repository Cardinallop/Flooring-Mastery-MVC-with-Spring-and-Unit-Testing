/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.math.BigDecimal;

/**
 *
 * @author bobur
 */
public class Product {
    
    private String type;
	private BigDecimal CostPerSquareFoot;
	private BigDecimal LaborPerSquareFoot;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getCostPerSquareFoot() {
		return CostPerSquareFoot;
	}
	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		CostPerSquareFoot = costPerSquareFoot;
	}
	public BigDecimal getLaborPerSquareFoot() {
		return LaborPerSquareFoot;
	}
	public void setLaborPerSquareFoot(BigDecimal laborPerSquareFoot) {
		LaborPerSquareFoot = laborPerSquareFoot;
	}
	
	

    
}
