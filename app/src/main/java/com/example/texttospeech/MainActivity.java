package com.example.texttospeech;
import android.os.Bundle;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private EditText editText;
    private Button speedUpButton;
    private Button speedDownButton;
    private float speechSpeed = 1.0f;
    private Spinner languageSpinner;
    private ProgressBar progressBar;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        seekBar = findViewById(R.id.seekBar);

        textToSpeech = new TextToSpeech(this, this);
        editText = findViewById(R.id.editText);
        languageSpinner = findViewById(R.id.languageSpinner);
        speedUpButton = findViewById(R.id.speedUpButton);
        speedDownButton = findViewById(R.id.speedDownButton);


        Button convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTextToSpeech();
            }
        });



        speedUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustSpeechSpeed(true);
            }
        });

        speedDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustSpeechSpeed(false);
            }
        });
    }

    private void adjustSpeechSpeed(boolean speedUp) {
        if (textToSpeech != null) {
            if (speedUp) {
                speechSpeed += 0.1f; // Increase speed by 0.1
            } else {
                speechSpeed -= 0.1f; // Decrease speed by 0.1
            }

            // Ensure the speech speed is within valid limits (0.1 to 3.0)
            speechSpeed = Math.max(0.1f, Math.min(speechSpeed, 3.0f));

            // Set the updated speech speed
            textToSpeech.setSpeechRate(speechSpeed);
        }



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.languages,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setLanguage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

//    private void convertTextToSpeech() {
//        String text = editText.getText().toString();
//        if (!text.isEmpty()) {
//            showProgressBar(true); // Show progress bar
//
//            // Reset seek bar progress
//            seekBar.setProgress(0);
//
//            // Set up parameters for speech
//            HashMap<String, String> params = new HashMap<>();
//            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "speechId");
//
//            // Calculate the duration of speech in milliseconds (approximate)
//            int speechDuration = calculateSpeechDuration(text);
//
//            // Start speaking
//            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);
//
//            // Schedule updating SeekBar progress when speech ends
//            seekBar.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Hide progress bar when speech is completed
//                    showProgressBar(false);
//                }
//            }, speechDuration);
//        }
//    }

    private void convertTextToSpeech() {
        // ... (your existing code)
        // Set up parameters for speech
                String text = editText.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "speechId");

        // Calculate the duration of speech in milliseconds (approximate)
        int speechDuration = calculateSpeechDuration(text);

        // Start speaking with the specified speed
        textToSpeech.setSpeechRate(speechSpeed);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);

        // Schedule updating SeekBar progress when speech ends
        seekBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide progress bar when speech is completed
                showProgressBar(false);
            }
        }, speechDuration);
    }
    private int calculateSpeechDuration(String text) {
        // Assume average speech rate in words per minute
        int wordsPerMinute = 150;

        // Estimate the duration of speech in milliseconds
        int wordCount = text.split("\\s+").length;
        int speechDuration = (int) ((wordCount / (float) wordsPerMinute) * 60 * 1000);

        return speechDuration;
    }

    private void showProgressBar(final boolean show) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (show) {
                    // Show progress bar
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    // Hide progress bar
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setLanguage(int position) {
        Locale locale = Locale.getDefault(); // Default to device's locale (e.g., English)
        switch (position) {
            // Cases for different languages
            case 0:
                locale = new Locale("hi"); // Hindi

                break;
            case 1:
                locale = Locale.ENGLISH;
                break;
            case 2:
                locale = new Locale("es"); // Spanish
                break;
            case 3:
                locale = Locale.GERMAN;
                break;
            case 4:
                locale = Locale.ITALIAN;
                break;
            case 5:
                locale = new Locale("pt"); // Portuguese
                break;
            case 6:
                locale = new Locale("ru"); // Russian
                break;
            case 7:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case 8:
                locale = Locale.JAPANESE;
                break;
            case 9:
                locale = Locale.KOREAN;
                break;
            case 10:
                locale = new Locale("ar"); // Arabic
                break;
            case 11:
                locale = new Locale("tr"); // Turkish
                break;
            case 12:
                locale = new Locale("nl"); // Dutch
                break;
            case 13:
                locale = new Locale("sv"); // Swedish
                break;
            case 14:
                locale = new Locale("no"); // Norwegian
                break;
            case 15:
                locale = new Locale("da"); // Danish
                break;
            case 16:
                locale = new Locale("fi"); // Finnish
                break;
            case 17:
                locale = new Locale("el"); // Greek
                break;
            case 18:
                locale = new Locale("iw"); // Hebrew
                break;
            case 19:
                locale = Locale.FRENCH;

                break;
        }
        textToSpeech.setLanguage(locale);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // TTS engine initialized successfully

            // Set the OnUtteranceCompletedListener
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    // Not needed for this example
                }

                @Override
                public void onDone(String utteranceId) {
                    // Hide progress bar when speech is completed
                    showProgressBar(false);
                }

                @Override
                public void onError(String utteranceId) {
                    // Hide progress bar in case of an error
                    showProgressBar(false);
                }

//                @Override
                public void onProgress(String utteranceId, int progress) {
                    // Update SeekBar progress during speech
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(progress);
                        }
                    });
                }
            });
// Set the progress change listener for the SeekBar
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // You can update UI or perform actions based on seek bar progress if needed
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Not needed for this example
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Not needed for this example
                }
            });

            seekBar.setMax(textToSpeech.getMaxSpeechInputLength());

        } else {
            // Handle TTS initialization failure
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}


