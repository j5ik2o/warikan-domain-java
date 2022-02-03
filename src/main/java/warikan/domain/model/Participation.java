package warikan.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Participation {
  private Member member;
  private PaymentDuty duty;
}
