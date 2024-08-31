
import java.util.Random;
import java.util.Scanner;

public class GuessNum {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int rounds = 3;  // Number of rounds
        int maxAttempts = 5;  // Max attempts per round
        int score = 0;  // Player's score

        System.out.println("Welcome to the Guess the Number Game!");

        for (int i = 1; i <= rounds; i++) {
            int numberToGuess = random.nextInt(100) + 1;  // Random number between 1 and 100
            int attempts = 0;
            boolean isGuessed = false;
            
            System.out.println("\nRound " + i + ":");
            System.out.println("You have " + maxAttempts + " attempts to guess the number between 1 and 100.");
            
            while (attempts < maxAttempts && !isGuessed) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;
                
                if (userGuess == numberToGuess) {
                    System.out.println("Congratulations! You guessed the number.");
                    isGuessed = true;
                    score += (maxAttempts - attempts + 1) * 10;  // Higher points for fewer attempts
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }
            
            if (!isGuessed) {
                System.out.println("Sorry, you've used all your attempts. The number was: " + numberToGuess);
            }
        }
        
        System.out.println("\nGame over! Your final score is: " + score);
        scanner.close();
    }
}
