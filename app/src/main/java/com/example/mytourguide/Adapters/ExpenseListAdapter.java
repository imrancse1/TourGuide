package com.example.mytourguide.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mytourguide.PojoClasses.ExpenseModel;
import com.example.mytourguide.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>{
    private List<ExpenseModel> expenseModelList;
    private Context context;

    public ExpenseListAdapter(List<ExpenseModel> expenseModelList, Context context) {
        this.expenseModelList = expenseModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.expense_row, viewGroup, false);
        return new ExpenseViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int i) {
         final ExpenseModel expenseModel= expenseModelList.get(i);
         holder.ExpenseName.setText(expenseModel.getExpenseName());
         holder.ExpenseAmount.setText(""+expenseModel.getExpenseAmount());
         String dates=new SimpleDateFormat("dd/MM/yyyy").format(new Date(expenseModel.getDate()));
         holder.ExpenseDate.setText(dates);
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView ExpenseName, ExpenseAmount, ExpenseDate;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            ExpenseName= itemView.findViewById(R.id.ExpenseNameTV);
            ExpenseAmount=itemView.findViewById(R.id.ExpenseAmountTV);
            ExpenseDate=itemView.findViewById(R.id.ExpenseDateTV);
        }
    }
}
