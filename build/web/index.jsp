<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IAC.CALCULATOR</title>
    </head>
    <body>
        <h1>IAC.CALCULATOR</h1>
        <form action="iac_calculation" method="post">
            NAME: <input type="text" name="name" required><br>  <!-- Corregido a text -->
            AGE: <input type="number" name="age" required><br>  <!-- Se mantiene number -->
            SEX: 
            <select name="sex" required>                       <!-- Usamos un select para sex -->
                <option value="M">Male</option>
                <option value="F">Female</option>
            </select><br>
            HEIGHT (m): <input type="number" name="height" step="0.01" required><br> 
            WEIGHT (kg): <input type="number" name="weight" step="0.1" required><br> 
            HIP CIRCUMFERENCE (cm): <input type="number" name="hip_circumference" step="0.1" required><br>

            <input type="submit" value="CALCULATE IAC.">
        </form>
        
        <% if (request.getAttribute("iac_result") != null) { %>
            <p>YOUR IAC RESULT IS. <%= request.getAttribute("iac_result") %></p>
        <% } %>
    </body>
</html>
