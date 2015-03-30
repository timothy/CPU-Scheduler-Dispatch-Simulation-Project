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
        Process p1 = PC.MakeProcess();
        MyQue ProcQueue = new MyQue();
        ProcQueue.push(p1);
        //add the all proccess loop
        if (ProcQueue.peek().getIOTime() < ProcQueue.peek().getCPUTime()) {
            ProcQueue.peek().stats.setCPU(1);
            CPU1.ProcessAL.add(ProcQueue.pop());
            if (CPU1.CPURRHQ.size() < 100){
                CPU1.CPURRHQ.push(CPU1.ProcessAL.get(0));
                System.out.printf("%f",CPU1.CPURRHQ.peek().getIOTime());
            }
        }
    }
}