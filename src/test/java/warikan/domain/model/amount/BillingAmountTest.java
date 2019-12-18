package warikan.domain.model.amount;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BillingAmountTest {

  @Test
  public void expectToSubtract() {
    BillingAmount billingAmount = BillingAmount.of(Money.of(1, Money.JPY));
    var subtract =
        billingAmount.subtract(PaymentAmount.of(Money.of(1, Money.JPY)).toBillingAmount());
    assertThat(subtract.value(), is(Money.of(0, Money.JPY)));
  }
}
