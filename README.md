# 飲み会の割勘ドメイン

## 成果物

- チームで設計に合意すること
- SNSでの共有は ハッシュタグ [#WarikanModeling](https://twitter.com/search?q=%23WarikanModeling&src=typed_query) で

## 副産物

- ドメインモデル図
    - モデル名を書いた付箋を模造紙に貼る
    - モデル同士の関連を書く
- ドメインオブジェクトの型定義
    - 型名、プロパティ、メソッドの形式が分かればよい

## チーム

1チーム最大6名でモブワークしながら進める

## 実装言語

- Java(Java 10以降)

※Git, Java, IDEなどは事前にインストール・設定しておいてください。

JDKを切り替えることができる[jabba](https://github.com/shyiko/jabba)はオススメです。

## 対象ドメイン

飲み会の割勘を、幹事の負担軽減のために、ソフトウェアで解決する

## アクター

- 幹事

## ユースケース

- 幹事が **飲み会** を開催する(システムの外)
- 幹事が、システム上に、開催した **飲み会** の **名前**, **開催日時** などを設定する
- 幹事が、システム上の、開催した **飲み会** の **支払区分** (多め,普通,少なめ)ごとに **支払割合** を設定する
- 幹事が、システム上の、開催した **飲み会** の **参加者** を追加/削除する
- 幹事が、システム上の、開催した **飲み会** の **請求金額** を設定する  
- 幹事が、システムを利用して **参加者ごとの支払金額** を計算する
    - 割勘の具体的な計算方法は決まっていません。チームで相談してください。ただし以下は考慮してください
    - 割勘の方針には、 **支払金額** が多め・普通・少なめを考慮すること(上司は多め、遅く来た人は少なめに金額設定するため)
    - **飲み会** の **請求金額** と割勘した **合計支払金額** に **差額** が生じる場合は、システム化対象外してよいです(余裕があるチームは幹事が負担する仕様で検討してください)
        - たとえば、20,000円で3人で均等の割勘の場合、1人6,666円とすると2円の不足が生じます。不足分は幹事が負担するときれいですが仕様外にしてもよいです。あまり細かいところに時間を掛けすぎないようにしましょう。

### 注意事項

- ユースケースに疑問を感じたらいつでも意見や感想を共有しよう。そこからモデルのヒントが得られるかもしれない
- ドメイン上で扱われる情報だけでなく、意味のある計算処理に注目し、最終的にメソッドとして表現すること
- 割勘計算後の物理的なお金の移動に関することはスコープ外
- HTTPやDB I/Oなどの入出力は一旦忘れましょう。考えるのはドメインモデルとその実装だけです。

## ドメインモデルの洗い出し(達成=REQUIRED,時間=30分)

- ドメインモデルを洗い出し共有する
    - もくもくと考え込まずに声に出す
    - 役に立つ計算とは何かを考えて議論する(計算式が書けるなら書いてみよう)
    - 思い付いた概念名を付箋に書き出してみる

以下は一例。他にどんな概念があるか議論してみよう。

|名前(日本語)|英語名(任意)|概要|
|---|----|---|
|飲み会|||
|参加者|||
|...|||

英語名に悩むなら日本語クラス名にしてもよいです。

## ドメインオブジェクトを実装する(達成=RECOMMENDED, 時間=30分)

- 本プロジェクトをひな型プロジェクトとして利用する。各チームでフォークしてください。
    - [Moneyクラス](src/main/java/warikan/domain/model/Money.java)だけがあります。お金の計算などに使ってください。
    - ライブラリの依存関係(使うのは任意)
        - commons-lang
            - Apache Commonsの言語拡張用ライブラリ。表明プログラミングをする際は`Validate`を使うとよいです。
        - vavr
            - Immutableな関数型コレクションライブラリ
        - JSR305
            - Nonnullアノテーションが使えます
- 概念モデルを実装に反映する
    - 上記で決めた概念名を持つ、具体的な型(クラス or 列挙型 or インターフェイス)を定義する
        - 区分は列挙型で定義しましょう
    - 型の責務を考える
    - プロパティだけではなく、メソッドも仮で定義する
    - プリミティブ型よりドメイン固有型を選択する    
    - 細かい実装は後回し
        - `TODO` or `FIXME` タグをつけて、`return null;` or `throw new NotImplementedException();`などを使うとよい
    - テストを書くか書かないかはチームごとに決めてください
- ビルドとJIGの利用
    - `$ ./gradlew build`でビルドできます。
    - `$ ./gradlew jigReports`で[JIG](https://github.com/dddjava/jig)のレポートを出力できます。
        - 区分値依存関係(build/jig/category-usage.svg)
        - クラス依存関係(build/jig/business-rule-relation.svg)
        - パッケージ依存関係(build/jig/package-relation-depth?.svg)

## ドメインオブジェクトを改善する(達成=OPTIONAL, 時間=45分)

- モデルに対してチームで議論し改善していく。以下 主な設計や実装の観点(必要に応じて実装も追加してよい)
    - 不変条件を表明する
        - 値の表明はできてはいけないことを表明する
        - 型の表明はできることだけを表明する
        - メンバーの集合を決して空にできない場合。可能であれば(1)より(2)を選択する

            ```java
            public class Members {
                private final List<Member> values;
                // (1)値の制約。違反した場合は実行時例外がスローされる
                public Members(List<Member> values) {
                    Validate.notEmpty(values); // 違反すると例外スロー
                    this.values = new ArrayList<>(values);
                }
                // (2)型の制約。例外はない。コンパイルできないだけ
                public Members(Member head, Member... tail) {
                    values = new ArrayList<>();
                    values.add(head);
                    values.addAll(Arrays.asList(tail));
                }
            }
            ```

    - Tell Don't Ask(求めるな 命じよ)を厳守
        - ある処理をする際、その処理に必要な情報をオブジェクトから引き出さないで、情報を持ったオブジェクトに処理を命令すること
        - 内部データを計算しないでそのまま返すようなメソッド(Getter)は作らない
        
            ```java
            // 好ましくない例) 相手のオブジェクトから内部データを求めている
            var secretaries = members.values().stream().filter(Member::isSecretary).collect(Collectors.toList());
            // 好ましい例) 相手のオブジェクトに命令してください。
            var secretaries = members.secretaries();
            ```
            
            ```java
            // 好ましい例のMembersクラス
            public final Members {
                private final List<Member> values;
                
                public Members(Member head, Member... tail) {
                    values = new ArrayList<>();
                    values.add(head);
                    values.addAll(Arrays.asList(tail));
                }
                
                // 定義したとしてもドメインの計算文脈では利用しない(注意:I/O文脈では必要なることがある)
                // public List<Member> values() {
                //     return new ArrayList<>(values);
                // }
                
                // Membersクラス内部で計算させる
                public Optional<Members> secretaries() {
                　　 var result = values.stream().filter(Member::isSecretary).collect(Collectors.toList())
                    if (result.isEmpty()) {
                      return Optional.empty();
                    } else {
                      return Optional.of(new Members(result));
                    }
                }
            }
            ```

    - 誤りやすく安全ではない可変クラスではなく、不変(Immutable)クラスを採用する
    
        ```java
        // 可変クラス
        public final Members {
            private List<Member> values;
            
            // コンストラクタ省略
            
            // 可変メソッド
            public Members add(Member other) {
                // 可変リストなので相手に渡しても害がないように複製を作る
                var currentMembers = new ArrayList<>(values);
                currentMembers.add(other);
                return new Members(currentMembers);
            }
        }
        ```
        
        - Javaのコレクションは可変コレクションしかないので、上記のような回りくどい実装になります。[vavr](https://github.com/vavr-io/vavr)の依存関係が入っているのでそちらの不変コレクションを利用してもよいです。

    - 依存関係はシンプルに
        - パッケージやクラスの依存の方向が相互にならないように工夫してみる

- その場で解決できそうにない問題やリスクについては、赤い付箋でホットスポットとして表現しておく
    - コードにもTODOがあるとよい

## 成果物を共有する(達成=REQUIRED, 時間=15分=3分/チーム*5チーム)

- チーム内でモデルと実装が一致しているかをレビューする
- チームごとに以下を軸に考えたモデルを共有する
    - ドメインモデルの選択と集中
    - 議論が白熱したホットスポット


