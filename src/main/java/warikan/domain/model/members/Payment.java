package warikan.domain.model.members;

import javax.annotation.Nonnull;

import warikan.domain.model.Money;

/** 支払い金額 */
public final class Payment {
  private final Money value;

  private Payment(@Nonnull Money value) {
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 支払い金額。
   * @return {@link MemberName}
   */
  @Nonnull
  public static Payment of(@Nonnull Money value) {
    return new Payment(value);
  }

  /**
   * 平均金額を取得する
   */
  public Money getAveragePaymen(Money totalPayment, long sizeOf) {
    return totalPayment.divide(sizeOf);
  }

  /*
   * TODO: 多めの人の金額を取得するメソッドを作成
   */
  private Money getMuchPayment() {
    return null;
  }

  /**
   * TODO: ふつうの人の金額を取得するメソッドを作成
   */
  private Money getMeanPayment() {
    return null;
  }

  /**
   * TODO: 少なめの人の金額を取得するメソッドを作成
   */
  private Money getLittlePayment() {
    return null;
  }

}