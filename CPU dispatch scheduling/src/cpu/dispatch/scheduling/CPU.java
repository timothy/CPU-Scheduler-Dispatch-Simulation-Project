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
MyQue q = new MyQue();
    ArrayList<Process> ProcessAL = new ArrayList<>(); //Array List containing all Proccess on this Proccesor
    MyQue CPURRHQ;//CPU Queue Round Robin High Quantum
    MyQue CPURRLQ;//CPU Queue Round Robin Low Quantum
    Queue<Process> CPUHPN = new PriorityQueue<>();//CPU Queue Higest Priority Next
    MyQue CPUFCFS;//CPU Queue First Come First Serve

    MyQue IORRHQ;//IO Queue Round Robin High Quantum
    MyQue IORRLQ;//IO Queue Round Robin Low Quantum
    Queue<Process> IOHPN = new PriorityQueue<>();////IO Queue Higest Priority Next
    MyQue IOFCFS;//IO Queue First Come First Serve

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
        clock.tick++;
    }

    /**
     * @param clock the clock to set
     */
    public void setClock(int clock) {
        this.clock.setTick(clock);
    }

}
