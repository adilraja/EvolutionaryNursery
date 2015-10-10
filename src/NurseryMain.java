/*
 * NurseryMain.java
 *
 * Created on September 3, 2007, 8:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package EvoNursery;
/**
 *
 * @author adilraja
 */
import java.util.*;
public class NurseryMain {
    
    /** Creates a new instance of NurseryMain */
    public NurseryMain() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double Fitness=90;
    double[] bestGene=new double[2];
        Nursery nurse=new Nursery();
        
        ArrayList<Genotype> pop=nurse.initializePopulation(300, 2, -6, +6);//should initialize a population
        int numGens=15;
        while(true){
        for(int i=0;i<numGens;i++){
            ArrayList<Genotype> childPop=nurse.applySelectionTournament(pop,4);
            nurse.applySinglePointCrossover(childPop,2,0.8);
            nurse.applyMutationUniform(childPop,2,0.2);
            pop.addAll(childPop);//append the two pops here
            for(int j=0;j<pop.size();j++){
                double[] tmpGenes=pop.get(j).getGenotype();
                double fitness=10*2+tmpGenes[0]*tmpGenes[0]-10*java.lang.Math.cos(2*3.141*tmpGenes[0])
                +tmpGenes[1]*tmpGenes[1]-10*java.lang.Math.cos(2*3.141*tmpGenes[1]);
                pop.get(j).setFitness(fitness);
                if(fitness<Fitness){
                    Fitness=fitness;
                    bestGene[0]=tmpGenes[0];
                    bestGene[1]=tmpGenes[1];
                }
                nurse.applySurvival(pop);
            }
        }
        System.out.println(Fitness+" "+bestGene[0]+" "+bestGene[1]);
        }
    }
    
}
