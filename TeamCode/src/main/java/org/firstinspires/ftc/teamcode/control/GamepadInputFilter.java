package org.firstinspires.ftc.teamcode.control;


public class GamepadInputFilter {
    private double currLeftStickX = 0;
    private double currLeftStickY = 0;
    private double currRightStickX = 0;
    private double STEP_AMOUNT = 0.16;

    public double lazyLeftStickX(double leftStickX) {
        if (leftStickX < currLeftStickX) {
            currLeftStickX -= STEP_AMOUNT;
        } else if (leftStickX > currLeftStickX) {
            currLeftStickX += STEP_AMOUNT;
        }

        if (Math.abs(leftStickX - currLeftStickX) < STEP_AMOUNT) {
            currLeftStickX = leftStickX;
        }

        return currLeftStickX;
    }

    public double lazyLeftStickY(double leftStickY) {
        if (leftStickY < currLeftStickY) {
            currLeftStickY -= STEP_AMOUNT;
        } else if (leftStickY > currLeftStickY) {
            currLeftStickY += STEP_AMOUNT;
        }

        if (Math.abs(leftStickY - currLeftStickY) < STEP_AMOUNT) {
            currLeftStickY = leftStickY;
        }

        return currLeftStickY;
    }

    public double lazyRighStickX(double rightStickX) {
        if (rightStickX < currRightStickX) {
            currRightStickX -= STEP_AMOUNT;
        } else if (rightStickX > currRightStickX) {
            currRightStickX += STEP_AMOUNT;
        }

        if (Math.abs(rightStickX - currRightStickX) < STEP_AMOUNT) {
            currRightStickX = rightStickX;
        }

        return currRightStickX;
    }

}
