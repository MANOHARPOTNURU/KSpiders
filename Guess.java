package Kspiders;

import java.util.Scanner;
import java.util.Random;

public class Guess {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        
        int numberToGuess = random.nextInt(10) + 1; // Generates 1 to 10
        int userGuess = 0;
        
        System.out.println("I'm thinking of a number between 1 and 10. Can you guess it?");
        
        while (userGuess != numberToGuess) {
            System.out.print("Enter your guess: ");
            userGuess = input.nextInt();
            
            if (userGuess < numberToGuess) {
                System.out.println("Too low! Try again.");
            } else if (userGuess > numberToGuess) {
                System.out.println("Too high! Try again.");
            } else {
                System.out.println("🎉 Correct! You guessed the number!");
            }
        }
        
        input.close();
    }
}
