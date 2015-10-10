/*
 * Genotype.java
 *
 * Created on September 3, 2007, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package EvoNursery;
/**
 *
 * @author adilraja
 */
public class Genotype {
    private double [] genes;
    private double fitness;
    private boolean evaluated;
    
    /**
     * Creates a new instance of Genotype
     *Sets the genotype to input genotypes
     */
    public Genotype(double []inGenes) {
        genes=new double[inGenes.length];
        for(int i=0;i<inGenes.length;i++){
            this.genes[i]=inGenes[i];
        }
        this.fitness=Double.MAX_VALUE;//set it to a high value
        this.evaluated=false;
    }

/**
 *Copy Constructor
 */    
 public Genotype(Genotype copy){
     double[] tmpGenes=copy.getGenotype();
     this.genes=new double[tmpGenes.length];
    for(int i=0;i<tmpGenes.length;i++){
        this.genes[i]=tmpGenes[i];
    }
    this.fitness=copy.getFitness();//set it to a high value
    this.evaluated=copy.evaluated;
 }
    /**
     *setGenotype
     */
    public void setGenotype(final double [] inGenes){
        for(int i=0;i<inGenes.length;i++){
            this.genes[i]=inGenes[i];
        }
    }
   
    /**
     *Get the genotype array
     */
    public final double[] getGenotype(){
        return this.genes;
    }
    
    /**
     *set the fitness of the genotype
     */
    public void setFitness(final double inFitness){
        this.fitness=inFitness;
    }
    /**
     *get fitness of this Genotype
     */
    public final double getFitness(){
        return this.fitness;
    }
    /**
     *Set the evaualted flag
     */
    public void setEvaluated(final boolean eval){
        this.evaluated=eval;
    }
    /**
     *Get the evaluation flag's value
     */
    public final boolean getEvaluatedFlag(){
        return this.evaluated;
    }
}
