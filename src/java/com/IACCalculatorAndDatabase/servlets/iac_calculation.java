
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

/**
 *
 * @author Angel
 */
@WebServlet(name = "iac_calculation", urlPatterns = {"/iac_calculation"})
public class iac_calculation extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String sex = request.getParameter("sex");
        double weight = Double.parseDouble(request.getParameter("weight"));
        double hip_circumference = Double.parseDouble(request.getParameter("hip_circumference"));
        double height = Double.parseDouble(request.getParameter("height"));
        
        IACCalculations calc = new IACCalculations();
        double result = calc.CalculateIAC(hip_circumference, height);
        
        try {
                // Intentar cargar el controlador JDBC de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  // Esto te ayudará a ver si hubo un error cargando el controlador
                throw new ServletException("MySQL JDBC Driver not found", e);
            }
        
        
        String url = "jdbc:mysql://localhost:3306/iaccalculatordatabase?useSSL=false&serverTimezone=UTC";
        String user = "root"; // O el usuario que estés usando para acceder
        String password = "04ejemploMAYOR";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            
            // Preparar la sentencia SQL para insertar los datos
            String query = "INSERT INTO users_iac (user_name, age, sex, height, weight, hip_circumference, iac, calculation_date) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, sex);
            preparedStatement.setDouble(4, height);
            preparedStatement.setDouble(5, weight);
            preparedStatement.setDouble(6, hip_circumference);
            preparedStatement.setDouble(7, result);

            // Ejecutar la inserción
            preparedStatement.executeUpdate();

            // Cerrar la conexión
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
                e.printStackTrace(); // Muestra el error en la consola
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                throw new ServletException("Error when connecting to the database", e);
            }
        
        // Enviar el resultado a la JSP
        request.setAttribute("iac_result", result);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
