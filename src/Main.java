import entities.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));


        // Remover João da lista
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // Conversão da data para padrão pedido no desafio
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

        // Impressão dos funcionários
        System.out.println("\n -------- Funcionários --------");
        funcionarios.forEach(f -> {
            System.out.println(
                    f.getNome() + " | " +
                    f.getDataNascimento().format(dtf) + " | " +
                    nf.format(f.getSalario()) + " | " +
                    f.getFuncao()
            );
        });

        // Aumento de 10%
        funcionarios.forEach(f -> {
            f.setSalario(f.getSalario().multiply(new BigDecimal(1.10)));
        });

        // Agrupar por função
        Map<String, List<Funcionario>> agrupados =
                funcionarios.stream()
                        .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir agrupados
        System.out.println("\n -------- Funcionários de cada setor --------");
        agrupados.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });

        // Aniversariantes mês 10 e 12
        System.out.println("\n -------- Aniversariantes (Outubro e Dezembro) --------");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonth() == Month.OCTOBER ||
                        f.getDataNascimento().getMonth() == Month.DECEMBER)
                .forEach(f -> System.out.println(f.getNome()));


        // Funcionário mais velho
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null){
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("\n Mais Velho: " + maisVelho.getNome() + " - " + idade + " anos");
        }

        // Ordem alfabética
        System.out.println("\n -------- Ordem alfabética --------");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // Soma total dos salários
        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);


        System.out.println("\nTotal salários: " + nf.format(total));

        // Quantidade de salários mínimos

        BigDecimal salarioMinimo = new BigDecimal("1212");

        System.out.println("\n -------- Salários mínimos --------");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + ": " + qtd);
        });
    }
}