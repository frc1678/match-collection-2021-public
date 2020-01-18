package com.example.match_collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.counter_panel.*

// Class to create and customize a counter panel fragment for subjective scouting a single team.
class CounterPanel : Fragment() {
    // Create a counter panel view through inflation.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.counter_panel, container)
    }

    // Set specific team number at top of counter panel.
    fun setTeamNumber(teamNumber: String){
        tv_team_number.setText(teamNumber)
    }

    // Retrieves inputted subjective data of specific team in dictionary of data points to their rankings.
    fun getRankingData(): HashMap<String, Int> {
        val rankingData: HashMap<String, Int> = HashMap()
        val rootLayout = getView() as LinearLayout
        var counter: Counter
        for (i in 0 until (getView() as LinearLayout).childCount - 1) {
            counter = rootLayout.getChildAt(i + 1) as Counter
            rankingData.put(counter.dataName, counter.value)
        }
        return rankingData
    }
}