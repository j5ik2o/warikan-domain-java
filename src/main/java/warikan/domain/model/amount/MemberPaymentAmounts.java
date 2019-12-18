package warikan.domain.model.amount;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import warikan.domain.model.amount.rate.MemberAmountRate;
import warikan.domain.model.amount.rate.MemberAmountRates;
import warikan.domain.model.members.Member;
import warikan.domain.model.members.Members;
import warikan.domain.model.members.SecretaryType;

/** 参加者支払金額リスト。 */
public final class MemberPaymentAmounts {
  private final List<MemberPaymentAmount> values;

  private MemberPaymentAmounts(@Nonnull List<MemberPaymentAmount> values) {
    this.values = values;
  }

  /**
   * {@link BillingAmount}を基に{@link MemberPaymentAmounts}を計算する。
   *
   * @param billingAmount {@link BillingAmount}
   * @return {@link MemberPaymentAmounts}
   */
  @Nonnull
  public static MemberPaymentAmounts of(
      MemberAmountRates memberAmountRates, BillingAmount billingAmount) {
    // FIXME: Getter
    return MemberPaymentAmounts.of(
        memberAmountRates
            .values()
            .map(convertToMemberAmountRatePair(billingAmount))
            .map(convertToPairMemberPaymentAmount())
            .toList());
  }

  private static Function<Tuple2<Member, PaymentAmount>, MemberPaymentAmount>
      convertToPairMemberPaymentAmount() {
    return pair -> MemberPaymentAmount.of(pair._1(), pair._2());
  }

  private static Function<MemberAmountRate, Tuple2<Member, PaymentAmount>>
      convertToMemberAmountRatePair(@Nonnull BillingAmount billingAmount) {
    return memberPaymentRate ->
        Tuple.of(
            memberPaymentRate.member(),
            PaymentAmount.of(billingAmount, memberPaymentRate.amountRate()));
  }

  @Nonnull
  public static MemberPaymentAmounts empty() {
    return new MemberPaymentAmounts(List.empty());
  }

  @Nonnull
  public static MemberPaymentAmounts of(List<MemberPaymentAmount> values) {
    return new MemberPaymentAmounts(values);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemberPaymentAmounts that = (MemberPaymentAmounts) o;
    return Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  public String toString() {
    return "MemberPaymentAmounts{" + "values=" + values + '}';
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull MemberPaymentAmount element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(@Nonnull Predicate<MemberPaymentAmount> p) {
    return values.exists(p);
  }

  /**
   * 要素数を取得する。
   *
   * @return 要素数
   */
  public int size() {
    return values.size();
  }

  /**
   * {@link Members}を取得する。
   *
   * @return {@link Members}
   */
  @Nonnull
  public Members toMembers() {
    List<Member> members = values.map(MemberPaymentAmount::member).toList();
    return Members.of(members.head(), members.tail());
  }

  /**
   * {@link PaymentAmounts}に取得する。
   *
   * @return {@link PaymentAmounts}
   */
  private PaymentAmounts toPaymentAmounts() {
    List<PaymentAmount> list = values.map(MemberPaymentAmount::paymentAmount).toList();
    return PaymentAmounts.of(list.head(), list.tail());
  }

  /**
   * 合計支払金額を取得する。
   *
   * @return {@link PaymentAmount 合計支払金額}
   */
  @Nonnull
  public PaymentAmount totalPaymentAmount() {
    return toPaymentAmounts().sum();
  }

  /**
   * {@link MemberPaymentAmount 要素}を追加する。
   *
   * @param value {@link MemberPaymentAmount 要素}
   * @return {@link MemberPaymentAmounts}
   */
  @Nonnull
  public MemberPaymentAmounts addMemberPaymentAmount(MemberPaymentAmount value) {
    List<MemberPaymentAmount> list = values.append(value);
    return new MemberPaymentAmounts(list);
  }

  /**
   * 指定した{@link SecretaryType}の先頭メンバーだけに支払金額を追加する。
   *
   * @param additionalAmount 追加する金額
   * @param secretaryType {@link SecretaryType}
   * @return {@link MemberPaymentAmounts}
   */
  @Nonnull
  public MemberPaymentAmounts updatePaymentAmountOfHeadMember(
      @Nonnull Money additionalAmount, @Nonnull SecretaryType secretaryType) {
    Tuple2<Boolean, MemberPaymentAmounts> tuple2 =
        values.foldLeft(
            Tuple.of(false, MemberPaymentAmounts.empty()),
            (pair, element) -> {
              if (!pair._1() && element.member().secretaryType().equals(secretaryType)) {
                MemberPaymentAmounts newMemberPaymentAmounts =
                    pair._2()
                        .addMemberPaymentAmount(element.withPaymentAmountAdded(additionalAmount));
                return pair.update1(true).update2(newMemberPaymentAmounts);
              } else {
                MemberPaymentAmounts newMemberPaymentAmounts =
                    pair._2().addMemberPaymentAmount(element);
                return pair.update2(newMemberPaymentAmounts);
              }
            });
    return tuple2._2();
  }

  /**
   * 指定した{@link SecretaryType}の全メンバーに支払金額を追加する。
   *
   * @param additionalAmount 追加する金額
   * @param secretaryType {@link SecretaryType}
   * @return {@link MemberPaymentAmounts}
   */
  @Nonnull
  public MemberPaymentAmounts updatePaymentAmountOfAllMembers(
      @Nonnull Money additionalAmount, @Nonnull SecretaryType secretaryType) {
    return new MemberPaymentAmounts(
        values.map(addPaymentAmountTo(additionalAmount, secretaryType)).toList());
  }

  private Function<MemberPaymentAmount, MemberPaymentAmount> addPaymentAmountTo(
      @Nonnull Money money, @Nonnull SecretaryType secretaryType) {
    return memberPaymentAmount -> {
      if (memberPaymentAmount.member().secretaryType().equals(secretaryType)) {
        return memberPaymentAmount.withPaymentAmountAdded(money);
      } else {
        return memberPaymentAmount;
      }
    };
  }
}
