public class PersonalLoan extends Loan{
    public PersonalLoan(){
        super();
        super.interestRate += 2;
    }

    public PersonalLoan(int loanNumber, double loanAmount, int term, String lastName, double interest) {
        super(loanNumber, loanAmount, term, lastName);
        super.interestRate = interest + 2;
    }
}
