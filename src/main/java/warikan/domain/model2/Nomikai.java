package warikan.domain.model2;

public class Nomikai {
  public Warikan calculate(int totalBillingAmount, Slope slope, NumberOfMembers numberOfMembers) {
    double weightedSum =
        slope.largeSlope * numberOfMembers.large
            + slope.mediumSlope * numberOfMembers.medium
            + slope.smallSlope * numberOfMembers.small;

    int mediumPaymentAmount = (int) (totalBillingAmount / weightedSum);
    int largePaymentAmount = (int) (mediumPaymentAmount * slope.largeSlope);
    int smallPaymentAmount = (int) (mediumPaymentAmount * slope.smallSlope);

    return new Warikan(largePaymentAmount, mediumPaymentAmount, smallPaymentAmount);
  }
}
