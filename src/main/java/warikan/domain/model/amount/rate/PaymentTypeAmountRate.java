package warikan.domain.model.amount.rate;

import java.util.Objects;
import javax.annotation.Nonnull;
import warikan.domain.model.payment_type.PaymentType;

/** 支払区分料金割合。 */
public final class PaymentTypeAmountRate implements Comparable<PaymentTypeAmountRate> {
  private final PaymentType paymentType;
  private final AmountRate amountRate;

  private PaymentTypeAmountRate(@Nonnull PaymentType paymentType, @Nonnull AmountRate amountRate) {
    this.paymentType = paymentType;
    this.amountRate = amountRate;
  }

  /**
   * ファクトリメソッド。
   *
   * @param paymentType {@link PaymentType}
   * @param amountRate {@link AmountRate}
   */
  @Nonnull
  public static PaymentTypeAmountRate of(
      @Nonnull PaymentType paymentType, @Nonnull AmountRate amountRate) {
    return new PaymentTypeAmountRate(paymentType, amountRate);
  }

  @Override
  public int compareTo(PaymentTypeAmountRate o2) {
    return Integer.compare(paymentType.weight(), o2.paymentType.weight());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaymentTypeAmountRate that = (PaymentTypeAmountRate) o;
    return paymentType == that.paymentType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentType);
  }

  @Override
  public String toString() {
    return "PaymentTypeAmountRate{"
        + "paymentType="
        + paymentType
        + ", amountRate="
        + amountRate
        + '}';
  }

  /**
   * {@link AmountRate}を置き換える。
   *
   * @param newAmountRate {@link AmountRate}
   * @return {@link PaymentTypeAmountRate}
   */
  @Nonnull
  public PaymentTypeAmountRate withNewAmountRate(@Nonnull AmountRate newAmountRate) {
    return of(paymentType, newAmountRate);
  }

  /**
   * {@link PaymentType}を取得する。
   *
   * @return {@link PaymentType}
   */
  @Nonnull
  PaymentType paymentType() {
    return paymentType;
  }

  /**
   * {@link AmountRate}を取得する。
   *
   * @return {@link AmountRate}
   */
  @Nonnull
  AmountRate amountRate() {
    return amountRate;
  }
}
