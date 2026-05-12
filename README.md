# Spring Boot 실습 프로젝트

## AppConfig - Self-invocation & Transactional 학습 내용

### 1. `self.work1()` vs `this.work1()` 차이

핵심은 **Spring AOP 프록시**이다.

#### `self.work1()` (현재 코드)

```
ApplicationRunner → self (Spring 프록시) → @Transactional 적용 → 실제 work1() 실행
```

- `self`는 `@Autowired @Lazy`로 주입된 **Spring 프록시 객체**
- 프록시를 거치기 때문에 `@Transactional`이 **정상 동작** (트랜잭션 시작 → 커밋/롤백)

#### `this.work1()` (변경 시)

```
ApplicationRunner → this (실제 AppConfig 객체) → @Transactional 무시 → work1() 실행
```

- `this`는 **실제 AppConfig 인스턴스** (프록시가 아님)
- Spring AOP 프록시를 **우회**하기 때문에 `@Transactional`이 **동작하지 않음**

| | `self.work1()` | `this.work1()` |
|---|---|---|
| 호출 대상 | Spring 프록시 | 실제 객체 |
| `@Transactional` | 동작 O | 동작 X |
| 트랜잭션 | 보장됨 | 보장 안 됨 |

> 이것이 **Self-invocation 문제**이다. 같은 클래스 안에서 `@Transactional` 메서드를 호출할 때 `this`를 쓰면 프록시를 건너뛰기 때문에, `@Lazy`로 자기 자신을 주입받아 `self`로 호출하는 방식을 사용한다.

---

### 2. `work1()`에서 `@Transactional`을 제거하면?

Spring의 기본 전파 방식은 `REQUIRED` - 외부 트랜잭션이 있으면 참여하고, 없으면 새로 만든다.

#### `@Transactional` 있을 때 (현재)

```
work1() 트랜잭션 시작
  ├── memberService.join("system", ...) → 동일 트랜잭션 참여
  ├── memberService.join("admin", ...)  → 동일 트랜잭션 참여
  ├── memberService.join("user1", ...) → 동일 트랜잭션 참여
  └── ...
트랜잭션 커밋 (하나라도 실패하면 전체 롤백)
```

#### `@Transactional` 제거 시

```
work1() (트랜잭션 없음)
  ├── memberService.join("system", ...) → 새 트랜잭션 시작 → 커밋
  ├── memberService.join("admin", ...)  → 새 트랜잭션 시작 → 커밋
  ├── memberService.join("user1", ...) → 새 트랜잭션 시작 → 커밋
  └── ...
```

`memberService.join()`에 `@Transactional`이 있다면 각자 별개의 트랜잭션으로 동작한다.

| | `@Transactional` 있음 | `@Transactional` 없음 |
|---|---|---|
| 트랜잭션 범위 | `work1()` 전체가 하나 | 각 메서드가 독립적 |
| `user3` 저장 중 오류 | system~user3 **전부 롤백** | system~user2는 이미 **커밋됨** |
| 원자성 | 보장 | 보장 안 됨 |
