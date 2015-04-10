/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * CPU handles Queues for processing process, methods for adding and processing
 * process Containers for holding processes and processes stats
 *
 * @author tbrad_000 and Zeus
 */
public class CPU {

    /**
     * stats used to all processes stats on cpu
     */
    ArrayList<Stats> stats = new ArrayList<>();
    /**
     * ArrayList used to temp hold all processes on cpu
     */
    ArrayList<Process> ProcessAL = new ArrayList<>(); //Array List containing all Proccess on this Proccesor
    /**
     * Used to hold processes being ran with round robin with a quantum of 4
     */
    public MyQue CPURRHQ = new MyQue();//CPU Queue Round Robin High Quantum
    /**
     * Used to hold processes being ran with round robin with a quantum of 2
     */
    public MyQue CPURRLQ = new MyQue();//CPU Queue Round Robin Low Quantum
    /**
     * Used to hold processes being ran with respect to priority
     */
    PriorityQueue<Process> CPUHPN = new PriorityQueue<>(25, (Process p1, Process p2) -> {// public PriorityQueue<Process> CPUHPN =  new PriorityQueue<>(25, comparator); ;//CPU Queue Higest Priority Next 
        if (p1.getPriority() < p2.getPriority()) {
            return -1;
        }
        if (p1.getPriority() > p2.getPriority()) {
            return 1;
        }
        return 0;
    });
    /**
     * Clean up Queue for Processes first come first serve
     */
    public MyQue CPUFCFS = new MyQue();    //CPU Queue First Come First Serve
    /**
     * Used to hold i/o being ran with round robin with a quantum of 4
     */
    public MyQue IORRHQ = new MyQue();//IO Queue Round Robin High Quantum
    /**
     * Used to hold i/o being ran with round robin with a quantum of 2
     */
    public MyQue IORRLQ = new MyQue();//IO Queue Round Robin Low Quantum
    /**
     * Used to hold i/o being ran with respect to priority
     */
    PriorityQueue<Process> IOHPN = new PriorityQueue<>(25, (Process p1, Process p2) -> { //public Queue<Process> IOHPN = new PriorityQueue<>(25, comparator);////IO Queue Higest Priority Next
        if (p1.getPriority() < p2.getPriority()) {
            return -1;
        }
        if (p1.getPriority() > p2.getPriority()) {
            return 1;
        }
        return 0;
    });
    /**
     * Clean up Queue for i/o first come first serve
     */
    public MyQue IOFCFS = new MyQue();//IO Queue First Come First Serve
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

    /**
     * Run Round Robin at inputed quantum
     *
     * @param q quantum
     */
    public void runProcessesRR(int q) {
//RR
        while ((CPURRHQ.size() > 0) || (IORRHQ.size() > 0)) {//run CPU 4 till its dry
            for (int q8 = 0; q8 < q; q8++) {//loop for a quantam of 8
                if (CPURRHQ.size() > 0) {//check for processes
                    this.CPURRHQ.push(this.CPURRHQ.pop());
                    if ((CPURRHQ.peek().getCPUTime() + CPURRHQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(CPURRHQ.pop().stats);
                        this.CPURRHQ.peek().stats.genStats(this.CPURRHQ.pop());
                        break;
                    }
                    if (CPURRHQ.peek().IOInterrupt(CPURRHQ.peek()) != true) {
                        for (int i = 0; i < CPURRHQ.size(); i++) {
                            //move the process to IO if it is time for IO
                            CPURRHQ.peek().decCPUTime();//decrease the ammount of time left for the front process
                            CPURRHQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            CPURRHQ.push(CPURRHQ.pop());
                            for (int k = 0; k < CPURRHQ.size() - 1; k++) {//inc wait time for remaining processes
                                CPURRHQ.peek().IncWaitTime();
                                CPURRHQ.peek().stats.genStats(this.CPURRHQ.peek());
                                CPURRHQ.push(CPURRHQ.pop());
                            }
                        }
                    } else {//move process to IOQueue
                        CPURRHQ.peek().stats.ContextSwitchTime++;
                        IORRHQ.push(CPURRHQ.pop());
                    }
                }//end q4 loop CPU
            }
            for (int q8 = 0; q8 < q; q8++) {//loop for a quantam of 4
                if (IORRHQ.size() > 0) {//check for processes
                    this.IORRHQ.push(this.IORRHQ.pop());
                    if ((IORRHQ.peek().getCPUTime() + IORRHQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(IORRHQ.pop().stats);
                        this.IORRHQ.peek().stats.genStats(this.IORRHQ.pop());
                        break;
                    }
                    if (IORRHQ.peek().IOInterrupt(IORRHQ.peek()) == true) {
                        for (int i = 0; i < IORRHQ.size(); i++) {
                            //move the process to CPU if it is time for IO done
                            IORRHQ.peek().decIOTIME();//decrease the ammount of time left for the front process
                            IORRHQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            IORRHQ.push(IORRHQ.pop());
                            for (int k = 0; k < IORRHQ.size() - 1; k++) {//inc i/o wait time
                                IORRHQ.peek().IncWaitTime();
                                IORRHQ.peek().stats.genStats(this.IORRHQ.peek());
                                IORRHQ.push(IORRHQ.pop());
                            }
                        }
                    } else {//move process to CPUQueue
                        IORRHQ.peek().stats.ContextSwitchTime++;
                        CPURRHQ.push(IORRHQ.pop());
                    }

                }
            }
        }
    }

    /**
     * run Highest Priority Next
     */
    public void runProcessesHPN() {
//HPN
        while (CPUHPN.size() > 0) {//run till dry
            if ((CPUHPN.peek().getCPUTime() + CPUHPN.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                //stats.add(CPUHPN.poll().stats);
                this.CPUHPN.peek().stats.genStats(this.CPUHPN.poll());
                break;
            }
            for (int i = 0; i < CPUHPN.size(); i++) {
                if (CPUHPN.peek().IOInterrupt(CPUHPN.peek()) != true) {//move the process to IO if it is time for IO
                    CPUHPN.peek().decCPUTime();//decrease the ammount of time left for the front process
                    CPUHPN.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                    CPUHPN.add(CPUHPN.poll());
                    for (int k = 0; k < CPUHPN.size() - 1; k++) {//inc remaining wait time
                        CPUHPN.peek().IncWaitTime();
                        CPUHPN.peek().stats.genStats(this.CPUHPN.peek());
                        CPUHPN.add(CPUHPN.poll());
                    }
                } else {//move process to IOQueue
                    CPUHPN.peek().stats.ContextSwitchTime++;
                    IOHPN.add(CPUHPN.poll());
                }
            }
            //IncClock();//increment the time that the cpu has been running}
        }//end HPN loop CPU
        while (IOHPN.size() > 0) {//run till dry
            if ((IOHPN.peek().getCPUTime() + IOHPN.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                //stats.add(IOHPN.poll().stats);
                this.IOHPN.peek().stats.genStats(this.IOHPN.poll());
                break;
            }
            for (int i = 0; i < IOHPN.size(); i++) {
                if (IOHPN.peek().IOInterrupt(IOHPN.peek()) != true) {//move the process to CPU if it is time for IO done
                    IOHPN.peek().decIOTIME();//decrease the ammount of time left for the front process
                    IOHPN.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                    IOHPN.add(IOHPN.poll());
                    for (int k = 0; k < IOHPN.size() - 1; k++) {//inc wait time
                        IOHPN.peek().IncWaitTime();
                        IOHPN.peek().stats.genStats(this.IOHPN.peek());
                        IOHPN.add(IOHPN.poll());
                    }
                } else {
                    IOHPN.peek().stats.ContextSwitchTime++;
                    CPUHPN.add(IOHPN.poll());
                }//end move
            }
        }//end HPN loop
    }

    /**
     * run First come first serve
     */
    public void runProcessesFCFS() {
//FCFS
        while (CPUFCFS.size() > 0) { //run till dry
            if ((CPUFCFS.peek().getCPUTime() + CPUFCFS.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                //stats.add(CPUFCFS.pop().stats);
                this.CPUFCFS.peek().stats.genStats(this.CPUFCFS.pop());
                break;
            }
            for (int i = 0; i < CPUFCFS.size(); i++) {
                if (CPUFCFS.peek().IOInterrupt(CPUFCFS.peek()) != true) {//move the process to IO if it is time for IO
                    CPUFCFS.peek().decCPUTime();//decrease the ammount of time left for the front process
                    CPUFCFS.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                    CPUFCFS.push(CPUFCFS.pop());
                    for (int k = 0; k < CPUFCFS.size() - 1; k++) {//inc wait time
                        CPUFCFS.peek().IncWaitTime();
                        CPUFCFS.peek().stats.genStats(this.CPUFCFS.peek());
                        CPUFCFS.push(CPUFCFS.pop());
                    }
                } else {//move process to IOQueue
                    CPUFCFS.peek().stats.ContextSwitchTime++;
                    IOFCFS.push(CPUFCFS.pop());
                }
            }
        }
        while (IOFCFS.size() > 0) { //run till dry
            if ((IOFCFS.peek().getCPUTime() + IOFCFS.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                //stats.add(IOFCFS.pop().stats);
                this.IOFCFS.peek().stats.genStats(this.IOFCFS.pop());
                break;
            }
            for (int i = 0; i < IOFCFS.size(); i++) {
                if (IOFCFS.peek().IOInterrupt(IOFCFS.peek()) != true) {//move the process to CPU if it is time for IO done
                    IOFCFS.peek().decCPUTime();//decrease the ammount of time left for the front process
                    IOFCFS.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                    IOFCFS.push(IOFCFS.pop());
                    for (int k = 0; k < IOFCFS.size() - 1; k++) {//inc wait time
                        IOFCFS.peek().IncWaitTime();
                        IOFCFS.peek().stats.genStats(this.IOFCFS.peek());
                        IOFCFS.push(IOFCFS.pop());
                    }
                } else {//move process to CPUQueue
                    IOFCFS.peek().stats.ContextSwitchTime++;
                    CPUFCFS.push(IOFCFS.pop());
                }
            }
        }//end FCFS i/o time
    }

    /**
     * load CPU Queues with data pass procQueue and stats.cpu#
     *
     * @param p process
     * @param i CPU Number
     */
    public void loadProces(Process p, int i) {
        p.stats.setCPU(i);
        stats.add(p.stats);
        ProcessAL.add(p);
        //Add to CPU Queue
        if (CPURRHQ.size() < 20) {
            p.stats.setQue("RRHQ");
            CPURRHQ.push(p);
        } else if (CPURRLQ.size() < 20) {
            p.stats.setQue("RRLQ");
            CPURRLQ.push(p);
        } else if (CPUHPN.size() < 10) {
            p.stats.setQue("HPN");
            CPUHPN.add(p);
        } else {
            p.stats.setQue("FCFS");
            CPUFCFS.push(p);
        }
    }// end loadProcess

    public void clear() {
        this.CPUFCFS.clear();
        this.CPUHPN.clear();
        this.CPURRHQ.clear();
        this.CPURRLQ.clear();
        this.IOFCFS.clear();
        this.IOHPN.clear();
        this.IORRHQ.clear();
        this.IORRLQ.clear();
        this.count = 0;
        this.stats.clear();
        this.ProcessAL.clear();
    }
}
