package com.example.mytourguide;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytourguide.Adapters.EventListAdapter;
import com.example.mytourguide.Adapters.ExpenseListAdapter;
import com.example.mytourguide.Dialog.ExpenseDialogBox;
import com.example.mytourguide.PojoClasses.EventModel;
import com.example.mytourguide.PojoClasses.ExpenseModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragments extends Fragment {
    private ImageButton addexpense, viewAllExpense, addExtraBudget; ;
    private ImageButton Camera, Gallery;
    private PopupWindow popupWindow;
    private LinearLayout linearLayout;
    private Context context;
    private Dialog dialog;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    private DatabaseReference evenid;
    private DatabaseReference expenseref;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private TextView expense, budgets, percentage;
    private List<ExpenseModel> expenseModelList = new ArrayList<>();
    private ExpenseListAdapter adapter;
    private int toatlExpense;
    private String[] amounts = {""};

    private int sumAmount =0;
    public static final int REQUEST_PHOTO_CAPTURE = 111;


    public EventDetailsFragments() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialog= new Dialog(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event_details_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Camera = view.findViewById(R.id.CameraOnBtn);
        Gallery = view.findViewById(R.id.GalleryBtn);
        addExtraBudget=view.findViewById(R.id.AddExtraBudgetBtn);
        viewAllExpense=view.findViewById(R.id.ViewAllExpenseBtn);
        expense= view.findViewById(R.id.EventID);
        budgets= view.findViewById(R.id.totalbudget);
        percentage=view.findViewById(R.id.percentageTv);
        addexpense= view.findViewById(R.id.AddExpenseBtn);
        progressBar =view.findViewById(R.id.progressBar1);
        final String E_id= getArguments().getString("EventId");

        //eventid.setText(E_id);
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(user.getUid());
        eventRef = userRef.child("Event");
        evenid= eventRef.child(E_id);
        expenseref= evenid.child("Expense");
//        getting amount from database
        eventRef.child(E_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseModelList.clear();
                amounts[0] =dataSnapshot.child("budget").getValue().toString();
                budgets.setText(amounts[0]);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        expenseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               int progress=0;
                expenseModelList.clear();
                int  sum=0;
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    ExpenseModel expenseModel = d.getValue(ExpenseModel.class);
                    expenseModelList.add(expenseModel);

                }
                for(int i=0; i<expenseModelList.size();i++) {
                    sum =sum+ expenseModelList.get(i).getExpenseAmount();
                    //progress= Integer.parseInt(amounts); -expenseModelList.get(i).getExpenseAmount();
                    //progressBar.setProgress((int)progress);

                }
                //budgets.setText(""+(100-((Integer.parseInt(amounts)-sum)*100/Integer.parseInt(amounts))));
                expense.setText(String.valueOf(sum));

                toatlExpense=sum;
                progressBar.setProgress((100-((Integer.parseInt(amounts[0])-sum)*100/Integer.parseInt(amounts[0]))));
                percentage.setText(""+(100-((Integer.parseInt(amounts[0])-sum)*100/Integer.parseInt(amounts[0])))+"%");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        camera action on
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getActivity(), CameraActivity.class));

            }
        });

//        gallery here
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


//        }); //addExtraBudget end here
        addExtraBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evenid.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final EditText budget;
                        final TextView cancel;
                        final Button update;
                        final  TextView AddExpense;
                        dialog.setContentView(R.layout.add_extra_budget_popup);

                        final EventModel eventModel=  dataSnapshot.getValue(EventModel.class);
                        AddExpense=(TextView) dialog.findViewById(R.id.AddExpenseTV);
                        budget=(EditText) dialog.findViewById(R.id.AddExtraExpenseET);
                        update=(Button) dialog.findViewById(R.id.SaveExtraExpenseBtn);
                        cancel= (TextView) dialog.findViewById(R.id.crossbudget);
                        AddExpense.setText(eventModel.getBudget());
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String Id= E_id;
                                String budgets= budget.getText().toString();
                                int PreviousBudget= Integer.parseInt(eventModel.getBudget());
                                int newBudget= Integer.parseInt(budgets);
                                int TotalBudget=PreviousBudget+newBudget;
                                String UpdatedBudget= String.valueOf(TotalBudget);
                                eventRef.child(Id).child("budget").setValue(UpdatedBudget)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });


                        dialog.show();
                        //eventid.setText(eventModel.getEventId()+eventModel.getBudget()+eventModel.getDestination());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
//        viewAllExpense Start here
        viewAllExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.all_expense_popup);
                final RecyclerView ExpenseRecycler;
                ExpenseRecycler= (RecyclerView) dialog.findViewById(R.id.expenseRecycler);

                expenseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        expenseModelList.clear();
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            ExpenseModel expenseModel = d.getValue(ExpenseModel.class);
                            expenseModelList.add(expenseModel);
                        }

                       // eventid.setText(""+sum[0]);
                        adapter = new ExpenseListAdapter(expenseModelList, getActivity());
                        ExpenseRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ExpenseRecycler.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.show();
            }
        }); //viewAllExpense End here
//        add expense start here
        addexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView cancel;
                final EditText amount, description;
                final Button savebtn;
                dialog.setContentView(R.layout.add_expense_popup);
                cancel=(TextView) dialog.findViewById(R.id.cross);
                amount=(EditText) dialog.findViewById(R.id.AddExpenseET);
                description=(EditText) dialog.findViewById(R.id.DetailsExpenseET);
                savebtn=(Button) dialog.findViewById(R.id.SaveExpenseBtn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //cancel.setText(""+toatlExpense);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
               savebtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String expenseAmount= amount.getText().toString();
                       long date= System.currentTimeMillis();
                       int amount= Integer.parseInt(expenseAmount);
                       String details= description.getText().toString();
                       String Expenseid= expenseref.push().getKey();
                       if(toatlExpense+amount>Integer.parseInt(amounts[0])) {
                           AlertBoxMessage();
                       }
                       else {
                           ExpenseModel expenseModel= new ExpenseModel(Expenseid,details, amount, date);
                           expenseref.child(Expenseid).setValue(expenseModel)
                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                                           dialog.dismiss();
                                       }
                                   }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                               }
                           });
                       }

                   }
               });
            }

        });  //addExpense end here
    }
    public void  AlertBoxMessage() {
        ExpenseDialogBox dialogBox= new ExpenseDialogBox();
        dialogBox.show(getFragmentManager(), "expense");
    }
}
