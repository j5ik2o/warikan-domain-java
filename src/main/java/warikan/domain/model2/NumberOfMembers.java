package warikan.domain.model2;

public class NumberOfMembers {
  int large;
  int medium;
  int small;

  public NumberOfMembers(int large, int medium, int small) {
    if (large < 0 || medium < 0 || small < 0) {
      throw new IllegalArgumentException("パラメータが不正です");
    }
    this.large = large;
    this.medium = medium;
    this.small = small;
  }
}
