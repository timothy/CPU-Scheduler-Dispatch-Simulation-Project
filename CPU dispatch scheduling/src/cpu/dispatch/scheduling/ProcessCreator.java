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
        this.PID = u.getURand().get(count); //UniqueRand(1000, 10000);
        count++;
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
        this.WT = 0;
    }//no time as it has just been made will be updated later

    private void MakeTIP() {
        this.TIP = 0;
    }//no time as it has just been made will be updated later

    private void MakeCPUN() {
        this.CPUN = 0;
    }//Process starts without CPU will be assinded later

    private void MakeCTS() {
        this.CTS = 2;
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
        return new Process(this.PID, this.ArivalT, this.CPUT, this.IOT, this.Prior, this.WT, this.TIP, this.CPUN, this.CTS);
    }
}
