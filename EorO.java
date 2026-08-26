package Kspiders;
import java.util.Scanner;


public class EorO {

	public static void main(String[] args) {
Scanner input = new Scanner(System.in);
        
        System.out.print("Enter any whole number: ");
        int number = input.nextInt();
        
        if (number % 2 == 0) {
            System.out.println(number + " is an EVEN number.");
        } else {
            System.out.println(number + " is an ODD number.");
        }
        
        input.close();
		// TODO Auto-generated method stub

	}

}
