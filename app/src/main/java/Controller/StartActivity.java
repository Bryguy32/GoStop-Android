package Controller;

import android.content.Intent;
import android.os.Bundle;
import com.example.gostopgui.R;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class StartActivity extends AppCompatActivity  {
    private Button btnStartGame;
    private Button btnLoadGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartGame = (Button) findViewById(R.id.btnNewGame);
        btnLoadGame = (Button) findViewById(R.id.btnLoadGame);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("loadGame", false);
                startActivity(intent);

            }
        });

        btnLoadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("loadGame", true);
                startActivity(intent);


            }
        });
    }



}
