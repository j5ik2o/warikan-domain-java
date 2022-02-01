package warikan.domain.model.members;

import java.util.Objects;
import javax.annotation.Nonnull;

/** 参加者。 */
public final class Member {
  private final MemberName name;
  private final PaymentRatio paymentRatio;
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
   * @param secretaryType {@link SecretaryType}
   * @return {@link Member}
   */
  @Nonnull
  public static Member of(@Nonnull MemberName name, @Nonnull PaymentRatio paymentRatio, @Nonnull Payment payment) {
    return new Member(name, paymentRatio, payment);
  }

  public PaymentRatio paymentRatio() {
    return this.paymentRatio;
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

  @Nonnull
  MemberName name() {
    return name;
  }
}
