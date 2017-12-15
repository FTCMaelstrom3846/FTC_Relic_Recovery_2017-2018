package ftc.vision;


import org.opencv.core.Scalar;

/**
 * Storage class for the position and color of the beacon
 * This file was made by the electronVolts, FTC team 7393
 * Date Created: 8/17/16.
 */
public class JewelColorResult {
  
  public enum JewelColor {
    RED     (ImageUtil.RED),
    GREEN   (ImageUtil.GREEN),
    BLUE    (ImageUtil.BLUE),
    UNKNOWN (ImageUtil.BLACK);
    
    public final Scalar color;
    
    JewelColor(Scalar scalar){
      this.color = scalar;
    }
  }
  private final JewelColor leftColor, rightColor;
  
  public JewelColorResult() {
    this.leftColor = JewelColor.UNKNOWN;
    this.rightColor = JewelColor.UNKNOWN;
  }
  
  public JewelColorResult(JewelColor leftColor, JewelColor rightColor) {
    this.leftColor = leftColor;
    this.rightColor = rightColor;
  }
  
  public String toString(){
    return leftColor + ", " + rightColor;
  }
  
  public JewelColor getLeftColor() {
    return leftColor;
  }
  
  public JewelColor getRightColor() {
    return rightColor;
  }
}
