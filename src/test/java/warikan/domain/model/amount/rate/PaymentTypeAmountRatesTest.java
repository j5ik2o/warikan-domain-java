package warikan.domain.model.amount.rate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import io.vavr.control.Option;
import org.junit.Test;
import warikan.domain.model.amount.types.MemberPaymentType;
import warikan.domain.model.amount.types.MemberPaymentTypes;
import warikan.domain.model.members.Member;
import warikan.domain.model.members.MemberName;
import warikan.domain.model.members.SecretaryType;
import warikan.domain.model.payment_type.PaymentType;

public class PaymentTypeAmountRatesTest {
  Member kato = Member.of(MemberName.of("加藤"), SecretaryType.Secretary);
  Member shaka = Member.of(MemberName.of("村上"), SecretaryType.Secretary);
  Member fujii = Member.of(MemberName.of("藤井"));
  MemberPaymentTypes memberPaymentTypes =
      MemberPaymentTypes.of(
          MemberPaymentType.of(kato, PaymentType.WARIKAN_PLUS),
          MemberPaymentType.of(shaka, PaymentType.WARIKAN),
          MemberPaymentType.of(fujii, PaymentType.WARIKAN));

  @Test
  public void expectToExists() {
    PaymentTypeAmountRates paymentTypeAmountRates =
        PaymentTypeAmountRates.of(
            AmountRate.of(1),
            memberPaymentTypes,
            PaymentTypeAmountRate.of(PaymentType.WARIKAN, AmountRate.of(0.25)),
            PaymentTypeAmountRate.of(PaymentType.WARIKAN_PLUS, AmountRate.of(0.5)));
    assertTrue(paymentTypeAmountRates.exists(v -> v.paymentType().equals(PaymentType.WARIKAN)));
    assertTrue(
        paymentTypeAmountRates.exists(v -> v.paymentType().equals(PaymentType.WARIKAN_PLUS)));
    assertFalse(
        paymentTypeAmountRates.exists(v -> v.paymentType().equals(PaymentType.WARIKAN_MINUS)));
  }

  @Test
  public void expectToPaymentTypes() {
    PaymentTypeAmountRates paymentTypeAmountRates =
        PaymentTypeAmountRates.of(
            AmountRate.of(1),
            memberPaymentTypes,
            PaymentTypeAmountRate.of(PaymentType.WARIKAN, AmountRate.of(0.25)),
            PaymentTypeAmountRate.of(PaymentType.WARIKAN_PLUS, AmountRate.of(0.5)));
    assertTrue(paymentTypeAmountRates.paymentTypes().contains(PaymentType.WARIKAN));
    assertTrue(paymentTypeAmountRates.paymentTypes().contains(PaymentType.WARIKAN_PLUS));
    assertFalse(paymentTypeAmountRates.paymentTypes().contains(PaymentType.WARIKAN_MINUS));
  }

  @Test
  public void expectToAmountRate() {
    PaymentTypeAmountRates paymentTypeAmountRates =
        PaymentTypeAmountRates.of(
            AmountRate.of(1),
            memberPaymentTypes,
            PaymentTypeAmountRate.of(PaymentType.WARIKAN, AmountRate.of(0.25)),
            PaymentTypeAmountRate.of(PaymentType.WARIKAN_PLUS, AmountRate.of(0.5)));
    assertThat(
        paymentTypeAmountRates.amountRate(PaymentType.WARIKAN),
        is(Option.some(AmountRate.of(0.25))));
  }
}
