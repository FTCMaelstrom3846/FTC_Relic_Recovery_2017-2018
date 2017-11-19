package org.firstinspires.ftc.teamcode.control;

public class PIDController implements Constants{
    private double i = 0, d, KP, KI,
            KD, previousError = 0, maxI,
            previousTime = 0;


    
    public PIDController(double KP, double KI, double KD, double maxI) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.maxI = maxI;
    }

    public double power(double target, double currentLoc) {
        double error = target - currentLoc;
        double deltaTime = (System.nanoTime() - previousTime)/NANOSECONDS_PER_MINUTE;
        i += Math.abs(currentLoc) > Math.abs(target) * 0.85 ? error*deltaTime : 0;
        //i = Math.min(maxI, Math.max(-maxI, i));
        d = (error - previousError)/deltaTime;
        double power = (KP*error) + (KI*i) + (KD*d);
        previousTime = System.nanoTime();
        previousError = error;
        return power;
    }

    public double getI () {
        return i;
    }

    public void reset() {
        i = 0;
        previousError = 0;
    }
}