/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 *
 * @author bobur
 */
public class Order {
    
    private LocalDate OrderDate;
    private Integer OrderNumber;
    private String CustomerName;
    private String State;
    private BigDecimal TaxRate;
    private String ProductType;
    private BigDecimal Area;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;
    private BigDecimal MaterialCost;
    private BigDecimal LaborCost;
    private BigDecimal Tax;
    private BigDecimal Total;
    
    public Order(Integer OrderNumber){
        
        this.OrderNumber = OrderNumber;
    }
    
    public void setOrderNumber(Integer OrderNum) {
    	this.OrderNumber = OrderNum;
    }

    public LocalDate getOrderDate() {
        
       return OrderDate;
    }

    public void setOrderDate(LocalDate OrderDate) {
        this.OrderDate = OrderDate;
    }

    public Integer getOrderNumber() {
        return OrderNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(BigDecimal TaxRate) {
        this.TaxRate = TaxRate.setScale(2, RoundingMode.HALF_UP);
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String ProductType) {
        this.ProductType = ProductType;
    }

    public BigDecimal getArea() {
        return Area;
    }

    public void setArea(BigDecimal Area) {
        this.Area = Area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal CostPerSquareFoot) {
        this.CostPerSquareFoot = CostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal LaborCostPerSquareFoot) {
        this.LaborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        
        MaterialCost = Area.multiply(CostPerSquareFoot);
        return MaterialCost;
    }

    public BigDecimal getLaborCost() {
        
        LaborCost = Area.multiply(LaborCostPerSquareFoot);
        
        return LaborCost;
    }

    public BigDecimal getTax() {
        
        BigDecimal tax = (MaterialCost.add(LaborCost)).multiply(TaxRate.divide(new BigDecimal(100)));
        
        Tax = tax.setScale(2, RoundingMode.HALF_UP);
       
        return Tax;
    }


    public BigDecimal getTotal() {
        return MaterialCost.add(LaborCost.add(Tax));
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Area == null) ? 0 : Area.hashCode());
		result = prime * result + ((CostPerSquareFoot == null) ? 0 : CostPerSquareFoot.hashCode());
		result = prime * result + ((CustomerName == null) ? 0 : CustomerName.hashCode());
		result = prime * result + ((LaborCost == null) ? 0 : LaborCost.hashCode());
		result = prime * result + ((LaborCostPerSquareFoot == null) ? 0 : LaborCostPerSquareFoot.hashCode());
		result = prime * result + ((MaterialCost == null) ? 0 : MaterialCost.hashCode());
		result = prime * result + ((OrderDate == null) ? 0 : OrderDate.hashCode());
		result = prime * result + ((OrderNumber == null) ? 0 : OrderNumber.hashCode());
		result = prime * result + ((ProductType == null) ? 0 : ProductType.hashCode());
		result = prime * result + ((State == null) ? 0 : State.hashCode());
		result = prime * result + ((Tax == null) ? 0 : Tax.hashCode());
		result = prime * result + ((TaxRate == null) ? 0 : TaxRate.hashCode());
		result = prime * result + ((Total == null) ? 0 : Total.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (Area == null) {
			if (other.Area != null)
				return false;
		} else if (!Area.equals(other.Area))
			return false;
		if (CostPerSquareFoot == null) {
			if (other.CostPerSquareFoot != null)
				return false;
		} else if (!CostPerSquareFoot.equals(other.CostPerSquareFoot))
			return false;
		if (CustomerName == null) {
			if (other.CustomerName != null)
				return false;
		} else if (!CustomerName.equals(other.CustomerName))
			return false;
		if (LaborCost == null) {
			if (other.LaborCost != null)
				return false;
		} else if (!LaborCost.equals(other.LaborCost))
			return false;
		if (LaborCostPerSquareFoot == null) {
			if (other.LaborCostPerSquareFoot != null)
				return false;
		} else if (!LaborCostPerSquareFoot.equals(other.LaborCostPerSquareFoot))
			return false;
		if (MaterialCost == null) {
			if (other.MaterialCost != null)
				return false;
		} else if (!MaterialCost.equals(other.MaterialCost))
			return false;
		if (OrderDate == null) {
			if (other.OrderDate != null)
				return false;
		} else if (!OrderDate.equals(other.OrderDate))
			return false;
		if (OrderNumber == null) {
			if (other.OrderNumber != null)
				return false;
		} else if (!OrderNumber.equals(other.OrderNumber))
			return false;
		if (ProductType == null) {
			if (other.ProductType != null)
				return false;
		} else if (!ProductType.equals(other.ProductType))
			return false;
		if (State == null) {
			if (other.State != null)
				return false;
		} else if (!State.equals(other.State))
			return false;
		if (Tax == null) {
			if (other.Tax != null)
				return false;
		} else if (!Tax.equals(other.Tax))
			return false;
		if (TaxRate == null) {
			if (other.TaxRate != null)
				return false;
		} else if (!TaxRate.equals(other.TaxRate))
			return false;
		if (Total == null) {
			if (other.Total != null)
				return false;
		} else if (!Total.equals(other.Total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [OrderDate=" + OrderDate + ", OrderNumber=" + OrderNumber + ", CustomerName=" + CustomerName
				+ ", State=" + State + ", TaxRate=" + TaxRate + ", ProductType=" + ProductType + ", Area=" + Area
				+ ", CostPerSquareFoot=" + CostPerSquareFoot + ", LaborCostPerSquareFoot=" + LaborCostPerSquareFoot
				+ ", MaterialCost=" + MaterialCost + ", LaborCost=" + LaborCost + ", Tax=" + Tax + ", Total=" + Total
				+ "]";
	}
    
    

    
    
}
