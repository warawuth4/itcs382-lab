Files read: `bikeshare-poor/src/main/java/bikeshare/BikeRecord.java`, `bikeshare-poor/src/main/java/bikeshare/Main.java`.

| No. | Check | Code location (file:line) | Finding | Severity (Must fix, Should fix or Note) | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | `bikeshare-poor/src/main/java/bikeshare/Main.java:68` | Hardcoded string literal `"rides.csv"` in file writing method. | Note | Extract the file path into a configurable constant or parameter. |
| 2 | Check 1 | `bikeshare-poor/src/main/java/bikeshare/Main.java:71` | Catch block prints error to `System.err` instead of using a structured logger. | Note | Use a logger framework or throw an unchecked domain exception. |
| 3 | Check 1 | N/A | Methods that are too long or too complex to read easily: not found. | Note | None. |
| 4 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/BikeRecord.java:4` | Fields `bikeId`, `memberType`, and `startStation` are declared `public`, violating information hiding. | Must fix | Make fields `private` and provide getter methods. |
| 5 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/Main.java:12` | Common coupling via mutable `public static` fields `stationBikes` and `activeRentals`. | Must fix | Encapsulate shared state inside an instance-based service class with private collections. |
| 6 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/Main.java:11` | Weak class cohesion (Single Responsibility Principle violation): `Main` combines CLI UI, rental business rules, fee calculation, and CSV persistence. | Must fix | Decompose `Main` into dedicated classes for UI, domain service, fee policy, and persistence. |
| 7 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/Main.java:49` | Logical cohesion in `returnBike`: boolean flag `printReceipt` controls whether the method calculates fee or prints/saves receipt. | Should fix | Split into two distinct methods: one for calculating return fee and another for handling receipt output. |
| 8 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/Main.java:34` | Control coupling: `main` passes a boolean flag (`true`) to `returnBike` to dictate its internal execution path. | Should fix | Remove the boolean parameter and let the caller invoke receipt printing explicitly. |
| 9 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/Main.java:51` | Content coupling: `Main` directly accesses and relies on the public internal fields of `BikeRecord`. | Should fix | Access `BikeRecord` attributes via encapsulation methods. |
| 10 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/Main.java:41` | Connascence of convention: member types are represented by magic integer codes `1` (student) and `2` (staff). | Should fix | Replace raw integer codes with a strongly-typed `MemberType` enum. |
| 11 | Check 2 | `bikeshare-poor/src/main/java/bikeshare/Main.java:21` | `main` has procedural cohesion, combining command parsing, validation, flow control, and console I/O. | Note | Extract command parsing and dispatching to a dedicated CLI controller. |

### Number of findings for each check
- **Check 1 (Code quality):** 2 findings (plus 1 "not found" category)
- **Check 2 (Cohesion, coupling and connascence):** 8 findings
