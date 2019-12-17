package warikan.domain.model.payment_type;

/** 支払区分 */
public enum PaymentType {
  WARIKAN_PLUS(3),
  WARIKAN(2),
  WARIKAN_MINUS(1);

  private final int weight;

  public int weight() {
    return weight;
  }

  PaymentType(int weight) {
    this.weight = weight;
  }
}
