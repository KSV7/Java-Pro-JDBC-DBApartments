package com.gmail.kutepov89.sergey;

import java.sql.*;
import java.util.Scanner;

public class Apartments {
    private String district;
    private String address;
    private double area;
    private int numberRoom;
    private int price;

    public Apartments() {
        super();
    }

    public Apartments(String district, String address, double area, int numberRoom, int price) {
        this.district = district;
        this.address = address;
        this.area = area;
        this.numberRoom = numberRoom;
        this.price = price;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    void initDB() throws SQLException {
        DbProperties props = new DbProperties();
        try (Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword())) {
            try (Statement st = conn.createStatement()) {
                st.execute("DROP TABLE IF EXISTS Apartments");
                st.execute("CREATE TABLE Apartments (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                        ", district VARCHAR(64) NOT NULL" +
                        ", address VARCHAR(128) NOT NULL" +
                        ", area DOUBLE" +
                        ", numberRoom INT" +
                        ", price DOUBLE)"
                );
            } finally {
                conn.close();
            }
        }
    }

    void addApartment() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter apartment district: ");
        String district = sc.nextLine();
        System.out.print("Enter apartment address: ");
        String address = sc.nextLine();
        System.out.print("Enter apartment area: ");
        String areaVal = sc.nextLine();
        double area = Double.parseDouble(areaVal);
        System.out.print("Enter apartment number room: ");
        String numberRoomVal = sc.nextLine();
        int numberRoom = Integer.parseInt(numberRoomVal);
        System.out.print("Enter apartment price: ");
        String priceVal = sc.nextLine();
        double price = Double.parseDouble(priceVal);

        DbProperties props = new DbProperties();
        Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Apartments (district" +
                ", address" +
                ", area" +
                ", numberRoom" +
                ", price)" +
                " VALUES(?, ?, ?, ?, ?)")) {
            ps.setString(1, district);
            ps.setString(2, address);
            ps.setDouble(3, area);
            ps.setInt(4, numberRoom);
            ps.setDouble(5, price);
            ps.executeUpdate();
        }
    }

    void viewAllApartments() throws SQLException {
        DbProperties props = new DbProperties();
        Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments")) {
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(md.getColumnName(i) + "\t\t\t");
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(rs.getString(i) + "\t\t\t");
                    }
                    System.out.println();
                }
            } finally {
                conn.close();
            }
        }
    }

    void getApartmentsForDistrict() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter apartment district: ");
        String district = sc.nextLine();

        DbProperties props = new DbProperties();
        Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments WHERE district = ?");
        ps.setString(1, district);
        getResForParameter(ps);
    }

    void getApartmentsForAddress() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter apartment address: ");
        String address = sc.nextLine();

        DbProperties props = new DbProperties();
        Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments WHERE address = ?");
        ps.setString(1, address);
        getResForParameter(ps);
    }

    void getApartmentsForArea() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter apartment area: ");
        double area = sc.nextDouble();

        DbProperties props = new DbProperties();
        Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments WHERE area = ?");
        ps.setDouble(1, area);
        getResForParameter(ps);
    }

    void getApartmentsForPrice() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter apartment price: ");
        double price = sc.nextDouble();

        DbProperties props = new DbProperties();
        Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments WHERE price = ?");
        ps.setDouble(1, price);
        getResForParameter(ps);
    }

    void getApartmentsForNumberRoom() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter apartment number room: ");
        int numberRoom = sc.nextInt();

        DbProperties props = new DbProperties();
        Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments WHERE numberRoom = ?");
        ps.setInt(1, numberRoom);
        getResForParameter(ps);
    }

    private void getResForParameter(PreparedStatement ps) throws SQLException {
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {

            int id = resultSet.getInt("Id");
            String addressRes = resultSet.getString("address");
            int priceRes = resultSet.getInt("Price");

            System.out.printf("%d. %s - %d \n", id, addressRes, priceRes);
        }
    }

    @Override
    public String toString() {
        return "Apartments{" +
                "district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", number_rooms=" + numberRoom +
                ", price=" + price +
                '}';
    }
}