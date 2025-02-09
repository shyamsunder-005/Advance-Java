// Interface Segregation Principle (ISP)
interface PaymentMethod {
    void processPayment(double amount);
}
interface Refundable {
    void refund(double amount);
}
// Single Responsibility Principle (SRP)
class PaymentProcessor {
    public void processPayment(PaymentMethod paymentMethod, double amount) {
        paymentMethod.processPayment(amount);
    }

    public void refundPayment(Refundable refundablePayment, double amount) {
        refundablePayment.refund(amount);
    }
}
// Open-Closed Principle (OCP) - Extensible without modifying existing code
class CreditCardPayment implements PaymentMethod, Refundable {
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of " + amount + "/-");
    }
    public void refund(double amount) {
        System.out.println("Refunding credit card payment of " + amount+ "/-");
    }
}
class PayPalPayment implements PaymentMethod, Refundable {
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of " + amount+ "/-");
    }
    public void refund(double amount) {
        System.out.println("Refunding PayPal payment of " + amount+ "/-");
    }
}
class PhonePayPayment implements PaymentMethod {
    public void processPayment(double amount) {
        System.out.println("Processing PhonePay payment of " + amount+ "/-");
    }
}
// Dependency Injection (DIP) - Allows swapping payment processors easily
public class PaymentSystem {
    private PaymentProcessor paymentProcessor;
    public PaymentSystem(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    public void processPayment(PaymentMethod paymentMethod, double amount) {
        paymentProcessor.processPayment(paymentMethod, amount);
    }
    public void refundPayment(Refundable refundablePayment, double amount) {
        paymentProcessor.refundPayment(refundablePayment, amount);
    }
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();
        PaymentSystem s = new PaymentSystem(processor);
        PaymentMethod creditCard = new CreditCardPayment();
        PaymentMethod payPal = new PayPalPayment();
        PaymentMethod phonePay = new PhonePayPayment();
        s.processPayment(creditCard, 100.0);
        s.processPayment(payPal, 50.0);
        s.processPayment(phonePay, 25);
        s.refundPayment((Refundable) creditCard, 50.0);
        s.refundPayment((Refundable) payPal, 25.0);
    }
}
// Report 
/*
1. Interface Segregation Principle:  We created separate interfaces `PaymentMethod` and `Refundable`.  This prevents classes like `GooglePayPayment` from being forced to implement a `refund()` method if it doesn't support refunds.
2. Single Responsibility Principle: The `PaymentProcessor` class is solely responsible for processing payments and refunds.  
3. Open/Closed Principle:Adding a new payment method doesn't require modifying existing code.  We simply create a new class that implements the `PaymentMethod` interface (and `Refundable` if applicable).PaymentSystem` and `PaymentProcessor` can then work with this new payment method without any changes.
4. Liskov Substitution Principle Because all payment method classes implement the `PaymentMethod` interface, they can be used interchangeably.  This ensures that the `PaymentSystem` can work with any payment method without needing to know the specific type.  The same applies to the `Refundable` interface.
5. Dependency Inversion Principle:The `PaymentSystem` depends on the `PaymentProcessor` abstraction, not on concrete payment method
*/
