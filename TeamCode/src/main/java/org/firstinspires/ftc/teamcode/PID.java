package org.firstinspires.ftc.teamcode;

public class PID
{
    private double i = 0, d = 0, KP, KI,
            KD, previousError = 0, minI, maxI;
    
    public PID (double KP, double KI, double KD, double maxI, double minI) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.minI = minI;
        this.maxI = maxI;
    }

    public double power(double target, double currentLoc, double maxPower, double dt) {
        double error = target - currentLoc;
        i = Math.min(maxI, Math.max(minI, i + error*dt));
        d = (error - previousError)/dt;
        double power = (KP*error) + (i/KI) + (KD*d);

        previousError = error;
        return Math.min(maxPower, Math.max(-maxPower, power));
    }

    public void reset() {
        i = 0;
        previousError = 0;
    }
}