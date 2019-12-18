package warikan.domain.model.amount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;
import warikan.domain.model.amount.rate.AmountRate;

/**
 * 支払金額。
 *
 * <p>支払う金額を表す値オブジェクト。
 */
public final class PaymentAmount {
  private final Money value;

  @Nonnull
  public static PaymentAmount zero(Currency currency) {
    return of(Money.zero(currency));
  }

  private PaymentAmount(@Nonnull Money value) {
    Validate.isTrue(!value.isNegative(), "value is negative");
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param billingAmount {@link BillingAmount}
   * @param amountRate {@link AmountRate}
   * @return {@link PaymentAmount}
   */
  @Nonnull
  public static PaymentAmount of(
      @Nonnull BillingAmount billingAmount, @Nonnull AmountRate amountRate) {
    return of(billingAmount.value().times(amountRate.value(), RoundingMode.DOWN));
  }

  /**
   * ファクトリメソッド。
   *
   * @param value {@link Money}
   * @return {@link PaymentAmount}
   */
  @Nonnull
  public static PaymentAmount of(@Nonnull Money value) {
    return new PaymentAmount(value);
  }

  /**
   * ファクトリメソッド。
   *
   * @param value {@link BigDecimal}
   * @param currency {@link Currency}
   * @return {@link PaymentAmount}
   */
  @Nonnull
  public static PaymentAmount of(@Nonnull BigDecimal value, @Nonnull Currency currency) {
    return of(Money.of(value, currency));
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 通貨の量
   * @param currency {@link Currency}
   * @return {@link PaymentAmount}
   */
  @Nonnull
  public static PaymentAmount of(long value, @Nonnull Currency currency) {
    return of(Money.of(value, currency));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaymentAmount that = (PaymentAmount) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "PaymentAmount{" + "value=" + value + '}';
  }

  @Nonnull
  public Money value() {
    return value;
  }

  public BillingAmount toBillingAmount() {
    return BillingAmount.of(value);
  }

  @Nonnull
  public PaymentAmount add(@Nonnull PaymentAmount other) {
    return of(value.add(other.value));
  }

  @Nonnull
  public PaymentAmount add(@Nonnull Money money) {
    return of(value.add(money));
  }
}
