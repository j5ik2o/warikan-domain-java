package warikan.domain.model.amount.types;

import java.util.Objects;
import javax.annotation.Nonnull;
import warikan.domain.model.members.Member;
import warikan.domain.model.payment_type.PaymentType;

/**
 * 参加者支払区分。
 *
 * <p>参加者に設定された支払い区分を表す。
 */
public final class MemberPaymentType {
  private final Member member;
  private final PaymentType paymentType;

  private MemberPaymentType(@Nonnull Member member, @Nonnull PaymentType paymentType) {
    this.member = member;
    this.paymentType = paymentType;
  }

  /**
   * ファクトリメソッド。
   *
   * @param member {@link Member}
   * @param paymentType {@link PaymentType}
   */
  @Nonnull
  public static MemberPaymentType of(@Nonnull Member member, @Nonnull PaymentType paymentType) {
    return new MemberPaymentType(member, paymentType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemberPaymentType that = (MemberPaymentType) o;
    return Objects.equals(member, that.member) && paymentType == that.paymentType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(member, paymentType);
  }

  @Override
  public String toString() {
    return "MemberPaymentType{" + "member=" + member + ", paymentType=" + paymentType + '}';
  }

  @Nonnull
  Member member() {
    return member;
  }

  @Nonnull
  PaymentType paymentType() {
    return paymentType;
  }
}
