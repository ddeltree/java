package br.ufal3;

import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Principal {
    static Scanner scanner = new Scanner(System.in);

    public record Pair<T1, T2>(T1 field, T2 value) {
    }

    public static void main(String[] args) throws Exception {
        String opt = scan("Tipo de colaborador (tecnico / vendedor)").toLowerCase();
        Colaborador colaborador = opt.equals("tecnico")
                ? new Tecnico()
                : new Vendedor();

        List<Pair<Field, Object>> entries = reflectOn(new Colaborador());
        entries.addAll(reflectOn(colaborador));
        for (Pair<Field, Object> entry : entries)
            entry.field.set(colaborador, entry.value);

        System.out.println();
        System.out.println(colaborador.getSalario());
        System.out.println(((Vendedor) colaborador).getComissao());
        System.out.println(colaborador.calculaSalario());
    }

    static List<Pair<Field, Object>> reflectOn(Object thing) {
        List<Pair<Field, Object>> entries = new ArrayList<>();
        for (Field field : thing.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String input = scan(field.getName());
            Object value = tryConvertToPrimitive(input, field.getType());
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
            return null;
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
