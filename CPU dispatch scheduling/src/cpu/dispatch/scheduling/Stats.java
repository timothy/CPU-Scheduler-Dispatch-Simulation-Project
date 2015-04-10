/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

/**
 * Stats to be collected and stored on each process and passed back and forth to
 * the CPU and Dispatch class for collection and review
 *
 * @author tbrad_000 and Zeus
 */
public class Stats {

    /**
     * ID Number for Process
     */
    protected int PID;
    /**
     * CPU the Processor was on
     */
    protected int CPU;
    //protected int ThroughputTime;//# of processes that complete execution per time unit DO NOT USE!!!!
    /**
     * Time from submission of process till completion
     */
    protected int TurnaroundTime;
    /**
     * Time processor spent waiting IN QUEUES DO NOT INCLUDE CONTEXT SWITCH
     */
    protected int WaitTime;
    /**
     * Time from submission of a request until the response begins to be recived
     */
    protected int ResponseTime;
    /**
     * time taken for context switching, THIS DOES NOT ADD TO WAIT TIME
     */
    protected int ContextSwitchTime;
    /**
     * The percentage of time that the processor is busy
     */
    protected int ProcessorUtilization;
    /**
     * will need to calculate by comparing differences
     */
    protected int Speedup;

    /**
     * @return the PID
     */
    public int getPID() {
        return PID;
    }

    /**
     * @param PID the PID to set
     */
    public void setPID(int PID) {
        this.PID = PID;
    }

    /**
     * @return the CPU
     */
    public int getCPU() {
        return CPU;
    }

    /**
     * @param CPU the CPU to set
     */
    public void setCPU(int CPU) {
        this.CPU = CPU;
    }

//    /**
//     * @return the ThroughputTime
//     */
//    public int getThroughputTime() {
//        return ThroughputTime;
//    }
//    /**
//     * @param ThroughputTime the ThroughputTime to set
//     */
//    public void setThroughputTime(int ThroughputTime) {
//        this.ThroughputTime = ThroughputTime;
//    }
    /**
     * @return the TurnaroundTime
     */
    public int getTurnaroundTime() {
        return TurnaroundTime;
    }

    /**
     * @param TurnaroundTime the TurnaroundTime to set
     */
    public void setTurnaroundTime(int TurnaroundTime) {
        this.TurnaroundTime = TurnaroundTime;
    }

    /**
     * @return the WaitTime
     */
    public int getWaitTime() {
        return WaitTime;
    }

    /**
     * @param WaitTime the WaitTime to set
     */
    public void setWaitTime(int WaitTime) {
        this.WaitTime = WaitTime;
    }

    /**
     * @return the ResponseTime
     */
    public int getResponseTime() {
        return ResponseTime;
    }

    /**
     * @param ResponseTime the ResponseTime to set
     */
    public void setResponseTime(int ResponseTime) {
        this.ResponseTime = ResponseTime;
    }

    /**
     * @return the ContextSwitchTime
     */
    public int getContextSwitchTime() {
        return ContextSwitchTime;
    }

    /**
     * @param ContextSwitchTime the ContextSwitchTime to set
     */
    public void setContextSwitchTime(int ContextSwitchTime) {
        this.ContextSwitchTime = ContextSwitchTime;
    }

    /**
     * @return the ProcessorUtilization
     */
    public int getProcessorUtilization() {
        return ProcessorUtilization;
    }

    /**
     * @param ProcessorUtilization the ProcessorUtilization to set
     */
    public void setProcessorUtilization(int ProcessorUtilization) {
        this.ProcessorUtilization = ProcessorUtilization;
    }

    /**
     * @return the Speedup
     */
    public int getSpeedup() {
        return Speedup;
    }

    /**
     * @param Speedup the Speedup to set
     */
    public void setSpeedup(int Speedup) {
        this.Speedup = Speedup;
    }

    /**
     * generate statistics from process passed
     *
     * @param p process
     */
    public void genStats(Process p) {
        int junk = 1;
        p.stats.setTurnaroundTime(p.getTimeInProc() + p.getWaitTime());
        p.stats.setWaitTime(p.getWaitTime());
        p.stats.setResponseTime(8 * this.getContextSwitchTime());
    }

}
