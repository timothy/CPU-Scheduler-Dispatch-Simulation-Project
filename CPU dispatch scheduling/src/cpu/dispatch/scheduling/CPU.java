/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.*;

/**
 *
 * @author tbrad_000
 */
public class CPU {

    ArrayList<Process> ProcessAL = new ArrayList<>(); //Array List containing all Proccess on this Proccesor
    public MyQue CPURRHQ = new MyQue();
    //CPU Queue Round Robin High Quantum
    public MyQue CPURRLQ = new MyQue();
    //CPU Queue Round Robin Low Quantum
    public Queue<Process> CPUHPN = new PriorityQueue<>();//CPU Queue Higest Priority Next
    public MyQue CPUFCFS = new MyQue();
    //CPU Queue First Come First Serve
    public MyQue IORRHQ = new MyQue();
    //IO Queue Round Robin High Quantum
    public MyQue IORRLQ = new MyQue();
    //IO Queue Round Robin Low Quantum
    public Queue<Process> IOHPN = new PriorityQueue<>();////IO Queue Higest Priority Next
    public MyQue IOFCFS = new MyQue();
    //IO Queue First Come First Serve
    private int count = 0; //count 3 of processes on CPU counter

    protected Clock clock = new Clock();//Total Time on CPU

    /**
     * @return the clock
     */
    public int getClock() {
        return clock.getTick();
    }

    /**
     * Increment the CPU Clock
     */
    public void IncClock() {
        clock.tick();
    }

    /**
     * @param clock the clock to set
     */
    public void setClock(int clock) {
        this.clock.setTick(clock);
    }

    /**
     * set increment # of process on cpu counter
     */
    public void incCount() {
        this.count++;
    }

    /**
     * count is the number of processes on the cpu
     *
     * @return count
     */
    public int getCount() {
        return count;
    }

}
