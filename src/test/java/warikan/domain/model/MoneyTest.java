package warikan.domain.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import warikan.domain.model.amount.Money;

public class MoneyTest {

  @Test
  public void expectToIsZero() {
    Money money = Money.of(0, Money.JPY);
    assertTrue(money.isZero());
    assertThat(money, is(Money.zero(Money.JPY)));
  }

  @Test
  public void expectToAdd() {
    Money money = Money.of(1, Money.JPY);
    assertThat(money.add(Money.of(1, Money.JPY)), is(Money.of(2, Money.JPY)));
  }

  @Test
  public void expectToSubtract() {
    Money money = Money.of(1, Money.JPY);
    assertThat(money.subtract(Money.of(1, Money.JPY)), is(Money.of(0, Money.JPY)));
  }

  @Test
  public void expectToIsGreaterThan() {
    Money money = Money.of(10, Money.JPY);
    assertTrue(money.isGreaterThan(Money.of(9, Money.JPY)));
  }

  @Test
  public void expectTo() {
    Money money = Money.of(10, Money.JPY);
    assertTrue(money.isLessThan(Money.of(11, Money.JPY)));
  }

  @Test
  public void expectToIsPositive() {
    Money money = Money.of(1, Money.JPY);
    assertTrue(money.isPositive());
  }

  @Test
  public void expectToIsNegative() {
    Money money = Money.of(-1, Money.JPY);
    assertTrue(money.isNegative());
  }

  @Test
  public void expectToDivide() {
    Money money = Money.of(10, Money.JPY);
    assertThat(money.divide(2), is(Money.of(5, Money.JPY)));
  }

  @Test
  public void expectToTimes() {
    Money money = Money.of(10, Money.JPY);
    assertThat(money.times(2), is(Money.of(20, Money.JPY)));
  }

  @Test
  public void expectToRemainder() {
    Money money = Money.of(10, Money.JPY);
    assertThat(money.remainder(3), is(Money.of(1, Money.JPY)));
  }
}
