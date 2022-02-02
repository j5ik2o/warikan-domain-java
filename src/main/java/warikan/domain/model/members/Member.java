package warikan.domain.model.members;

import java.util.Objects;
import javax.annotation.Nonnull;

/** 参加者。 */
public final class Member {
  /** 参加者名 */
  private final MemberName name;
  /** 支払割合 */
  private final PaymentRatio paymentRatio;
  /** 支払金額 */
  private final Payment payment;

  private Member(@Nonnull MemberName name, @Nonnull PaymentRatio paymentRatio, @Nonnull Payment payment ) {
    this.name = name;
    this.paymentRatio = paymentRatio;
    this.payment = payment;
  }

  /**
   * ファクトリメソッド。
   *
   * @param name {@link MemberName}
   * @param paymentRatio {@link PaymentRatio}
   * @param payment {@link Payment}
   * @return {@link Member}
   */
  @Nonnull
  public static Member of(@Nonnull MemberName name, @Nonnull PaymentRatio paymentRatio, @Nonnull Payment payment) {
    return new Member(name, paymentRatio, payment);
  }
  
  @Nonnull
  public MemberName name() {
    return this.name;
  }

  @Nonnull
  public PaymentRatio paymentRatio() {
    return this.paymentRatio;
  }

  @Nonnull
  public Payment payment() {
    return this.payment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Member member = (Member) o;
    return Objects.equals(name, member.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Member{" + "name=" + name + '}';
  }

}
