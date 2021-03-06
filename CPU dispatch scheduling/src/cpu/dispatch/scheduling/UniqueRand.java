/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;
import java.util.Random;

/**
 * Program to create and store Unique random numbers, to give some authentic
 * flavor to our simulation
 *
 * @author tbrad_000
 */
public class UniqueRand {

    private int min;
    private int max;
    private ArrayList<Integer> uniquRan;
    private int random = 0;
    private int temp = 0;
    private Random rand;

    /**
     * unused no arg
     */
    UniqueRand() {
        this.rand = new Random();
        this.uniquRan = new <Integer>ArrayList();
    }

    /**
     *
     * @param max the max random number that will occur
     * @param min the min random number that will occur
     */
    UniqueRand(int min, int max) {
        this.rand = new Random();
        this.uniquRan = new <Integer>ArrayList();
        this.max = max;
        this.min = min;
        Urand();
    }

    /**
     *
     * @param max the max random number that will occur
     * @param min the min random number that will occur
     * @return an ArrayList of unique numbers that are scrambled in a random
     * sequence between max and min
     */
    public ArrayList<Integer> Urand(int max, int min) {
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

    public void Urand() {
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
