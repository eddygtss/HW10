public abstract class Loan implements LoanConstants{
    int loanNumber, term;
    double loanAmount, interestRate;
    String lastName;

    public Loan(){

    }

    public Loan(int loanNumber, double loanAmount, double term, String lastName) {
        // If the loan is over the maximum allowed or less than 0 cancel the creation of the object.
        if (loanAmount < maximumLoan && loanAmount > 0){
            this.loanAmount = loanAmount;
        } else {
            return;
        }
        this.loanNumber = loanNumber;
        // Determine what term length if none of the constant terms then default to short term.
        if (term == shortTerm){
            this.term = shortTerm;
        } else if (term == mediumTerm) {
            this.term = mediumTerm;
        } else if (term == longTerm) {
            this.term = longTerm;
        } else {
            this.term = shortTerm;
        }
        this.lastName = lastName;
    }

    public int getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(int loanNumber) {
        this.loanNumber = loanNumber;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
                "Loan Number= " + loanNumber +
                ", Last Name= '" + lastName + '\'' +
                ", Loan Amount= $" + loanAmount +
                ", Interest Rate= " + interestRate + "%" +
                ", Term= " + term + " year(s)" +
                ", Total Due= $" + ((loanAmount * (interestRate/100)) * term + loanAmount) +
                '}';
    }
}
