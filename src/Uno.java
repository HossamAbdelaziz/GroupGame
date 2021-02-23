/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hossam
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Uno
{
    public static void main(String[] args)
    {
        ArrayList<Unocard> playerdeck = new ArrayList<>();
        ArrayList<Unocard> compdeck = new ArrayList<>();
        int win; 
        Scanner input;
        Unocard topCard; 
        int choiceIndex; 
        String currentColor; 

        gameLoop:
        while (true)
        {
            playerdeck.clear();
            compdeck.clear();
            win = 0;
            topCard = new Unocard();
            currentColor = topCard.color;

            System.out.println("\nWelcome to Uno! Initialising decks...");
            draw(7, playerdeck);
            draw(7, compdeck);

            
            for (boolean playersTurn = true; win == 0; playersTurn ^= true)
            {
                choiceIndex = 0;
                System.out.println("\nThe top card is: " + topCard.getFace());

                if (playersTurn) 
                {
                    
                    System.out.println("Your turn! Your choices:");
                    for (int i = 0; i < playerdeck.size(); i++)
                    {
                        System.out.print(String.valueOf(i + 1) + ". " + 
                        ((Unocard) playerdeck.get(i) ).getFace() + "\n");
                    }
                    System.out.println(String.valueOf(playerdeck.size() + 1 ) + ". " + "Draw card" + "\n" + 
                                       String.valueOf(playerdeck.size() + 2) + ". " + "Quit");
                    
                    do
                    {
                        System.out.print("\nPlaease input the number of your choice: ");
                        input = new Scanner(System.in);
                    } while (!input.hasNextInt() );
                    choiceIndex = input.nextInt() - 1;

                    if (choiceIndex == playerdeck.size() )
                        draw(1, playerdeck);
                    else if (choiceIndex == playerdeck.size() + 1)
                        break gameLoop;
                    else if ( ((Unocard) playerdeck.get(choiceIndex)).canPlace(topCard, currentColor) )
                    {
                        topCard = (Unocard) playerdeck.get(choiceIndex);
                        playerdeck.remove(choiceIndex);
                        currentColor = topCard.color;


                        if (topCard.value >= 10)
                        {
                            playersTurn = false; 

                            switch (topCard.value)
                            {
                                case 12 -> {
                                    // Draw 2
                                    System.out.println("Drawing 2 cards...");
                                    draw(2,compdeck);
                                }

                                case 13, 14 -> {
                                    // Wild cards
                                    do // Repeats every time the user doesn't input a valid color
                                    {
                                        System.out.print("\nEnter the color you want: ");
                                        input = new Scanner(System.in);
                                    } while (!input.hasNext("R..|r..|G....|g....|B...|b...|Y.....|y.....") ); //Something I learned recently
                                    if (input.hasNext("R..|r..") )
                                        currentColor = "Red";
                                    else if (input.hasNext("G....|g....") )
                                        currentColor = "Green";
                                    else if (input.hasNext("B...|b...") )
                                        currentColor = "Blue";
                                    else if (input.hasNext("Y.....|y.....") )
                                        currentColor = "Yellow";
                                    
                                    System.out.println("You chose " + currentColor);
                                    if (topCard.value == 14) // Wild draw 4
                                    {
                                        System.out.println("Drawing 4 cards...");
                                        draw(4,compdeck);
                                    }
                                }                            }
                        }
                    } else System.out.println("Invalid choice. Turn skipped.");
                    


                } else 
                {
                    System.out.println("My turn! I have " + String.valueOf(compdeck.size() ) 
                                        + " cards left!" + ((compdeck.size() == 1) ? "...Uno!":"") );



                    for (choiceIndex = 0; choiceIndex < compdeck.size(); choiceIndex++)
                    {
                        if ( ((Unocard) compdeck.get(choiceIndex)).canPlace(topCard, currentColor) ) // Searching for playable cards
                            break; 
                    }

                    if (choiceIndex == compdeck.size() )
                    {
                         System.out.println("I've got nothing! Drawing cards...");
                         draw(1,compdeck);
                    } else 
                    {
                         topCard = (Unocard) compdeck.get(choiceIndex);
                         compdeck.remove(choiceIndex);
                         currentColor = topCard.color;
                         System.out.println("I choose " + topCard.getFace() + " !");

                         if (topCard.value >= 10)
                         {
                             playersTurn = true; 

                             switch (topCard.value)
                             {
                                 case 12 -> {


                                     System.out.println("Drawing 2 cards for you...");
                                     draw(2,playerdeck);
                                 }

                                 case 13, 14 -> {
                                     
                                     do 
                                     {
                                         currentColor = new Unocard().color;
                                     } while ("none".equals(currentColor));
                                     
                                     System.out.println("New color is " + currentColor);
                                     if (topCard.value == 14) 
                                     {
                                         System.out.println("Drawing 4 cards for you...");
                                         draw(4,playerdeck);
                                     }
                                 }                             }
                         }
                    }

                    
                    if (playerdeck.isEmpty())
                        win = 1;
                    else if (compdeck.isEmpty())
                        win = -1;
                }

            } 

            
            if (win == 1)
                System.out.println("You win :)");
            else 
                System.out.println("You lose :(");

            System.out.print("\nPlay again? ");
            input = new Scanner(System.in);

            if (input.next().toLowerCase().contains("n") )
                break;
        } 

        System.out.println("Bye bye");
    }
    
    public static void draw(int cards, ArrayList<Unocard> deck)
    {
        for (int i = 0; i < cards; i++)
            deck.add(new Unocard() );
    }
}
