package warikan.domain.model2;

public class Slope {
  double largeSlope;
  double mediumSlope;
  double smallSlope;

  public Slope(double largeSlope, double mediumSlope, double smallSlope) {
      if (largeSlope < mediumSlope || mediumSlope < smallSlope) {
          throw new IllegalArgumentException("パラメータが不正です");
      }
    this.largeSlope = largeSlope;
    this.mediumSlope = mediumSlope;
    this.smallSlope = smallSlope;
  }
}
