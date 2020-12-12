/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery;

import controller.controller;
import dao.Dao;
import dao.DaoFileImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ServiceLayer;
import service.ServiceLayerImpl;
import view.UserIO;
import view.UserIOconsoleImpl;
import view.View;

/**
 *
 * @author bobur
 */
public class App {
    
    public static void main(String[] args) {
    	
//        UserIO myIO = new UserIOconsoleImpl();
//        
//        View myView = new View(myIO);
//        
//        Dao myDAO = new DaoFileImpl();
//        
//        ServiceLayer myService = new ServiceLayerImpl(myDAO);
//        
//        Controller controller = new Controller(myService, myView);
//        
//        controller.run();
    	
    	
   	
//    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//    	       Controller controller = 
//    	           ctx.getBean("Controller", Controller.class);
//    	        controller.run();

    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
    appContext.scan("com.mycompany.flooringmastery");
    appContext.scan("controller");
    appContext.scan("dao");
    appContext.scan("dto");
    appContext.scan("service");
    appContext.scan("view");
    appContext.refresh();
    
    
    controller contr = appContext.getBean("controller", controller.class);
    contr.run();
    	
    	
    }
    
    
}
