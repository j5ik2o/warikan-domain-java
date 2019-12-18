package warikan.domain.model.amount.rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * 料金割合。
 *
 * <p>料金の割合を表す値オブジェクト。
 */
public final class AmountRate implements Comparable<AmountRate> {
  private static final int DEFAULT_SCALE = 2;
  private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
  public static final AmountRate ZERO =
      new AmountRate(BigDecimal.ZERO, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);

  private final BigDecimal value;
  private final int scale;
  private final RoundingMode roundingMode;

  private AmountRate(@Nonnull BigDecimal value, int scale, @Nonnull RoundingMode roundingMode) {
    if (value.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("value is negative");
    this.value = value;
    this.scale = scale;
    this.roundingMode = roundingMode;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 割合
   * @param scale 浮動小数点の精度
   * @param roundingMode {@link RoundingMode}
   * @return {@link AmountRate}
   */
  @Nonnull
  public static AmountRate of(
      @Nonnull BigDecimal value, int scale, @Nonnull RoundingMode roundingMode) {
    return new AmountRate(value, scale, roundingMode);
  }

  @Nonnull
  public static AmountRate of(@Nonnull BigDecimal value) {
    return of(value, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
  }

  @Nonnull
  public static AmountRate of(long value, int scale, @Nonnull RoundingMode roundingMode) {
    return new AmountRate(BigDecimal.valueOf(value), scale, roundingMode);
  }

  @Nonnull
  public static AmountRate of(long value) {
    return of(BigDecimal.valueOf(value), DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
  }

  @Nonnull
  public static AmountRate of(Double value, int scale, @Nonnull RoundingMode roundingMode) {
    return new AmountRate(BigDecimal.valueOf(value), scale, roundingMode);
  }

  @Nonnull
  public static AmountRate of(Double value) {
    return of(BigDecimal.valueOf(value), DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AmountRate that = (AmountRate) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "AmountRate{" + "value=" + value + '}';
  }

  /**
   * 合計を基に料金割合を再計算する。
   *
   * @param total 合計値
   * @return {@link AmountRate}
   */
  @Nonnull
  public AmountRate adjust(@Nonnull BigDecimal total) {
    if (total.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("total is negative.");
    return new AmountRate(value.divide(total, scale, roundingMode), scale, roundingMode);
  }

  /**
   * 合計を基に料金割合を再計算する。
   *
   * @param total 合計値
   * @return {@link AmountRate}
   */
  @Nonnull
  public AmountRate adjust(@Nonnull AmountRate total) {
    return adjust(total.value);
  }

  /**
   * 合計を基に料金割合を再計算する。
   *
   * @param total 合計値
   * @return {@link AmountRate}
   */
  @Nonnull
  public AmountRate adjust(long total) {
    return adjust(BigDecimal.valueOf(total));
  }

  /**
   * 料金割合を除算する。
   *
   * @param n 数値
   * @return {@link AmountRate}
   */
  @Nonnull
  public AmountRate divide(@Nonnull BigDecimal n) {
    if (n.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("n is negative");
    return of(value.divide(n, scale, roundingMode));
  }

  /**
   * 料金割合を除算する。
   *
   * @param n 数値
   * @return {@link AmountRate}
   */
  @Nonnull
  public AmountRate divide(long n) {
    return divide(BigDecimal.valueOf(n));
  }

  /**
   * 料金割合を乗算する。
   *
   * @param n 数値
   * @return {@link AmountRate}
   */
  @Nonnull
  public AmountRate times(BigDecimal n) {
    return of(value.multiply(n));
  }

  /**
   * 料金割合を加算する。
   *
   * @param other {@link AmountRate}
   * @return {@link AmountRate}
   */
  @Nonnull
  public AmountRate add(AmountRate other) {
    return of(value.add(other.value));
  }

  @Nonnull
  public BigDecimal value() {
    return value;
  }

  @Override
  public int compareTo(AmountRate o) {
    return value.compareTo(o.value);
  }
}
