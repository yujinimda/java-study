# step26 — 네트워킹

**책 범위: 16장 「네트워킹(Networking)」 전체**

step24~25에서 데이터를 **파일**과 주고받았습니다. 그 통로의 반대쪽 끝을 **다른 컴퓨터**로
바꾸면 네트워킹이 됩니다. 스트림을 읽고 쓰는 코드는 거의 그대로입니다.

프론트에서 `fetch` 로 서버에 요청을 보내봤다면, 이 장은 **그 서버 쪽을 직접 만들어보는**
셈입니다. `WebSocket` 을 써봤다면 소켓이 낯설지 않을 것입니다.

**책의 마지막 장입니다.**

---

## 1. 읽을 것

- 16.1 네트워킹
  - 클라이언트/서버, IP 주소, **`InetAddress`**
  - URL, **`URLConnection`**
- 16.2 소켓 프로그래밍
  - TCP 와 UDP, **TCP 소켓 프로그래밍** (`ServerSocket`, `Socket`)
  - 입출력 스트림 연결 (step24가 여기서 그대로 쓰입니다)
  - **UDP 소켓 프로그래밍** (`DatagramSocket`, `DatagramPacket`)

### 함께 읽을 글

[Inpa Dev](https://inpa.tistory.com/category/Language/Java) 블로그에는 자바 네트워킹을
다룬 글이 없습니다. step26은 책과 아래 문제·과제로 진행합니다.

## 2. 손으로 정리할 것

1. **TCP 와 UDP 비교.**

   | | TCP | UDP |
   |---|---|---|
   | 연결 | 연결한 뒤 주고받음 | 연결 없이 그냥 보냄 |
   | 신뢰성 | 순서·도착 보장 | 보장 없음 (유실 가능) |
   | 속도 | 상대적으로 느림 | 빠름 |
   | 클래스 | `ServerSocket` / `Socket` | `DatagramSocket` / `DatagramPacket` |
   | 비유 | 전화 | 우편엽서 |
   | 쓰는 곳 | 웹, 파일 전송, 채팅 | 실시간 영상, 게임, DNS |

2. **TCP 통신 흐름**을 순서대로 그리세요.

   ```
   서버                              클라이언트
   ServerSocket 생성 (포트 지정)
   accept()  ← 기다림
                              ←──  Socket 생성 (주소 + 포트)
   Socket 획득
   ─────────── 스트림으로 데이터 주고받기 ───────────
   close()                          close()
   ```

   **`accept()` 는 접속이 올 때까지 멈춰 있습니다.** 이게 처음 보면 당황스러운 부분입니다.

3. **IP 와 포트.**

   ```
   IP 주소   어느 컴퓨터냐        192.168.0.10
   포트      그 안의 어느 프로그램이냐   9999
   ```

   건물 주소(IP)와 호실 번호(포트)입니다. 0~65535 중 **0~1023 은 예약**돼 있어서
   실습에는 보통 **9000번대 이상**을 씁니다.

4. **소켓에서 스트림을 얻는 두 줄.** step24의 조합이 그대로 나옵니다.

   ```java
   BufferedReader in  = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
   PrintWriter    out = new PrintWriter(socket.getOutputStream(), true);
   ```

   `PrintWriter` 의 두 번째 인자 `true` 는 **autoFlush** 입니다. 이게 없으면
   보낸 데이터가 버퍼에 남아 상대에게 안 갑니다. (step24 문제 7과 같은 이야기)

## 3. 문제

### 문제 1 — InetAddress
```java
InetAddress local = InetAddress.getLocalHost();
System.out.println(local.getHostName());
System.out.println(local.getHostAddress());

InetAddress lb = InetAddress.getByName("localhost");
System.out.println(lb);
System.out.println(lb.getHostAddress());
```
`localhost` 를 그냥 출력하면 `localhost/127.0.0.1` 처럼 나옵니다.
`127.0.0.1` 이 무엇을 가리키는 주소인지 한 줄로 쓰세요.

### 문제 2 — URL 파싱
```java
URL url = new URL("https://inpa.tistory.com/category/Language/Java?page=2");
System.out.println(url.getProtocol());
System.out.println(url.getHost());
System.out.println(url.getPort());
System.out.println(url.getDefaultPort());
System.out.println(url.getPath());
System.out.println(url.getQuery());
```
`getPort()` 가 `-1` 인데 `getDefaultPort()` 는 `443` 입니다. 왜 다른가요?

> **컴파일하면 경고가 나옵니다.**
> ```
> warning: [deprecation] URL(String) in URL has been deprecated
> ```
> JDK 20부터 `new URL(문자열)` 이 deprecated 됐습니다. 지금은 이렇게 씁니다.
> ```java
> URL url = URI.create("https://example.com/a/b?x=1").toURL();
> ```
> 책이 3판이라 옛 방식으로 나옵니다. 둘 다 동작하지만 새 코드에는 `URI` 쪽을 씁니다.

### 문제 3 — accept() 가 멈춘다
```java
ServerSocket serverSocket = new ServerSocket(9999);
System.out.println("서버 시작");
Socket socket = serverSocket.accept();
System.out.println("접속됨");
```
실행하면 "서버 시작" 만 찍히고 프로그램이 끝나지 않습니다. **고장이 아닙니다.**
무슨 일이 일어나고 있는지 설명하세요.

이 상태를 빠져나오려면 터미널에서 무엇을 누르나요?

### 문제 4 — 서버가 없을 때
```java
Socket s = new Socket("localhost", 9998);   // 아무도 안 듣고 있는 포트
```
실행 중 죽습니다. 예외 이름과 메시지를 쓰세요.

```
java.net.ConnectException: Connection refused
```

### 문제 5 — 포트 중복 ⭐
```java
ServerSocket a = new ServerSocket(9997);
ServerSocket b = new ServerSocket(9997);   // 같은 포트
```
```
java.net.BindException: Address already in use
```

이 에러는 실습 중에 **아주 자주** 만납니다. 앞서 띄운 서버를 안 끄고 다시 실행할 때 납니다.
해결 방법 두 가지를 쓰세요. (힌트: 포트를 바꾸거나, 이전 프로세스를 찾아 끄거나)

이전 프로세스를 찾는 명령입니다.
```bash
lsof -i :9999          # 그 포트를 쓰는 프로세스 확인
```

### 문제 6 — 스트림의 방향
서버와 클라이언트가 같은 소켓을 통해 이야기합니다.

```
서버의 getInputStream()  ←────  클라이언트의 getOutputStream()
서버의 getOutputStream() ────→  클라이언트의 getInputStream()
```

서버 코드에서 클라이언트가 보낸 말을 **읽으려면** 어느 스트림을 쓰나요?
양쪽이 동시에 `readLine()` 을 하면 어떻게 될지도 생각해보세요.

### 문제 7 — 한 번에 한 명
아래 서버는 클라이언트 **한 명씩만** 처리할 수 있습니다.

```java
while (true) {
    Socket socket = serverSocket.accept();
    // 이 클라이언트와 대화가 끝날 때까지 여기 머무른다
    handleClient(socket);
}
```

두 번째 클라이언트가 접속하면 어떻게 되나요?
여러 명을 동시에 받으려면 무엇이 필요한가요? (step19~20)

### 문제 8 — TCP 와 UDP 고르기
아래 상황에 무엇이 맞을지 고르고 이유를 쓰세요.

1. 파일 다운로드
2. 실시간 화상 통화
3. 채팅
4. 온라인 게임의 캐릭터 위치 전송
5. 은행 이체

## 4. 손코딩 과제

`src/step26/` 에 씁니다.

> ### 실행 방법 — 터미널이 두 개 필요합니다 ⚠️
>
> 서버와 클라이언트는 **동시에 떠 있어야** 합니다. 한 터미널에서는 안 됩니다.
>
> **터미널 1** (서버 — 먼저 띄웁니다)
> ```bash
> ./run step26/EchoServer.java
> ```
> "서버 시작..." 이 찍힌 채로 멈춰 있으면 정상입니다.
>
> **터미널 2** (클라이언트)
> ```bash
> ./run step26/EchoClient.java
> ```
>
> **끄는 법** — 각 터미널에서 `Ctrl+C`.
> 서버를 안 끄고 다시 띄우면 문제 5의 `BindException` 이 납니다.
> 그럴 때는 `lsof -i :9999` 로 찾아서 끄면 됩니다.

### 과제 1 — `MyAddress.java`

가장 간단한 것부터 합니다. 터미널 하나면 됩니다.

- 내 컴퓨터의 호스트명과 IP
- `localhost` 의 IP
- 도메인 이름으로 IP 조회 (`InetAddress.getByName("www.google.com")`)
- `getAllByName()` 으로 **한 도메인에 IP 가 여러 개** 붙어 있는 것 확인

없는 도메인을 넣으면 `UnknownHostException` 이 납니다. `try-catch` 로 처리하세요. (step10)

### 과제 2 — `ReadWebPage.java`

웹페이지를 읽어옵니다. **step24의 `BufferedReader` 가 그대로 쓰입니다.**

```java
URL url = URI.create("https://example.com").toURL();
try (BufferedReader br = new BufferedReader(
         new InputStreamReader(url.openStream(), "UTF-8"))) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
```

**파일을 읽던 코드와 거의 같습니다.** 통로의 반대쪽만 바뀐 것입니다.

확장: 자바 11부터 있는 `HttpClient` 로도 해보세요. 책에는 없지만 실무는 이쪽입니다.

```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest req = HttpRequest.newBuilder()
    .uri(URI.create("https://example.com"))
    .GET().build();
HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
System.out.println(res.statusCode());
System.out.println(res.body().length());
```

`fetch` 와 구조가 비슷합니다. 상태코드 `200` 을 직접 확인해보세요.

### 과제 3 — `EchoServer.java` + `EchoClient.java` ⭐

이 단계의 핵심 과제입니다. 클라이언트가 보낸 말을 서버가 되돌려줍니다.

**서버**
- 9999번 포트로 `ServerSocket` 생성
- `accept()` 로 접속을 기다림
- 받은 줄 앞에 `"echo: "` 를 붙여 돌려보냄
- `"bye"` 를 받으면 인사하고 연결 종료

**클라이언트**
- `localhost:9999` 로 접속
- 키보드 입력을 서버로 보내고, 응답을 출력
- `"bye"` 를 보내면 종료

```
[터미널 1 — 서버]
서버 시작. 9999번 포트에서 기다립니다.
접속: 127.0.0.1
받음: 안녕
받음: 자바
받음: bye
연결 종료

[터미널 2 — 클라이언트]
서버에 연결됐습니다. 보낼 말을 입력하세요 (bye 로 종료)
안녕
서버 응답: echo: 안녕
자바
서버 응답: echo: 자바
bye
서버 응답: 안녕히 가세요
```

**한글이 제대로 오가는지** 꼭 확인하세요. 깨진다면 step24 문제 6의 인코딩 문제입니다.

### 과제 4 — `UdpSender.java` + `UdpReceiver.java`

UDP 로 같은 일을 해보세요. TCP 와 무엇이 다른지 코드로 비교하는 게 목적입니다.

```java
// 받는 쪽
DatagramSocket socket = new DatagramSocket(9990);
byte[] buf = new byte[256];
DatagramPacket packet = new DatagramPacket(buf, buf.length);
socket.receive(packet);                                  // 기다림
String msg = new String(packet.getData(), 0, packet.getLength());
```

```java
// 보내는 쪽
DatagramSocket socket = new DatagramSocket();
byte[] data = "UDP 안녕".getBytes();
DatagramPacket packet = new DatagramPacket(
    data, data.length, InetAddress.getByName("localhost"), 9990);
socket.send(packet);
```

TCP 와 비교해서 **없는 것**을 주석으로 적으세요.
(힌트: `accept()`, `Socket`, 스트림, 연결 상태)

그리고 **받는 쪽을 안 띄운 채로 보내보세요.** TCP 와 달리 에러가 안 납니다. 왜일까요?

### 과제 5 — `ChatServer.java` (선택) ⭐

문제 7의 한계를 해결합니다. **step19~20의 쓰레드**가 필요합니다.

- 접속한 클라이언트마다 쓰레드를 하나씩 만들어 처리
- 한 명이 보낸 메시지를 **접속한 모두에게** 전달
- 접속·퇴장 알림

```java
while (true) {
    Socket socket = serverSocket.accept();
    clients.add(socket);                       // 목록에 추가
    new Thread(() -> handleClient(socket)).start();   // 쓰레드로 넘김
}
```

여러 쓰레드가 `clients` 목록을 동시에 건드리므로 **동기화가 필요합니다.**
step20에서 배운 것을 여기서 씁니다. `Collections.synchronizedList()` 를 쓰거나
`synchronized` 블록으로 감싸세요.

터미널 **세 개**로 서버 하나 + 클라이언트 둘을 띄워 확인하세요.

## 5. 자가 점검

- [ ] TCP 와 UDP 의 차이를 표로 설명할 수 있다
- [ ] IP 와 포트의 역할을 구분할 수 있다
- [ ] `accept()` 가 멈추는 것이 정상임을 안다
- [ ] 소켓에서 입출력 스트림을 얻어 쓸 수 있다
- [ ] `BindException` 이 왜 나는지 알고 해결할 수 있다
- [ ] 서버와 클라이언트를 터미널 두 개로 띄워 통신시킬 수 있다
- [ ] 한 서버가 한 번에 한 명만 처리하는 이유와 해결책을 안다
- [ ] 파일 읽기 코드와 네트워크 읽기 코드가 거의 같다는 걸 안다

---

## 완주

『자바의 정석』 **1장부터 16장까지, step00~26 전 과정**이 끝났습니다.

### step00 의 숙제

첫 단계에서 "지금은 안 읽힌다"고 넘겼던 파일이 있습니다. 이제 열어보세요.

```bash
unzip -p $JAVA_HOME/lib/src.zip 'java.base/java/lang/String.java' | less
```

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence,
               Constable, ConstantDesc {
```

한 줄에 이만큼이 들어 있습니다.

- `final` 클래스 — 상속 금지 (step08)
- `implements` 가 여럿 — 다중 구현 (step09)
- `Serializable` — 직렬화 가능 (step25)
- `Comparable<String>` — 정렬 기준을 스스로 가짐 (step16), 그리고 제네릭 (step17)
- `CharSequence` — 문자열처럼 취급되는 것들의 공통 규격

5천 줄짜리 파일이지만 **앞부분은 이제 읽힙니다.** `equals()`, `hashCode()`,
`compareTo()` 구현을 찾아보면 step07·step11·step16에서 배운 것이 그대로 있습니다.

### 이 저장소에 남은 것

- `notes/errors.md` — 직접 만난 컴파일 에러 모음. 처음에는 `';' expected` 였고
  지금은 `InvalidClassException` 까지 와 있을 것입니다
- `src/step00` ~ `src/step26` — 손으로 쓰고 컴파일한 코드 전부

### 다음에 갈 만한 곳

- **Spring Boot** — 자바로 웹 서버를 만드는 사실상의 표준입니다. step26에서 소켓으로
  직접 만든 것을 프레임워크가 대신 해줍니다. 프론트 경험이 있으면 API 를 만드는 쪽이
  감이 빠릅니다
- **JPA / Hibernate** — 객체(step05~09)와 데이터베이스 테이블을 잇는 도구입니다.
  step25의 직렬화가 "객체를 어떻게 저장하나"의 원시적인 형태였다면 이쪽이 실무 답입니다
- **알고리즘 문제 풀이** — 백준·프로그래머스. step14~16의 컬렉션과 step24의
  `BufferedReader` 가 바로 쓰입니다. 문법을 손에 붙이는 데 좋습니다
- **『이펙티브 자바』** — "문법을 아는 것"과 "잘 쓰는 것"은 다릅니다. step07의
  `equals`/`hashCode` 규약, step23의 `Optional` 안티패턴 같은 것을 90가지로 정리한 책입니다.
  지금 읽으면 절반은 이미 겪어본 이야기일 것입니다
