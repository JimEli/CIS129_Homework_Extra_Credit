/*************************************************************************
 * Title: Loops and Arrays (Extra Credit Lab)
 * File: CIS129_JamesEli_LABec.java
 * Author: James Eli
 * Date: 7/22/2016
 *
 * Program reads 5 series of numbers input by user. Each series is 
 * terminated with a negative number. There is no limit placed on the 
 * amount of numbers per series (it's only limited by heap size). The 5 
 * series of numbers are then totaled and the maximum sum is identified. 
 * The program displays the maximum sum along with the addends which make 
 * up that series. Program uses the Collection, ArrayList, Generics and 
 * Lambdas/Streams features of Java.
 * 
 * Submitted in partial fulfillment of the requirements of PCC CIS-219.
 *************************************************************************/
// Scanner class for user input
import java.util.Scanner;
// List, ArrayList & Collections used to hold series of numbers & manipulate them.
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
// Use lambdas/streams to sum series.
import java.util.stream.IntStream;

public class CIS129_JamesEli_EC {
    // Class-wide declarations.
    // Create a single one-time class-level shared Scanner object for keyboard input.
    // All keyboard input is handled through this class.
    private static Scanner scanner = new Scanner(System.in);
    // Total number of series. The numbers per series is not limited.
    private final static int TOTAL_SERIES = 5;
    
   /*********************************************************************
    * Start of main program. Command line arguments are ignored.
    *********************************************************************/
    public static void main( String[] args ) {
      // Instantiate an ArrayList of ArrayList of Integers.
      ArrayList<ArrayList<Integer>> series = new ArrayList<ArrayList<Integer>>();
      // Instantiate an ArrayList for the sums of the individual series.
      List<Integer> seriesSums = new ArrayList<Integer>();

      // Display program instructions.
      System.out.format( "Enter %d series of numbers, with as many numbers per series as desired.%n", TOTAL_SERIES );
      System.out.println( "Input a negative number to terminate each series." );

      // Input series numbers.
      int number=0; // Temporary holds input.
      for ( int i=0; i<TOTAL_SERIES; i++ ) {
        int count = 1; // Counts numbers in each series.
  
        do {
          try {
            System.out.format( "Enter series %d, number #%d: %n", i + 1, count );
            if ( scanner.hasNextInt() ) {
              number = scanner.nextInt();
              while ( i >= series.size() )   // Add new row?
                series.add( new ArrayList<Integer>() ); 
              series.get( i ).add( number ); // Add element to array.
              count++;
            } else {
              // Eat non-integer input.
              if ( scanner.hasNext() ) scanner.next(); 
              if ( scanner.hasNextLine() ) scanner.nextLine(); 
            }
          } catch ( Exception e ) {
            System.out.println( "Unexpected input received. Try again." );      
          }
        } while ( number >= 0 );
      }
      
      // Cover remainder of program under 1 try block, because an exception inside here would be an array index issue.
      try {
        
        // Use lambdas/streams for summation of arrays.
        for ( int i=0; i<TOTAL_SERIES; i++ ) {
          // Converts ArrayList to int array first, then sums.
          seriesSums.add( i, IntStream.of( series.get( i ).stream()
                                                          .filter( x -> x!=null )
                                                          .mapToInt( Integer::intValue )
                                                          .toArray() )
                                      .filter( y -> y>0 )
                                      .sum() ); // Ignore negative numbers.
        }

        // Maximum sum of all series.
        int maxSum = Collections.max( seriesSums );
      
        // Display maximum series sum and it's addends, if (maxSum!=0).
        System.out.print( "The maximum sum is " + maxSum );
        if ( maxSum > 0  && seriesSums.indexOf( maxSum ) != -1 ) {
          System.out.print(", which was computed using" );
          for ( Integer num : series.get( seriesSums.indexOf( maxSum ) ) ) {
            System.out.print( (num < 0) ? ".\r\n" : ", " + num );
            if ( num < 0 ) 
              break; // Bypass negative valued sentinel.
          }
        } else {
          System.out.println();
        }
        
      } catch ( Exception e ) {
        System.err.println( e.getMessage() );
      }

      // Program terminates here.
      
    } // End of main.

    // Prevent instantiation of this class.
    private CIS129_JamesEli_EC() { }
    
} // End of CIS129_JamesEli_LABec Class



