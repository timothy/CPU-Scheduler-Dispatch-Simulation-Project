/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author tbrad_000
 */
public class UniqueRand {

    private int min;
    private int max;
    private ArrayList<Integer> uniquRan = new <Integer>ArrayList();
    private int random = 0;
    private int temp = 0;
    private Random rand = new Random();

    UniqueRand() {

    }

    /**
     *
     * @param max the max random number that will occur
     * @param min the min random number that will occur
     */
    UniqueRand(int min, int max) {
        this.max = max;
        this.min = min;
        Urand();
    }

    /**
     *
     * @return an ArrayList of unique numbers that are scrambled in a random
     * sequence between max and min
     */
    public ArrayList<Integer> Urand(int min, int max) {
        for (int i = min; i < max; i++) {
            uniquRan.add(i);
        }

        for (int i = 0; i < max - min; i++) {
            random = rand.nextInt(max - min);

            temp = uniquRan.get(i);
            uniquRan.set(i, uniquRan.get(random));
            uniquRan.set(random, temp);
        }
        return uniquRan;
    }
    /**
     *
     * @param max the max random number that will occur
     * @param min the min random number that will occur
     * @return an ArrayList of unique numbers that are scrambled in a random
     * sequence between max and min
     */
    private void Urand() {
        for (int i = min; i < max; i++) {
            uniquRan.add(i);
        }

        for (int i = 0; i < max - min; i++) {
            random = rand.nextInt(max - min);

            temp = uniquRan.get(i);
            uniquRan.set(i, uniquRan.get(random));
            uniquRan.set(random, temp);
        }
    }

    /**
     *
     * @param max the max random number that will occur
     * @param min the min random number that will occur
     * @return an ArrayList of unique numbers that are scrambled in a random
     * sequence between max and min
     */
//    public ArrayList<Integer> Urand(int max, int min) {
//        for (int i = min; i < max; i++) {
//            uniquRan.add(i);
//        }
//
//        for (int i = 0; i < max - min; i++) {
//            random = rand.nextInt(max - min);
//
//            temp = uniquRan.get(i);
//            uniquRan.set(i, uniquRan.get(random));
//            uniquRan.set(random, temp);
//        }
//        return uniquRan;
//    }

    /**
     * @return the lowest random number
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the lowest random number to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the highest random number to set
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the highest random number
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return an ArrayList of unique random numbers
     */
    public ArrayList<Integer> getURand() {
        return uniquRan;
    }
}
