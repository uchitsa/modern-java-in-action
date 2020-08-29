import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

class Trader {

    private final String name;
    private final String city;

    Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    String getName() {
        return name;
    }

    String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

public class Transaction {

    private final Trader trader;
    private final int year;
    private final int value;

    private Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    private Trader getTrader() {
        return trader;
    }

    private int getYear() {
        return year;
    }

    private int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                '}';
    }

    public static void main(String[] args) {
        Trader raul = new Trader("Raul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raul, 2012, 1000),
                new Transaction(raul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        List<Transaction> transactions2011 = transactions
                .stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(toList());
        System.out.println("Transactions from 2011 sorted by value: " + transactions2011);

        List<String> cities = transactions
                .stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(toList());
        System.out.println("Distinct cities of traders: " + cities);

        List<Trader> cambridgeTraders = transactions
                .stream()
                .map(Transaction::getTrader)
                .filter(trader1 -> trader1.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(toList());
        System.out.println("Traders from Cambridge: " + cambridgeTraders);

        List<String> tradersNames = transactions
                .stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .collect(toList());
        System.out.println("Trader's names sorted asc: " + tradersNames);

        boolean existTraderFromMilan = transactions.stream().map(Transaction::getTrader).anyMatch(trader1 -> trader1.getCity().equals("Milan"));
        System.out.println("Is trader from Milan exist: " + existTraderFromMilan);

        int sumTransactionsCambridgeTraders = transactions
                .stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);
        System.out.println("Sum transactions of Cambridge's traders: " + sumTransactionsCambridgeTraders);

        Optional<Integer> maxSumTransaction = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        System.out.println("Max transaction's sum: " + maxSumTransaction.orElse(0));

        Optional<Transaction> minimalTransaction = transactions
                .stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        System.out.println("Minimal transaction is: " + minimalTransaction);
    }
}
