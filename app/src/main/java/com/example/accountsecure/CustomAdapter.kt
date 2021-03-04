
package com.example.accountsecure
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val BankAccounts: BankAccounts): RecyclerView.Adapter<CustomAdapter.ScannedDevicesListViewHolder>() {

    class ScannedDevicesListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv1: TextView = view.findViewById(R.id.Name)
        val tv2: TextView  = view.findViewById(R.id.Amount)
        val tv3: TextView  = view.findViewById(R.id.Iban)
        val tv4: TextView  = view.findViewById(R.id.account)
        val tv5: TextView  = view.findViewById(R.id.username)
        val tv6: TextView  = view.findViewById(R.id.Customer)
        val tv7: TextView  = view.findViewById(R.id.Quantity_Price)
        val tv8: TextView  = view.findViewById(R.id.note)

    }
    // numberOfItems
    override fun getItemCount(): Int {
        return BankAccounts.listAccount.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedDevicesListViewHolder  {
        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false) as View
        return ScannedDevicesListViewHolder(linearLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ScannedDevicesListViewHolder, position: Int) {
        holder.tv1.text = "Account : " + BankAccounts.listAccount[position].account_name
        holder.tv2.text = "Amount : " + BankAccounts.listAccount[position].amount.toString() + " " + BankAccounts.listAccount[position].currency
        holder.tv3.text = "Iban : " + BankAccounts.listAccount[position].iban
        holder.tv4.text = "Account email : " + BankAccounts.listAccount[position].account
        holder.tv5.text = "Username : " + BankAccounts.listAccount[position].username
        holder.tv6.text = "Customer : " + BankAccounts.listAccount[position].Customer
        holder.tv7.text = "Quantity and Price : " + BankAccounts.listAccount[position].Quantity.toString() + " ; " + BankAccounts.listAccount[position].Price.toString()
        holder.tv8.text = "Note : " + BankAccounts.listAccount[position].note
    }
}