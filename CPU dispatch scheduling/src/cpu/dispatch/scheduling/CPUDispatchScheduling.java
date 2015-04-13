/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.util.ArrayList;
import java.util.List;

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
    static ArrayList<Double> turnaround = new ArrayList<>();
    static ArrayList<Double> response = new ArrayList<>();
    static ArrayList<Double> wait = new ArrayList<>();
    static ArrayList<Double> avgturnaround = new ArrayList<>();
    static ArrayList<Double> avgresponse = new ArrayList<>();
    static ArrayList<Double> avgwait = new ArrayList<>();

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
        GraphPanel graph = new GraphPanel();
        int HQ = 38;
        int LQ = 12;

        for (int i = 0; i < 100; i++) { //Make X (100) processes and store them in ProcQueue
            Process p1 = PC.MakeProcess();
            ProcQueue.push(p1);
        }

//for 1 CPU only
        for (int k = 0; k < 3; k++) {
            response.clear();
            turnaround.clear();
            wait.clear();
            CPU1.clear();
            for (int i = 0; i < ProcQueue.size(); i++) {//add the all proccess to a CPU
                //CPU1           
                //remove from procQueue and store on CPU queue
                CPU1.loadProces(ProcQueue.get(i), 1);
            }//End For loop for adding all proccess to CPU

            CPU1.runProcessesRR(HQ);
            CPU1.runProcessesRR(LQ);
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

            graph.start(response, "1 CPU response");
            graph.start(turnaround, "1 CPU turnaround");
            graph.start(wait, "1 CPU wait");

//for 1 CPU
//for 2 CPUs
            response.clear();
            turnaround.clear();
            wait.clear();
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

            CPU1.runProcessesRR(HQ);
            CPU1.runProcessesRR(LQ);
            CPU1.runProcessesHPN();
            CPU1.runProcessesFCFS();

            CPU2.runProcessesRR(HQ);
            CPU2.runProcessesRR(LQ);
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
                responseTime2 = avgs.get(0) / 2;
                turnAround2 = avgs.get(1) / 2;
                waitTime2 = avgs.get(2) / 2;
            }
            graph.start(response, "2 CPU response");
            graph.start(turnaround, "2 CPU turnaround");
            graph.start(wait, "2 CPU wait");
//for 2 CPUs

//for 4 CPU's
            response.clear();
            turnaround.clear();
            wait.clear();
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

            CPU1.runProcessesRR(HQ);
            CPU1.runProcessesRR(LQ);
            CPU1.runProcessesHPN();
            CPU1.runProcessesFCFS();

            CPU2.runProcessesRR(40);
            CPU2.runProcessesRR(8);
            CPU2.runProcessesHPN();
            CPU2.runProcessesFCFS();

            CPU3.runProcessesRR(HQ);
            CPU3.runProcessesRR(LQ);
            CPU3.runProcessesHPN();
            CPU3.runProcessesFCFS();

            CPU4.runProcessesRR(HQ);
            CPU4.runProcessesRR(LQ);
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

            graph.start(response, "4 CPU response");
            graph.start(turnaround, "4 CPU turnaround");
            graph.start(wait, "4 CPU wait");

            avgs = statsGen(stats, responseTime, turnAround, waitTime);
            responseTime4 = avgs.get(0) / 4;
            turnAround4 = avgs.get(1) / 4;
            waitTime4 = avgs.get(2) / 4;

//for 4 CPUS
            System.out.printf("***For a Round Robin Time Quantum of %d and %d***", HQ, LQ);
            System.out.print(
                    "\n");
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
            System.out.print(
                    "\n");
            System.out.printf(
                    "Speed up from 1 CPU's to 2: %f", (double) (1 / (1 - ((double) turnAround2 / (double) turnAround)) + ((1 - ((double) turnAround2 / (double) turnAround)) / 2)) / 4);
            System.out.print(
                    "\n");
            System.out.printf(
                    "Speed up from 1 CPU's to 4: %f", (double) (1 / (1 - ((double) turnAround4 / (double) turnAround)) + ((1 - ((double) turnAround4 / (double) turnAround)) / 4)) / 4);
            System.out.print(
                    "\n");
            HQ -= 10;
            LQ -= 4;
            System.out.print(
                    "\n");
        }
    }

    public static ArrayList statsGen(ArrayList Stats, int responseTime, int turnAround, int waitTime) {
        ArrayList<Stats> stats = new ArrayList<>();
        ArrayList<Integer> avgs = new ArrayList<>();
        stats = Stats;
        for (int i = 0; i < stats.size(); i++) {
//            System.out.print("***Process***\n");
//            System.out.printf("PID: %d", stats.get(i).getPID());
//            System.out.print("\n");
//            System.out.printf("CPU: %d", stats.get(i).getCPU());
////            System.out.print("\n");
////            System.out.printf("Context Switch Time: %d", stats.get(i).getContextSwitchTime());
//            System.out.print("\n");
//            System.out.printf("Response Time: %d", stats.get(i).getResponseTime());
            responseTime += stats.get(i).getResponseTime();
            response.add((double) responseTime);
//            System.out.print("\n");
//            System.out.printf("Turnaround Time: %d", stats.get(i).getTurnaroundTime());
            turnAround += stats.get(i).getTurnaroundTime();
            turnaround.add((double) turnAround);
//            System.out.print("\n");
//            System.out.printf("Wait Time: %d", stats.get(i).getWaitTime());
            waitTime += stats.get(i).getWaitTime();
            wait.add((double) waitTime);
//            System.out.print("\n");
//            System.out.printf("On Queue: %s", stats.get(i).getQue());
//            System.out.print("\n");
//            System.out.print("************************\n");
//            System.out.print("\n");
//            System.out.print("\n");
        }
        avgs.add(responseTime / stats.size());
        avgs.add(turnAround / stats.size());
        avgs.add(waitTime / stats.size());
        return avgs;
    }
}
