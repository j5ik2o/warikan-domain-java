package warikan.domain.model.amount;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

/**
 * 請求金額。
 *
 * <p>飲み会の請求金額を表す値オブジェクト。
 */
public final class BillingAmount {
  private final Money value;

  private BillingAmount(@Nonnull Money value) {
    Validate.isTrue(!value.isNegative(), "value is negative");
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value {@link Money}
   * @return {@link BillingAmount}
   */
  @Nonnull
  public static BillingAmount of(@Nonnull Money value) {
    return new BillingAmount(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BillingAmount that = (BillingAmount) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "BillingAmount{" + "value=" + value + '}';
  }

  @Nonnull
  public Money value() {
    return value;
  }

  @Nonnull
  public BillingAmount subtract(@Nonnull BillingAmount totalAmount) {
    return BillingAmount.of(value.subtract(totalAmount.value()));
  }

  public boolean nonZero() {
    return value.nonZero();
  }
}
