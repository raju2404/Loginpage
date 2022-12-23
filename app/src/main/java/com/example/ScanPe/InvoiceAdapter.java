package com.example.ScanPe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ScanPe.R;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.viewholder> {
    List<InvoiceItem> InvoiceItem;
    Context context;
    public InvoiceAdapter(List<InvoiceItem> InvoiceItem,  Context context) {
        this.InvoiceItem = InvoiceItem;
        this.context= context;
    }

    @NonNull
    @Override
    public InvoiceAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.invoicerowitem,parent,false);
        return new InvoiceAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.viewholder holder, int position) {

       // holder.orderid.setText(String.valueOf(InvoiceItem.get(position).getORDERID()));
        holder.prodid.setText(String.valueOf(InvoiceItem.get(position).getPRODUCTID()));
        holder.prodname.setText(InvoiceItem.get(position).getPRODUCTNAME());
        holder.prodprice.setText(String.valueOf(InvoiceItem.get(position).getPRICE()));
        holder.prodqnt.setText(String.valueOf(InvoiceItem.get(position).getQUANTITY()));

    }

    @Override
    public int getItemCount() {
         return InvoiceItem.size();
    }
    public class viewholder extends RecyclerView.ViewHolder
    {
        TextView prodid,prodname,prodprice, prodqnt;

        public viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            //orderid=itemView.findViewById(R.id.orderid);
            prodid=itemView.findViewById(R.id.prodid);
            prodname=itemView.findViewById(R.id.prodname);
            prodprice=itemView.findViewById(R.id.prodprice);
            prodqnt=itemView.findViewById(R.id.prodqnt);

        }
    }


}
