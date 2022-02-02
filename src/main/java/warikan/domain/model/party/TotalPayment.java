package warikan.domain.model.party;

import java.util.Objects;

import javax.annotation.Nonnull;

import warikan.domain.model.Money;

/** 請求金額 */
public final class TotalPayment {
  private final Money value;
  private TotalPayment(@Nonnull Money value) {
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 請求金額。
   * @return {@link TotalPayment}
   */
  @Nonnull
  public static TotalPayment of(@Nonnull Money value) {
    return new TotalPayment(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TotalPayment that = (TotalPayment) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "TotalPayment{" + "value='" + value + '\'' + '}';
  }
}