package warikan.domain.model.party;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.annotation.Nonnull;

/** 請求金額 */
public final class PartyDatetime {
  private final LocalDateTime value;

  private PartyDatetime(@Nonnull LocalDateTime value) {
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 請求金額。
   * @return {@link PartyDatetime}
   */
  @Nonnull
  public static PartyDatetime of(@Nonnull LocalDateTime value) {
    return new PartyDatetime(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    PartyDatetime that = (PartyDatetime) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "PartyDatetime{" + "value='" + value + '\'' + '}';
  }
}