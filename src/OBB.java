
/**
 * @author scott.cgi
 * @since  2012-11-19
 *  
 * Oriented bounding box 
 */
public class OBB {
 
  private float[] centerPoint;
 
  private float halfWidth;
 
  private float halfHeight;
 
  // unit vector of x axis
  private float[] axisX;
  // unit vector of y axis
  private float[] axisY;
 
  // 0 -360
  private float rotation;
 
  /**
   * Create default OBB
   * 
   * @param x Top left x
   * @param y Top left y
   * @param width
   * @param height
   */
  public OBB(float x, float y, float width, float height) {
 
    this.axisX = new float[2];
    this.axisY = new float[2];
 
    this.setRotation(0.0f);
 
    this.halfWidth  = width / 2;
    this.halfHeight = height / 2;
 
    this.centerPoint = new float[2];
 
    this.setXY(x, y);
  }
 
  /**
   * Get axisX and axisY projection radius distance on axis
   */
  public float getProjectionRadius(float[] axis) {
 
    // axis, axisX and axisY are unit vector
 
    // projected axisX to axis
    float projectionAxisX = this.dot(axis, this.axisX);
    // projected axisY to axis
    float projectionAxisY = this.dot(axis, this.axisY);
 
    return this.halfWidth * projectionAxisX + this.halfHeight * projectionAxisY;
  }
 
  /**
   * OBB is collision with other OBB
   */
  public boolean isCollision(OBB obb) {
    // two OBB center distance vector
    float[] centerDistanceVertor = {
        this.centerPoint[0] - obb.centerPoint[0],
        this.centerPoint[1] - obb.centerPoint[1]
    };
 
    float[][] axes = {
        this.axisX,
        this.axisY,
        obb.axisX,
        obb.axisY,
    };
 
    for(int i = 0; i < axes.length; i++) {
      // compare OBB1 radius projection add OBB2 radius projection to centerDistance projection
      if(this.getProjectionRadius(axes[i]) + obb.getProjectionRadius(axes[i]) 
          <= this.dot(centerDistanceVertor, axes[i])) {
        return false;
      }
    }
 
    return true;
  }
 
  /**
   * dot-multiply
   */
  private float dot(float[] axisA, float[] axisB) {
    return Math.abs(axisA[0] * axisB[0] + axisA[1] * axisB[1]);
  }
 
  /**
   * Set axis x and y by rotation
   * 
   * @param rotation float 0 - 360 
   */
  public OBB setRotation(float rotation) {
    this.rotation = rotation;
 
    this.axisX[0] = MathUtils.cosDeg(rotation);
    this.axisX[1] = MathUtils.sinDeg(rotation);
 
    this.axisY[0] = -MathUtils.sinDeg(rotation);
    this.axisY[1] = MathUtils.cosDeg(rotation);
 
    return this;
  }
 
  /**
   * Set OBB top left x, y
   */
  public OBB setXY(float x, float y) {
    this.centerPoint[0] = x + this.halfWidth;
    this.centerPoint[1] = y + this.halfHeight;
 
    return this;
  }
 
  public float getRotation() {
    return this.rotation;
  }
 
  public float getX() {
    return this.centerPoint[0] - this.halfWidth;
  }
 
  public float getY() {
    return this.centerPoint[1] - this.halfHeight;
  }
 
  public float getWidth() {
    return this.halfWidth * 2;
  }
 
  public float getHeight() {
    return this.halfHeight * 2;
  }
 
}