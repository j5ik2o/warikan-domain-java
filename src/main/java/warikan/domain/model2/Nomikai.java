package warikan.domain.model2;

public class Nomikai {
  public Warikan calculate(int totalBillingAmount, Slope slope, NumberOfMembers numberOfMembers) {
    return Warikan.create(totalBillingAmount, slope, numberOfMembers);
  }
}
