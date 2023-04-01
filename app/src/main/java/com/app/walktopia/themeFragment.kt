package com.app.walktopia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class themeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_theme, container, false)

        val languages = resources.getStringArray(R.array.custom)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)

        val autocompleteTV = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val autocompleteTV2 = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        val autocompleteTV3 = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView3)
        val autocompleteTV4 = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView4)
        val card1 = view.findViewById<CardView>(R.id.card1)
        val card2 = view.findViewById<CardView>(R.id.card2)
        val card3 = view.findViewById<CardView>(R.id.card3)
        val card4 = view.findViewById<CardView>(R.id.card4)
        val show1 = view.findViewById<CardView>(R.id.show1)
        val show2 = view.findViewById<CardView>(R.id.show2)
        val show3 = view.findViewById<CardView>(R.id.show3)
        val show4 = view.findViewById<CardView>(R.id.show4)
        val custom1 = view.findViewById<TextView>(R.id.custom1)
        val custom2 = view.findViewById<TextView>(R.id.custom2)
        val custom3 = view.findViewById<TextView>(R.id.custom3)
        val custom4 = view.findViewById<TextView>(R.id.custom4)
        val close1 = view.findViewById<ImageView>(R.id.close1)
        val close2 = view.findViewById<ImageView>(R.id.close2)
        val close3 = view.findViewById<ImageView>(R.id.close3)
        val close4 = view.findViewById<ImageView>(R.id.close4)
        val edit1 = view.findViewById<EditText>(R.id.edit1)


        close1.setOnClickListener {
            show1.visibility = View.GONE
            card1.visibility = View.VISIBLE
        }
        close2.setOnClickListener {
            show2.visibility = View.GONE
            card2.visibility = View.VISIBLE
        }

        close3.setOnClickListener {
            show3.visibility = View.GONE
            card3.visibility = View.VISIBLE
        }

        close4.setOnClickListener {
            show4.visibility = View.GONE
            card4.visibility = View.VISIBLE
        }


        custom1.setOnClickListener {
            card1.visibility = View.GONE
            show1.visibility = View.VISIBLE
        }
        custom2.setOnClickListener {
            card2.visibility = View.GONE
            show2.visibility = View.VISIBLE
        }
        custom3.setOnClickListener {
            card3.visibility = View.GONE
            show3.visibility = View.VISIBLE
        }
        custom4.setOnClickListener {
            card4.visibility = View.GONE
            show4.visibility = View.VISIBLE
        }

        autocompleteTV.setAdapter(arrayAdapter)
        autocompleteTV2.setAdapter(arrayAdapter)
        autocompleteTV3.setAdapter(arrayAdapter)
        autocompleteTV4.setAdapter(arrayAdapter)


        return view
    }

}