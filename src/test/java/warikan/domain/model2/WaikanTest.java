package warikan.domain.model2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WaikanTest {
  @Test
  public void 傾斜2対1対0_5で合計35000円() {
    Nomikai nomikai = new Nomikai();

    Warikan warikan = nomikai.calculate(35000);

    assertEquals(warikan.large, 20000);
    assertEquals(warikan.medium, 10000);
    assertEquals(warikan.small, 5000);
  }
}
