package warikan.domain.model2;

public class Nomikai {
  public Warikan calculate(int totalBillingAmount, Slope slope, NumberOfMembers numberOfMembers) {
    WeightedSum weightedSum = new WeightedSum(slope, numberOfMembers);
    return new Warikan(totalBillingAmount, weightedSum, slope);
  }
}
