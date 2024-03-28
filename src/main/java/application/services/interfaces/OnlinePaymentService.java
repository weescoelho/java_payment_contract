package application.services.interfaces;

public interface OnlinePaymentService {
    public Double paymentFee(Double amount);
    public Double interest(Double amount, Integer months);
}
