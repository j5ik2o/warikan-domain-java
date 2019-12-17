package warikan.domain.model.amount.rate;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import warikan.domain.model.amount.types.MemberPaymentTypes;
import warikan.domain.model.payment_type.PaymentType;
import warikan.domain.model.payment_type.PaymentTypes;

/** 支払区分料金割合リスト。 */
public final class PaymentTypeAmountRates {

  private final AmountRate total;
  private final Set<PaymentTypeAmountRate> values;

  private PaymentTypeAmountRates(
      @Nonnull AmountRate total,
      @Nonnull MemberPaymentTypes memberPaymentTypes,
      @Nonnull PaymentTypeAmountRate head,
      @Nonnull Set<PaymentTypeAmountRate> tail) {
    this.total = total;
    Set<PaymentTypeAmountRate> _values = tail.add(head);
    AmountRate sum = sumOfAmountRates(memberPaymentTypes, _values);
    if (sum.compareTo(total) != 0)
      throw new IllegalArgumentException("The sum of values and the total are not match");
    this.values = adjustAmountRates(total, _values);
  }

  @Nonnull
  private static Set<PaymentTypeAmountRate> adjustAmountRates(
      @Nonnull AmountRate total, Set<PaymentTypeAmountRate> _values) {
    return _values.map(v -> v.withNewAmountRate(v.amountRate().adjust(total))).toSet();
  }

  @Nonnull
  private AmountRate sumOfAmountRates(
      @Nonnull MemberPaymentTypes memberPaymentTypes, Set<PaymentTypeAmountRate> _values) {
    return _values
        .map(
            v -> v.amountRate().times(memberPaymentTypes.memberCountByPaymentType(v.paymentType())))
        .reduce(AmountRate::add);
  }

  /**
   * {@link PaymentType}の序列に対して{@link AmountRate}の正比例しているか検証する。
   *
   * @param head
   * @param tail
   */
  private static void assertOrdering(PaymentTypeAmountRate head, PaymentTypeAmountRate... tail) {
    List<PaymentTypeAmountRate> values = List.of(tail).prepend(head).sorted();
    Optional<PaymentTypeAmountRate> cur = Optional.empty();
    for (PaymentTypeAmountRate next : values) {
      if (cur.isPresent() && cur.get().amountRate().compareTo(next.amountRate()) >= 0) {
        throw new IllegalArgumentException("Failure due to ordering of amounts");
      }
      cur = Optional.of(next);
    }
  }

  /**
   * 重複した要素がないか検証する。
   *
   * @param head
   * @param tail
   */
  private static void assertDuplicated(PaymentTypeAmountRate head, PaymentTypeAmountRate... tail) {
    HashSet<PaymentTypeAmountRate> all = HashSet.of(tail).add(head);
    if (1 + tail.length != all.size())
      throw new IllegalArgumentException("Failure due to duplicate elements");
  }

  /**
   * ファクトリメソッド。
   *
   * @param total {@link AmountRate 合計のレート}
   * @param memberPaymentTypes {@link MemberPaymentTypes}
   * @param head {@link PaymentTypeAmountRate}
   * @param tail {@link PaymentTypeAmountRate}
   * @return {@link PaymentTypeAmountRates}
   */
  @Nonnull
  public static PaymentTypeAmountRates of(
      AmountRate total,
      MemberPaymentTypes memberPaymentTypes,
      PaymentTypeAmountRate head,
      PaymentTypeAmountRate... tail) {
    assertDuplicated(head, tail);
    assertOrdering(head, tail);
    return new PaymentTypeAmountRates(total, memberPaymentTypes, head, HashSet.of(tail));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaymentTypeAmountRates that = (PaymentTypeAmountRates) o;
    return Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  public String toString() {
    return "PaymentTypeAmountRates{" + "values=" + values + '}';
  }

  private static Predicate<PaymentTypeAmountRate> equalsPaymentType(PaymentType tpe) {
    return paymentTypeAmountRate -> paymentTypeAmountRate.paymentType().equals(tpe);
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull PaymentTypeAmountRate element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(@Nonnull Predicate<PaymentTypeAmountRate> p) {
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
   * すべての{@link PaymentType}を取得する。
   *
   * @return {@link PaymentType}のセット
   */
  @Nonnull
  public PaymentTypes paymentTypes() {
    return PaymentTypes.of(values.map(PaymentTypeAmountRate::paymentType).toSet());
  }

  /**
   * 指定した{@link PaymentType}の{@link AmountRate}を取得する。
   *
   * @param tpe {@link PaymentType}
   * @return {@link AmountRate}
   */
  @Nonnull
  public Option<AmountRate> amountRate(PaymentType tpe) {
    return values.find(equalsPaymentType(tpe)).map(PaymentTypeAmountRate::amountRate);
  }
}
