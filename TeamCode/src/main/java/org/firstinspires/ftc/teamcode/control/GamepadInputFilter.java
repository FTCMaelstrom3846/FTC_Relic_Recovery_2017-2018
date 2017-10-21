package org.firstinspires.ftc.teamcode.control;


public class GamepadInputFilter {
    private double currLeftStickX = 0;
    private double currLeftStickY = 0;
    private double currRightStickX = 0;
    private double STEP_AMOUNT = 0.01;
    private int TIME_PER_STEP_NANO = 500000;
    private long previousTime = 0;
    private long deltaTime = 0;

    public double lazyLeftStickX(double leftStickX) {
        if (deltaTime > TIME_PER_STEP_NANO) {
            if (leftStickX < currLeftStickX) {
                currLeftStickX -= STEP_AMOUNT;
            } else if (leftStickX > currLeftStickX) {
                currLeftStickX += STEP_AMOUNT;
            }
        }
        return currLeftStickX;
    }

    public double lazyLeftStickY(double leftStickY) {
        if (deltaTime > TIME_PER_STEP_NANO) {
            if (leftStickY < currLeftStickY) {
                currLeftStickY -= STEP_AMOUNT;
            } else if (leftStickY > currLeftStickY) {
                currLeftStickY += STEP_AMOUNT;
            }
        }
        return currLeftStickY;
    }

    public double lazyRighStickX(double rightStickX) {
        if (deltaTime > TIME_PER_STEP_NANO) {
            if (rightStickX < currRightStickX) {
                currRightStickX -= STEP_AMOUNT;
            } else if (rightStickX > currRightStickX) {
                currRightStickX += STEP_AMOUNT;
            }
        }
        return currRightStickX;
    }

    public void updateLazyTime() {
        deltaTime = System.nanoTime() - previousTime;
        previousTime = System.nanoTime();
    }

}
