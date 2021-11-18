package warikan.domain.model2;

public class Slope {
  double largeSlope;
  double mediumSlope;
  double smallSlope;

  public Slope(double largeSlope, double smallSlope) {
    mediumSlope = 1;
    if (largeSlope < mediumSlope || mediumSlope < smallSlope) {
      throw new IllegalArgumentException("パラメータが不正です");
    }
    this.largeSlope = largeSlope;
    this.smallSlope = smallSlope;
  }
}
