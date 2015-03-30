/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

/**
 *
 * @author tbrad_000
 */
public class Stats {

    protected int PID;//ID Number for Process
    protected int CPU;//CPU the Processor was on
    protected int ThroughputTime;//# of processes that complete execution per time unit DO NOT USE!!!!
    protected int TurnaroundTime;//Time from submission of process till completion
    protected int WaitTime;//Time processor spent waiting IN QUEUES DO NOT INCLUDE CONTEXT SWITCH
    protected int ResponseTime;//Time from submission of a request until the response begins to be recived
    protected int ContextSwitchTime;//time taken for context switching, THIS DOES NOT ADD TO WAIT TIME
    protected int ProcessorUtilization;//The percentage of time that the processor is busy
    protected int Speedup;//

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

    /**
     * @return the ThroughputTime
     */
    public int getThroughputTime() {
        return ThroughputTime;
    }

    /**
     * @param ThroughputTime the ThroughputTime to set
     */
    public void setThroughputTime(int ThroughputTime) {
        this.ThroughputTime = ThroughputTime;
    }

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

}
