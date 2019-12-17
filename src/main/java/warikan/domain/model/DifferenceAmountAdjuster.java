package warikan.domain.model;

import javax.annotation.Nonnull;
import warikan.domain.model.amount.MemberPaymentAmounts;
import warikan.domain.model.amount.Money;
import warikan.domain.model.members.SecretaryType;

/** 差額調整 */
public final class DifferenceAmountAdjuster {

  private final MemberPaymentAmounts unadjustedAmounts;
  private final Money differenceAmount;
  private final DifferenceAmountAdjustmentType differenceAmountAdjustmentType;

  private DifferenceAmountAdjuster(
      @Nonnull MemberPaymentAmounts unadjustedAmounts,
      @Nonnull Money differenceAmount,
      @Nonnull DifferenceAmountAdjustmentType differenceAmountAdjustmentType) {
    this.unadjustedAmounts = unadjustedAmounts;
    this.differenceAmount = differenceAmount;
    this.differenceAmountAdjustmentType = differenceAmountAdjustmentType;
  }

  @Nonnull
  public static DifferenceAmountAdjuster of(
      MemberPaymentAmounts unadjustedAmounts,
      Money differenceAmount,
      DifferenceAmountAdjustmentType differenceAmountAdjustmentType) {
    return new DifferenceAmountAdjuster(
        unadjustedAmounts, differenceAmount, differenceAmountAdjustmentType);
  }

  @Nonnull
  public MemberPaymentAmounts adjust() {
    SecretaryType secretaryType = SecretaryType.NonSecretary;
    if (differenceAmountAdjustmentType.equals(DifferenceAmountAdjustmentType.幹事))
      secretaryType = SecretaryType.Secretary;
    Money remainder =
        differenceAmount.remainder(unadjustedAmounts.toMembers().sizeOf(secretaryType));
    if (remainder.isZero()) {
      Money money = differenceAmount.divide(unadjustedAmounts.toMembers().sizeOf(secretaryType));
      return unadjustedAmounts.updatePaymentAmountOfAllMembers(money, secretaryType);
    } else {
      return unadjustedAmounts.updatePaymentAmountOfHeadMember(remainder, secretaryType);
    }
  }
}
