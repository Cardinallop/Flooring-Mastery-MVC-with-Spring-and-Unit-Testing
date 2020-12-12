/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Order;
import dto.Product;
import dto.TaxRate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author bobur
 */
@Component
public class DaoFileImpl implements Dao {

    public static String FLOORING_FILE;
    public static final String DELIMITER = ",";

    private Map<Integer, Order> orders = new HashMap<>();
    //private Map<Integer, Order> testOrders = new HashMap<>();
    private Map<String, TaxRate> taxrate = new HashMap<>();
    private Map<String, Product> products = new HashMap<>();
    private Map<Integer, Order> exports = new HashMap<>();

    public DaoFileImpl() {

    }

    public DaoFileImpl(String testFile) {
        FLOORING_FILE = testFile;
    }

    @Override
    public List<Order> getAllOrders(LocalDate ld) throws PersistenceException {

        if ((FLOORING_FILE!=null)&&(FLOORING_FILE.equals("testFile.txt"))) {

            loadOrder(ld);

            return new ArrayList<Order>(orders.values());

        } else {
            
            
            if (fileForThisDate(ld)) {

                loadOrder(ld);
            }

            return new ArrayList<Order>(orders.values());

        }

    }

    @Override
    public void addOrder(Integer OrderNumber, Order order) throws PersistenceException, IOException {

        loadTax();
        
        if((FLOORING_FILE!=null)&&(FLOORING_FILE.equals("testFile.txt"))) {

            Order neworder = orders.put(OrderNumber, order);
            writeOrder(order.getOrderDate());

        }else{

            if (fileForThisDate(order.getOrderDate())) {

                boolean keepPushing = true;

                int count = OrderNumber;

                while (keepPushing) {

                    if (orders.get(count) == null) {

                        OrderNumber = count;

                        keepPushing = false;

                    } else {

                        count++;
                    }
                }

                order.setOrderNumber(OrderNumber);

            }

            Order neworder = orders.put(OrderNumber, order);
            writeOrder(order.getOrderDate());

        } 

    }

    @Override
    public Order getOrder(Integer OrderNumber, LocalDate date) throws PersistenceException {
        
        
        if ((FLOORING_FILE!=null)&&(FLOORING_FILE.equals("testFile.txt"))) {

            loadOrder(date);

            for (Order ord : orders.values()) {

                if ((ord.getOrderDate().equals(date)) && (ord.getOrderNumber().equals(OrderNumber))) {
                    return ord;
                }
            }

        }else{

            if (fileForThisDate(date)) {

                loadOrder(date);
            }

            for (Order ord : orders.values()) {

                if ((ord.getOrderDate().equals(date)) && (ord.getOrderNumber().equals(OrderNumber))) {
                    return ord;
                }
            }
           

        }
        return null;
       
    }

    @Override
    public void removeOrder(Integer OrderNum, LocalDate date) throws PersistenceException, IOException {

        loadOrder(date);

        orders.remove(OrderNum);

        writeOrder(date);
    }

    @Override
    public boolean validateStateName(String state) {

        try {
            loadTax();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (TaxRate TR : taxrate.values()) {
            if (TR.getFullname().equals(state)) {
                return true;
            }

            if (TR.getInitials().equals(state.substring(0, 2).toUpperCase())) {
                return true;
            }
        }

        return false;
    }

    //textline into an object
    private TaxRate unmarshallTax(String taxAsText) {

        String[] taxTokens = taxAsText.split(DELIMITER);

        TaxRate taxFromFile = new TaxRate();

        taxFromFile.setInitials(taxTokens[0]);
        taxFromFile.setFullname(taxTokens[1]);
        taxFromFile.setTaxrate(new BigDecimal(taxTokens[2]));

        return taxFromFile;

    }

    //reads the file and puts the objects into map
    private void loadTax() throws PersistenceException {

        Scanner scanner;

        try {
            //Create Scanner for reading the file

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Data/Taxes.txt")));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("-_- Could not load tax data into memory", e);

        }

        String curtLine;

        TaxRate currTax;

        while (scanner.hasNextLine()) {

            curtLine = scanner.nextLine();

            currTax = unmarshallTax(curtLine);

            taxrate.put(currTax.getInitials(), currTax);
        }

        scanner.close();
    }

    @Override
    public BigDecimal getOrderTaxRate(String state) throws PersistenceException, IOException{
       
        for (TaxRate TX : taxrate.values()) {
            if (TX.getFullname().equals(state)) {
                return TX.getTaxrate();
            }

            if (TX.getInitials().equals(state)) {
                return TX.getTaxrate();
            }
        }
        return null;

    }

    @Override
    public String[] getProductTypesInArray() {

        try {
            loadProducTypes();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] typesList = new String[products.size()];

        ArrayList<String> keyList = new ArrayList<String>(products.keySet());

        for (int i = 0; i < typesList.length; i++) {

            typesList[i] = keyList.get(i);
        }

        return typesList;
    }

    @Override
    public BigDecimal getCostPSF(String productType) {

        for (Product prod : products.values()) {
            if (prod.getType().equals(productType)) {
                return prod.getCostPerSquareFoot();
            }
        }
        return null;

    }

    @Override
    public BigDecimal getLaborCPSF(String productType) {

        for (Product prod : products.values()) {
            if (prod.getType().equals(productType)) {
                return prod.getLaborPerSquareFoot();
            }
        }
        return null;

    }

    private void loadProducTypes() throws PersistenceException {

        Scanner scanner2;

        try {
            //Create Scanner for reading the file

            scanner2 = new Scanner(
                    new BufferedReader(
                            new FileReader("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Data/Products.txt")));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("-_- Could not load tax data into memory", e);

        }

        String curtLine;

        Product currProd;

        while (scanner2.hasNextLine()) {

            curtLine = scanner2.nextLine();

            currProd = unmarshallProduct(curtLine);

            products.put(currProd.getType(), currProd);
        }

        scanner2.close();
    }

    private Product unmarshallProduct(String prodAsText) {

        String[] prodTokens = prodAsText.split(DELIMITER);

        Product prodFromFile = new Product();

        prodFromFile.setType(prodTokens[0]);
        prodFromFile.setCostPerSquareFoot(new BigDecimal(prodTokens[1]));
        prodFromFile.setLaborPerSquareFoot(new BigDecimal(prodTokens[2]));

        return prodFromFile;

    }

    @Override
    public boolean getRequestedOrderForEditing(LocalDate dateForEdit, Integer orderNumForEdit) throws PersistenceException {

        loadOrder(dateForEdit);

        for (Order unitOrder : orders.values()) {
            if ((unitOrder.getOrderDate().equals(dateForEdit)) && (unitOrder.getOrderNumber().equals(orderNumForEdit))) {
                return true;
            }
        }
        return false;

    }

    //turn a text line into an object
    private Order unmarshallOrder(String orderAsText) {

        String[] orderTokens = orderAsText.split(DELIMITER);

        Integer OrderNumber = Integer.parseInt(orderTokens[0]);

        Order orderFromFile = new Order(OrderNumber);

        orderFromFile.setCustomerName(orderTokens[1]);

        orderFromFile.setState(orderTokens[2]);

        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));

        orderFromFile.setProductType(orderTokens[4]);

        orderFromFile.setArea(new BigDecimal(orderTokens[5]));

        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));

        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));

        orderFromFile.setOrderDate(LocalDate.parse(orderTokens[12]));

        return orderFromFile;

    }

    //reads the file and puts the objects into map
    private void loadOrder(LocalDate ld) throws PersistenceException {

        //first, make sure every order has a date property
        //second, give the right file to process
        LocalDate ldd = ld;

        if ((FLOORING_FILE!=null)&&(FLOORING_FILE.equals("testFile.txt"))){

            Scanner scanner;

            try {
                //Create Scanner for reading the file

                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/" + FLOORING_FILE)));
            } catch (FileNotFoundException e) {
                throw new PersistenceException("-_- Could not load flooring data into memory, and its in LoadOrder(", e);

            }

            String currentLine;

            Order currentOrder;

            while (scanner.hasNextLine()) {

                currentLine = scanner.nextLine();

                currentOrder = unmarshallOrder(currentLine);

                orders.put(currentOrder.getOrderNumber(), currentOrder);
            }

            scanner.close();

        }else{

            File directoryPath = new File("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Orders");
            //List of all files and directories
            String contents[] = directoryPath.list();

            String sss = "Orders" + ldd.toString();

            for (int i = 0; i < contents.length; i++) {

                if (contents[i].equals(sss)) {
                    FLOORING_FILE = contents[i];
                }
            }

            Scanner scanner;

            try {
                //Create Scanner for reading the file

                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Orders/" + FLOORING_FILE)));
            } catch (FileNotFoundException e) {
                throw new PersistenceException("-_- Could not load flooring data into memory, and its in LoadOrder(", e);

            }

            String currentLine;

            Order currentOrder;

            while (scanner.hasNextLine()) {

                currentLine = scanner.nextLine();

                currentOrder = unmarshallOrder(currentLine);

                orders.put(currentOrder.getOrderNumber(), currentOrder);
            }

            scanner.close();

        } 
    }

    //puts the object into a text line
    private String marshallOrder(Order order) {

        String orderAsText = order.getOrderNumber() + DELIMITER;

        orderAsText += order.getCustomerName() + DELIMITER;

        orderAsText += order.getState() + DELIMITER;

        orderAsText += order.getTaxRate() + DELIMITER;

        orderAsText += order.getProductType() + DELIMITER;

        orderAsText += order.getArea() + DELIMITER;

        orderAsText += order.getCostPerSquareFoot() + DELIMITER;

        orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;

        orderAsText += order.getMaterialCost() + DELIMITER;

        orderAsText += order.getLaborCost() + DELIMITER;

        orderAsText += order.getTax() + DELIMITER;

        orderAsText += order.getTotal() + DELIMITER;

        orderAsText += order.getOrderDate() + DELIMITER;

        return orderAsText;
    }

    //writes objects into file
    private void writeOrder(LocalDate lddl) throws PersistenceException, IOException {

        LocalDate ld = lddl;
        
        if ((FLOORING_FILE!=null)&&(FLOORING_FILE.equals("testFile.txt"))){

            PrintWriter scanner;
            try {
                scanner = new PrintWriter(new FileWriter("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/testFile.txt"));
            } catch (FileNotFoundException e) {
                throw new PersistenceException("-_- Could not load flooring data into memory.", e);
            }
            String currentLine;
            for (Map.Entry<Integer, Order> thisOrder : orders.entrySet()) {
                scanner.println(marshallOrder(thisOrder.getValue()));
            }
            scanner.close();

        }else{

            PrintWriter scanner;
            try {
                scanner = new PrintWriter(new FileWriter("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Orders/" + writeToCorrectFile(ld)));
            } catch (FileNotFoundException e) {
                throw new PersistenceException("-_- Could not load flooring data into memory.", e);
            }
            String currentLine;
            for (Map.Entry<Integer, Order> thisOrder : orders.entrySet()) {
                scanner.println(marshallOrder(thisOrder.getValue()));
            }
            scanner.close();

        } 
    }

    private String writeToCorrectFile(LocalDate ld) {

        if (fileForThisDate(ld)) {

            //return fileForThisDate(ld);
            File directoryPath = new File("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Orders");

            //List of all files and directories
            String contents[] = directoryPath.list();

            String sss = "Orders" + ld.toString();

            for (int i = 0; i < contents.length; i++) {

                if (contents[i].equals(sss)) {
                    FLOORING_FILE = contents[i];
                }
            }

        } else {

            String name = "Orders" + ld;

            FLOORING_FILE = name;

        }
        return FLOORING_FILE;
    }

    private boolean fileForThisDate(LocalDate ld) {

        File directoryPath = new File("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Orders");
        //List of all files and directories
        String contents[] = directoryPath.list();

        String ttt = "Orders" + ld.toString();

        for (int i = 0; i < contents.length; i++) {

            if (contents[i].equals(ttt)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void exportAllAvailableData() throws PersistenceException, IOException {

        File dir = new File("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Orders");

        String[] contentos = dir.list();

        for (String conten : contentos) {

            Scanner scanner;

            try {
                //Create Scanner for reading the file

                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Orders/" + conten)));
            } catch (FileNotFoundException e) {
                throw new PersistenceException("-_- Could not load flooring data into memory, and its in export(", e);

            }

            String currentLine;

            Order currentOrder;

            while (scanner.hasNextLine()) {

                currentLine = scanner.nextLine();

                currentOrder = unmarshallOrder(currentLine);

                exports.put(currentOrder.getOrderNumber(), currentOrder);
            }

            scanner.close();

        }

        PrintWriter scanner;
        try {
            scanner = new PrintWriter(new FileWriter("/Users/bobur/Documents/NetBeansProjects/FlooringMastery/Backup/DataExport.txt"));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("-_- Could not load flooring data into memory.", e);
        }
        String currentLine;
        for (Map.Entry<Integer, Order> thisOrder : exports.entrySet()) {
            scanner.println(marshallOrder(thisOrder.getValue()));
        }
        scanner.close();

    }

    @Override
    public void editOrder(Integer orderNumber, Order editedOrder) throws PersistenceException, IOException {

        loadTax();

        if (fileForThisDate(editedOrder.getOrderDate())) {

            loadOrder(editedOrder.getOrderDate());
            
            orders.remove(orderNumber);
         
            Order neworder = orders.put(orderNumber, editedOrder);
            writeOrder(editedOrder.getOrderDate());

        }

    }

}
