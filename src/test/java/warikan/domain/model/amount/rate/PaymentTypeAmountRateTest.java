package warikan.domain.model.amount.rate;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import warikan.domain.model.payment_type.PaymentType;

public class PaymentTypeAmountRateTest {

  @Test
  public void expectToWithNewAmountRate() {
    PaymentTypeAmountRate paymentTypeAmountRate =
        PaymentTypeAmountRate.of(PaymentType.WARIKAN, AmountRate.of(1));
    assertThat(
        paymentTypeAmountRate.withNewAmountRate(AmountRate.ZERO).amountRate(),
        Matchers.is(AmountRate.ZERO));
  }
}
