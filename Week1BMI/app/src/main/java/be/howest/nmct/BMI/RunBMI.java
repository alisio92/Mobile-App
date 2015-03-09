package be.howest.nmct.BMI;

import java.util.Scanner;

/**
 * Created by alisio on 12/02/2015.
 */
public class RunBMI {
    public static void main(String[] args){
        getBMI();
    }

    private static void getBMI(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your height(in m, eg: 1,72): ");
        float height = scanner.nextFloat();

        System.out.println("Enter your mass(in kg, eg: 70): ");
        int mass = scanner.nextInt();

        BMIInfo info = new BMIInfo(height, mass);
        float index = info.getBmiIndex();
        System.out.println("Your body mass index is: " + index + ", you are in the category " + BMIInfo.Category.getCategory(index));
    }
}
