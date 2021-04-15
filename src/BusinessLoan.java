public class BusinessLoan extends Loan{
    public BusinessLoan(){
        super();
        super.interestRate += 1;
    }

    public BusinessLoan(int loanNumber, double loanAmount, int term, String lastName, double interest) {
        super(loanNumber, loanAmount, term, lastName);
        super.interestRate = interest + 1;
    }
}
