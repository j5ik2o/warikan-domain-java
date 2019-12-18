package warikan.domain.model.members;

import io.vavr.collection.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

/** メンバーグループ。 */
public final class Members {
  private final List<Member> values;

  private Members(@Nonnull List<Member> values) {
    Validate.notEmpty(values.toJavaList(), "values are empty");
    this.values = values;
  }

  private Members(@Nonnull Member head, @Nonnull List<Member> tail) {
    this.values = tail.prepend(head);
    if (!this.values.exists(Member::isSecretary))
      throw new IllegalArgumentException("secretaries are empty");
  }

  @Nonnull
  public static Members of(@Nonnull Member head, @Nonnull Member... tail) {
    return of(head, List.of(tail));
  }

  @Nonnull
  public static Members of(@Nonnull Member head, @Nonnull List<Member> tail) {
    return new Members(head, tail);
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull Member element) {
    return values.contains(element);
  }

  /**
   * 述語に該当するかを返す。
   *
   * @param p 述語関数
   * @return 該当する場合true
   */
  public boolean exists(@Nonnull Predicate<Member> p) {
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
   * 指定された幹事区分の要素数を取得する。
   *
   * @param secretaryType {@link SecretaryType}
   * @return 要素数
   */
  public int sizeOf(@Nonnull SecretaryType secretaryType) {
    if (secretaryType.equals(SecretaryType.Secretary)) {
      return sizeOfSecretaries();
    } else {
      return sizeOfNonSecretaries();
    }
  }

  /**
   * 幹事の要素数を取得する。
   *
   * @return 要素数
   */
  public int sizeOfSecretaries() {
    return values.count(Member::isSecretary);
  }

  /**
   * 非幹事の要素数を取得する。
   *
   * @return 要素数
   */
  public int sizeOfNonSecretaries() {
    return values.count(Member::nonSecretary);
  }

  /**
   * 幹事だけの{@link Members}を取得する。
   *
   * @return {@link Members}
   */
  @Nonnull
  public Members secretaries() {
    List<Member> members = values.filter(Member::isSecretary).toList();
    return new Members(members);
  }

  /**
   * 非幹事だけの{@link Members}を取得する。
   *
   * @return {@link Members}
   */
  @Nonnull
  public Members nonSecretaries() {
    List<Member> members = values.filter(Member::nonSecretary).toList();
    return new Members(members);
  }
}
