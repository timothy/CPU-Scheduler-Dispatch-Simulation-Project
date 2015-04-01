/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.dispatch.scheduling;

import java.math.*;

/**
 *
 * @author tbrad_000
 */
public class ProcessCreator {

    private UniqueRand u = new UniqueRand(1000, 10000);

    private int PID;
    private int ArivalT;
    private int CPUT;
    private int IOT;
    private int Prior;
    private int WT;
    private int TIP;
    private int CPUN;
    private int CTS;
    private int count = 0;

    private void MakePID() {
        this.PID = getU().getURand().get(getCount()); //UniqueRand(1000, 10000);
        setCount(getCount() + 1);
    }//call UniqueRand for individualPID

    private void MakeArivalT() {
        this.ArivalT = 0 + (int) (Math.random() * 100);
    } //minimum + (int)(Math.random()*maximum)

    private void MakeCPUT() {
        this.CPUT = 0 + (int) (Math.random() * 100);
    }//minimum + (int)(Math.random()*maximum)

    private void MakeIOT() {
        this.IOT = 0 + (int) (Math.random() * 100);
    }//minimum + (int)(Math.random()*maximum)

    private void MakePrior() {
        this.Prior = 0 + (int) (Math.random() * 4);
    }//minimum + (int)(Math.random()*maximum)

    private void MakeWT() {
        this.setWT(0);
    }//no time as it has just been made will be updated later

    private void MakeTIP() {
        this.setTIP(0);
    }//no time as it has just been made will be updated later

    private void MakeCPUN() {
        this.setCPUN(0);
    }//Process starts without CPU will be assinded later

    private void MakeCTS() {
        this.setCTS(2);
    }//Context switch =2ms

    public Process MakeProcess() {
        this.MakePID();
        this.MakeArivalT();
        this.MakeCPUT();
        this.MakeIOT();
        this.MakePrior();
        this.MakeWT();
        this.MakeTIP();
        this.MakeCPUN();
        this.MakeCTS();
        return new Process(this.getPID(), this.getArivalT(), this.getCPUT(), this.getIOT(), this.getPrior(), this.getWT(), this.getTIP(), this.getCPUN(), this.getCTS());
    }

    /**
     * @return the u
     */
    public UniqueRand getU() {
        return u;
    }

    /**
     * @return the PID
     */
    public int getPID() {
        return PID;
    }

    /**
     * @return the ArivalT
     */
    public int getArivalT() {
        return ArivalT;
    }

    /**
     * @return the CPUT
     */
    public int getCPUT() {
        return CPUT;
    }

    /**
     * @return the IOT
     */
    public int getIOT() {
        return IOT;
    }

    /**
     * @return the Prior
     */
    public int getPrior() {
        return Prior;
    }

    /**
     * @return the WT
     */
    public int getWT() {
        return WT;
    }

    /**
     * @param WT the WT to set
     */
    public void setWT(int WT) {
        this.WT = WT;
    }

    /**
     * @return the TIP
     */
    public int getTIP() {
        return TIP;
    }

    /**
     * @param TIP the TIP to set
     */
    public void setTIP(int TIP) {
        this.TIP = TIP;
    }

    /**
     * @return the CPUN
     */
    public int getCPUN() {
        return CPUN;
    }

    /**
     * @param CPUN the CPUN to set
     */
    public void setCPUN(int CPUN) {
        this.CPUN = CPUN;
    }

    /**
     * @return the CTS
     */
    public int getCTS() {
        return CTS;
    }

    /**
     * @param CTS the CTS to set
     */
    public void setCTS(int CTS) {
        this.CTS = CTS;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}
