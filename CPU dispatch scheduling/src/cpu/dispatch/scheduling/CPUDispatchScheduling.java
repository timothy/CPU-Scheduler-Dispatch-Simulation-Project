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
                CPU1.loadProces(ProcQueue.get(i), 1);
            }
            //CPU2 mostly CPU
            if ((ProcQueue.peek().getIOTime()) < ProcQueue.peek().getCPUTime() - 25) {
                //remove from procQueue and store on CPU queue
                CPU2.loadProces(ProcQueue.get(i), 2);
            }
            //CPU3 mostly even IO/CPU
            if ((ProcQueue.peek().getIOTime() - ProcQueue.peek().getCPUTime()) < 10) {
                //remove from procQueue and store on CPU queue
                CPU3.loadProces(ProcQueue.get(i), 3);
            } //CPU4 Catch all
            else //remove from procQueue and store on CPU queue
            {
                CPU4.loadProces(ProcQueue.get(i), 4);
            }
        }//End For loop for adding all proccess to CPU

        CPU1.runProcesses();
        CPU2.runProcesses();
        CPU3.runProcesses();
        CPU4.runProcesses();

        for (int i = 0; i < 10; i++) {
            if (CPU1.stats.size() > 0) {
                stats.add(CPU1.stats.get(i));
            }
            if (CPU2.stats.size() > 0) {
                stats.add(CPU2.stats.get(i));
            }
            if (CPU3.stats.size() > 0) {
                stats.add(CPU3.stats.get(i));
            }
            if (CPU4.stats.size() > 0) {
                stats.add(CPU4.stats.get(i));
            }
        }

        for (int i = 0; i < stats.size(); i++) {
            System.out.print("***Process***\n");
            System.out.printf("PID: %d", stats.get(i).getPID());
            System.out.print("\n");
            System.out.printf("CPU: %d", stats.get(i).getCPU());
            System.out.print("\n");
            System.out.printf("Context Switch Time: %d",stats.get(i).getContextSwitchTime());
            System.out.print("\n");
            System.out.printf("Response Time: %d",stats.get(i).getResponseTime());
            System.out.print("\n");
            System.out.printf("Turnaround Time: %d",stats.get(i).getTurnaroundTime());
            System.out.print("\n");
            System.out.printf("Wait Time: %d",stats.get(i).getWaitTime());
            System.out.print("\n");
            System.out.print("************************\n");
            System.out.print("\n");
            System.out.print("\n");
        }
        System.out.print("\n");
        System.out.print("clock1\n");
        System.out.println(CPU1.clock.getTick());
        System.out.print("\n");
        System.out.print("clock2\n");
        System.out.println(CPU1.clock.getTick());
        System.out.print("\n");
        System.out.print("clock3\n");
        System.out.println(CPU1.clock.getTick());
        System.out.print("\n");
        System.out.print("clock4\n");
        System.out.println(CPU1.clock.getTick());

        System.out.print("Done"); //test break point
    }

}
