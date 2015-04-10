/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

/**
 * Processes contain their own stats and report them to CPU to Report to Dispatch stats arraylist
 * @author Zeus and tbrad_000
 */
public class Process {

    /**
     * Process ID Number
     */
    protected int PidNum;
    /**
     * Process Arival
     */
    protected int ArivalTime;
    /**
     * Total Time In CPU Queues
     */
    protected int CPUTime;
    /**
     * Total Time in IO Queues
     */
    protected int IOTime;
    /**
     * Priority Level
     */
    protected int Priority;
    /**
     * Time Waiting
     */
    protected int WaitTime;
    /**
     * Time Spent Proccessing
     */
    protected int TimeInProc;
    /**
     * CPU Assigned To
     */
    protected int CPUNum;
    /**
     * stats for this processor
     */
    Stats stats = new Stats();
    /**
     * Time to perform context switch
     */
    protected int ContextSwitch;
    /**
     * use for no arg constructor to make new process
     */
    private ProcessCreator PC = new ProcessCreator();

    /**
     * makes new process with randomly generated info
     */
    public Process() {
        PC.MakeProcess();

    }

    /**
     * makes new process whit the info specified
     *
     * @param PID Process ID Number
     * @param ArivalT Arrival Time
     * @param CPUT CPU TIme
     * @param IOT IO Time
     * @param Prior Priority
     * @param WT Wait Time
     * @param TIP Time in Process
     * @param CPUN Which CPU process is on
     * @param CTS Context Switch
     */
    public Process(int PID, int ArivalT, int CPUT, int IOT, int Prior, int WT, int TIP, int CPUN, int CTS) {
        this.PidNum = PID;
        this.ArivalTime = ArivalT;
        this.CPUTime = CPUT;
        this.IOTime = IOT;
        this.Priority = Prior;
        this.WaitTime = WT;
        this.TimeInProc = TIP;
        this.CPUNum = CPUN;
        this.ContextSwitch = CTS;
        this.stats.setPID(PID);
    }

    /**
     * @return the PidNum
     */
    public int getPidNum() {
        return PidNum;
    }

    /**
     * @param PidNum the PidNum to set
     */
    public void setPidNum(int PidNum) {
        this.PidNum = PidNum;
    }

    /**
     * @return the ArivalTime
     */
    public int getArivalTime() {
        return ArivalTime;
    }

    /**
     * @param ArivalTime the ArivalTime to set
     */
    public void setArivalTime(int ArivalTime) {
        this.ArivalTime = ArivalTime;
    }

    /**
     * @return the CPUTime
     */
    public int getCPUTime() {
        return CPUTime;
    }

    /**
     * @param CPUTime the CPUTime to set
     */
    public void setCPUTime(int CPUTime) {
        this.CPUTime = CPUTime;
    }

    /**
     * reduce the CPUTime
     */
    public void decCPUTime() {
        this.CPUTime--;
    }

    /**
     * @return the IOTime
     */
    public int getIOTime() {
        return IOTime;
    }

    /**
     * @param IOTime the IOTime to set
     */
    public void setIOTime(int IOTime) {
        this.IOTime = IOTime;
    }

    /**
     * reduce IOTime
     */
    public void decIOTIME() {
        this.IOTime--;
    }

    /**
     * @return the Priority
     */
    public int getPriority() {
        return Priority;
    }

    /**
     * @param Priority the Priority to set
     */
    public void setPriority(int Priority) {
        this.Priority = Priority;
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
     * @return the TimeInProc
     */
    public int getTimeInProc() {
        return TimeInProc;
    }

    /**
     * @param TimeInProc the TimeInProc to set
     */
    public void setTimeInProc(int TimeInProc) {
        this.TimeInProc = TimeInProc;
    }

    /**
     * @return the CPUNum
     */
    public int getCPUNum() {
        return CPUNum;
    }

    /**
     * @param CPUNum the CPUNum to set
     */
    public void setCPUNum(int CPUNum) {
        this.CPUNum = CPUNum;
    }

    /**
     * Increment the time in process
     */
    public void IncTimeInProc() {
        this.TimeInProc++;
    }

    /**
     * Increment the wait time
     */
    public void IncWaitTime() {
        this.WaitTime++;
    }

    /**
     * @return the ContextSwitch
     */
    public int getContextSwitch() {
        return ContextSwitch;
    }

    /**
     * @param ContextSwitch the ContextSwitch to set
     */
    public void setContextSwitch(int ContextSwitch) {
        this.ContextSwitch = ContextSwitch;
    }

    /**
     * Determine if the process should be doing IO
     *
     * @param p1 is the process in question
     * @return true if there is greater then 2x time IO
     */
    public boolean IOInterrupt(Process p1) {
        if ((p1.getIOTime()) > (2 * p1.getCPUTime())) {
            return true;
        } else {
            return false;
        }
    }

}
