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
        int responseTime = 0;
        int turnAround = 0;
        int waitTime = 0;
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
            if ((ProcQueue.get(i).getCPUTime() - ProcQueue.get(i).getIOTime()) > 50) {
                //remove from procQueue and store on CPU queue
                CPU1.loadProces(ProcQueue.get(i), 1);
                continue;
            } //CPU2 mostly CPU
            else if (ProcQueue.get(i).getIOTime() - ProcQueue.get(i).getCPUTime() > 50) {
                //remove from procQueue and store on CPU queue
                CPU2.loadProces(ProcQueue.get(i), 2);
                continue;
            } //CPU3 mostly even IO/CPU
            else if (ProcQueue.get(i).getIOTime() > ProcQueue.get(i).getCPUTime()) {
                //remove from procQueue and store on CPU queue
                CPU3.loadProces(ProcQueue.get(i), 3);
                continue;
            } //CPU4 Catch all
            else //remove from procQueue and store on CPU queue
            {
                CPU4.loadProces(ProcQueue.get(i), 4);
                continue;
            }
        }//End For loop for adding all proccess to CPU

        CPU1.runProcesses();
        CPU2.runProcesses();
        CPU3.runProcesses();
        CPU4.runProcesses();

        for (int i = 0; i < CPU1.ProcessAL.size(); i++) {
            stats.add(CPU1.stats.get(i));
        }
        for (int i = 0; i < CPU2.ProcessAL.size(); i++) {
            stats.add(CPU2.stats.get(i));
        }
        for (int i = 0; i < CPU3.ProcessAL.size(); i++) {
            stats.add(CPU3.stats.get(i));
        }
        for (int i = 0; i < CPU4.ProcessAL.size(); i++) {
            stats.add(CPU4.stats.get(i));
        }

        for (int i = 0; i < stats.size(); i++) {
            System.out.print("***Process***\n");
            System.out.printf("PID: %d", stats.get(i).getPID());
            System.out.print("\n");
            System.out.printf("CPU: %d", stats.get(i).getCPU());
//            System.out.print("\n");
//            System.out.printf("Context Switch Time: %d", stats.get(i).getContextSwitchTime());
            System.out.print("\n");
            System.out.printf("Response Time: %d", stats.get(i).getResponseTime());
            responseTime += stats.get(i).getResponseTime();
            System.out.print("\n");
            System.out.printf("Turnaround Time: %d", stats.get(i).getTurnaroundTime());
            turnAround += stats.get(i).getTurnaroundTime();
            System.out.print("\n");
            System.out.printf("Wait Time: %d", stats.get(i).getWaitTime());
            waitTime += stats.get(i).getWaitTime();
            System.out.print("\n");
            System.out.print("************************\n");
            System.out.print("\n");
            System.out.print("\n");
        }

        System.out.print(
                "\n");
        System.out.printf(
                "Avg Response Time: %d", responseTime / stats.size());
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Tunaround Time: %d", turnAround / stats.size());
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Wait Time: %d", waitTime / stats.size());
        System.out.print(
                "\n");
        //test break point
    }

}
