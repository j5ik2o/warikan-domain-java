package warikan.domain.model;

import java.util.Objects;
import javax.annotation.Nonnull;

/** 飲み会名。 */
public final class PartyName {
  private final String value;

  private PartyName(@Nonnull String value) {
    if (value.isEmpty()) throw new IllegalArgumentException("value is empty.");
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 飲み会名
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
    PartyName partyName = (PartyName) o;
    return Objects.equals(value, partyName.value);
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
