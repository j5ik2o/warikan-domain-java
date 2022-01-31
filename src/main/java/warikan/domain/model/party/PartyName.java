package warikan.domain.model.party;

import java.util.Objects;

import javax.annotation.Nonnull;

public final class PartyName {
  private final String value;

  private PartyName(@Nonnull String value) {
    if (value.isEmpty()) throw new IllegalArgumentException("value is empty.");
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 参加者名。
   * @return {@link PartyName}
   */
  @Nonnull
  public static PartyName of(@Nonnull String value) {
    return new PartyName(value);
  }

  @Nonnull
  public String asString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PartyName that = (PartyName) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "PartyName{" + "value='" + value + '\'' + '}';
  }
}
