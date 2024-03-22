import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Queens {
    private static int boardSize = 10;

    // creates a valid genotype with random values
    public static Integer[] randomGeno() {
        Integer[] genotype = new Integer[boardSize];
        for (int i = 0; i < boardSize; i++) {
            genotype[i] = i + 1;
        }
        Collections.shuffle(Arrays.asList(genotype));
        return genotype;
    }

    // swaps 2 genes in the genotype
    // the swap happens with probability p
    public static Integer[] swapMutation(Integer[] genotype, double p) {
        Random rand = new Random();
        for (int i = 0; i < boardSize; i++) {
            if (rand.nextDouble() <= p) {
                int swapIndex = rand.nextInt(boardSize);
                // Swap values at index i and swapIndex
                int temp = genotype[i];
                genotype[i] = genotype[swapIndex];
                genotype[swapIndex] = temp;
            }
        }
        return genotype;
    }


    public static Integer[][] cutAndCrossfill(Integer[] parent1, Integer[] parent2) {
        Integer[][] children = new Integer[2][boardSize];
        int crossoverPoint = boardSize / 2;

        // Copy first half from parent1 to child1
        for (int i = 0; i < crossoverPoint; i++) {
            children[0][i] = parent1[i];
        }

        // Fill the second half of child1 from parent2, skipping already filled values
        int childIndex = crossoverPoint;
        for (int i = crossoverPoint; i < boardSize; i++) {
            int geneIndex = i;
            int gene = parent2[geneIndex];
            while (Arrays.asList(children[0]).contains(gene)) {
                geneIndex = (geneIndex + 1) % boardSize; // Wrap around to the beginning if reached the end
                gene = parent2[geneIndex];
            }
            children[0][childIndex++] = gene;
        }

        // Copy first half from parent2 to child2
        for (int i = 0; i < crossoverPoint; i++) {
            children[1][i] = parent2[i];
        }

        // Fill the second half of child2 from parent1, skipping already filled values
        childIndex = crossoverPoint;
        for (int i = crossoverPoint; i < boardSize; i++) {
            int geneIndex = i;
            int gene = parent1[geneIndex];
            while (Arrays.asList(children[1]).contains(gene)) {
                geneIndex = (geneIndex + 1) % boardSize; // Wrap around to the beginning if reached the end
                gene = parent1[geneIndex];
            }
            children[1][childIndex++] = gene;
        }

        return children;
    }

//    public static Integer[][] cutAndCrossfill(Integer[] parent1, Integer[] parent2) {
//        Integer[][] children = new Integer[2][boardSize];
//        int crossoverPoint = boardSize / 2;
//
//        // Copy first half from parent1 to child1
//        for (int i = 0; i < crossoverPoint; i++) {
//            children[0][i] = parent1[i];
//        }
//
//        // Fill the second half of child1 from parent2, skipping already filled values
//        int childIndex = crossoverPoint;
//        for (int i = crossoverPoint; i < boardSize; i++) {
//            int gene = parent2[i];
//            if (!Arrays.asList(children[0]).contains(gene)) {
//                children[0][childIndex++] = gene;
//            }
//        }
//
//        // Copy first half from parent2 to child2
//        for (int i = 0; i < crossoverPoint; i++) {
//            children[1][i] = parent2[i];
//        }
//
//        // Fill the second half of child2 from parent1, skipping already filled values
//        childIndex = crossoverPoint;
//        for (int i = crossoverPoint; i < boardSize; i++) {
//            int gene = parent1[i];
//            if (!Arrays.asList(children[1]).contains(gene)) {
//                children[1][childIndex++] = gene;
//            }
//        }
//
//        return children;
//    }




    // calculates the fitness of an individual
    public static int getFitness(Integer[] genotype) {
        int fitness = (int) (0.5 * boardSize * (boardSize - 1));
        for (int i = 0; i < boardSize - 1; i++) {
            for (int j = i + 1; j < boardSize; j++) {
                if (genotype[i] == genotype[j] || Math.abs(i - j) == Math.abs(genotype[i] - genotype[j])) {
                    fitness--;
                }
            }
        }
        return fitness;
    }
}