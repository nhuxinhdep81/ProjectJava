package ra.edu.business.model;

import java.util.Scanner;

public class Admin implements IApp {

    private int id;
    private String username;
    private String password;

    public Admin() {

    }

    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void inputData(Scanner sc) {
        System.out.print("Nhập vào tên admin: ");
        while (true) {
            username = sc.nextLine().trim();
            if (!username.isEmpty()) break;
            System.out.print("Tên không được để trống. Nhập lại: ");
        }

        System.out.print("Nhập vào mật khẩu admin: ");
        while (true) {
            password = sc.nextLine().trim();
            if (!password.isEmpty()) break;
            System.out.print("Mật khẩu không được để trống. Nhập lại: ");
        }
    }

}
