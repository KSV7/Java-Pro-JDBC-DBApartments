package com.gmail.kutepov89.sergey;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException{
        Apartments ap = new Apartments();
        // Create table
        // ap.initDB();

        // Add apartment
        // ap.addApartment();

        // View all apartments
        ap.viewAllApartments();

        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Search apartment by parameter");
                System.out.println("1 - district");
                System.out.println("2 - address");
                System.out.println("3 - area");
                System.out.println("4 - number room");
                System.out.println("5 - price");
                System.out.println("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        ap.getApartmentsForDistrict();
                        break;
                    case "2":
                        ap.getApartmentsForAddress();
                        break;
                    case "3":
                        ap.getApartmentsForArea();
                        break;
                    case "4":
                        ap.getApartmentsForNumberRoom();
                        break;
                    case "5":
                        ap.getApartmentsForPrice();
                        break;
                    default:
                        return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
