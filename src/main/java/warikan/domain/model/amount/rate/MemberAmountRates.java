package warikan.domain.model.amount.rate;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;
import warikan.domain.model.amount.MemberPaymentAmounts;
import warikan.domain.model.amount.types.MemberPaymentTypes;
import warikan.domain.model.members.Member;
import warikan.domain.model.payment_type.PaymentType;

/** 参加者料金割合リスト。 */
public final class MemberAmountRates {

  private final List<MemberAmountRate> values;

  private MemberAmountRates(@Nonnull List<MemberAmountRate> values) {
    Validate.notEmpty(values.toJavaList(), "values are empty");
    this.values = values;
  }

  private static Predicate<Tuple2<Member, Option<AmountRate>>> nonEmptyAmountRate() {
    return pair -> pair._2().isDefined();
  }

  private static Function<Tuple2<Member, Option<AmountRate>>, MemberAmountRate>
      convertToMemberAmountRate() {
    return pair -> MemberAmountRate.of(pair._1, pair._2.get());
  }

  private static Function<Tuple2<Member, PaymentType>, Tuple2<Member, Option<AmountRate>>>
      convertToMemberAmountRateOpt(PaymentTypeAmountRates paymentTypeAmountRates) {
    return pair -> pair.map2(v -> paymentTypeAmountRates.amountRate(pair._2()));
  }

  /**
   * {@link PaymentTypeAmountRates}を基に{@link MemberPaymentAmounts}を計算する
   *
   * @param paymentTypeAmountRates {@link PaymentTypeAmountRates}
   * @return {@link MemberPaymentAmounts}
   */
  @Nonnull
  public static MemberAmountRates of(
      MemberPaymentTypes memberPaymentTypes, PaymentTypeAmountRates paymentTypeAmountRates) {
    // FIXME: Getter
    return MemberAmountRates.of(
        memberPaymentTypes
            .toTuple2Values()
            .map(convertToMemberAmountRateOpt(paymentTypeAmountRates))
            .filter(nonEmptyAmountRate())
            .map(convertToMemberAmountRate())
            .toList());
  }

  @Nonnull
  public static MemberAmountRates of(@Nonnull List<MemberAmountRate> values) {
    return new MemberAmountRates(values);
  }

  public List<MemberAmountRate> values() {
    return values;
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(MemberAmountRate element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(Predicate<MemberAmountRate> p) {
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
}
