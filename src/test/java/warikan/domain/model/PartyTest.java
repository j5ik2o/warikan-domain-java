package warikan.domain.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import io.vavr.control.Option;
import java.math.BigDecimal;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import warikan.domain.model.amount.*;
import warikan.domain.model.amount.Money;
import warikan.domain.model.amount.rate.AmountRate;
import warikan.domain.model.amount.rate.PaymentTypeAmountRate;
import warikan.domain.model.amount.rate.PaymentTypeAmountRates;
import warikan.domain.model.amount.types.MemberPaymentType;
import warikan.domain.model.amount.types.MemberPaymentTypes;
import warikan.domain.model.members.Member;
import warikan.domain.model.members.MemberName;
import warikan.domain.model.members.Members;
import warikan.domain.model.members.SecretaryType;
import warikan.domain.model.payment_type.PaymentType;

public class PartyTest {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  Member kato = Member.of(MemberName.of("加藤"), SecretaryType.Secretary);
  Member shaka = Member.of(MemberName.of("村上"), SecretaryType.Secretary);
  Member fujii = Member.of(MemberName.of("藤井"));
  PartyName name = PartyName.of("TEST");

  @Test
  public void expectToWarikan_FractionalSharingType_幹事負担() {
    MemberPaymentTypes memberPaymentTypes =
        MemberPaymentTypes.of(
            MemberPaymentType.of(kato, PaymentType.WARIKAN),
            MemberPaymentType.of(shaka, PaymentType.WARIKAN));
    Party party =
        Party.of(
            name,
            Members.of(kato, shaka),
            memberPaymentTypes,
            Option.some(DifferenceAmountAdjustmentType.幹事));
    // 請求金額
    BillingAmount billing = BillingAmount.of(Money.of(BigDecimal.valueOf(33333), Money.JPY));
    // 割り勘の設定
    PaymentTypeAmountRates paymentTypeAmountRates =
        PaymentTypeAmountRates.of(
            AmountRate.of(1.00),
            memberPaymentTypes,
            PaymentTypeAmountRate.of(PaymentType.WARIKAN, AmountRate.of(0.5)));
    System.out.println(paymentTypeAmountRates);
    // メンバーの割り勘を行う
    MemberPaymentAmounts memberPaymentAmounts = party.warikan(billing, paymentTypeAmountRates);
    System.out.println("memberPaymentAmounts = " + memberPaymentAmounts);
    System.out.println("memberPaymentAmounts.size() = " + memberPaymentAmounts.size());
    assertTrue(
        memberPaymentAmounts.contains(
            MemberPaymentAmount.of(kato, PaymentAmount.of(Money.of(16667, Money.JPY)))));
    assertTrue(
        memberPaymentAmounts.contains(
            MemberPaymentAmount.of(shaka, PaymentAmount.of(Money.of(16666, Money.JPY)))));
    assertThat(memberPaymentAmounts.size(), is(2));

    // メンバー支払金額の合計
    PaymentAmount totalAmount = memberPaymentAmounts.totalPaymentAmount();
    assertThat(totalAmount, is(PaymentAmount.of(billing.value())));
    System.out.println(totalAmount);
  }

  @Test
  public void expectToWarikan_FractionalSharingType_幹事以外負担() {
    MemberPaymentTypes memberPaymentTypes =
        MemberPaymentTypes.of(
            MemberPaymentType.of(kato, PaymentType.WARIKAN_PLUS),
            MemberPaymentType.of(shaka, PaymentType.WARIKAN),
            MemberPaymentType.of(fujii, PaymentType.WARIKAN));
    Party party =
        Party.of(
            name,
            Members.of(kato, shaka, fujii),
            memberPaymentTypes,
            Option.some(DifferenceAmountAdjustmentType.幹事以外));
    // 請求金額
    BillingAmount billing = BillingAmount.of(Money.of(BigDecimal.valueOf(20000), Money.JPY));
    // 割り勘の設定
    PaymentTypeAmountRates paymentTypeAmountRates =
        PaymentTypeAmountRates.of(
            AmountRate.of(1.00),
            memberPaymentTypes,
            PaymentTypeAmountRate.of(PaymentType.WARIKAN, AmountRate.of(0.25)),
            PaymentTypeAmountRate.of(PaymentType.WARIKAN_PLUS, AmountRate.of(0.5)));

    // メンバーの割り勘を行う
    MemberPaymentAmounts memberPaymentAmounts = party.warikan(billing, paymentTypeAmountRates);
    System.out.println("memberPaymentAmounts = " + memberPaymentAmounts);
    System.out.println("memberPaymentAmounts.size() = " + memberPaymentAmounts.size());
    assertTrue(
        memberPaymentAmounts.contains(
            MemberPaymentAmount.of(kato, PaymentAmount.of(Money.of(10000, Money.JPY)))));
    assertTrue(
        memberPaymentAmounts.contains(
            MemberPaymentAmount.of(shaka, PaymentAmount.of(Money.of(5000, Money.JPY)))));
    assertTrue(
        memberPaymentAmounts.contains(
            MemberPaymentAmount.of(fujii, PaymentAmount.of(Money.of(5000, Money.JPY)))));
    assertThat(memberPaymentAmounts.size(), is(3));

    // メンバー支払金額の合計
    PaymentAmount totalAmount = memberPaymentAmounts.totalPaymentAmount();
    assertThat(totalAmount, is(PaymentAmount.of(billing.value())));
    System.out.println(totalAmount);
  }
}
