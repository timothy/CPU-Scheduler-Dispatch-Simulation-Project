/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author tbrad_000
 */
public class CPUDispatchScheduling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CPU CPU1 = new CPU();
        CPU CPU2 = new CPU();
        CPU CPU3 = new CPU();
        CPU CPU4 = new CPU();
        ArrayList<Stats> stats = new ArrayList<>();//ArrayList containing all the stats from finished processes to create avg
        ProcessCreator PC = new ProcessCreator();
        MyQue ProcQueue = new MyQue();

        for (int i = 0; i < 100; i++) { //Make X (100) processes and store them in ProcQueue
            Process p1 = PC.MakeProcess();
            ProcQueue.push(p1);
        }

        for (int i = 0; i < ProcQueue.size(); i++) {//add the all proccess to a CPU
            //CPU1 mostly IO
            if ((ProcQueue.peek().getIOTime() - 25) > ProcQueue.peek().getCPUTime()) {
                //remove from procQueue and store on CPU queue
                if (CPU1.ProcessAL.size() > 25) {
                    ProcQueue.peek().stats.setCPU(1);
                    CPU1.ProcessAL.add(ProcQueue.pop());
                    //Add to CPU Queue
                    if (CPU1.CPURRHQ.size() < 5) {
                        CPU1.CPURRHQ.push(CPU1.ProcessAL.get(CPU1.getCount()));
                    } else if (CPU1.CPURRLQ.size() < 5) {
                        CPU1.CPURRLQ.push(CPU1.ProcessAL.get(CPU1.getCount()));
                    } else if (CPU1.CPUHPN.size() < 5) {
                        CPU1.CPUHPN.add(CPU1.ProcessAL.get(CPU1.getCount()));
                    } else {
                        CPU1.CPUFCFS.push(CPU1.ProcessAL.get(CPU1.getCount()));
                    }
                }//End remove from procQueue and store on CPU queue
            }// End CPU1
            //CPU2 mostly CPU
            if ((ProcQueue.peek().getIOTime()) < ProcQueue.peek().getCPUTime() - 25) {
                //remove from procQueue and store on CPU queue
                if (CPU2.ProcessAL.size() < 26) {
                    ProcQueue.peek().stats.setCPU(2);
                    CPU2.ProcessAL.add(ProcQueue.pop());
                    //Add to CPU Queue
                    if (CPU2.CPURRHQ.size() < 5) {
                        CPU2.CPURRHQ.push(CPU2.ProcessAL.get(CPU2.getCount()));
                    } else if (CPU2.CPURRLQ.size() < 5) {
                        CPU2.CPURRLQ.push(CPU2.ProcessAL.get(CPU2.getCount()));
                    } else if (CPU2.CPUHPN.size() < 5) {
                        CPU2.CPUHPN.add(CPU2.ProcessAL.get(CPU2.getCount()));
                    } else {
                        CPU2.CPUFCFS.push(CPU2.ProcessAL.get(CPU2.getCount()));
                    }
                }//End remove from procQueue and store on CPU queue
            }// End CPU2
            //CPU3 mostly even IO/CPU
            if ((ProcQueue.peek().getIOTime() - ProcQueue.peek().getCPUTime()) < 10) {
                //remove from procQueue and store on CPU queue
                if (CPU3.ProcessAL.size() < 26) {
                    ProcQueue.peek().stats.setCPU(3);
                    CPU3.ProcessAL.add(ProcQueue.pop());
                    //Add to CPU Queue
                    if (CPU3.CPURRHQ.size() < 5) {
                        CPU3.CPURRHQ.push(CPU3.ProcessAL.get(CPU3.getCount()));
                    } else if (CPU3.CPURRLQ.size() < 5) {
                        CPU3.CPURRLQ.push(CPU3.ProcessAL.get(CPU3.getCount()));
                    } else if (CPU3.CPUHPN.size() < 5) {
                        CPU3.CPUHPN.add(CPU3.ProcessAL.get(CPU3.getCount()));
                    } else {
                        CPU3.CPUFCFS.push(CPU3.ProcessAL.get(CPU3.getCount()));
                    }
                }//End remove from procQueue and store on CPU queue
            }// End CPU3
            //CPU4 Catch all
            else //remove from procQueue and store on CPU queue
            {
                ProcQueue.peek().stats.setCPU(4);
                CPU4.ProcessAL.add(ProcQueue.pop());
                //Add to CPU Queue
                if (CPU4.CPURRHQ.size() < 5) {
                    CPU4.CPURRHQ.push(CPU4.ProcessAL.get(CPU4.getCount()));
                } else if (CPU4.CPURRLQ.size() < 5) {
                    CPU4.CPURRLQ.push(CPU4.ProcessAL.get(CPU4.getCount()));
                } else if (CPU4.CPUHPN.size() < 5) {
                    CPU4.CPUHPN.add(CPU4.ProcessAL.get(CPU4.getCount()));
                } else {
                    CPU4.CPUFCFS.push(CPU4.ProcessAL.get(CPU4.getCount()));
                }//End remove from procQueue and store on CPU queue
            }// End CPU4
        }//End For loop for adding all proccess to CPU
        while (CPU4.hasProcesses()) {//run CPU 4 till its dry
            if (CPU4.CPURRHQ.size() > 0) {//check for processes
                for (int q4 = 0; q4 < 4; q4++) {//loop for a quantam of 4
                    if ((CPU4.CPURRHQ.peek().getCPUTime() + CPU4.CPURRHQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        stats.add(CPU4.CPURRHQ.pop().stats);
                        break;
                    }//end stats if
                    CPU4.CPURRHQ.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < CPU4.CPURRHQ.size(); i++) {
                        if (CPU4.CPURRHQ.peek().IOInterrupt(CPU4.CPURRHQ.peek()) != true) {//move the process to IO if it is time for IO
                            CPU4.CPURRHQ.push(CPU4.CPURRHQ.pop());
                            CPU4.CPURRHQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                        } else {//move process to IOQueue
                            CPU4.CPURRHQ.peek().stats.ContextSwitchTime++;
                            CPU4.IORRHQ.push(CPU4.CPURRHQ.pop());
                        }//end move
                    }
                    CPU4.IncClock();//increment the time that the cpu has been running}
                }//end q4 loop CPU
            }
           else if (CPU4.IORRHQ.size() > 0) {//check for processes
                for (int q4 = 0; q4 < 4; q4++) {//loop for a quantam of 4
                    if ((CPU4.IORRHQ.peek().getCPUTime() + CPU4.IORRHQ.peek().getIOTime()) < 1) {//if process is dry of cpu and io pop off queue and put process stats in stats array
                        stats.add(CPU4.IORRHQ.pop().stats);
                        break;
                    }//end stats if
                    CPU4.IORRHQ.peek().decCPUTime();//decrease the ammount of time left for the front process
                    for (int i = 1; i < CPU4.IORRHQ.size(); i++) {
                        if (CPU4.IORRHQ.peek().IOInterrupt(CPU4.IORRHQ.peek()) != true) {//move the process to CPU if it is time for IO done
                            CPU4.IORRHQ.push(CPU4.IORRHQ.pop());
                            CPU4.IORRHQ.peek().IncTimeInProc();//increment the waiting time of the remainder processes
                        } else {//move process to CPUQueue
                            CPU4.IORRHQ.peek().stats.ContextSwitchTime++;
                            CPU4.CPURRHQ.push(CPU4.IORRHQ.pop());
                        }//end move
                    }
                    CPU4.IncClock();//increment the time that the IO has been running}
                }//end q4 loop IO
            }
        }//end while loop
    }
}
