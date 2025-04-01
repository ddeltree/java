package problemas;

import utils.InputHandler;

// Faça um programa que leia uma data no formato DD/MM/AAAA e indique se ela é válida.
public class Segundo {
  public Segundo() {
    solve();
  }

  boolean solve() {
    var scanner = new InputHandler();
    var date = scanner.askForString("Digite a data (DD/MM/AAAA): ");
    if (!date.matches("\\d{2}-\\d{2}-\\d{4}"))
      return false;
    String[] partes = date.split("/");
    int dia = Integer.parseInt(partes[0]);
    int mes = Integer.parseInt(partes[1]);
    int ano = Integer.parseInt(partes[2]);
    return isValidDate(dia, mes, ano);
  }

  boolean isValidDate(int day, int month, int year) {
    if (year < 1000 || year > 9999 || month < 1 || month > 12 || day < 1)
      return false;
    int[] daysPerMonth = { -1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    if (month == 2 && isLeapYear(year))
      daysPerMonth[2] = 29;
    return day <= daysPerMonth[month];
  }

  boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
  }
}
