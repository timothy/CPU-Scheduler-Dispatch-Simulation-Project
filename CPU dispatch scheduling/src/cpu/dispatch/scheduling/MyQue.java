/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;

/**
 * Special upside down stack blended with a vector I guess this is a Upside Down
 * VectorStack
 *
 * @author tbrad_000 and Zeus
 */
public class MyQue {

    /**
     * base container
     */
    private ArrayList<Process> a = new <Process>ArrayList();

    /**
     * push onto container
     *
     * @param p of processes, should have used generic type but hind sight 20/20
     */
    public void push(Process p) {
        a.add(p);
    }

    /**
     * access and remove the first element
     * @return size AND REMOVE first element
     */
    public Process pop() {
        return a.remove(a.size() - 1);
    }

    /**
     * access the first element
     *
     * @return first process
     */
    public Process peek() {
        return a.get(a.size() - 1);
    }

    /**
     * get the container size
     *
     * @return the amount of items in the container
     */
    public int size() {
        return a.size();
    }

    /**
     * get selected element in container
     *
     * @param i pass index position from primary arraylist
     * @return processor in the index position
     */
    public Process get(int i) {
        return a.get(i);
    }

}
