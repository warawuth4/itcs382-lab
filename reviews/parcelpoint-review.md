Files read: `parcelpoint/src/main/java/parcelpoint/Locker.java`, `parcelpoint/src/main/java/parcelpoint/Main.java`, `parcelpoint/src/main/java/parcelpoint/Parcel.java`, `parcelpoint/src/main/java/parcelpoint/ParcelService.java`.

| No. | Check | Code location (file:line) | Finding | Severity (Must fix, Should fix or Note) | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:70` | `FileWriter` is opened without being flushed or closed, causing data loss. | Must fix | Wrap the `FileWriter` in a try-with-resources statement. |
| 2 | Check 1 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:39` | Strings are compared using `==` instead of `equals()`, causing pickup lookups to always fail. | Must fix | Change `parcel.getCode() == code` to `parcel.getCode().equals(code)`. |
| 3 | Check 1 | `parcelpoint/src/main/java/parcelpoint/Main.java:7` | `Scanner` resource is opened on `System.in` but never closed. | Should fix | Wrap `Scanner` in a try-with-resources block or close it after the input loop. |
| 4 | Check 1 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:74` | Empty catch block silently swallows `IOException`. | Should fix | Log the exception or rethrow it as an unchecked exception. |
| 5 | Check 1 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:6` | Unused import `java.util.Date`. | Note | Remove the unused `import java.util.Date;`. |
| 6 | Check 1 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:11` | Unused private field `maxParcels`. | Note | Remove the unused field `maxParcels`. |
| 7 | Check 1 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:26` | String literal `"STORED"` is repeated as a magic string across lines 26 and 61. | Note | Replace the literal with a constant or enum value. |
| 8 | Check 1 | N/A | Methods that are too long or too complex to read easily: not found. | Note | None. |
| 9 | Check 2 | `parcelpoint/src/main/java/parcelpoint/Parcel.java:7` | Field `status` is declared `public`, violating information hiding. | Must fix | Make `status` private and provide getter and setter methods. |
| 10 | Check 2 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:19` | `getLockers()` returns the direct reference to the internal mutable `List<Locker>`. | Must fix | Return an unmodifiable view using `Collections.unmodifiableList(lockers)`. |
| 11 | Check 2 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:26` | Content coupling: `ParcelService` directly modifies the public field `parcel.status` at lines 26 and 41. | Must fix | Encapsulate status updates inside a method on `Parcel`. |
| 12 | Check 2 | `parcelpoint/src/main/java/parcelpoint/Main.java:6` | `main` has procedural cohesion, mixing command-line parsing, UI printing, and business flow. | Should fix | Delegate parsing and dispatching to a dedicated controller or handler method. |
| 13 | Check 2 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:57` | `printParcels` has logical cohesion, selecting different printing behaviors via the `forStaff` flag. | Should fix | Split `printParcels` into two separate methods: `printCustomerParcels()` and `printStaffParcels()`. |
| 14 | Check 2 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:8` | Weak class cohesion: `ParcelService` handles business logic, persistence to CSV, and console SMS/output. | Should fix | Extract persistence into a repository class and notifications into a notifier class. |
| 15 | Check 2 | `parcelpoint/src/main/java/parcelpoint/Main.java:19` | Control coupling: `Main` passes a boolean flag (`false` at line 19, `true` at line 21) to control `printParcels` execution. | Should fix | Replace the flagged call with two distinct, intention-revealing method calls. |
| 16 | Check 2 | `parcelpoint/src/main/java/parcelpoint/Main.java:22` | Strong coupling / Law of Demeter violation: `Main` reaches through `service.getLockers()` to filter `Locker` instances. | Should fix | Add a `countFreeLockers()` method directly to `ParcelService`. |
| 17 | Check 2 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:48` | Stamp coupling: `fee()` accepts an entire `Parcel` object but only accesses `parcel.getSize()`. | Note | Pass `int size` directly to `fee()` instead of the entire `Parcel` object. |
| 18 | Check 2 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:26` | Connascence of convention: parcel lifecycle statuses are shared as raw strings (`"STORED"`, `"PICKED_UP"`). | Should fix | Replace raw string status conventions with a shared `Status` enum. |
| 19 | Check 2 | `parcelpoint/src/main/java/parcelpoint/ParcelService.java:51` | Connascence of convention: parcel and locker sizes are represented as magic integers `1`, `2`, `3` across classes. | Should fix | Define a shared `Size` enum instead of primitive integers. |
| 20 | Check 2 | `parcelpoint/src/main/java/parcelpoint/Main.java:12` | Connascence of position: `new Parcel(words[1], words[2], Integer.parseInt(words[3]))` depends strictly on constructor argument ordering. | Note | Use descriptive factory methods or a builder if parameter order is ambiguous. |

### Number of findings for each check
- **Check 1 (Code quality):** 7 findings (plus 1 "not found" category)
- **Check 2 (Cohesion, coupling and connascence):** 12 findings