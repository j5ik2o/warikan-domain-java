package warikan.domain.model.amount.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import warikan.domain.model.members.Member;
import warikan.domain.model.payment_type.PaymentType;
import warikan.domain.model.payment_type.PaymentTypes;

/** 参加者支払区分リスト。 */
public final class MemberPaymentTypes {

  private final List<MemberPaymentType> values;

  private MemberPaymentTypes(@Nonnull List<MemberPaymentType> values) {
    this.values = values;
  }

  @Nonnull
  public static MemberPaymentTypes empty() {
    return of(List.empty());
  }

  /**
   * ファクトリメソッド。
   *
   * @param head 先頭の要素
   * @param tail 残りのリスト
   * @return {@link MemberPaymentTypes}
   */
  @Nonnull
  public static MemberPaymentTypes of(
      @Nonnull MemberPaymentType head, @Nonnull List<MemberPaymentType> tail) {
    List<MemberPaymentType> list = tail.prepend(head);
    return of(list);
  }

  /**
   * ファクトリメソッド。
   *
   * @param head 先頭の要素
   * @param tail 残りの要素
   * @return {@link MemberPaymentTypes}
   */
  @Nonnull
  public static MemberPaymentTypes of(
      @Nonnull MemberPaymentType head, @Nonnull MemberPaymentType... tail) {
    return of(head, List.of(tail));
  }

  @Nonnull
  static MemberPaymentTypes of(@Nonnull List<MemberPaymentType> values) {
    return new MemberPaymentTypes(values);
  }

  private static Predicate<MemberPaymentType> equalsPaymentType(@Nonnull PaymentType paymentType) {
    return element -> element.paymentType().equals(paymentType);
  }

  private static Function<MemberPaymentType, Tuple2<Member, PaymentType>>
      convertToMemberPaymentTypePair() {
    return memberPaymentType ->
        Tuple.of(memberPaymentType.member(), memberPaymentType.paymentType());
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull MemberPaymentType element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(@Nonnull Predicate<MemberPaymentType> p) {
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
   * 支払区分に対応するメンバー数を取得する。
   *
   * @param paymentType 支払区分
   * @return メンバー数
   */
  @Nonnull
  public BigDecimal memberCountByPaymentType(@Nonnull PaymentType paymentType) {
    return BigDecimal.valueOf(values.count(equalsPaymentType(paymentType)));
  }

  /**
   * {@link PaymentTypes}を取得する。
   *
   * @return {@link PaymentTypes}
   */
  @Nonnull
  public PaymentTypes paymentTypes() {
    Set<PaymentType> set = values.map(MemberPaymentType::paymentType).toSet();
    return PaymentTypes.of(set.head(), set.tail());
  }

  public List<Tuple2<Member, PaymentType>> toTuple2Values() {
    return values.map(convertToMemberPaymentTypePair()).toList();
  }
}
