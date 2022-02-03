package warikan.domain.model;

import static org.junit.Assert.assertEquals;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class PartyTest {

  @Test
  public void testWarikan() {
    var ishibashi = new Member("石橋");
    var motoyama = new Member("本山");
    var party = new Party( "歓迎会", ishibashi,  OffsetDateTime.now());
    party.setRates(Map.of(PaymentDuty.LARGE, 10.0, PaymentDuty.MIDDLE, 8.0, PaymentDuty.SMALL, 5.0));
    party.participate(ishibashi, PaymentDuty.LARGE);
    party.participate(motoyama, PaymentDuty.MIDDLE);

    var fees = party.warikan(Money.of(100000, Money.JPY));

    assertEquals(Money.of(55560, Money.JPY), fees.get(ishibashi));
    assertEquals(Money.of(44448, Money.JPY), fees.get(motoyama));
  }
}
