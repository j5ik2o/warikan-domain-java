package warikan.domain.model.amount.rate;

import java.util.Objects;
import javax.annotation.Nonnull;
import warikan.domain.model.members.Member;

/** 参加者支払割合。 */
public final class MemberAmountRate {
  private final Member member;
  private final AmountRate amountRate;

  private MemberAmountRate(@Nonnull Member member, @Nonnull AmountRate amountRate) {
    this.member = member;
    this.amountRate = amountRate;
  }

  /**
   * ファクトリメソッド。
   *
   * @param member {@link Member}
   * @param amountRate {@link AmountRate}
   * @return {@link MemberAmountRate}
   */
  @Nonnull
  public static MemberAmountRate of(@Nonnull Member member, @Nonnull AmountRate amountRate) {
    return new MemberAmountRate(member, amountRate);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemberAmountRate that = (MemberAmountRate) o;
    return member.equals(that.member) && amountRate.equals(that.amountRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(member, amountRate);
  }

  @Override
  public String toString() {
    return "MemberAmountRate{" + "member=" + member + ", amountRate=" + amountRate + '}';
  }

  @Nonnull
  public Member member() {
    return member;
  }

  @Nonnull
  public AmountRate amountRate() {
    return amountRate;
  }
}
