package warikan.domain.model2;

public class Nomikai {
  public Warikan calculate(int totalBillingAmount, Slope slope, NumberOfMembers numberOfMembers) {
    WeightedSum weightedSum = new WeightedSum(slope, numberOfMembers);

    int mediumPaymentAmount = (int) (totalBillingAmount / weightedSum.toDouble());
    int largePaymentAmount = (int) (mediumPaymentAmount * slope.largeSlope);
    int smallPaymentAmount = (int) (mediumPaymentAmount * slope.smallSlope);

    return new Warikan(largePaymentAmount, mediumPaymentAmount, smallPaymentAmount);
  }
}
