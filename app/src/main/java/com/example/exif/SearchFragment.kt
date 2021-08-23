package com.example.exif

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
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

        val searchBar:ArrayList<SearchView> = arrayListOf()

        for (i in 1..30) {
            var search = "search$i"
            val searchId = resources.getIdentifier(search, "id", "com.example.exif")
            searchBar.add(view.findViewById(searchId))
        }

        searchBar[0].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result1", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[1].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result2", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[2].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result3", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[3].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result4", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[4].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result5", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[5].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result6", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[6].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result7", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[7].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result8", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[8].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result9", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[9].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result10", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[10].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result11", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[11].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result12", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[12].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result13", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[13].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result14", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[14].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result15", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[15].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result16", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[16].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result17", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[17].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result18", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[18].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result19", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[19].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result20", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[20].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result21", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[21].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result22", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[22].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result23", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[23].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result24", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[24].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result25", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[25].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result26", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[26].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result27", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[27].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result28", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[28].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result29", query)
                startActivity(intent)
                return false
            }
        })

        searchBar[29].setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("result30", query)
                startActivity(intent)
                return false
            }
        })

    }
}