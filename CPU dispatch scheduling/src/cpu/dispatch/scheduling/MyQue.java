/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;

/**
 *
 * @author tbrad_000
 */
public class MyQue {

    private ArrayList<Process> a = new <Process>ArrayList();

    public void push(Process p) {
        a.add(p);
    }

    public Process pop() {
        return a.remove(a.size() - 1);
    }

    public Process peek() {
        return a.get(a.size() - 1);
    }

    public int size() {
        return a.size();
    }
    
    public Process get(int i){
        return a.get(i);
    }

}
