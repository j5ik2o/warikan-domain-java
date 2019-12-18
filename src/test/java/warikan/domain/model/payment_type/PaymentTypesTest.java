package warikan.domain.model.payment_type;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PaymentTypesTest {

  @Test
  public void expectToExists() {
    assertTrue(
        PaymentTypes.of(PaymentType.WARIKAN, PaymentType.WARIKAN_MINUS, PaymentType.WARIKAN_PLUS)
            .exists(v -> v.equals(PaymentType.WARIKAN)));
  }

  @Test
  public void expectToContains() {
    assertTrue(
        PaymentTypes.of(PaymentType.WARIKAN, PaymentType.WARIKAN_MINUS, PaymentType.WARIKAN_PLUS)
            .contains(PaymentType.WARIKAN));
  }
}
