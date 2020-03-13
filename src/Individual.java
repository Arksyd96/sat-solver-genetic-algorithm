import java.util.Random;

public class Individual implements Comparable<Individual>{

    private BitArray genotype;
    private int fitnessScore = 0;

    public Individual(BitArray genotype) {
        this.genotype = genotype;
    }
    
    public BitArray getGenotype() {
    	return this.genotype;
    }

    public int genotypeSize() {
        return genotype.size();
    }

    public int getFitnessScore() {
        return fitnessScore;
    }

    public void resetFitnessScore() {
        this.fitnessScore = 0;
    }

    public void incrementFitness() {
        fitnessScore++;
    }

	@Override
	public int compareTo(Individual i) {
		return i.getFitnessScore() - this.fitnessScore;
	}
}
