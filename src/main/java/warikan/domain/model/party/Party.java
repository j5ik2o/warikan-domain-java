package warikan.domain.model.party;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;


import warikan.domain.model.Money;
import warikan.domain.model.members.Member;
import warikan.domain.model.members.Payment;
import warikan.domain.model.members.PaymentRatio;

/**
 * どこまでやる？
 * ・値オブジェクトを作成
 * ・値オブジェクト内で必要なメソッドを作成する
 *   ・どんなメソッドが必要か検討（来週2/までに考えてくる）
 */
public class Party {
  private final PartyName partyName;
  private final TotalPayment totalPayment; // TODO: 請求金額用の値オブジェクトを作成。その中でMoneyを使用
  private final PartyDatetime dateTime;
  private final LittleRatio littleRatio; // TODO: 弱者控除割合用の値オブジェクトを作成。その中でMoneyを使用

  private List<Member> members = new ArrayList<Member>();

  // メンバー追加
  public void addMember(Member member){
    this.members.add(member);
  }

  public void decidePayment(){

    // 支払い区分と支払金額の対応
    Map<PaymentRatio,Payment> paymentMap = new Map<PaymentRatio,Payment>;

    // 多めの人の人数
    long muchNum = this.members.stream().filter(member -> member.paymentRatio() == PaymentRatio.Much).count();
    // 普通の人の人数
    long meanNum = this.members.stream().filter(member -> member.paymentRatio() == PaymentRatio.Mean).count();
    // 少なめの人の人数
    long littleNum = this.members.stream().filter(member -> member.paymentRatio() == PaymentRatio.Little).count();
    // 合計人数
    long memberNum = muchNum + meanNum + littleNum;
    
    // 平均金額を決定
    Payment averagePayment = totalPayment.devide(memberNum);
    paymentMap.put(PaymentRatio.Mean, averagePayment);

    // 弱者控除
    Payment littlePayment = averagePayment.devide(littleRatio);
    paymentMap.put(PaymentRatio.Little, littlePayment);

    // 負担すべき差額計算
    Payment meanMembersTotalPayment = averagePayment.multiple(meanNum);
    Payment littleMembersTotalPayment = littlePayment.multiple(littleNum);
    Payment muchMembersTotalPayment = totalPayment.subtract(meanMembersTotalPayment).subtract(littleMembersTotalPayment);
    
    // 多めの人の支払金額を決定
    Payment muchPayment = muchMembersTotalPayment.devide(muchNum);
    paymentMap.put(PaymentRatio.Much,muchPayment);

    // 各メンバーに支払金額を割り振る
    this.members = this.members.stream().map(member -> member.setPayment(paymentMap.get(member.paymentRatio()))).toList();
  }

  // private Party(@Nonnull PartyName partyName, Money claimMoney, LocalDateTime dateTime, int littleRatio){
  //   this.partyName = partyName;
  //   this.claimMoney = claimMoney;
  //   this.dateTime = dateTime;
  //   this.littleRatio = littleRatio;
  // }

  /**
   * ファクトリメソッド
   */
  // @Nonnull
  // public static Party of(@Nonnull PartyName partyName, @Nonnull Money claimMoney,
  // @Nonnull LocalDateTime dateTime, @Nonnull int littleRatio){
  //   return new Party(partyName, claimMoney, dateTime, littleRatio);
  // }
}