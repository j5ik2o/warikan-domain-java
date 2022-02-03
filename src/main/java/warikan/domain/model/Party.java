package warikan.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Party {
  private final String name;
  private final Member secretary;
  private final OffsetDateTime holdAt;
  private Map<PaymentDuty, Double> rates;
  private List<Participation> participations = new ArrayList<>();

  public boolean participate(Member member, PaymentDuty duty) {
    return participations.add(new Participation(member, duty));
  }


  public Map<Member, Money> warikan(Money total) {
    double totalWeight = participations.stream()
        .map(m -> m.getDuty())
        .mapToDouble(d -> rates.get(d))
        .sum();
    var unit = total.divide(totalWeight);
    return participations.stream()
        .collect(Collectors.toMap(
          m -> m.getMember(),
          m -> unit.times(rates.get(m.getDuty())
        )));
  }
}
