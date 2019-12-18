package warikan.domain.model.amount;

import io.vavr.collection.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

/** 支払金額リスト。 */
public final class PaymentAmounts {
  private final List<PaymentAmount> values;

  private PaymentAmounts(@Nonnull List<PaymentAmount> values) {
    Validate.notEmpty(values.toJavaList(), "values are empty");
    this.values = values;
  }

  /**
   * ファクトリメソッド。
   *
   * @param head
   * @param tail
   * @return
   */
  @Nonnull
  public static PaymentAmounts of(@Nonnull PaymentAmount head, @Nonnull List<PaymentAmount> tail) {
    return new PaymentAmounts(tail.prepend(head));
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull PaymentAmount element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(@Nonnull Predicate<PaymentAmount> p) {
    return values.exists(p);
  }

  /**
   * 支払金額の合計を取得する。
   *
   * @return {@link PaymentAmount}
   */
  @Nonnull
  public PaymentAmount sum() {
    return PaymentAmount.of(values.map(PaymentAmount::value).reduce(Money::add));
  }
}
