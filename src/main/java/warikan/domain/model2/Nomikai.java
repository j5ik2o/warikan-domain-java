package warikan.domain.model2;

public class Nomikai {
  public Warikan calculate(
      int totalBillingAmount, double largeSlope, double mediumSlope, double smallSlope) {
    int numberOfLarge = 1;
    int numberOfMedium = 1;
    int numberOfSmall = 1;

    double weightedSum =
        largeSlope * numberOfLarge + mediumSlope * numberOfMedium + smallSlope * numberOfSmall;

    int mediumPaymentAmount = (int) (totalBillingAmount / weightedSum);
    int largePaymentAmount = (int) (mediumPaymentAmount * largeSlope);
    int smallPaymentAmount = (int) (mediumPaymentAmount * smallSlope);

    return new Warikan(largePaymentAmount, mediumPaymentAmount, smallPaymentAmount);
  }
}
