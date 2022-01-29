package warikan.domain.model.members;

import java.util.Objects;
import javax.annotation.Nonnull;

/** 参加者名。 */
public final class MemberName {
  private final String value;

  private MemberName(@Nonnull String value) {
    if (value.isEmpty()) throw new IllegalArgumentException("value is empty.");
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 参加者名。
   * @return {@link MemberName}
   */
  @Nonnull
  public static MemberName of(@Nonnull String value) {
    return new MemberName(value);
  }

  @Nonnull
  public String asString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemberName that = (MemberName) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "MemberName{" + "value='" + value + '\'' + '}';
  }
}
