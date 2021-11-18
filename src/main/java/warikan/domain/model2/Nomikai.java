package warikan.domain.model2;

public class Nomikai {
  public Warikan calculate(int totalBillingAmount, Slope slope) {
    int numberOfLarge = 1;
    int numberOfMedium = 1;
    int numberOfSmall = 1;

    double weightedSum =
        slope.largeSlope * numberOfLarge
            + slope.mediumSlope * numberOfMedium
            + slope.smallSlope * numberOfSmall;

    int mediumPaymentAmount = (int) (totalBillingAmount / weightedSum);
    int largePaymentAmount = (int) (mediumPaymentAmount * slope.largeSlope);
    int smallPaymentAmount = (int) (mediumPaymentAmount * slope.smallSlope);

    return new Warikan(largePaymentAmount, mediumPaymentAmount, smallPaymentAmount);
  }
}
