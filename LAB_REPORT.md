# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/warawuth4/itcs382-lab

## Tools

| Item                    | Version or name   |
|-------------------------|-------------------|
| JDK                     | 17.0.17           |
| Maven                   | 3.10.0            |
| IDE                     | Intellij 2026.2.3 |
| SonarQube for IDE       | 13.0.0.85671      |
| AI tool and exact model | Gemini 3.8 Flash  |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: `c72debc (HEAD -> main, origin/main) Starter code`

Table 1. SonarQube issues in the starter code

| No. | Rule                                  | File:line                                                | Message                                                            |
|-----|---------------------------------------|----------------------------------------------------------|--------------------------------------------------------------------|
| 1   | Intentionality issue \| Not clear     | Main.java: 6                                             | Unused method parameters should be removed                         |
| 2   | Adaptability issue \| Not modular     | Main.java: 9, 15, 17, 22, ParcelService.java: 30, 67, 69 | Standard outputs should not be used directly to log anything       |
| 3   | Consistency issue \| Not conventional | ParcelService.java: 0                                    | "java.time" classes should be used for dates and times             |
| 4   | Intentionality issue \| Not clear     | ParcelService.java: 80                                   | Unused exception parameter should use the unnamed variable pattern |
| 5   | Intentionality issue \| Not clear     | ParcelService.java: 80                                   | Nested blocks of code should not be left empty                     |
| 6   | Intentionality issue \| Not clear     | ParcelService.java: 12                                   | Unused "private" fields should be removed                          |

## Task 2. AI review of the starter code

| Item                                                                                   | Value                         |
|----------------------------------------------------------------------------------------|-------------------------------|
| Date                                                                                   | 19-Oct-2026                   |
| AI tool and exact model                                                                | Gemini 3.8 Flash              |
| Commit reviewed                                                                        | c72debc                       |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | opened the folder             |
| Output file in the reviews folder                                                      | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line                                                | Finding                                                                                                        | Found by (SonarQube, AI or both) | SonarQube rule or course term                                                       | Correct? (yes or no, and why)                                                                |
|-----|----------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|----------------------------------|-------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| 1   | Main.java: 6                                             | Unused method parameter `String[] args` in entry point                                                         | SonarQube                        | Unused method parameters should be removed                                          | No, `main(String[] args)` is standard required signature for a Java application entry point. |
| 2   | Main.java: 9, 15, 17, 22, ParcelService.java: 30, 67, 69 | Standard output (`System.out.println`) used directly instead of a logger                                       | SonarQube                        | Standard outputs should not be used directly to log anything                        | Yes, production systems should use a logging framework, although common in simple CLI apps.  |
| 3   | ParcelService.java: 6                                    | Unused legacy import `java.util.Date`                                                                          | Both                             | Code smell (Unused import) / "java.time" classes should be used for dates and times | Yes, `Date` is never used in the class and is legacy API.                                    |
| 4   | ParcelService.java: 11                                   | Unused private field `maxParcels`                                                                              | Both                             | Code smell (Unused field) / Unused "private" fields should be removed               | Yes, `maxParcels` is initialized to 100 but never read or referenced.                        |
| 5   | ParcelService.java: 74                                   | Empty catch block silently ignores `IOException`                                                               | Both                             | Code smell (Empty block) / Nested blocks of code should not be left empty           | Yes, swallowing exceptions hides file writing failures and prevents debugging.               |
| 6   | ParcelService.java: 74                                   | Unused exception parameter `e`                                                                                 | SonarQube                        | Unused exception parameter should use the unnamed variable pattern                  | Yes, modern Java supports `_` for unused catch parameters.                                   |
| 7   | ParcelService.java: 70                                   | `FileWriter` is opened without being closed or flushed, leaving `parcels.csv` empty                            | AI                               | Defect / Resource leak                                                              | Yes, unclosed writer leaves buffer unwritten, causing data loss.                             |
| 8   | ParcelService.java: 39                                   | Strings compared using `==` instead of `equals()`, causing pickup to fail                                      | AI                               | Defect / Value vs reference equality                                                | Yes, `==` compares references so `code` matching always fails and fee returns 0.0.           |
| 9   | Main.java: 7                                             | `Scanner` opened on `System.in` is never closed                                                                | AI                               | Resource leak                                                                       | Yes, open stream should be closed or managed with try-with-resources.                        |
| 10  | Parcel.java: 7                                           | Field `status` is declared `public`                                                                            | AI                               | Encapsulation violation / Information hiding                                        | Yes, breaks encapsulation and allows arbitrary external mutation.                            |
| 11  | ParcelService.java: 19                                   | `getLockers()` returns direct reference to internal mutable list `lockers`                                     | AI                               | Information hiding violation                                                        | Yes, callers can modify the internal collection directly.                                    |
| 12  | ParcelService.java: 26                                   | Content coupling: `ParcelService` directly mutates `parcel.status` (lines 26, 41)                              | AI                               | Content coupling                                                                    | Yes, one class directly accesses and mutates the internal field of another.                  |
| 13  | Main.java: 6                                             | `main` has procedural cohesion, mixing command parsing, flow control, and console UI                           | AI                               | Procedural cohesion                                                                 | Yes, combines multiple unrelated procedural responsibilities in a single loop.               |
| 14  | ParcelService.java: 57                                   | `printParcels` has logical cohesion driven by the `forStaff` boolean flag                                      | AI                               | Logical cohesion                                                                    | Yes, the method chooses different behaviors based on a control flag.                         |
| 15  | ParcelService.java: 8                                    | `ParcelService` combines business logic, CSV persistence, and console SMS/printing                             | AI                               | Weak class cohesion (Single Responsibility Principle)                               | Yes, the class takes on multiple unrelated responsibilities.                                 |
| 16  | Main.java: 19                                            | Control coupling: `Main` passes boolean flags to `printParcels` to alter its behavior (lines 19, 21)           | AI                               | Control coupling                                                                    | Yes, callers pass a flag controlling the callee's execution path.                            |
| 17  | Main.java: 22                                            | Law of Demeter violation: `Main` navigates through `service.getLockers()` to filter `Locker` objects           | AI                               | Strong coupling (Law of Demeter)                                                    | Yes, `Main` knows too much about the internal structure of lockers.                          |
| 18  | ParcelService.java: 48                                   | Stamp coupling: `fee()` accepts an entire `Parcel` object but only accesses its size                           | AI                               | Stamp coupling                                                                      | Yes, passes composite object when only an integer size is needed.                            |
| 19  | ParcelService.java: 26                                   | Connascence of convention: parcel lifecycle statuses shared as raw string literals (`"STORED"`, `"PICKED_UP"`) | AI                               | Connascence of convention                                                           | Yes, classes rely on agreed-upon magic string literals.                                      |
| 20  | ParcelService.java: 51                                   | Connascence of convention: sizes represented as magic integers `1, 2, 3` across classes                        | AI                               | Connascence of convention                                                           | Yes, classes rely on tacit knowledge of integer size meanings.                               |
| 21  | Main.java: 12                                            | Connascence of position: `new Parcel(...)` relies on constructor parameter ordering                            | AI                               | Connascence of position                                                             | Yes, constructor arguments must follow exact positional ordering.                            |

2.1 Name two problems that only the AI review found.

1. **String comparison using `==` instead of `.equals()` (`ParcelService.java:39`):** `parcel.getCode() == code`
   compares object references instead of string values, causing parcel pickup lookups to always fail and returning a fee
   of `0.0`.
2. **Unclosed `FileWriter` resource leak (`ParcelService.java:70`):** `FileWriter` is opened without try-with-resources
   or `.close()`, so write buffers are never flushed to disk and `parcels.csv` remains completely empty (0 bytes).

2.2 Name one problem that only SonarQube found, or write none.

Unused method parameter `String[] args` in `Main.java:6` (SonarQube rule: *Unused method parameters should be removed*).

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you
checked them.

Finding No. 21 (Connascence of position in `Main.java:12` for
`new Parcel(words[1], words[2], Integer.parseInt(words[3]))`) is overly pedantic: positional parameters in a standard
3-parameter Java constructor are conventional and do not warrant refactoring to a Builder or parameter object in a small
domain class. Every other finding was verified directly against the code: the functional bugs (`==` comparison, unclosed
`FileWriter`) were confirmed through runtime execution and observing empty file output, while code smells and design
problems (public fields, mutable collection leaks, control/content coupling) match standard course definitions.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
|----------------|---------------|--------------------------------------|
| 1              | Kept          | Kept because `main(String[] args)` is the standard entry point signature required by the Java runtime specification. |
| 2              | Kept          | Kept because ParcelPoint is a simple command-line interface program where `System.out` is the required standard output mechanism for user prompts. |
| 3              | Fixed         | Removed unused legacy import `java.util.Date`. |
| 4              | Fixed         | Handled `IOException` parameter `e` by logging an error message via `System.err.println`. |
| 5              | Fixed         | Populated the empty catch block with proper error reporting so file write failures are handled. |
| 6              | Fixed         | Removed unused private field `maxParcels`. |

Number of SonarQube issues that remain after your fixes: 2

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
|------------------------------|------------------|--------|
| Encapsulation violation / Content coupling | Made `status` private in `Parcel.java` and provided getter and setter methods (`getStatus()`, `setStatus()`). Updated `ParcelService` to mutate state via setter instead of direct field access. | Pending commit: Fix encapsulation and content coupling in Parcel |
| Stamp coupling | Changed method signature from `fee(Parcel parcel, int days)` to `fee(int size, int days)` so `ParcelService` only passes the required primitive `int size` rather than the composite `Parcel` object. | Pending commit: Remove stamp coupling in ParcelService fee calculation |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content
of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3
```

Content of `parcels.csv`:

```text
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item                                                     | bikeshare-poor | bikeshare-good |
|----------------------------------------------------------|----------------|----------------|
| Date                                                     | 19-Oct-2026    | 19-Oct-2026    |
| AI tool and exact model                                  | Gemini 3.8 Flash | Gemini 3.8 Flash |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B as given in prompts.md | Prompt C as given in prompts.md |
| Builds with mvn package (yes or no)                      | yes            | yes            |
| Number of classes, interfaces, enums and records         | 2              | 8              |
| SonarQube issues, total                                  | 7              | 2              |
| SonarQube issues, by rule                                | java:S1104 (3), java:S2386 (2), java:S106 (1), java:S1172 (1) | java:S106 (1), java:S1172 (1) |
| AI review findings in Check 1                            | 2              | 2              |
| AI review findings in Check 2                            | 8              | 1              |
| Review output file in the reviews folder                 | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

No, SonarQube did not report any cohesion or coupling problems in `bikeshare-poor`. SonarQube's static analysis rules inspect syntax patterns, potential code smells, and security/reliability rules (such as `java:S2386` for mutable `public static` fields and `java:S1104` for public class attributes), but it has no rules or metrics for evaluating architectural cohesion (such as logical or procedural cohesion) or coupling types (such as control coupling, common coupling, or Single Responsibility Principle violations).

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for
bikeshare-good.

Yes, the AI review clearly distinguished between the two designs. For `bikeshare-poor`, the AI identified **control coupling** (`bikeshare-poor/src/main/java/bikeshare/Main.java:34`: callers pass a boolean flag `printReceipt` to `returnBike` which alters the internal execution flow of the method). This finding does not appear in `bikeshare-good` because `bikeshare-good` decomposes behaviors into dedicated, cohesive methods without control flag branching.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

I would much rather extend **`bikeshare-good`**. In `bikeshare-good`, adding a new member type is straightforward and adheres to the Open/Closed Principle: we only need to add a single enum constant `VISITOR(4.0)` to `MemberType.java` (and its corresponding text mapping in `fromString`), requiring zero modifications to `BikeShareService`, `PricingPolicy`, or `Station`. In contrast, in `bikeshare-poor`, member types are represented by raw magic integer codes (`1`, `2`) with conditional calculation logic hardcoded directly inside `returnBike` (`(record.memberType == 1) ? 2.0 : 3.0`), requiring invasive edits across multiple static methods and introducing significant risk of connascence of convention errors.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

SonarQube checks static syntax patterns, unused declarations, coding conventions, and common defect anti-patterns. For instance, in this lab, SonarQube detected the unused private field `maxParcels`, the legacy `java.util.Date` import, the unhandled empty catch block, and mutable `public static` fields. However, SonarQube does not check software architecture, cohesion, or runtime semantic bugs; it failed to detect the critical runtime bug where strings were compared with `==` in `ParcelService.pickUp`, the unclosed `FileWriter` resource leak, and architectural flaws like control coupling, stamp coupling, or Law of Demeter violations.

5.2 When should you not trust the AI review? Give an example from this lab.

You should not trust the AI review when it produces overly pedantic recommendations that ignore programming idioms, or when its findings lack practical significance for the project's scale. For example, in this lab, the AI flagged connascence of position for constructor parameter ordering in `new Parcel(words[1], words[2], Integer.parseInt(words[3]))`, suggesting a Builder pattern for a standard 3-field Java domain model. In addition, AI reviews may hallucinate line numbers or propose over-engineered refactorings that increase incidental complexity, so every suggestion must be verified against actual system requirements.

5.3 What will you do differently in Project Phase 2 after this lab?

In Project Phase 2, I will use SonarQube as an automated CI/pre-commit gate for code hygiene (catching unused fields, unhandled exceptions, and unclosed streams), while employing AI reviews specifically targeted at detecting architectural smells such as control coupling, weak class cohesion, and encapsulation leaks before writing code. Crucially, I will verify all AI recommendations with automated unit tests and avoid premature abstractions or over-engineered patterns, ensuring that our team's design remains cohesive, loosely coupled, and maintainable.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
|-------------------------|---------|--------------------------------------------|----------------------------|
| Gemini 3.8 Flash        | Code quality and design review (Prompt A) | `reviews/parcelpoint-review.md`, `reviews/bikeshare-poor-review.md`, `reviews/bikeshare-good-review.md` | Verified reported line numbers against source code, checked severity ratings, and confirmed flagged issues against course design concepts. |
| Gemini 3.8 Flash        | Code generation for poor and good designs (Prompts B and C) | `bikeshare-poor/` and `bikeshare-good/` source files and `pom.xml` | Compiled both projects with `mvn package`, executed interactive CLI sessions to verify correctness, and validated generated `rides.csv`. |
| Gemini 3.8 Flash        | Lab report completion and analysis | `LAB_REPORT.md` (Tasks 3, 4, 5, and declaration) | Tested fixed `parcelpoint` program output and `parcels.csv` content; verified all SonarQube and design issue counts. |
