/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import cpu.dispatch.scheduling.Process;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.PriorityQueue;

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
    // public PriorityQueue<Process> CPUHPN =  new PriorityQueue<>(25, comparator); ;//CPU Queue Higest Priority Next
    PriorityQueue<Process> CPUHPN = new PriorityQueue<>(25, new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            if (p1.getPriority() < p2.getPriority()) {
                return -1;
            }
            if (p1.getPriority() > p2.getPriority()) {
                return 1;
            }
            return 0;
        }
    });
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

    /**
     * Check if queues are empty
     *
     * @return true if queues still have processes
     */
    public boolean hasProcesses() {
        if ((this.CPUFCFS.size() > 0) || (this.CPUHPN.size() > 0) || (this.CPURRHQ.size() > 0) || (this.CPURRLQ.size() > 0) || (this.IOFCFS.size() > 0) || (this.IOHPN.size() > 0) || (this.IORRHQ.size() > 0) || (this.IORRLQ.size() > 0)) {
            return true;
        } else {
            return false;
        }
    }
}
