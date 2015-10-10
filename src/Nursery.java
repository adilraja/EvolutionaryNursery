/*
 * Nursery.java
 *
 * Created on September 3, 2007, 5:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *this is the file where evolution would occur
 */
package EvoNursery;
/**
 *
 * @author adilraja
 */
import java.util.*;

public class Nursery {
    
    /**
     * 
     * Creates a new instance of Nursery 
     *Initializes a population
     * popSize-population size
     * numCoeffs-- to be tuned
     * upperBound -- The upper bound of a number
     * lower Bound -- The lower bound of a coeff
     */
    private Random dice;
    private ArrayList<Integer> lMateVector;
    private double span;
    private double lowerBound;
    private int parentPopSize;
    
    
    public Nursery() {
        dice=new Random();//We need a Random number generator
        this.lMateVector=new ArrayList();
       
    }

/**
 *Initialize a population
 */
    public ArrayList<Genotype> initializePopulation(int popSize, int numCoeffs, double upperBound, double lowerBound){
        ArrayList<Genotype> population=new ArrayList(popSize);//creates an empty list with this much of initial capacity
        //The elements would be added manually
        this.span=upperBound-lowerBound;
        this.lowerBound=lowerBound;
        this.parentPopSize=popSize;
        double[] tmpGenotype=new double[numCoeffs];
        for(int j=0;j<popSize;j++){
            for(int i=0;i<numCoeffs;i++){
                tmpGenotype[i]=dice.nextDouble()*span+lowerBound;      
            }
            population.add(new Genotype(tmpGenotype));
        }
        return population;
    }
/**
 *Puppy Style tournamentSelection
 */    
public ArrayList<Genotype> applySelectionTournament(ArrayList<Genotype> ioPopulation, int inNumberParticipants)
{
  if(ioPopulation.size() == 0) return null;
  
  ArrayList<Genotype> childPop=new ArrayList(ioPopulation.size());
 
  //choose the first candidate and store in the child population
  while(childPop.size()<=ioPopulation.size()){
  int jj=0;
  int lChosenCand1=0, lChosenCand2=0;
   int tmpCand1=ioPopulation.size()+1, tmpCand2=ioPopulation.size()+1;
  while(jj<inNumberParticipants){
      lChosenCand1=dice.nextInt(ioPopulation.size());
      if(tmpCand1<ioPopulation.size()){
          if(ioPopulation.get(lChosenCand1).getFitness()<ioPopulation.get(tmpCand1).getFitness()){//||(ioPopulation.get(lChosenCand).compareTrees(tmpCand) && ioPopulation.get(lChoosenCand).size()<ioPopulation.get(lTriedIndividual).size()) ) {
                tmpCand1=lChosenCand1;//choose an individual, 
          }
      }
      else
          tmpCand1=lChosenCand1;//choose this
      jj++;
  }
  childPop.add(new Genotype(ioPopulation.get(tmpCand1)));//Add this to the child population
   //choose the second candidate now and store in the child population
  jj=0;
  while(jj<inNumberParticipants){
      lChosenCand2=dice.nextInt(ioPopulation.size());
      if(tmpCand2<=ioPopulation.size()){//implements Gustafson and LPP
          if(ioPopulation.get(lChosenCand2).getFitness()<ioPopulation.get(tmpCand2).getFitness())
          {
                tmpCand2=lChosenCand2;//choose an individual,       
          }
      }
      else
          tmpCand2=lChosenCand2;//choose this
      jj++;
  }
  childPop.add(new Genotype(ioPopulation.get(tmpCand2)));//Add this to the child population
  }
 
  return childPop;
}


/**
 *Apply single-point crossover
 */
public void applySinglePointCrossover(ArrayList<Genotype> ioPopulation, int genomeLength, double xoverProb){
 
  if((ioPopulation.size() % 2) != 0) ioPopulation.remove(lMateVector.size()-1);
  
  for(int j=0; j<ioPopulation.size(); j+=2) {
   double []tmpParent1=ioPopulation.get(j).getGenotype();
   double []tmpParent2=ioPopulation.get(j+1).getGenotype();
   double swapGene;
   
   int XoverPoint=dice.nextInt(genomeLength);
   if(genomeLength==2 && XoverPoint==0) XoverPoint=1;//The special case when genome length is 2, to ensure that some crossover would happen
   for(int i=0;i<XoverPoint; i++){
       swapGene=tmpParent1[i];
       tmpParent1[i]=tmpParent2[i];
       tmpParent2[i]=swapGene;//xover is no big deal
   }
   ioPopulation.get(j).setEvaluated(false);
   ioPopulation.get(j+1).setEvaluated(false);
  }
    
}

/**
 *Applies standard mutation to the population. The numbers are drawn from a uniform distributation
 */
public void applyMutationUniform(ArrayList<Genotype> ioPopulation, int genomeLength,double mutationProb){
    Iterator<Genotype> genoIt=ioPopulation.iterator(); 
    while(genoIt.hasNext()){
        boolean flag=false;
        Genotype genome=genoIt.next();
        double []tmpParent=genome.getGenotype();
        for(int i=0;i<genomeLength;i++){
            if(dice.nextDouble()<=mutationProb){
                tmpParent[i]=dice.nextDouble()*this.span+this.lowerBound;
                flag=true;
            }
        }
        if(flag)
            genome.setEvaluated(false);
    }       
}

/**
 *an elitist survival criterion
 */
public void applySurvival(ArrayList<Genotype> ioPopulation){

    

    Collections.sort(ioPopulation, new FitnessComparator());//Sort the pop wrt fitness first

//    Collections.sort(ioPopulation, new DepthComparator())//Sort wrt fitness -- I guess no need for this sort
    while(ioPopulation.size()!=this.parentPopSize)
        ioPopulation.remove(this.parentPopSize);

} 

}

 

/**

 *Fitness Comparator

 */

class FitnessComparator implements java.util.Comparator<Genotype>{
        public int compare(Genotype o1, Genotype o2){
            double fit1=o1.getFitness();
            double fit2=o2.getFitness();
            if (fit1<fit2) return -1;
            if(fit1==fit2)return 0;
            return 1;

        }

}

