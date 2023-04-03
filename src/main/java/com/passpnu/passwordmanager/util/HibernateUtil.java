package com.passpnu.passwordmanager.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil(){}

    public static  SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }catch (Exception e){
                System.out.println("Error: " + e);
            }
        }
        return sessionFactory;
    }
}