package warikan.domain.model2;

public class WeightedSum {
  private Slope slope;
  private NumberOfMembers numberOfMembers;

  public WeightedSum(Slope slope, NumberOfMembers numberOfMembers) {
    this.slope = slope;
    this.numberOfMembers = numberOfMembers;
  }

  public double toDouble() {
    return slope.largeSlope * numberOfMembers.large
        + slope.mediumSlope * numberOfMembers.medium
        + slope.smallSlope * numberOfMembers.small;
  }
}
