/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;

/**
 * Create a processes creator to make 100 processes, load them into a container
 * and distribute them to select processors Uncomment the number of processors
 * desired 1,2, or 4 Have the CPUs based on number run the processes through
 * their queues Collect processes stats and add them to an array Out put
 * processes stats and their avgs
 *
 * @author Zeus
 */
public class CPUDispatchScheduling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int responseTime = 0;
        int turnAround = 0;
        int waitTime = 0;
        int responseTime2 = 0;
        int turnAround2 = 0;
        int waitTime2 = 0;
        int responseTime4 = 0;
        int turnAround4 = 0;
        int waitTime4 = 0;
        CPU CPU1 = new CPU();
        CPU CPU2 = new CPU();
        CPU CPU3 = new CPU();
        CPU CPU4 = new CPU();
        ArrayList<Stats> stats = new ArrayList<>();//ArrayList containing all the stats from finished processes to create avg
        ArrayList<Integer> avgs = new ArrayList<>();
        ProcessCreator PC = new ProcessCreator();
        MyQue ProcQueue = new MyQue();

        for (int i = 0; i < 100; i++) { //Make X (100) processes and store them in ProcQueue
            Process p1 = PC.MakeProcess();
            ProcQueue.push(p1);
        }

//for 1 CPU only
        CPU1.clear();
        for (int i = 0; i < ProcQueue.size(); i++) {//add the all proccess to a CPU
            //CPU1           
            //remove from procQueue and store on CPU queue
            CPU1.loadProces(ProcQueue.get(i), 1);
        }//End For loop for adding all proccess to CPU

        CPU1.runProcessesRR(8);
        CPU1.runProcessesRR(2);
        CPU1.runProcessesHPN();
        CPU1.runProcessesFCFS();
        avgs.clear();
        stats.clear();
        for (int i = 0; i < CPU1.ProcessAL.size(); i++) {
            stats.add(CPU1.stats.get(i));
        }

        avgs = statsGen(stats, responseTime, turnAround, waitTime);
        responseTime = avgs.get(0);
        turnAround = avgs.get(1);
        waitTime = avgs.get(2);
//for 1 CPU

//for 2 CPUs
        CPU1.clear();
        CPU2.clear();
        for (int i = 0; i < ProcQueue.size(); i++) {//add the all proccess to a CPU
            //CPU1 mostly IO
            if (CPU1.ProcessAL.size() < 50) {
                //remove from procQueue and store on CPU queue
                CPU1.loadProces(ProcQueue.get(i), 1);
            } //CPU2 mostly CPU
            else {
                //remove from procQueue and store on CPU queue
                CPU2.loadProces(ProcQueue.get(i), 2);
            } //CPU3 mostly even IO/CPU
        }//End For loop for adding all proccess to CPU

        CPU1.runProcessesRR(8);
        CPU1.runProcessesRR(2);
        CPU1.runProcessesHPN();
        CPU1.runProcessesFCFS();
        
        CPU2.runProcessesRR(8);
        CPU2.runProcessesRR(2);
        CPU2.runProcessesHPN();
        CPU2.runProcessesFCFS();
        
        avgs.clear();
        stats.clear();
        for (int i = 0; i < CPU1.ProcessAL.size(); i++) {
            stats.add(CPU1.stats.get(i));
        }
        for (int i = 0; i < CPU2.ProcessAL.size(); i++) {
            stats.add(CPU2.stats.get(i));

            avgs = statsGen(stats, responseTime, turnAround, waitTime);
            responseTime2 = avgs.get(0)/2;
            turnAround2 = avgs.get(1)/2;
            waitTime2 = avgs.get(2)/2;
        }
//for 2 CPUs

//for 4 CPU's
        CPU1.clear();
        CPU2.clear();
        CPU3.clear();
        CPU4.clear();
        for (int i = 0; i < ProcQueue.size(); i++) {//add the all proccess to a CPU
            //CPU1 mostly IO
            if (CPU1.ProcessAL.size() < 25) {
                //remove from procQueue and store on CPU queue
                CPU1.loadProces(ProcQueue.get(i), 1);
            } //CPU2 mostly CPU
            else if (CPU2.ProcessAL.size() < 25) {
                //remove from procQueue and store on CPU queue
                CPU2.loadProces(ProcQueue.get(i), 2);
            } //CPU3 mostly even IO/CPU
            else if (CPU3.ProcessAL.size() < 25) {
                //remove from procQueue and store on CPU queue
                CPU3.loadProces(ProcQueue.get(i), 3);
            } //CPU4 Catch all
            else //remove from procQueue and store on CPU queue
            {
                CPU4.loadProces(ProcQueue.get(i), 4);
            }
        }//End For loop for adding all proccess to CPU

        CPU1.runProcessesRR(8);
        CPU1.runProcessesRR(2);
        CPU1.runProcessesHPN();
        CPU1.runProcessesFCFS();
        
        CPU2.runProcessesRR(8);
        CPU2.runProcessesRR(2);
        CPU2.runProcessesHPN();
        CPU2.runProcessesFCFS();
        
        CPU3.runProcessesRR(8);
        CPU3.runProcessesRR(2);
        CPU3.runProcessesHPN();
        CPU3.runProcessesFCFS();
        
        CPU4.runProcessesRR(8);
        CPU4.runProcessesRR(2);
        CPU4.runProcessesHPN();
        CPU4.runProcessesFCFS();
        
        avgs.clear();
        stats.clear();
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

        avgs = statsGen(stats, responseTime, turnAround, waitTime);
        responseTime4 = avgs.get(0)/4;
        turnAround4 = avgs.get(1)/4;
        waitTime4 = avgs.get(2)/4;
//for 4 CPUS

        System.out.print(
                "\n");
        System.out.printf(
                "Avg Response Time for 1 CPU's: %d", responseTime);
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Tunaround Time for 1 CPU's: %d", turnAround);
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Wait Time for 1 CPU's: %d", waitTime);
        System.out.print(
                "\n");
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Response Time for 2 CPU's: %d", responseTime2);
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Tunaround Time for 2 CPU's: %d", turnAround2);
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Wait Time for 2 CPU's: %d", waitTime2);
        System.out.print(
                "\n");
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Response Time for 4 CPU's: %d", responseTime4);
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Tunaround Time for 4 CPU's: %d", turnAround4);
        System.out.print(
                "\n");
        System.out.printf(
                "Avg Wait Time for 4 CPU's: %d", waitTime4);
        System.out.print(
                "\n");
    }

    public static ArrayList statsGen(ArrayList Stats, int responseTime, int turnAround, int waitTime) {
        ArrayList<Stats> stats = new ArrayList<>();
        ArrayList<Integer> avgs = new ArrayList<>();
        stats = Stats;
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
            System.out.printf("On Queue: %s", stats.get(i).getQue());
            System.out.print("\n");
            System.out.print("************************\n");
            System.out.print("\n");
            System.out.print("\n");
        }
        avgs.add(responseTime / stats.size());
        avgs.add(turnAround / stats.size());
        avgs.add(waitTime / stats.size());
        return avgs;
    }
}
