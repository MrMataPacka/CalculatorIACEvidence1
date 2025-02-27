
package com.IACCalculatorAndDatabase.model;

public class IACCalculations 
{
    public double CalculateIAC(double hip_circumference, double height)
    {
        double heightPow = Math.pow(height, 1.5);
        return (hip_circumference/heightPow)-18;
    }
}
