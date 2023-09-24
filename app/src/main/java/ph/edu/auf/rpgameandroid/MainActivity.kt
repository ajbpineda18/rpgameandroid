package ph.edu.auf.rpgameandroid

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import kotlin.random.Random
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private var playerHP = 100
    private var enemyHP = 100
    private lateinit var rollResultTextView: TextView
    private lateinit var gameLogTextView: TextView
    private lateinit var playerHPTextView: TextView
    private lateinit var enemyHPTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val attackButton: Button = findViewById(R.id.button)
        val defendButton: Button = findViewById(R.id.button3)
        val healButton: Button = findViewById(R.id.button4)
        val rollButton: Button = findViewById(R.id.button2)

        rollResultTextView = findViewById(R.id.textView3)
        gameLogTextView = findViewById(R.id.textView)
        playerHPTextView = findViewById(R.id.textView12)
        enemyHPTextView = findViewById(R.id.textView7)

        updateHPTextViews()

        attackButton.setOnClickListener {
            playerAttack()
        }

        defendButton.setOnClickListener {
            playerDefend()
        }

        healButton.setOnClickListener {
            playerHeal()
        }

        rollButton.setOnClickListener {
            val rollResult = rollDice()
            rollResultTextView.text = "$rollResult"

            val enemyAction = chooseRandomEnemyAction()
            performEnemyAction(enemyAction)

            updateHPTextViews()
            checkGameResult()
        }
    }
    private fun rollDice(): Int {
        return Random.nextInt(1, 7) // Rolls a 6-sided dice (1-6)
    }
    private fun playerAttack() {
        val damage = Random.nextInt(10, 21) // Player deals random damage between 10 and 20
        enemyHP -= damage
        logGameAction("Player Attacked For $damage Damage!")
    }
    private fun playerDefend() {
        val defense = Random.nextInt(5, 11) // Player defends and gains random defense between 5 and 10
        playerHP += defense
        logGameAction("Player Defended And Gained $defense HP!")
    }
    private fun playerHeal() {
        val healing = Random.nextInt(10, 21) // Player heals for random amount between 10 and 20
        playerHP += healing
        logGameAction("Player Healed For $healing HP!")
    }
    private fun chooseRandomEnemyAction(): String {
        val actions = arrayOf("Attack", "Defend", "Heal")
        return actions[Random.nextInt(actions.size)]
    }
    private fun performEnemyAction(action: String) {
        when (action) {
            "Attack" -> {
                val damage = Random.nextInt(10, 21)
                playerHP -= damage
                logGameAction("Enemy Attacked For $damage Damage!")
            }
            "Defend" -> {
                val defense = Random.nextInt(5, 11)
                enemyHP += defense
                logGameAction("Enemy Defended And Gained $defense HP!")
            }
            "Heal" -> {
                val healing = Random.nextInt(10, 21)
                enemyHP += healing
                logGameAction("Enemy Healed For $healing HP!")
            }
        }
    }

    private fun logGameAction(action: String) {
        gameLogTextView.text = ""
        gameLogTextView.append("$action\n")
    }

    private fun updateHPTextViews() {
        playerHPTextView.text = "YOUR HP: $playerHP"
        enemyHPTextView.text = "ENEMY HP: $enemyHP"
    }

    private fun checkGameResult() {
        if (playerHP <= 0) {
            logGameAction("You Lost The Game!")
        } else if (enemyHP <= 0) {
            logGameAction("You Won The Game!")
        }
    }
}
