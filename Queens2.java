import java.lang.Math;
import java.util.*;

/* DO NOT REMOVE THESE COMMENTS
 *
 * YOU NEED TO ADD YOUR CODE TO THIS CLASS, REMOVING ALL DUMMY CODE
 *
 * DO NOT CHANGE THE NAME OR SIGNATURE OF ANY OF THE EXISTING METHODS
 * (Signature includes parameter types, return types and being static)
 *
 * You can add private methods to this class if it makes your code cleaner,
 * but this class MUST work with the UNMODIFIED Tester2.java class.
 *
 * This is the ONLY class that you can submit for your assignment.
 *
 * This is an INDIVIDUAL assignment to be completed ALONE.
 * You cannot use an AI in any way.
 * Cheating or collusion will cause severe penalties.
 *
 * MH DOC_24.02.23.10.23
 */
public class Queens2
{
    private static int boardSize = 10;
    static String problem = "N Queens";
    
    // ************************************************************************
    // ************ A. GENETIC DIVERSITY - CALCULATE MEAN HAMMING DISTANCE ****
    // ************************************************************************
    
    /* calculates a measure of genetic diversity
     * useful as a termination criterion (low diversity means convergence)
     * (see the pseudocode in the assignment module for how to calculate)
     * 
     * this method will take a population of genotypes as input and:
     *	a) identify the genotype with the highest fitness, then
     *  b) calculate the mean hamming distance to all other individuals
     *     in the population
     *  c) return that distance as a double
     *
     * in the event of there being two or more genotypes with the highest fitness,
     * you can choose any of those genotypes as the "fittest member"
     */
    public static double meanHammingDistance(Integer[][] population)
    {
    	// the mean hamming distance to be calculated
    	double distance = 0.0;
    	
    	// knowing the fitness of each member of the population will be useful!
    	Integer[] fitnesses = getFitnesses(population);
    	
    	// YOUR CODE GOES HERE
        int maxFitnessIndex = 0;
        for (int i = 1; i < fitnesses.length; i++) {
            if (fitnesses[i] > fitnesses[maxFitnessIndex]) {
                maxFitnessIndex = i;
            }
        }

        double sumOfDistances = 0.0;
        int count = 0;
        for (int i = 0; i < population.length; i++) {
            if (i != maxFitnessIndex) {
                int distanceForThisIndividual = 0;
                for (int j = 0; j < population[i].length; j++) {
                    if (!population[maxFitnessIndex][j].equals(population[i][j])) {
                        distanceForThisIndividual++;
                    }
                }
                sumOfDistances += distanceForThisIndividual;
                count++;
            }
        }

        if (count > 0) {
            distance = sumOfDistances / count;
        }
        // END OF YOUR CODE
        
        return distance;
    }

    
    // ************************************************************************
    // ************ B. TOURNAMENT PARENT SELECTION PART ONE *******************
    // ************    CHOOSING A RANDOM SET OF UNIQUE COMPETITORS ************
    // ************************************************************************
    
    /* a worker method for tournament parent selection
     * randomly chooses a set of t members from the population
     * 
     * @input: the size of the population
     * @output: a size t array of *unique* random integers from the interval
     *          [0, population size]
     *
     * Example: if populationSize = 10 and t = 3 then typical output is: 5,2,7
     */
    public static Integer[] chooseCompetitors(int populationSize, int tournamentSize)
    {
    	Integer[] competitors = new Integer[tournamentSize];
    	
    	// YOUR CODE GOES HERE
        HashSet<Integer> chosen = new HashSet<>();
        Random rand = new Random();
        int count = 0;

        while (count < tournamentSize) {
            int nextCompetitor = rand.nextInt(populationSize);
            if (chosen.add(nextCompetitor)) {
                competitors[count++] = nextCompetitor;
            }
        }
        // END OF YOUR CODE
        
        return competitors;
    }
    
    
	// ************************************************************************
    // ************ C. TOURNAMENT PARENT SELECTION PART TWO *******************
    // ************    SELECTING THE BEST FROM A SET OF COMPETITORS ***********
    // ************************************************************************
    
    /* a worker method for tournament parent selection
     * chooses the fittest member from a set of t competitors
     * 
     * @input: a list of indices of competitors (see B above)
     * @input: the *entire* population of genotypes (so fitnesses can be
     *         measured)
     * @output: the population index of the fittest *competitor*
     *
     * Example: suppose a population of size 10
     *          suppose that five chosen competitors are at indices 1,4,5,3,6
     *          suppose their respective fitnesses are 60,42,56,62,38
     *          then the genotype at index 3 has best fitness (=62)
     *          so the method will return 3
     *
     * in the event of two or more competitors tying for highest fitness,
     * this method will randomly choose one of those competitors
     */
    public static int parentSelection(Integer[] competitors, Integer[][] population)
    {
    	// YOUR CODE GOES HERE
        int bestCompetitor = competitors[0];
        int highestFitness = Queens.getFitness(population[bestCompetitor]);
        ArrayList<Integer> tiedCompetitors = new ArrayList<>();
        tiedCompetitors.add(bestCompetitor);

        for (int i = 1; i < competitors.length; i++) {
            int currentFitness = Queens.getFitness(population[competitors[i]]);

            if (currentFitness > highestFitness) {
                highestFitness = currentFitness;
                bestCompetitor = competitors[i];
                tiedCompetitors.clear();
                tiedCompetitors.add(bestCompetitor);
            } else if (currentFitness == highestFitness) {
                tiedCompetitors.add(competitors[i]);
            }
        }
        if (tiedCompetitors.size() > 1) {
            Random rand = new Random();
            bestCompetitor = tiedCompetitors.get(rand.nextInt(tiedCompetitors.size()));
        }
        // END OF YOUR CODE
    	
    	return bestCompetitor;
    }
    
    
    // ************************************************************************
    // ************ D. (μ, λ) SURVIVOR SELECTION ******************************
    // ************************************************************************
    
    /* creates a new population through (μ, λ) survivor selection
     * given a required population of size n, and a set of children of size 2n,
     * this method will return the n fittest children as the new population
     * (see class slides & course text)
     */
    public static Integer[][] survivorSelection(Integer[][] children, int n)
    {
        Integer [][] newPopulation = new Integer [n][boardSize];
        
        // YOUR CODE GOES HERE
        int childrenCount = children.length;

        int[][] fitnesses = new int[childrenCount][2];
        for (int i = 0; i < childrenCount; i++) {
            fitnesses[i][0] = Queens.getFitness(children[i]);
            fitnesses[i][1] = i;
        }

        Arrays.sort(fitnesses, (a, b) -> b[0] - a[0]);

        for (int i = 0; i < n; i++) {
            newPopulation[i] = children[fitnesses[i][1]];
        }
        // END OF YOUR CODE
        
        return newPopulation;
    }
    
    // ************************************************************************
    // ************ E.  SCRAMBLE MUTATION ************************************
    // ************************************************************************
    
    /* scrambles the order of a series of genes in the genotype
     * (see class slides & course text)
     */
    public static Integer[] scramble(Integer[] genotype, double p)
    {
    	// YOUR CODE GOES HERE
        if (Math.random() <= p) {
            Random rand = new Random();
            int start = rand.nextInt(genotype.length);
            int end = rand.nextInt(genotype.length);

            if (start > end) {
                int temp = start;
                start = end;
                end = temp;
            }

            List<Integer> subList = Arrays.asList(genotype).subList(start, end + 1);
            Collections.shuffle(subList);
            for (int i = start; i <= end; i++) {
                genotype[i] = subList.get(i - start);
            }
        }
        // END OF YOUR CODE
    	
        return genotype;
    }
    
    // ************ DO NOT EDIT OR DELETE THE METHOD BELOW! *******************
    
    // ************************************************************************
    // ************ GET THE FITNESS VALUES OF A POPULATION ********************
    // ************************************************************************
    
    // returns an array of fitnesses for a population
    private static Integer[] getFitnesses(Integer [][] population)
    {
        int popSize = population.length;
        Integer [] fitnesses = new Integer [popSize];
        
        for (int index = 0; index < popSize; index ++)
        {
            fitnesses[index] = Queens.getFitness(population[index]);
        }
        
        return fitnesses;
    }
}