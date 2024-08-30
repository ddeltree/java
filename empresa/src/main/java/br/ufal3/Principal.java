package br.ufal3;

import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Principal {
    static Scanner scanner = new Scanner(System.in);

    public record Pair<T1, T2>(T1 field, T2 value) {
    }

    public static void main(String[] args) throws Exception {
        var opt = scan("Tipo de colaborador\n1. tecnico\n2. vendedor\n\t");
        Colaborador colaborador = opt.toLowerCase().startsWith("1")
                ? new Tecnico()
                : new Vendedor();

        var entries = Stream.concat(
                reflectOn(new Colaborador()).stream(),
                reflectOn(colaborador).stream()).toList();
        for (var entry : entries)
            entry.field.set(colaborador, entry.value);

        System.out.println("\nSalário líquido: " + colaborador.calculaSalario());
        System.out.println(new Gson().toJson(colaborador));
    }

    static List<Pair<Field, Object>> reflectOn(Object thing) {
        var entries = new ArrayList<Pair<Field, Object>>();
        for (var field : thing.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            var input = scan(field.getName());
            var value = tryConvertToPrimitive(input, field.getType());
            entries.add(new Pair<>(field, value));
        }
        return entries;
    }

    static String scan(String msg) {
        msg = msg.endsWith(": ") ? msg : msg + ": ";
        System.out.print(msg);
        return scanner.nextLine().strip();
    }

    static Object tryConvertToPrimitive(String input, Class<?> type) {
        try {
            return convertToPrimitive(input, type);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    static Object convertToPrimitive(String input, Class<?> type) throws NumberFormatException {
        Object output = input;
        if (type == Boolean.TYPE)
            output = Boolean.parseBoolean(input);
        if (type == Integer.TYPE)
            output = Integer.parseInt(input);
        if (type == Long.TYPE)
            output = Long.parseLong(input);
        if (type == Double.TYPE)
            output = Double.parseDouble(input);
        if (type == Float.TYPE)
            output = Float.parseFloat(input);
        return output;
    }
}
