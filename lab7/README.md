
Modyfikacja metod oznaczonych
adnotacją `@MeasureTime` z użyciem javaassist.


Jak uruchomić:
```
mvn package
mvn exec:exec
uruchamiamy przeglądarkę i wpisujemy adres - > localhost:4567/cokolwiek

```

Po wykonaniu zapytania pod adres `localhost:4567/cokolwiek`,
w logach aplikacji pojawi się czas wykonania metody.

Aby zatrzymać server wchodzimy na adres localhost:4567/stop