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
public class Clock {

    private int tick;//Number of tick that have gone by

    Clock() {
        tick = 0;
    }

    public void tick() {
        tick++;
    }

    /**
     * @return the tick
     */
    public int getTick() {
        return tick;
    }

    /**
     * @param tick the tick to set
     */
    public void setTick(int tick) {
        this.tick = tick;
    }

}
