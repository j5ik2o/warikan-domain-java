package warikan.domain.model.payment_type;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

/** 支払区分リスト。 */
public final class PaymentTypes {
  private final Set<PaymentType> values;

  private PaymentTypes(@Nonnull Set<PaymentType> values) {
    Validate.notEmpty(values.toJavaSet(), "values are empty");
    this.values = values;
  }

  /**
   * ファクトリメソッド。
   *
   * @param head 先頭要素
   * @param tail 残りの要素
   * @return {@link PaymentTypes}
   */
  @Nonnull
  public static PaymentTypes of(@Nonnull PaymentType head, @Nonnull PaymentType... tail) {
    return of(HashSet.of(tail).add(head));
  }

  /**
   * ファクトリメソッド。
   *
   * @param head 先頭要素
   * @param tail 残りのセット
   * @return {@link PaymentTypes}
   */
  @Nonnull
  public static PaymentTypes of(@Nonnull PaymentType head, @Nonnull Set<PaymentType> tail) {
    return of(tail.add(head));
  }

  @Nonnull
  public static PaymentTypes of(@Nonnull Set<PaymentType> values) {
    return new PaymentTypes(values);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaymentTypes that = (PaymentTypes) o;
    return Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  public String toString() {
    return "PaymentTypes{" + "values=" + values + '}';
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull PaymentType element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(@Nonnull Predicate<PaymentType> p) {
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
