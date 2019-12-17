package warikan.domain.model.amount;

import java.util.Objects;
import javax.annotation.Nonnull;
import warikan.domain.model.members.Member;

/** 参加者支払金額。 */
public final class MemberPaymentAmount {
  private final Member member;
  private final PaymentAmount paymentAmount;

  private MemberPaymentAmount(@Nonnull Member member, @Nonnull PaymentAmount paymentAmount) {
    this.member = member;
    this.paymentAmount = paymentAmount;
  }

  /**
   * ファクトリメソッド。
   *
   * @param member {@link Member}
   * @param paymentAmount {@link PaymentAmount}
   */
  @Nonnull
  public static MemberPaymentAmount of(
      @Nonnull Member member, @Nonnull PaymentAmount paymentAmount) {
    return new MemberPaymentAmount(member, paymentAmount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemberPaymentAmount that = (MemberPaymentAmount) o;
    return Objects.equals(member, that.member) && Objects.equals(paymentAmount, that.paymentAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(member, paymentAmount);
  }

  @Override
  public String toString() {
    return "MemberPaymentAmount{" + "member=" + member + ", paymentAmount=" + paymentAmount + '}';
  }

  @Nonnull
  Member member() {
    return member;
  }

  @Nonnull
  PaymentAmount paymentAmount() {
    return paymentAmount;
  }

  /**
   * {@link MemberPaymentAmount}を置き換える。
   *
   * @param value {@link PaymentAmount}
   * @return {@link MemberPaymentAmount}
   */
  @Nonnull
  public MemberPaymentAmount withNewPaymentAmount(@Nonnull PaymentAmount value) {
    return of(member, value);
  }

  /**
   * {@link PaymentAmount}を追加する。
   *
   * @param value {@link Money}
   * @return {@link MemberPaymentAmount}
   */
  @Nonnull
  public MemberPaymentAmount withPaymentAmountAdded(@Nonnull Money value) {
    return of(member, paymentAmount.add(value));
  }

  /**
   * {@link PaymentAmount}を追加する。
   *
   * @param value {@link PaymentAmount}
   * @return {@link MemberPaymentAmount}
   */
  @Nonnull
  public MemberPaymentAmount withPaymentAmountAdded(@Nonnull PaymentAmount value) {
    return of(member, paymentAmount.add(value));
  }
}
