package warikan.domain.model;

import io.vavr.control.Option;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import warikan.domain.model.amount.BillingAmount;
import warikan.domain.model.amount.MemberPaymentAmounts;
import warikan.domain.model.amount.rate.MemberAmountRates;
import warikan.domain.model.amount.rate.PaymentTypeAmountRates;
import warikan.domain.model.amount.types.MemberPaymentTypes;
import warikan.domain.model.members.Members;

/** 飲み会。 */
public final class Party {
  private static final Logger LOGGER = LoggerFactory.getLogger(Party.class);
  private static final Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.getDefault());
  private final PartyName name;
  private final Members members;
  private final MemberPaymentTypes memberPaymentTypes;
  private final Option<DifferenceAmountAdjustmentType> differenceAmountAdjustmentType;

  private Party(
      @Nonnull PartyName name,
      @Nonnull Members members,
      @Nonnull MemberPaymentTypes memberPaymentTypes,
      @Nonnull Option<DifferenceAmountAdjustmentType> differenceAmountAdjustmentType) {
    this.name = name;
    this.members = members;
    this.differenceAmountAdjustmentType = differenceAmountAdjustmentType;
    this.memberPaymentTypes = memberPaymentTypes;
  }

  /**
   * ファクトリメソッド。
   *
   * @param name {@link PartyName}
   * @param members {@link Members}
   * @param memberPaymentSetting {@link MemberPaymentTypes}
   * @param differenceAmountAdjustmentType {@link DifferenceAmountAdjustmentType}
   * @return {@link Party}
   */
  @Nonnull
  public static Party of(
      @Nonnull PartyName name,
      @Nonnull Members members,
      @Nonnull MemberPaymentTypes memberPaymentSetting,
      @Nonnull Option<DifferenceAmountAdjustmentType> differenceAmountAdjustmentType) {
    return new Party(name, members, memberPaymentSetting, differenceAmountAdjustmentType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Party party = (Party) o;
    return Objects.equals(name, party.name)
        && Objects.equals(members, party.members)
        && Objects.equals(memberPaymentTypes, party.memberPaymentTypes)
        && Objects.equals(differenceAmountAdjustmentType, party.differenceAmountAdjustmentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, members, memberPaymentTypes, differenceAmountAdjustmentType);
  }

  @Override
  public String toString() {
    return "Party{"
        + "name="
        + name
        + ", members="
        + members
        + ", memberPaymentTypes="
        + memberPaymentTypes
        + ", differenceAmountAdjustmentType="
        + differenceAmountAdjustmentType
        + '}';
  }

  /**
   * 請求金額の割り勘を行う。
   *
   * @param billingAmount {@link BillingAmount}
   * @param paymentTypeAmountRates {@link PaymentTypeAmountRates}
   * @return {@link MemberPaymentAmounts}
   */
  @Nonnull
  public MemberPaymentAmounts warikan(
      @Nonnull BillingAmount billingAmount,
      @Nonnull PaymentTypeAmountRates paymentTypeAmountRates) {
    if (notEqualsPaymentTypes(paymentTypeAmountRates))
      throw new IllegalArgumentException("Failure due to different paymentTypes");
    MemberAmountRates memberAmountRates =
        MemberAmountRates.of(memberPaymentTypes, paymentTypeAmountRates);
    MemberPaymentAmounts unadjustedAmounts =
        MemberPaymentAmounts.of(memberAmountRates, billingAmount);
    BillingAmount differenceAmount =
        billingAmount.subtract(unadjustedAmounts.totalPaymentAmount().toBillingAmount());
    // 差額が生じた場合
    if (differenceAmountAdjustmentType.isDefined() && differenceAmount.nonZero()) {
      return DifferenceAmountAdjuster.of(
              unadjustedAmounts, differenceAmount.value(), differenceAmountAdjustmentType.get())
          .adjust();
    }
    return unadjustedAmounts;
  }

  private boolean notEqualsPaymentTypes(PaymentTypeAmountRates paymentTypeAmountRates) {
    return !memberPaymentTypes.paymentTypes().equals(paymentTypeAmountRates.paymentTypes());
  }
}
