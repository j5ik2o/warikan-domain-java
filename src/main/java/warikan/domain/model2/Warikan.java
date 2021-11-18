package warikan.domain.model2;

public class Warikan {
  public int medium;
  private Slope slope;

  public static Warikan create(
      int totalBillingAmount, Slope slope, NumberOfMembers numberOfMembers) {
    WeightedSum weightedSum = new WeightedSum(slope, numberOfMembers);
    return new Warikan(totalBillingAmount, weightedSum, slope);
  }

  private Warikan(int totalBillingAmount, WeightedSum weightedSum, Slope slope) {
    this.slope = slope;
    this.medium = (int) (totalBillingAmount / weightedSum.toDouble());
  }

  public int large() {
    return (int) (this.medium * slope.largeSlope);
  }

  public int medium() {
    return medium;
  }

  public int small() {
    return (int) (this.medium * slope.smallSlope);
  }
}
