
package com.IACCalculatorAndDatabase.servlets;


import com.IACCalculatorAndDatabase.model.IACCalculations;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "iac_calculation", urlPatterns = {"/iac_calculation"})
public class iac_calculation extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        int age = Integer.parseInt(request.getParameter("age"));
        double weight = Double.parseDouble(request.getParameter("weight"));
        double hip_circumference = Double.parseDouble(request.getParameter("hip_circumference"));
        double height = Double.parseDouble(request.getParameter("height"));
        
        try {
        if (age < 18 || age > 120) {
            throw new IllegalArgumentException("Age must be between 18 and 120.");
        }
        if (height < 0.5 || height > 3) {
            throw new IllegalArgumentException("Height must be between 0.5m and 3m.");
        }
        if (weight < 20 || weight > 300) {
            throw new IllegalArgumentException("Weight must be between 20kg and 300kg.");
        }
        if (hip_circumference < 50 || hip_circumference > 200) {
            throw new IllegalArgumentException("Hip circumference must be between 50cm and 200cm.");
        }

    } catch (NumberFormatException e) {
        request.setAttribute("error", "Invalid input, please enter valid numbers.");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } catch (IllegalArgumentException e) {
        request.setAttribute("error", e.getMessage());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
        IACCalculations calc = new IACCalculations();
        double result = calc.CalculateIAC(hip_circumference, height);

  
        request.setAttribute("iac_result", result);
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
        
        
        
        try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace(); 
                throw new ServletException("MySQL JDBC Driver not found", e);
            }
        
        
        String url = "jdbc:mysql://localhost:3306/iaccalculatordatabase?useSSL=false&serverTimezone=UTC";
        String user = "root"; 
        String password = "04ejemploMAYOR";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            
            String query = "INSERT INTO users_iac (user_name, age, sex, height, weight, hip_circumference, iac, calculation_date) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, sex);
            preparedStatement.setDouble(4, height);
            preparedStatement.setDouble(5, weight);
            preparedStatement.setDouble(6, hip_circumference);
            preparedStatement.setDouble(7, result);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
                e.printStackTrace(); 
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                throw new ServletException("Error when connecting to the database", e);
            }
        
        request.setAttribute("iac_result", result);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}


