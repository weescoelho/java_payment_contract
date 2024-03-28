package application.services;

import application.services.interfaces.OnlinePaymentService;
import domain.entities.Contract;
import domain.entities.Installment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractService {
    final private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService){
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, Integer months){
        List<Installment> installments = new ArrayList<>();

        Double baseInstallmentValue = contract.getTotalValue() / months;

        for(int i = 0; i < months; i++){
            Installment installment = new Installment();
            Double interest = onlinePaymentService.interest(baseInstallmentValue, i + 1);
            Double paymentFee = onlinePaymentService.paymentFee(baseInstallmentValue + interest);

            Double installmentValue = baseInstallmentValue + interest + paymentFee;
            LocalDate dueDate = contract.getDate().plusMonths(i+1);

            installment.setAmount(installmentValue);
            installment.setDueDate(dueDate);

            installments.add(installment);

        }

        contract.setInstallments(installments);

    }
}
