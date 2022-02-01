package warikan.domain.model.members;

import java.util.Objects;
import javax.annotation.Nonnull;

import warikan.domain.model.Money;

/** 参加者。 */
public final class Member {
  private final MemberName name;
  private final PaymentRatio paymentRatio;
  private final Money amount; // TODO: ここも支払金額用の値オブジェクトを作成。

  private Member(@Nonnull MemberName name,
      @Nonnull PaymentRatio paymentRatio, @Nonnull Money amount ) {
    this.name = name;
    this.paymentRatio = paymentRatio;
    this.amount = amount;
  }

  /**
   * ファクトリメソッド。
   *
   * @param name {@link MemberName}
   * @param secretaryType {@link SecretaryType}
   * @return {@link Member}
   */
  @Nonnull
  public static Member of(@Nonnull MemberName name,
      @Nonnull PaymentRatio paymentRatio, @Nonnull Money amount) {
    return new Member(name, paymentRatio, amount);
  }

  /**
   * ファクトリメソッド。
   *
   * @param name {@link MemberName}
   * @return {@link Member}
   */
  // @Nonnull
  // public static Member of(@Nonnull MemberName name) {
  //   return new Member(name, SecretaryType.NonSecretary);
  // }

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

  public PaymentRatio paymentRatio() {
    return this.paymentRatio;
  }
}
