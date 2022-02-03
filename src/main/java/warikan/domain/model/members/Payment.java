package warikan.domain.model.members;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

import warikan.domain.model.Money;

/** 支払い金額 */
public final class Payment {
  /** 少なめの支払金額 */
  private final Money LittleMembersPayment;
  /** ふつうの支払金額 */
  private final Money MeanMembersPayment;
  /** 多めの支払金額 */
  private final Money muchMembersPayment;

  private Payment(@Nonnull Money LittleMembersPayment, @Nonnull Money MeanMembersPayment,
      @Nonnull Money muchMembersPayment) {
    this.LittleMembersPayment = LittleMembersPayment;
    this.MeanMembersPayment = MeanMembersPayment;
    this.muchMembersPayment = muchMembersPayment;
  }

  /**
   * ファクトリメソッド。
   *
   * @param LittleMembersPayment 少なめの支払金額。
   * @param MeanMembersPayment   ふつうの支払金額。
   * @param muchMembersPayment   多めの支払金額。
   * @return {@link Payment}
   */
  @Nonnull
  public static Payment of(@Nonnull Money LittleMembersPayment, @Nonnull Money MeanMembersPayment,
      @Nonnull Money muchMembersPayment) {
    return new Payment(LittleMembersPayment, MeanMembersPayment, muchMembersPayment);
  }

  /** getter */
  @Nonnull
  public Money LittleMembersPayment() {
    return this.LittleMembersPayment;
  }

  /** getter */
  @Nonnull
  public Money MeanMembersPayment() {
    return this.MeanMembersPayment;
  }

  /** getter */
  @Nonnull
  public Money muchMembersPayment() {
    return this.muchMembersPayment;
  }

  /**
   * 多めの人の支払金額を計算する
   * 
   * @param littleMembersTotalPayment 少なめの人の支払合計金額。
   * @param meanMembersTotalPayment   ふつうの人の支払合計金額。
   * @param TotalPayment              請求金額。
   * @param sizeOfTotal               参加者人数。
   * @return Money 多めの人の支払金額
   */

  private Money calculatemuchMembersPayment(Money littleMembersTotalPayment, Money meanMembersTotalPayment,
      Money TotalPayment, long sizeOfTotal) {
    var muchMembersTotalPayment = TotalPayment.subtract(meanMembersTotalPayment).subtract(littleMembersTotalPayment);
    return muchMembersTotalPayment.divide(sizeOfTotal);
  }

  /**
   * ふつうの人の支払金額を計算する
   * 
   * @param totalPayment 請求金額。
   * @param sizeOf       参加者人数。
   * @return Money ふううの人の支払金額
   */
  private Money calculateMeanMembersPayment(Money totalPayment, long sizeOf) {
    return totalPayment.divide(sizeOf);
  }

  /**
   * 少なめ人の支払金額を計算する
   * 
   * @param avaragePayment 請求金額。
   * @param littleRatio    弱者控除割合
   * @return Money 少なめ人の支払金額
   */
  private Money calculateLittleMembersPayment(Money avaragePayment, BigDecimal littleRatio) {
    return avaragePayment.times(littleRatio);
  }

}