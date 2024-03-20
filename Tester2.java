/*
 *  DO NOT SUBMIT THIS CLASS WITH YOUR ASSIGNMENT
 *
 *  This class is only provided so that you can test your code
 *  for Assignment 4.
 *
 *  ALL your code MUST be placed in the Queens2.java class.
 *
 *  MH February 2024
 */
public class Tester2
{
    private static int boardSize = 10;
    
	public static void main(String[] args)
	{
        // TEST A: measure genetic diversity using mean hamming distance
        // create a new population to test
        int popSize = 10;
        Integer [][] population1 = new Integer [popSize][boardSize];
        population1 [0] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        population1 [1] = new Integer[]{ 9, 5, 6, 10, 8, 7, 1, 3, 2, 4 };
        population1 [2] = new Integer[]{ 9, 4, 3, 1, 2, 5, 10, 7, 8, 6 };
        population1 [3] = new Integer[]{ 7, 8, 9, 1, 10, 2, 3, 4, 5, 6 };
        population1 [4] = new Integer[]{ 10, 6, 4, 2, 8, 5, 9, 1, 3, 7 };
        population1 [5] = new Integer[]{ 3, 2, 7, 4, 10, 1, 8, 9, 6, 5 };
        population1 [6] = new Integer[]{ 10, 9, 8, 6, 7, 2, 3, 4, 1, 5 };
        population1 [7] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        population1 [8] = new Integer[]{ 9, 5, 6, 10, 8, 7, 1, 3, 2, 4 };
        population1 [9] = new Integer[]{ 9, 4, 3, 1, 2, 5, 10, 7, 8, 6 };
        
        double actual = 9.444444444444445; // mean hamming distance from best individual
        System.out.println("\nA. Testing Genetic Diversity:");
        try
        {
        	double returned = Queens2.meanHammingDistance(population1);
        	System.out.println("Mean Hamming distance from best individual:");
        	System.out.println("Correct: " + actual);
        	System.out.println("Computed: " + returned);
        }
        catch (Exception ex)
        {
          	System.out.println("\n ! mean Hamming code caused an exception !");
        }

        
        // TEST B: choose unique random competitors
        int tournamentSize;
        System.out.println("\nB. Testing Competitor Choosing:");
        try
        {
        	for (int index = 0; index < 5; index ++)
        	{
        		popSize = 10 + 10*((int)(Math.random() * 4));
        		tournamentSize = 3 + (int)(Math.random() * 2);
        		System.out.println("\nRun " + index + ": Population size = "
        					+ popSize + ", tournament size = " + tournamentSize);
        		System.out.print("Competitors chosen: ");
        		Integer[] competitors = Queens2.chooseCompetitors(popSize, 	tournamentSize);
        		printArray(competitors);
        	}
        }
        catch (Exception ex)
        {
           System.out.println("\n ! competitor choosing code caused an exception !");
        }
        
        // TEST C: select best competitor from a set of competitors
        popSize = 100;
        tournamentSize = 5;
        Integer [][] population2 = createTestPopulation(popSize, false);
        
        // hardcoded chosen set (avoids double-jeopardy if code for B is broken)
        Integer [] chosen = new Integer[]{2,4,6,8,10};
        
        System.out.println("\nC. Testing Parent Selection:");
        try
        {
        	int parent = Queens2.parentSelection(chosen, population2);
        	System.out.println("\nTournament of size 5 from population of size 100.");
        	System.out.println("\nThe 5 chosen competitors have these fitnesses:");
        	for (int index = 0; index < 5; index ++)
        	{
        		Integer [] thisGeno = population2[chosen[index]];
        		System.out.println("Population Member #" + chosen[index] +
        		" has fitness = " + Queens.getFitness(thisGeno));
        	}
        
        	System.out.println("\nThe competitor selected to be a parent is: #" + parent);
        }
        catch (Exception ex)
        {
           System.out.println("\n ! parent selection code caused an exception !");
        }
                
        // TEST D: perform (μ, λ) survivor selection (replace population with best children)
        // Initialize population
        popSize = 10;
        Integer [][] population3 = new Integer [popSize][boardSize];
        
        System.out.println("\nD. Testing (μ, λ) Survivor Selection:");
        try
        {
        	// create random population of children
        	System.out.println("\nPool of 20 Children to select from:");
        	Integer [][] children = createTestPopulation(popSize * 2, true);
        
       		population3 = Queens2.survivorSelection(children, popSize);
        
        	System.out.println("\nFittest 10 children selected to form the new population:");
        	for (int index = 0; index < popSize; index ++)
        	{
         	   System.out.print(index + ": Fitness: " + Queens.getFitness(population3[index]) + ", Genotype: ");
         	   printArray(population3[index]);
        	}
        }
        catch (Exception ex)
        {
           System.out.println("\n ! survivor selection code caused an exception !");
        }
        
        
        // TEST E: perform scramble mutation on the population
        // create a new population to test
        popSize = 5;
        Integer [][] population4 = new Integer [popSize][boardSize];
        population4 [0] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        population4 [1] = new Integer[]{ 9, 5, 6, 10, 8, 7, 1, 3, 2, 4 };
        population4 [2] = new Integer[]{ 9, 4, 3, 1, 2, 5, 10, 7, 8, 6 };
        population4 [3] = new Integer[]{ 7, 8, 9, 1, 10, 2, 3, 4, 5, 6 };
        population4 [4] = new Integer[]{ 10, 6, 4, 2, 8, 5, 9, 1, 3, 7 };
        
        System.out.println("\nE. Testing scramble mutation: (60% rate) ");
        try
        {
        	for (int index = 0; index < popSize; index ++)
        	{
        	    System.out.print(index + ". Before: ");
        	    printArray( population4[index]);
       	     population1 [index] = Queens2.scramble(population4[index], 0.6);
            	System.out.print("   After:  ");
            	printArray( population1[index]);
            	System.out.println();
        	}
        }
        catch (Exception ex)
        {
           System.out.println("\n ! scramble mutation code caused an exception !");
        }
        
        System.out.println("END OF TESTING\n\n");
	}
    
    // worker method prints the contents of an integer array to console
    public static void printArray(Integer[] array)
    {
        for (int index = 0; index < array.length; index ++)
        {
            System.out.print(" " + array[index]);
        }
        System.out.println();
    }
    
    // worker method prints the contents of a double array to console (to 4 d.p.)
    public static void printArray(double[] array, int precision)
    {
        for (int index = 0; index < array.length; index ++)
        {
            System.out.print(" ");
            System.out.printf("%."+precision+"f", array[index]);
        }
        System.out.println();
    }
    
    // worker method creates a randomly seeded population of the required size
    public static Integer[][] createTestPopulation(int popSize, boolean print)
    {
        Integer[][] testPop = new Integer[popSize][boardSize];
        
        for (int index = 0; index < popSize; index ++)
        {
            testPop [index] = Queens.randomGeno();
            if (print)
            {
            	System.out.print(index + ": Fitness: " + Queens.getFitness(testPop[index]) + ", Genotype: ");
            	printArray(testPop[index]);
            }
        }
        
        return testPop;
    }
    
    // outputs a genotype to console
    public static void printGenotype(Integer[] genotype)
    {
        for (int index = 0; index < 10; index ++)
        {
            System.out.print(" " + genotype[index]);
        }
        System.out.println();
    }
}
