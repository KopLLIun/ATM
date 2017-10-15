package org.koplllun.atm;

import org.koplllun.atm.parser.Parser;
import org.koplllun.atm.parser.impl.ReadXMLFile;

import javax.xml.bind.JAXBException;
import java.io.*;

class ATM {

    private ClientBase clientBase;
    private int cardNumber;
    private String money;
    private Parser parser;
    private InputStream file;
    private File tmpFile;

    //пополнение
    private void deposit(CashCard card) {
        // проверка - пополнять можно минимум 100
        if (isValidDeposit()) {
            //получить введенные данные
            int cash = Integer.parseInt(money);

            //добавить деньги на счет
            card.deposit(cash);

            //сообщение пользователю
            System.out.println("Deposit was successful");

            // пересчет баланса
            displayBalance(card);
        }
    }

    //снятие
    private void withdraw(CashCard card) {
        // проверка на снятие суммы
        if (isValidWithdrawal(card)) {
            //получить введенные данные
            int cash = Integer.parseInt(money);

            //вычесть деньги со счета
            card.withdraw(cash);

            //сообщение пользователю
            System.out.println("Withdrawal was successful");

            // пересчет баланса
            displayBalance(card);
        }
    }

    //проверка суммы для пополнения
    private boolean isValidDeposit() {
        String message = "";

        //если не число
        if (!isInteger(money)) {
            message = "Input integer number";

            //если < 100
        } else if (Integer.parseInt(money) < 5) {
            message = "Minimum for deposit: 100";

            //если > 500
        } else if (Integer.parseInt(money) > 500) {
            message = "Maximum for deposit: 500";

            // если не кратно 5
        } else if (Integer.parseInt(money) % 5 != 0) {
            message = "The amount must be a multiple of 10";

        } else {
            return true;
        }

        //сообщение пользователю об ошибке
        System.out.println("Error: " + message);
        return false;
    }

    //проверка суммы для снятия
    private boolean isValidWithdrawal(CashCard card) {
        String message = "";

        //если не число
        if (!isInteger(money)) {
            message = "Input integer number";

            //если < 10
        } else if (Integer.parseInt(money) < 5) {
            message = "Minimum for withdrawal: 5";

            //если > 250
        } else if (Integer.parseInt(money) > 500) {
            message = "Maximum for withdrawal: 500";

            // если не кратно 5
        } else if (Integer.parseInt(money) % 5 != 0) {
            message = "The amount must be a multiple of 5";

            // недостаточно средств
        } else if (card.getBalance() - Integer.parseInt(money) < 0) {
            message = "Insufficient funds";

            //если все ок
        } else {
            return true;
        }

        //сообщение пользователю
        System.out.println("Error: " + message);
        return false;
    }

    //остаток на счету
    private void displayBalance(CashCard card) {
        int balanceCard = card.getBalance();
        System.out.println("Account balance: " + balanceCard);
    }

    //инициализация данных
    public void setUp() throws JAXBException {
        try {
            parser = new ReadXMLFile();
            file = getClass().getClassLoader().getResourceAsStream("account.xml");
            tmpFile = File.createTempFile("data", null);
            //file = new File("src/main/resources/accounts.xml");
            clientBase = (ClientBase) parser.getObject(file, ClientBase.class);

        } catch (JAXBException e) {
            System.out.println("File not found");
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //эмуляция работы банкомата
    public void runATM() throws IOException, JAXBException {
        setUp();
        int numberAttempts = 3;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println("Insert card (input ID) or Enter for exit: ");
            String id = reader.readLine();

            if (id.length() == 0)
                break;

            System.out.println("Input password: ");
            String pass = reader.readLine();

            if (!isValidData(id, pass)) {
                numberAttempts--;
                System.out.println("Error, " + numberAttempts + " attempts left");
                if (numberAttempts == 0)
                    break;

            } else {
                do {
                    System.out.println("Operations:\n" + "1) Deposit\n" + "2) Withdraw\n" + "3) Display balance");
                    System.out.println("Select operation or input word exit for end session");
                    String operation = reader.readLine();
                    if (operation.equals("exit")) {
                        break;

                    } else  if (operation.equals("1")) {
                        System.out.println("Enter the amount of money: ");
                        money = reader.readLine();
                        deposit(clientBase.getClientBase().get(cardNumber));
                        parser.saveObject(tmpFile, clientBase);

                    } else if (operation.equals("2")) {
                        System.out.println("Enter the amount of money: ");
                        money = reader.readLine();
                        withdraw(clientBase.getClientBase().get(cardNumber));
                        parser.saveObject(tmpFile, clientBase);

                    } else {
                        displayBalance(clientBase.getClientBase().get(cardNumber));
                    }
                } while (true);
            }
        } while (true);
        tmpFile.deleteOnExit();
    }

    //проверка на корректность данных карты
    private boolean isValidData(String id, String pass) {
        boolean valid = true;
        try {
            for (int i = 0; i < clientBase.getClientBase().size(); i++) {
                CashCard cashCard = clientBase.getClientBase().get(i);
                valid = (cashCard.getId().equals(id) && cashCard.getPass().equals(pass));
                if (valid) {
                    cardNumber = i;
                    break;
                }
            }
            return valid;

        } catch (NullPointerException e) {
            System.out.println("Error: database not found");
            return false;
        }
    }

    //проверка строки на тип Integer
    private boolean isInteger(String num) {
        try {
            int number = Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
