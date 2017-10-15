package org.firstinspires.ftc.teamcode.control;

public class PIDController {
    private double i = 0, d = 0, KP, KI,
            KD, previousError = 0, maxI;

    
    public PIDController(double KP, double KI, double KD, double maxI) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.maxI = maxI;
    }

    public double power(double target, double currentLoc) {
        double error = target - currentLoc;
        i = Math.min(maxI, Math.max(-maxI, i + error*dt));
        d = (error - previousError)/dt;
        double power = (KP*error) + (i/KI) + (KD*d);

        previousError = error;
        return power;
    }

    public void reset() {
        i = 0;
        previousError = 0;
    }
}