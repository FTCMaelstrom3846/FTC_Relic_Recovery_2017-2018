package org.firstinspires.ftc.teamcode.control;

public class PIDController {
    private double i = 0, d = 0, KP, KI,
            KD, previousError = 0, maxI,
            previousTime = 0, NANOSECONDS_PER_MINUTE = 6e+10;

    
    public PIDController(double KP, double KI, double KD, double maxI) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.maxI = maxI;
    }

    public double power(double target, double currentLoc) {
        double error = target - currentLoc;
        double deltaTime = (System.nanoTime() - previousTime)/NANOSECONDS_PER_MINUTE;
        i = Math.min(maxI, Math.max(-maxI, i + error*deltaTime));
        d = (error - previousError)/deltaTime;
        double power = (KP*error) + (i/KI) + (KD*d);
        previousTime = System.nanoTime();
        previousError = error;
        return power;
    }

    public void reset() {
        i = 0;
        previousError = 0;
    }
}