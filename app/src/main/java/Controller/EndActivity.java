package Controller;

import android.os.Bundle;
import com.example.gostopgui.R;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private int computerWon = -1;
    private Boolean tieGame = false;
    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        txtMessage = findViewById(R.id.txtMessage);
        computerWon = getIntent().getIntExtra("ComputerWon", -1);

        if (computerWon == 0) {
            txtMessage.setText("congratulations you won! :)");
        }
        else if (computerWon == 1) {
            txtMessage.setText("I am sorry the computer has won :(");
        }
        else {
            txtMessage.setText("Tie Game, good luck next time");
        }

    }
}