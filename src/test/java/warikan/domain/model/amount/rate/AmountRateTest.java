package warikan.domain.model.amount.rate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import org.junit.Test;

public class AmountRateTest {

  @Test
  public void expectToDivide() {
    AmountRate amountRate = AmountRate.of(100);
    assertThat(amountRate.divide(2), is(AmountRate.of(new BigDecimal("50.00"))));
  }

  @Test
  public void expectToAdjust() {
    AmountRate amountRate = AmountRate.of(100);
    assertThat(amountRate.adjust(100), is(AmountRate.of(new BigDecimal("1.00"))));
  }
}
