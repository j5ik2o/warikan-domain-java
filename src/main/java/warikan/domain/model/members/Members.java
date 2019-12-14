package warikan.domain.model.members;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

/** メンバーグループ。 */
public final class Members {
  private final List<Member> values;

  private Members(@Nonnull List<Member> values) {
    Validate.notEmpty(values, "values are empty");
    // validateSecretary(values);
    this.values = new ArrayList<>(values);
  }

  private Members(@Nonnull Member head, @Nonnull List<Member> tail) {
    List<Member> values = new ArrayList<>();
    values.add(head);
    values.addAll(tail);
    // validateSecretary(values);
    this.values = values;
  }

  private void validateSecretary(List<Member> values) {
    if (values.stream().noneMatch(Member::isSecretary))
      throw new IllegalArgumentException("secretaries are empty");
  }

  @Nonnull
  public static Members of(@Nonnull Member head, @Nonnull Member... tail) {
    return of(head, Arrays.asList(tail));
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
    return values.stream().anyMatch(p);
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
    Long count = values.stream().filter(Member::isSecretary).count();
    return Integer.parseInt(count.toString());
  }

  /**
   * 非幹事の要素数を取得する。
   *
   * @return 要素数
   */
  public int sizeOfNonSecretaries() {
    Long count = values.stream().filter(Member::nonSecretary).count();
    return Integer.parseInt(count.toString());
  }

  /**
   * 幹事だけの{@link Members}を取得する。
   *
   * @return {@link Members}
   */
  @Nonnull
  public Members secretaries() {
    List<Member> members = values.stream().filter(Member::isSecretary).collect(Collectors.toList());
    return new Members(members);
  }

  /**
   * 非幹事だけの{@link Members}を取得する。
   *
   * @return {@link Members}
   */
  @Nonnull
  public Members nonSecretaries() {
    List<Member> members =
        values.stream().filter(Member::nonSecretary).collect(Collectors.toList());
    return new Members(members);
  }
}
