package ph.edu.auf.rpgameandroid

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import kotlin.random.Random
import android.os.Bundle

val Button.isChecked: Boolean
    get() = this.isSelected

class MainActivity : AppCompatActivity() {

    private lateinit var attackRadioButton: Button
    private lateinit var defendRadioButton: Button
    private lateinit var healRadioButton: Button
    private lateinit var rollButton: Button

    private lateinit var rollResultTextView: TextView
    private lateinit var playerHPTextView: TextView
    private lateinit var enemyHPTextView: TextView
    private lateinit var logTextView: TextView

    private var playerHP = 100
    private var enemyHP = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        attackRadioButton = findViewById(R.id.radioButton3)
        defendRadioButton = findViewById(R.id.radioButton)
        healRadioButton = findViewById(R.id.radioButton4)
        rollResultTextView = findViewById(R.id.textView3)
        playerHPTextView = findViewById(R.id.textView12)
        enemyHPTextView = findViewById(R.id.textView7)
        logTextView = findViewById(R.id.textView)

        // Set initial HP values in TextViews
        updateHPTextViews()

        // Handle the Roll button click
        rollButton = findViewById(R.id.button2)
        rollButton.setOnClickListener {
            performPlayerTurn()
            performEnemyTurn()
        }
    }

    // Function to update the HP TextViews
    private fun updateHPTextViews() {
        playerHPTextView.text = "Your HP: $playerHP"
        enemyHPTextView.text = "Enemy HP: $enemyHP"
    }

    // Function to perform a player's turn
    private fun performPlayerTurn() {
        val rollResult = rollDice()
        rollResultTextView.text = "      $rollResult"

        val selectedAction = when  {
            attackRadioButton.isChecked -> "Attack"
            defendRadioButton.isChecked -> "Defend"
            healRadioButton.isChecked -> "Heal"
            else -> "No Action"
        }

        when (selectedAction) {
            "Attack" -> {
                val damage = calculatePlayerAttack(rollResult)
                enemyHP -= damage
                logTextView.text = "You Attacked For $damage Damage!"
            }
            "Defend" -> {
                val same = null
                playerHP == same
                logTextView.text = "You Defended Against The Enemy's Attack."
            }
            "Heal" -> {
                val healing = calculatePlayerHeal(rollResult)
                playerHP += healing
                logTextView.text = "You Healed For $healing HP."
            }
            else -> {
                logTextView.text = "No Action Selected."
            }
        }

        if (enemyHP <= 0) {
            logTextView.text = "You Defeated The Enemy!"
            enemyHP = 0
        }

        updateHPTextViews()
    }

    // Function to perform an enemy's turn (random action)
    private fun performEnemyTurn() {
        val enemyAction = when (Random.nextInt(3)) {
            0 -> "Attack"
            1 -> "Defend"
            else -> "Heal"
        }

        when (enemyAction) {
            "Attack" -> {
                val damage = calculateEnemyAttack()
                playerHP -= damage
                logTextView.text = "Enemy Attacked For $damage Damage!"
            }
            "Defend" -> {
                logTextView.text = "Enemy Defended Against Your Attack."
            }
            "Heal" -> {
                val healing = calculateEnemyHeal()
                enemyHP += healing
                logTextView.text = "Enemy Healed For $healing HP."
            }
        }

        if (playerHP <= 0) {
            logTextView.text = "You Were Defeated By The Enemy!"
            playerHP = 0
        }

        updateHPTextViews()
    }

    // Function to calculate player's attack damage
    private fun calculatePlayerAttack(rollResult: Int): Int {
        // You can adjust the logic for calculating damage based on the roll result
        return rollResult * 2
    }

    // Function to calculate enemy's attack damage
    private fun calculateEnemyAttack(): Int {
        // You can adjust the logic for calculating enemy attack damage
        return Random.nextInt(10, 21)
    }

    // Function to calculate player's heal amount
    private fun calculatePlayerHeal(rollResult: Int): Int {
        // You can adjust the logic for calculating heal amount based on the roll result
        return rollResult
    }

    // Function to calculate enemy's heal amount
    private fun calculateEnemyHeal(): Int {
        // You can adjust the logic for calculating enemy heal amount
        return Random.nextInt(5, 11)
    }

    // Function to roll a 6-sided dice
    private fun rollDice(): Int {
        return Random.nextInt(1, 7)
    }
}
