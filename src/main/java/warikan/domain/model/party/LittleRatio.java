package warikan.domain.model.party;

import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Nonnull;

/** 弱者控除額 */
public final class LittleRatio {
  private final BigDecimal value;
  private LittleRatio(@Nonnull BigDecimal value) {
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param value 弱者控除額。
   * @return {@link LittleRatio}
   */
  @Nonnull
  public static LittleRatio of(@Nonnull BigDecimal value) {
    return new LittleRatio(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LittleRatio that = (LittleRatio) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "LittleRatio{" + "value='" + value + '\'' + '}';
  }
}