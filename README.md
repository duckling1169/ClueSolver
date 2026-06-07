# ClueSolver

A command-line deduction helper for the board game Clue (Cluedo). As you record
who showed (or could not show) which cards across suggestions, the solver narrows
down the hidden suspect, weapon, and room in the envelope.

## Stack

- Java (standard library only), organized as a small module.

## Run

Compile and run the entry point:

```bash
javac -d out src/main/*.java src/module-info.java
java -cp out main.Runner
```

Then follow the prompts to log each suggestion and its outcome; the solver
reports what it can deduce.

## Structure

- `src/main/` — `Card`, `Hand`, `Player`, `Suggestion`, `Type`, and the `Solver`.
- `Runner.java` — interactive entry point.

## License

MIT — see [LICENSE](LICENSE).
