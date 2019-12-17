package warikan.domain.model.amount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

/**
 * 通貨。
 *
 * <p>通貨を表す値オブジェクト。
 */
public final class Money implements Comparable<Money> {
  public static final RoundingMode DefaultRoundingMode = RoundingMode.HALF_EVEN;
  public static final Currency DefaultCurrency = Currency.getInstance(Locale.getDefault());
  public static final Currency JPY = Currency.getInstance("JPY");
  public static final Currency USD = Currency.getInstance("USD");

  private final BigDecimal amount;
  private final Currency currency;

  private Money(@Nonnull BigDecimal amount, @Nonnull Currency currency) {
    Validate.isTrue(
        amount.scale() == currency.getDefaultFractionDigits(),
        "Scale of amount does not match currency");

    this.amount = amount;
    this.currency = currency;
  }

  @Nonnull
  public static Money of(@Nonnull BigDecimal rawAmount, @Nonnull Currency currency) {
    return new Money(rawAmount, currency);
  }

  @Nonnull
  public static Money of(long rawAmount, @Nonnull Currency currency) {
    return new Money(BigDecimal.valueOf(rawAmount), currency);
  }

  @Nonnull
  public static Money zero(@Nonnull Currency currency) {
    return of(BigDecimal.ZERO, currency);
  }

  @Nonnull
  public static Money one(@Nonnull Currency currency) {
    return of(BigDecimal.ONE, currency);
  }

  @Nonnull
  public static Money adjustBy(@Nonnull BigDecimal rawAmount, @Nonnull Currency currency) {
    return adjustBy(rawAmount, currency, DefaultRoundingMode);
  }

  @Nonnull
  public static Money adjustBy(long rawAmount, @Nonnull Currency currency) {
    return adjustBy(BigDecimal.valueOf(rawAmount), currency, DefaultRoundingMode);
  }

  @Nonnull
  public static Money adjustBy(
      @Nonnull BigDecimal rawAmount,
      @Nonnull Currency currency,
      @Nonnull RoundingMode roundingMode) {
    int defaultFractionDigits = currency.getDefaultFractionDigits();
    BigDecimal amount = rawAmount.setScale(defaultFractionDigits, roundingMode);
    return new Money(amount, currency);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }

  @Override
  public String toString() {
    return "Money{" + "amount=" + amount + ", currency=" + currency + '}';
  }

  @Override
  public int compareTo(Money o) {
    if (!currency.equals(o.currency)) throw new IllegalArgumentException("currency miss match.");
    return amount.compareTo(o.amount);
  }

  @Nonnull
  BigDecimal amount() {
    return amount;
  }

  @Nonnull
  Currency currency() {
    return currency;
  }

  public boolean isGreaterThan(Money o) {
    return this.compareTo(o) > 0;
  }

  public boolean isLessThan(Money o) {
    return this.compareTo(o) < 0;
  }

  public boolean isNegative() {
    return amount.compareTo(BigDecimal.ZERO) < 0;
  }

  public boolean isPositive() {
    return amount.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean isZero() {
    return equals(Money.adjustBy(BigDecimal.valueOf(0.0), currency));
  }

  public boolean nonZero() {
    return !isZero();
  }

  @Nonnull
  public Money negated() {
    return new Money(amount.negate(), currency);
  }

  @Nonnull
  public Money add(@Nullable Money o) {
    checkHasSameCurrencyAs(o);
    return adjustBy(amount.add(o.amount), currency);
  }

  @Nonnull
  public Money subtract(@Nonnull Money o) {
    return add(o.negated());
  }

  @Nonnull
  public Money times(@Nonnull BigDecimal factor, @Nonnull RoundingMode roundingMode) {
    return Money.adjustBy(amount.multiply(factor), currency, roundingMode);
  }

  @Nonnull
  public Money times(@Nonnull BigDecimal factor) {
    BigDecimal multiply = amount.multiply(factor);
    return Money.adjustBy(multiply, currency);
  }

  @Nonnull
  public Money times(long factor, @Nonnull RoundingMode roundingMode) {
    return times(BigDecimal.valueOf(factor), roundingMode);
  }

  @Nonnull
  public Money times(long factor) {
    return times(BigDecimal.valueOf(factor));
  }

  @Nonnull
  public Money times(double factor, @Nonnull RoundingMode roundingMode) {
    return times(BigDecimal.valueOf(factor), roundingMode);
  }

  @Nonnull
  public Money times(double factor) {
    return times(BigDecimal.valueOf(factor));
  }

  @Nonnull
  public Money divide(@Nonnull BigDecimal divisor, @Nonnull RoundingMode roundingMode) {
    BigDecimal newAmount = amount.divide(divisor, roundingMode);
    return new Money(newAmount, currency);
  }

  @Nonnull
  public Money divide(@Nonnull BigDecimal divisor) {
    return divide(divisor, DefaultRoundingMode);
  }

  @Nonnull
  public Money divide(long divisor, @Nonnull RoundingMode roundingMode) {
    return divide(BigDecimal.valueOf(divisor), roundingMode);
  }

  @Nonnull
  public Money divide(long divisor) {
    return divide(BigDecimal.valueOf(divisor));
  }

  @Nonnull
  public Money divide(double divisor, @Nonnull RoundingMode roundingMode) {
    return divide(BigDecimal.valueOf(divisor), roundingMode);
  }

  @Nonnull
  public Money divide(double divisor) {
    return divide(BigDecimal.valueOf(divisor));
  }

  @Nonnull
  public Money remainder(BigDecimal n) {
    return Money.of(amount.remainder(n), currency);
  }

  @Nonnull
  public Money remainder(long n) {
    return remainder(BigDecimal.valueOf(n));
  }

  private boolean hasSameCurrencyAs(@Nonnull Money arg) {
    return currency.equals(arg.currency)
        || arg.amount.equals(BigDecimal.ZERO)
        || amount.equals(BigDecimal.ZERO);
  }

  private void checkHasSameCurrencyAs(@Nonnull Money o) {
    if (!hasSameCurrencyAs(o)) {
      throw new IllegalArgumentException(
          o.toString() + " is not same currency as " + this.toString());
    }
  }
}
