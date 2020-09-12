package Controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gostopgui.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

import Model.Card;
import Model.Game;
import Model.Round;

/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSaveGame;
    private Button btnMove;
    private Button btnHelp;
    private Button btnQuit;

    private TextView txtComputerScore;
    private TextView txtHumanScore;
    private TextView txtMainDisplay;
    private TextView txtRound;
    private TextView txtNextPlayer;
    private TextView txtComputerCardCount;
    private TextView txtHumanCardCount;
    private TextView txtStockCardCount;
    private TextView txtLayoutCardCount;

    private LinearLayout linearLStockPile;
    private LinearLayout linearLComputerCapture;
    private LinearLayout linearLComputerHand;
    private LinearLayout linearLHumanHand;
    private LinearLayout linearLHumanCapture;
    private LinearLayout linearLLayout;

    private Boolean loadGame;
    private String fileName = "";
    private ImageView imageView;


    private Game game = new Game();
    private Round round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnMove = (Button) findViewById(R.id.btnMove);
        btnSaveGame = (Button) findViewById(R.id.btnSave);
        btnQuit = (Button) findViewById(R.id.btnQuit);

        txtComputerScore = (TextView) findViewById(R.id.txtComputerPoints);
        txtHumanScore = (TextView) findViewById(R.id.txtHumanPoints);
        txtRound = (TextView) findViewById(R.id.txtRound);
        txtNextPlayer = (TextView) findViewById(R.id.txtNextPlayer);
        txtMainDisplay =(TextView) findViewById(R.id.txtMainDisplay);
        txtStockCardCount = (TextView) findViewById(R.id.txtStockPileCardCount);
        txtLayoutCardCount = (TextView) findViewById(R.id.txtLayoutCardCount);
        txtHumanCardCount = (TextView) findViewById(R.id.txtHumanHandCardCount);
        txtComputerCardCount = (TextView) findViewById(R.id.txtComputerHandCardCount);

        txtMainDisplay.setMovementMethod(new ScrollingMovementMethod());

        linearLStockPile = (LinearLayout) findViewById(R.id.lLStockPile);
        linearLComputerCapture = (LinearLayout) findViewById(R.id.lLComputerCapture);
        linearLComputerHand = (LinearLayout) findViewById(R.id.lLComputerHand);
        linearLHumanCapture = (LinearLayout) findViewById(R.id.lLHumanCapture);
        linearLHumanHand = (LinearLayout) findViewById(R.id.lLHumanHand);
        linearLLayout = (LinearLayout) findViewById(R.id.lLLayout);

        /* Get selection from first screen */
        loadGame = getIntent().getBooleanExtra("loadGame", false);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtMainDisplay.setText(round.help());

            }
        });

        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (round.getNextPlayer() == "Computer") {
                    txtMainDisplay.setText(round.moveComputer());

                }

                Display();

            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EndActivity.class);

                if (round.getHumanScore() > round.getComputerScore()) {

                    intent.putExtra("computerWon", 0);

                }

                else if (round.getComputerScore() > round.getHumanScore()) {
                    intent.putExtra("computerWon", 1);

                }

                else {

                    intent.putExtra("computerWon", 2);
                }
                startActivity(intent);



            }
        });

        btnSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Please enter a filename: ");

                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileName = input.getText().toString();
                        int check = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if(check != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                        }

                        txtMainDisplay.setText(round.saveGame(fileName));

                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        setUpGame();
    }

    private void setUpGame() {

        if(loadGame) {
            // Load the game
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please enter a filename: ");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fileName = input.getText().toString();
                    int check = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(check != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                    }
                    round = game.loadGame(fileName);
                    txtMainDisplay.setText("File successfully loaded");
                    Display();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

          }

        else {
            /* Start the game */
            round = game.startGame();
            Display();
        }

    }

    private void Display() {
        round.display();

        /* Disable help button if currently computer turn
        Enable when human turn
         */
        if (round.getNextPlayer() == "Computer") {
            btnHelp.setEnabled(false);
            btnMove.setEnabled(true);

        }
        else if (round.getNextPlayer() == "Human") {
            btnHelp.setEnabled(true);
            btnMove.setEnabled(false);

        }

        txtComputerScore.setText("Score: " + round.getComputerScore());
        txtHumanScore.setText("Score: " + round.getHumanScore());
        txtRound.setText("Round: " + round.getRound());
        txtNextPlayer.setText("Next Player: " + round.getNextPlayer());



        List<Card> stockPile = round.getDeck();
        linearLStockPile.removeAllViews();
        displayCards(linearLStockPile, stockPile, false);

        List<Card> humanHand = round.getHumanDeck();
        linearLHumanHand.removeAllViews();
        displayCards(linearLHumanHand, humanHand, true);

        List<Card> humanCapture = round.gethumanCaptureDeck();
        linearLHumanCapture.removeAllViews();
        displayCards(linearLHumanCapture, humanCapture, false);

        List<Card> computerHand = round.getComputerDeck();
        linearLComputerHand.removeAllViews();
        displayCards(linearLComputerHand, computerHand, false);

        List<Card> computerCapture = round.getComputerCaptureDeck();
        linearLComputerCapture.removeAllViews();
        displayCards(linearLComputerCapture, computerCapture, false);

        List<Card> layout = round.getLayoutDeck();
        linearLLayout.removeAllViews();
        displayCards(linearLLayout, layout, false);

        /* Num of cards left */
        txtComputerCardCount.setText("Cards: " + computerHand.size());
        txtHumanCardCount.setText("Cards: " + humanHand.size());
        txtLayoutCardCount.setText("Cards: " + layout.size());
        txtStockCardCount.setText(("Cards: " + stockPile.size()));

        /* If decks are empty ask to start another round */
        if (computerHand.size() == 0 && humanHand.size() == 0) {
            newRound();
        }

        else if (computerHand.size() == 0 && round.getNextPlayer() == "Computer") {
            txtMainDisplay.setText(round.checkHand());
            Display();
        }

        else if (humanHand.size() == 0 && round.getNextPlayer() == "Human") {
            txtMainDisplay.setText(round.checkHand());
            Display();
        }


    }


    /**
     * Display Cards
     * @param linearLayout, the layout to add the cards
     * @param hand, List<Card> of the hand
     * @param isHumanDeck only human hand cards can be clickable
     */

    private void displayCards(LinearLayout linearLayout, List<Card> hand, boolean isHumanDeck) {

        for (int i = 0; i < hand.size(); i++) {

            /* How the cards are displayed */
            imageView = new ImageView(this);
            LinearLayout.LayoutParams linearLParameters = new LinearLayout.LayoutParams(500, 350);
            imageView.setLayoutParams(linearLParameters);

            String str = hand.get(i).cardMatcher();
            imageView.setTag(str);

            if (str.toString().trim().equalsIgnoreCase("[")) {
                str = "lbracket";
            }
            else if (str.toString().trim().equalsIgnoreCase("]")) {
                str = "rbracket";
            }
            else {
                StringBuffer sbr = new StringBuffer(str);
                sbr.reverse();
                str = sbr.toString();
                str = str.toLowerCase();
            }
            int imageId = getResources().getIdentifier(str, "drawable", getPackageName());
            imageView.setImageResource(imageId);

            /* Don't allow cards to be clicked when computer turn */
            if (isHumanDeck && round.getNextPlayer() == "Human") {
                imageView.setClickable(true);
                imageView.setOnClickListener(this);
            }

            linearLayout.addView(imageView);

        }

    }

    /**
     * Start another round if decks are empty
     */
    public void newRound() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Would you like to play another round?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                round.clearDecks();
               round = game.startGame();
               Display();

            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, EndActivity.class);

                if (round.getHumanScore() > round.getComputerScore()) {

                    intent.putExtra("computerWon", 0);

                }

                else if (round.getComputerScore() > round.getHumanScore()) {
                    intent.putExtra("computerWon", 1);

                }

                else {

                    intent.putExtra("computerWon", 2);
                }
                startActivity(intent);


            }
        });

        builder.show();
    }




    /* Move for human */
    @Override
    public void onClick(View v) {

        String cardPicked = (String) v.getTag();
        txtMainDisplay.setText("The card chosen is: " + cardPicked);
        round.moveHuman(cardPicked);

        Display();

    }
}
