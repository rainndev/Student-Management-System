/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem.util;

import javafx.scene.control.ComboBox;

/**
 *
 * @author rainndev
 */
public class Validator {
    public static boolean isRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isNumeric(String value) {
        return value != null && value.matches("\\d+");
    }

    public static boolean isSelected(ComboBox<?> comboBox) {
        return comboBox != null && comboBox.getValue() != null;
    }

    public static boolean hasMinLength(String value, int min) {
        return value != null && value.trim().length() >= min;
    }

    public static boolean hasMaxLength(String value, int max) {
        return value != null && value.trim().length() <= max;
    }

    public static boolean isDateSelected(Object date) {
        return date != null;
    }
    
    public static boolean isAlpha(String value) {
       return value != null && value.matches("[A-Za-z]+");
    }
    
    public static boolean isBigDecimalNumeric(String value) {
        // 1. Check for null or empty string first
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try {
            new java.math.BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
