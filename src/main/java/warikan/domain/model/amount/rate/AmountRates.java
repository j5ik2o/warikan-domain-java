package warikan.domain.model.amount.rate;

import io.vavr.collection.List;
import java.math.BigDecimal;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

/** 料金割合リスト。 */
public final class AmountRates {
  private final List<AmountRate> values;

  private AmountRates(@Nonnull List<AmountRate> values) {
    Validate.notEmpty(values.toJavaList());
    this.values = values;
  }

  /**
   * ファクトリメソッド。
   *
   * @param head 先頭の要素
   * @param tail 残りのリスト
   * @return {@link AmountRates}
   */
  @Nonnull
  public static AmountRates of(@Nonnull AmountRate head, @Nonnull List<AmountRate> tail) {
    return of(tail.prepend(head));
  }

  @Nonnull
  static AmountRates of(@Nonnull List<AmountRate> values) {
    return new AmountRates(values);
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull AmountRate element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(@Nonnull Predicate<AmountRate> p) {
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
   * 料金割合の合計を返す。
   *
   * @return 合計
   */
  @Nonnull
  public BigDecimal sum() {
    return values.map(AmountRate::value).reduce(BigDecimal::add);
  }
}
