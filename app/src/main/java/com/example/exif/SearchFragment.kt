package com.example.exif

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //サポートバー設定
        (activity as AppCompatActivity).supportActionBar?.title = "検索"

        val searchBar = view.findViewById<SearchView>(R.id.search)

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result", query)
                startActivity(intent)
                return false
            }
        })
    }
}