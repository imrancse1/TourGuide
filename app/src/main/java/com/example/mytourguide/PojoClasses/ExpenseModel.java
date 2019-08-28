package com.example.mytourguide.PojoClasses;

public class ExpenseModel {
    private String ExpenseId;
    private String ExpenseName;
    private int ExpenseAmount;
    private long Date;

    public ExpenseModel() {
    }

    public ExpenseModel(String expenseId, String expenseName, int expenseAmount, long date) {
        ExpenseId = expenseId;
        ExpenseName = expenseName;
        ExpenseAmount = expenseAmount;
        Date = date;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public String getExpenseId() {
        return ExpenseId;
    }

    public void setExpenseId(String expenseId) {
        ExpenseId = expenseId;
    }

    public String getExpenseName() {
        return ExpenseName;
    }

    public void setExpenseName(String expenseName) {
        ExpenseName = expenseName;
    }

    public int getExpenseAmount() {
        return ExpenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        ExpenseAmount = expenseAmount;
    }
}
