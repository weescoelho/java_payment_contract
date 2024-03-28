package application;

import application.services.ContractService;
import application.services.PaypalService;
import domain.entities.Contract;
import domain.entities.Installment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Entre com os dados do contrato:");
        System.out.print("Numero: ");
        Integer contractNumber = sc.nextInt();

        sc.nextLine();

        System.out.print("Data (dd/MM/yyyy): ");
        LocalDate contractDate = LocalDate.parse(sc.nextLine(), fmt);

        System.out.print("Valor do contrato: ");
        Double contractValue = sc.nextDouble();

        System.out.print("Entre com o numero de parcelas: ");
        Integer contractInstallments = sc.nextInt();

        Contract contract = new Contract(contractNumber, contractDate, contractValue);

        ContractService service = new ContractService(new PaypalService());
        service.processContract(contract, contractInstallments);

        System.out.println("Parcelas: ");
        List<Installment> contractInstallmentsList = contract.getInstallments();

        for(  Installment installment : contractInstallmentsList) {
            System.out.println(installment.getDueDate().format(fmt) + " - " + String.format("%.2f", installment.getAmount()));
        }


        sc.close();
    }
}
