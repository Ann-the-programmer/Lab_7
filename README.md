# Lab 7: Refactoring
## 1. SOLID and DRY Principles

### DRY (Don’t Repeat Yourself)
* **Definition:** Avoid duplicating the same logic or knowledge in multiple places. Every piece of business logic should have a single, authoritative representation in the codebase.
* **Application in this lab:** Originally, `ResidentialSite` and `LifelineSite` duplicated the same overall billing sequence (`base + tax`). By applying the Form Template Method refactoring, the shared algorithm is centralized once inside the `Site` superclass.

### SOLID Principles
* **S — Single Responsibility Principle (SRP):** A class or method should have one primary responsibility and one reason to change.
  * *In this lab:* Each calculation step (`getBaseAmount()` and `getTaxAmount()`) is separated from the overall bill-assembly algorithm (`getBillableAmount()`). `Site` is responsible for the billing workflow; subclasses are responsible only for their site-specific rate and tax rules.
* **O — Open/Closed Principle (OCP):** Software entities should be open for extension, but closed for modification.
  * *In this lab:* New site types (e.g., `CommercialSite`) can be added by extending `Site` and implementing `getBaseAmount()` and `getTaxAmount()` without modifying the shared `getBillableAmount()` template in `Site`.
* **L — Liskov Substitution Principle (LSP):** Subtypes must be substitutable for their base type without breaking expected behavior.
  * *In this lab:* Any `ResidentialSite` or `LifelineSite` instance can be referenced as a `Site` and have `getBillableAmount()` called polymorphically with consistent contract behavior.
* **I — Interface Segregation Principle (ISP):** Clients should not be forced to depend on methods they do not use.
  * *In this lab:* Not directly demonstrated, as this hierarchy uses a small abstract superclass with only the two essential hook methods needed by every `Site`.
* **D — Dependency Inversion Principle (DIP):** High-level modules should depend on abstractions rather than concrete implementations.
  * *In this lab:* The high-level billing algorithm in `Site.getBillableAmount()` depends on the abstract methods `getBaseAmount()` and `getTaxAmount()`, rather than depending on concrete subclass implementations.

---

## 2. Step-by-Step Refactoring & Design Principles

### Setup & Baseline Commits
1. **`Initial commit` / `Create original Site hierarchy`**
   * Created the starting `Site`, `ResidentialSite`, and `LifelineSite` classes with duplicated `getBillableAmount()` methods.
2. **`Small corrections`**
   * Verified and aligned the initial calculation formulas with Martin Fowler’s original example before starting refactoring (`ResidentialSite`: `units * rate` and `base * TAX_RATE`; `LifelineSite`: `units * rate * 0.5` and `base * TAX_RATE * 0.2`, using `TAX_RATE = 0.10` as a runnable demonstration constant).

---

### Refactoring Steps (Chronological)

1. **`Refactor 1: Extract base amount calculation`**
   * **IntelliJ Refactoring:** **Extract Method** (`Ctrl + Alt + M`)
   * **What changed:** Extracted `units * rate` (in `ResidentialSite`) and `units * rate * 0.5` (in `LifelineSite`) into `private double getBase()`.
   * **Principle:** **SRP** (at the method level)—separates computing the base amount from computing tax and assembling the total bill.

2. **`Refactor 2: Extract tax calculation`**
   * **IntelliJ Refactoring:** **Extract Method** (`Ctrl + Alt + M`)
   * **What changed:** Extracted the tax formulas into `private static double getTax(double base)` in both subclasses.
   * **Principle:** **SRP**—further isolates the tax calculation responsibility from the billing sequence, moving both subclasses toward an identical `getBillableAmount()` structure (**DRY** preparation).

3. **`Refactor 3: Make getTax non-static`**
   * **How performed:** Manual modifier removal (since IntelliJ auto-marked `getTax` as `static` when it only referenced its parameter and `Site.TAX_RATE`).
   * **What changed:** Converted `getTax` back into an instance method in both subclasses.
   * **Principle:** **OCP / LSP preparation**—`static` methods cannot be declared `abstract` or overridden polymorphically in Java, so making `getTax` an instance method is required for subclass polymorphism.

4. **`Refactor 4: Inline base variables`**
   * **IntelliJ Refactoring:** **Inline** (`Ctrl + Alt + N` — Inline Variable and Inline Parameter)
   * **What changed:** Inlined the local `base` variable in `getBillableAmount()` and inlined the `base` parameter inside `getTax()` so `getTax()` calls `getBase()` directly with zero parameters.
   * **Principle:** **DRY / Contract Alignment**—eliminates parameter differences and aligns both `getTax()` methods to a clean, uniform parameterless signature.

5. **`Refactor 5: Inline tax variables`**
   * **IntelliJ Refactoring:** **Inline** (`Ctrl + Alt + N`)
   * **What changed:** Inlined the local `tax` variable in `getBillableAmount()`, simplifying it in both subclasses to `return getBase() + getTax();`.
   * **Principle:** **DRY preparation**—makes the duplicated billing algorithm 100% identical line-for-line in both subclasses.

6. **`Refactor 6: Rename getBase and getTax to getBaseAmount and getTaxAmount, respectively`**
   * **IntelliJ Refactoring:** **Rename** (`Shift + F6`)
   * **What changed:** Renamed the extracted hook methods across all call sites to `getBaseAmount()` and `getTaxAmount()` to match Martin Fowler’s naming convention.
   * **Principle:** Improves clarity and establishes consistent, self-documenting hook names prior to pulling them up into `Site`.

7. **`Refactor 7: Change calculation methods visibility from "private" to "protected"`**
   * **IntelliJ Refactoring:** **Change Signature** (`Ctrl + F6`)
   * **What changed:** Changed `getBaseAmount()` and `getTaxAmount()` from `private` to `protected` in both subclasses.
   * **Principle:** **OCP & Encapsulation**—exposes consistent extension points to the `Site` superclass and future subclasses while keeping internal calculation steps hidden from external callers.

8. **`Refactor 8: Pull up getBillableAmount and abstract steps to Site`**
   * **IntelliJ Refactorings:** **Pull Members Up** (`Ctrl + Alt + Shift + T` → Pull Members Up) and **Safe Delete** (`Alt + Delete`)
   * **What changed:** Pulled `getBillableAmount()` up into `Site`, generated `protected abstract double getBaseAmount()` and `protected abstract double getTaxAmount()` in `Site`, added `@Override` in subclasses, and safely deleted the duplicate `getBillableAmount()` from `LifelineSite`.
   * **Principles:**
     * **DRY:** Centralizes the shared billing algorithm in `Site`.
     * **DIP:** `Site.getBillableAmount()` depends on abstractions (`abstract` methods) rather than concrete subclass logic.
     * **OCP:** New site types can extend `Site` without altering `Site.java`.
     * **LSP:** Any `Site` subtype can be substituted seamlessly when calling `getBillableAmount()`.

9. **`Refactor 9` & `Revert "Refactor 9: Make template method getBillableAmount final..."`**
   * **What changed:** Explored adding `final` to `getBillableAmount()` in `Site` (to prevent subclasses from overriding the shared algorithm—an **OCP** safeguard), but then reverted it to remain strictly faithful to Martin Fowler’s original *Form Template Method* code and keep the final design produced cleanly through standard refactoring steps.

---

### Summary of IntelliJ-Supported Refactorings Used
* **Extract Method** (`Ctrl + Alt + M`)
* **Inline** (`Ctrl + Alt + N` — both Inline Variable and Inline Parameter)
* **Rename** (`Shift + F6`)
* **Change Signature** (`Ctrl + F6`)
* **Pull Members Up** (`Ctrl + Alt + Shift + T` → Pull Members Up)
* **Safe Delete** (`Alt + Delete`)

---


## 3. Git Commit History

<img width="1086" height="410" alt="image" src="https://github.com/user-attachments/assets/21b6c941-7766-404d-b767-6ffefbe787cd" />


1. `Initial commit`
2. `Create original Site hierarchy`
3. `Small corrections`
4. `Refactor 1: Extract base amount calculation`
5. `Refactor 2: Extract tax calculation`
6. `Refactor 3: Make getTax non-static (IntelliJ generated it as static initially)`
7. `Refactor 4: Inline base variables`
8. `Refactor 5: Inline tax variables`
9. `Refactor 6: Rename getBase and getTax to getBaseAmount and getTaxAmount, respectively`
10. `Refactor 7: Change calculation methods visibility from "private" to "protected"`
11. `Refactor 8: Pull up getBillableAmount and abstract steps to Site`
12. `Refactor 9: Make template method getBillableAmount final (so subclasses cannot override or alter the shared billing sequence)`
13. `Revert "Refactor 9: Make template method getBillableAmount final (so subclasses cannot override or alter the shared billing sequence)"`
