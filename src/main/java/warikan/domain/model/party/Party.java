package warikan.domain.model.party;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import warikan.domain.model.Money;

/**
 * どこまでやる？
 * ・値オブジェクトを作成
 * ・値オブジェクト内で必要なメソッドを作成する
 *   ・どんなメソッドが必要か検討（来週2/までに考えてくる）
 */
public class Party {
  private final PartyName partyName;
  private final Money claimMoney; // TODO: 請求金額用の値オブジェクトを作成。その中でMoneyを使用
  private final LocalDateTime dateTime;
  private final int littleRatio; // TODO: 弱者控除割合用の値オブジェクトを作成。その中でMoneyを使用

  private Party(@Nonnull PartyName partyName, Money claimMoney, LocalDateTime dateTime, int littleRatio){
    this.partyName = partyName;
    this.claimMoney = claimMoney;
    this.dateTime = dateTime;
    this.littleRatio = littleRatio;
  }

  /**
   * ファクトリメソッド
   */
  @Nonnull
  public static Party of(@Nonnull PartyName partyName, @Nonnull Money claimMoney,
  @Nonnull LocalDateTime dateTime, @Nonnull int littleRatio){
    return new Party(partyName, claimMoney, dateTime, littleRatio);
  }
}