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
 * @author tbrad_000
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
     * Process processes on each processor and run them through the selected
     * queues
     */
    public void runProcesses() {
        while (hasProcesses()) {//run CPU 4 till its dry
            if (CPURRHQ.size() > 0) {//check for processes
                for (int q4 = 0; q4 < 4; q4++) {//loop for a quantam of 4
                    this.CPURRHQ.push(this.CPURRHQ.pop());
                    if ((CPURRHQ.peek().getCPUTime() + CPURRHQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(CPURRHQ.pop().stats);
                        this.CPURRHQ.peek().stats.genStats(this.CPURRHQ.pop());
                        break;
                    }
                    CPURRHQ.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < CPURRHQ.size(); i++) {
                        if (CPURRHQ.peek().IOInterrupt(CPURRHQ.peek()) != true) {//move the process to IO if it is time for IO
                            CPURRHQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            CPURRHQ.push(CPURRHQ.pop());
                            for (int k = 0; k < CPURRHQ.size(); k++) {//inc wait time for remaining processes
                                CPURRHQ.peek().IncWaitTime();
                                CPURRHQ.peek().stats.genStats(this.CPURRHQ.peek());
                                CPURRHQ.push(CPURRHQ.pop());
                            }
                        } else {//move process to IOQueue
                            CPURRHQ.peek().stats.ContextSwitchTime++;
                            IORRHQ.push(CPURRHQ.pop());
                        }
                    }
                    //IncClock();//increment the time that the cpu has been running}
                }//end q4 loop CPU
            } else if (IORRHQ.size() > 0) {//check for processes
                for (int q4 = 0; q4 < 4; q4++) {//loop for a quantam of 4
                    this.IORRHQ.push(this.IORRHQ.pop());
                    if ((IORRHQ.peek().getCPUTime() + IORRHQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(IORRHQ.pop().stats);
                        this.IORRHQ.peek().stats.genStats(this.IORRHQ.pop());
                        break;
                    }
                    IORRHQ.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < IORRHQ.size(); i++) {
                        if (IORRHQ.peek().IOInterrupt(IORRHQ.peek()) != true) {//move the process to CPU if it is time for IO done
                            IORRHQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            IORRHQ.push(IORRHQ.pop());
                            for (int k = 0; k < IORRHQ.size(); k++) {//inc i/o wait time
                                IORRHQ.peek().IncWaitTime();
                                IORRHQ.peek().stats.genStats(this.IORRHQ.peek());
                                IORRHQ.push(IORRHQ.pop());
                            }
                        } else {//move process to CPUQueue
                            IORRHQ.peek().stats.ContextSwitchTime++;
                            CPURRHQ.push(IORRHQ.pop());
                        }
                    }
                    //IncClock();//increment the time that the IO has been running}
                }//end q4 loop IO               
            } else if (CPURRLQ.size() > 0) {//check for processes
                if (CPURRHQ.size() < 5) {
//                    CPURRHQ.push(CPURRLQ.pop());
//                    continue;
//                }
                    for (int q2 = 0; q2 < 2; q2++) {//loop for a quantam of 2
                        this.CPURRLQ.push(this.CPURRLQ.pop());
                        if ((CPURRLQ.peek().getCPUTime() + CPURRLQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                            //stats.add(CPURRLQ.pop().stats);
                            this.CPURRLQ.peek().stats.genStats(this.CPURRLQ.pop());
                            break;
                        }
                        CPURRLQ.peek().decCPUTime();//decrease the ammount of time left for the front process
                        for (int i = 1; i < CPURRLQ.size(); i++) {
                            if (CPURRLQ.peek().IOInterrupt(CPURRLQ.peek()) != true) {//move the process to IO if it is time for IO
                                CPURRLQ.push(CPURRLQ.pop());
                                CPURRLQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                                for (int k = 0; k < CPURRLQ.size(); k++) {//inc remaining wait time
                                    CPURRLQ.peek().IncWaitTime();
                                    CPURRLQ.peek().stats.genStats(this.CPURRLQ.peek());
                                    CPURRLQ.push(CPURRLQ.pop());
                                }
                            } else {//move process to IOQueue
                                CPURRLQ.peek().stats.ContextSwitchTime++;
                                IORRLQ.push(CPURRLQ.pop());
                            }
                        }
                        //IncClock();//increment the time that the cpu has been running}
                    }//end loop
                }
            } else if (IORRLQ.size() > 0) {//check for processes
//                if (IORRHQ.size() < 5) {
//                    IORRHQ.push(IORRLQ.pop());
//                    continue;
//                }
                for (int q2 = 0; q2 < 2; q2++) {//loop for a quantam of 2
                    this.IORRLQ.push(this.IORRLQ.pop());
                    if ((IORRLQ.peek().getCPUTime() + IORRLQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(IORRLQ.pop().stats);
                        this.IORRLQ.peek().stats.genStats(this.IORRLQ.pop());
                        break;
                    }
                    IORRLQ.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < IORRLQ.size(); i++) {
                        if (IORRLQ.peek().IOInterrupt(IORRLQ.peek()) != true) {//move the process to CPU if it is time for IO done
                            IORRLQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            IORRLQ.push(IORRLQ.pop());
                            for (int k = 0; k < IORRLQ.size(); k++) {//inc remaining wait time
                                IORRLQ.peek().IncWaitTime();
                                IORRLQ.peek().stats.genStats(this.IORRLQ.peek());
                                IORRLQ.push(IORRLQ.pop());
                            }
                        } else {//move process to CPUQueue
                            IORRLQ.peek().stats.ContextSwitchTime++;
                            CPURRLQ.push(IORRLQ.pop());
                        }
                    }
                    //IncClock();//increment the time that the IO has been running}
                }//end q2 loop IO
            } else if (CPUHPN.size() > 0) {//check for processes
//                if (CPURRHQ.size() < 5) {
//                    CPURRHQ.push(CPUHPN.poll());
//                    continue;
//                }
                while (CPUHPN.size() > 0) {//run till dry
                    if ((CPUHPN.peek().getCPUTime() + CPUHPN.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(CPUHPN.poll().stats);
                        this.CPUHPN.peek().stats.genStats(this.CPUHPN.poll());
                        break;
                    }
                    CPUHPN.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < CPUHPN.size(); i++) {
                        if (CPUHPN.peek().IOInterrupt(CPUHPN.peek()) != true) {//move the process to IO if it is time for IO
                            CPUHPN.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            CPUHPN.add(CPUHPN.poll());
                            for (int k = 0; k < CPUHPN.size(); k++) {//inc remaining wait time
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
            } else if (IOHPN.size() > 0) {//check for processes
//                if (IORRHQ.size() < 5) {
//                    IORRHQ.push(IOHPN.poll());
//                    continue;
//                }
                while (IOHPN.size() > 0) {//run till dry
                    if ((IOHPN.peek().getCPUTime() + IOHPN.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(IOHPN.poll().stats);
                        this.IOHPN.peek().stats.genStats(this.IOHPN.poll());
                        break;
                    }
                    IOHPN.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < IOHPN.size(); i++) {
                        if (IOHPN.peek().IOInterrupt(IOHPN.peek()) != true) {//move the process to CPU if it is time for IO done
                            IOHPN.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            IOHPN.add(IOHPN.poll());
                            for (int k = 0; k < IOHPN.size(); k++) {//inc wait time
                                IOHPN.peek().IncWaitTime();
                                IOHPN.peek().stats.genStats(this.IOHPN.peek());
                                IOHPN.add(IOHPN.poll());
                            }
                        } else {
                            IOHPN.peek().stats.ContextSwitchTime++;
                            CPUHPN.add(IOHPN.poll());
                        }//end move
                    }
                    //IncClock();//increment the time that the IO has been running}
                }//end HPN loop
            } else if (CPUFCFS.size() > 0) {//check for processes
//                if (CPURRHQ.size() < 5) {
//                    CPURRHQ.push(CPUFCFS.pop());
//                    continue;
//                }
                while (CPUFCFS.size() > 0) { //run till dry

                    if ((CPUFCFS.peek().getCPUTime() + CPUFCFS.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(CPUFCFS.pop().stats);
                        this.CPUFCFS.peek().stats.genStats(this.CPUFCFS.pop());
                        break;
                    }
                    CPUFCFS.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < CPUFCFS.size(); i++) {
                        if (CPUFCFS.peek().IOInterrupt(CPUFCFS.peek()) != true) {//move the process to IO if it is time for IO
                            CPUFCFS.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            CPUFCFS.push(CPUFCFS.pop());
                            for (int k = 0; k < CPUFCFS.size(); k++) {//inc wait time
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
                //IncClock();//increment the time that the cpu has been running}
            }//end FCFS loop CPU
            else if (IOFCFS.size() > 0) {//check for processes
//                if (IORRHQ.size() < 5) {
//                    IORRHQ.push(IOFCFS.pop());
//                    continue;
//                }
                while (IOFCFS.size() > 0) { //run till dry
                    if ((IOFCFS.peek().getCPUTime() + IOFCFS.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        //stats.add(IOFCFS.pop().stats);
                        this.IOFCFS.peek().stats.genStats(this.IOFCFS.pop());
                        break;
                    }
                    IOFCFS.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < IOFCFS.size(); i++) {
                        if (IOFCFS.peek().IOInterrupt(IOFCFS.peek()) != true) {//move the process to CPU if it is time for IO done
                            IOFCFS.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                            IOFCFS.push(IOFCFS.pop());
                            for (int k = 0; k < IOFCFS.size(); k++) {//inc wait time
                                IOFCFS.peek().IncWaitTime();
                                IOFCFS.peek().stats.genStats(this.IOFCFS.peek());
                                IOFCFS.push(IOFCFS.pop());
                            }
                        } else {//move process to CPUQueue
                            IOFCFS.peek().stats.ContextSwitchTime++;
                            CPUFCFS.push(IOFCFS.pop());
                        }
                    }
                    //IncClock();//increment the time that the IO has been running}
                }//end FCFS i/o time
            }
        }//end while loop
    }//end runProcess

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
        if (CPURRHQ.size() < 5) {
            CPURRHQ.push(p);
        } else if (CPURRLQ.size() < 5) {
            CPURRLQ.push(p);
        } else if (CPUHPN.size() < 5) {
            CPUHPN.add(p);
        } else {
            CPUFCFS.push(p);
        }
    }// end loadProcess
}
