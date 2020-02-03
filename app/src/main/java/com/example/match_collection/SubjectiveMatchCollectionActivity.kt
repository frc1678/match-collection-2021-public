/*
* SubjectiveMatchCollectionActivity.kt
* match-collection
*
* Created on 1/18/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.match_collection

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.subjective_match_collection.*

// Activity for subjective match collection of the three teams on a single alliance.
class SubjectiveMatchCollectionActivity : CollectionActivity() {
    lateinit var panelOne: CounterPanel
    lateinit var panelTwo: CounterPanel
    lateinit var panelThree: CounterPanel

    lateinit var teamNumberOne: String
    lateinit var teamNumberTwo: String
    lateinit var teamNumberThree: String

    // Get intent extras from MatchInformationInputActivity.kt to retrieve team numbers
    // to be displayed on counter panels and used for recording data on specific attributes.
    fun getExtras() {
        teamNumberOne = intent.getExtras()?.getString("team_one").toString()
        teamNumberTwo = intent.getExtras()?.getString("team_two").toString()
        teamNumberThree = intent.getExtras()?.getString("team_three").toString()
    }

    // Function to create list of teams from best to worst of specific data point (i.e. speed, agility).
    fun recordRankingData(dataName: String): ArrayList<String> {
        val tempRankingList: ArrayList<String> = arrayListOf("rankOne", "rankTwo", "rankThree")

        tempRankingList.set(panelOne.getRankingData().getValue(dataName) - 1, teamNumberOne)
        tempRankingList.set(panelTwo.getRankingData().getValue(dataName) - 1, teamNumberTwo)
        tempRankingList.set(panelThree.getRankingData().getValue(dataName) - 1, teamNumberThree)

        return tempRankingList
    }

    // Initialize proceed button to record ranking data and proceed to QRGenerateActivity.kt when
    // proceed button is pressed.
    private fun initProceedButton() {
        btn_proceed_qr_generate.setOnClickListener { view ->
            speed_rankings = recordRankingData("Speed")
            agility_rankings = recordRankingData("Agility")

            if (speed_rankings.toString().contains("rank") || agility_rankings.toString().contains("rank")) {
                createErrorMessage("Please input different ranking values!", view)
            }
            else {
                val intent = Intent(this, QRGenerateActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                    btn_proceed_qr_generate, "proceed_button").toBundle())
            }
        }
    }

    // Initiate counter panels for the three different teams.
    fun initPanels() {
        panelOne = supportFragmentManager.findFragmentById(R.id.robotOne) as CounterPanel
        panelTwo = supportFragmentManager.findFragmentById(R.id.robotTwo) as CounterPanel
        panelThree = supportFragmentManager.findFragmentById(R.id.robotThree) as CounterPanel

        panelOne.setTeamNumber(teamNumberOne)
        panelTwo.setTeamNumber(teamNumberTwo)
        panelThree.setTeamNumber(teamNumberThree)

        panelOne.setAllianceColor()
        panelTwo.setAllianceColor()
        panelThree.setAllianceColor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }

        setContentView(R.layout.subjective_match_collection)

        getExtras()
        initProceedButton()
        initPanels()
    }
}